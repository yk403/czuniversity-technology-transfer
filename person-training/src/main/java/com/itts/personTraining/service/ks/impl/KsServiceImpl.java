package com.itts.personTraining.service.ks.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.dto.KsDTO;
import com.itts.personTraining.dto.KsExpDTO;
import com.itts.personTraining.mapper.szKsExp.SzKsExpMapper;
import com.itts.personTraining.model.ks.Ks;
import com.itts.personTraining.mapper.ks.KsMapper;
import com.itts.personTraining.model.ksExp.KsExp;
import com.itts.personTraining.model.szKsExp.SzKsExp;
import com.itts.personTraining.service.ks.KsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.service.ksExp.KsExpService;
import com.itts.personTraining.service.szKsExp.SzKsExpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 * 考试表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-05-06
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class KsServiceImpl extends ServiceImpl<KsMapper, Ks> implements KsService {

    @Autowired
    private KsService ksService;
    @Resource
    private KsMapper ksMapper;
    @Autowired
    private SzKsExpService szKsExpService;
    @Resource
    private SzKsExpMapper szKsExpMapper;
    @Autowired
    private KsExpService ksExpService;

    /**
     * 查询考试列表
     * @param pageNum
     * @param pageSize
     * @param kslx
     * @param pcId
     * @param pclx
     * @param kcmc
     * @return
     */
    @Override
    public PageInfo<KsDTO> findByPage(Integer pageNum, Integer pageSize, String kslx, Long pcId, String pclx, String kcmc) {
        log.info("【人才培养 - 分页条件查询考试列表,考试类型:{},批次id:{},批次类型:{},课程名称:{}】",kslx,pcId,pclx,kcmc);
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(ksMapper.findByPage(kslx,pcId,pclx,kcmc));
    }

    /**
     * 根据id查询考试详情
     * @param id
     * @return
     */
    @Override
    public KsDTO get(Long id) {
        log.info("【人才培养 - 根据id:{}查询考试详情】",id);
        KsDTO ksDTO = ksMapper.findById(id);
        //ksDTO.setSzIds(szKsMapper.selectByKsId(id));
        return ksDTO;
    }



    /**
     * 新增考试
     * @param ksDTO
     * @return
     */
    @Override
    public boolean add(KsDTO ksDTO) {
        log.info("【人才培养 - 新增考试:{}】",ksDTO);
        Long userId = getUserId();
        Ks ks = new Ks();
        ksDTO.setCjr(userId);
        ksDTO.setGxr(userId);
        BeanUtils.copyProperties(ksDTO,ks);
        if (ksService.save(ks)) {
            Long ksId = ks.getId();
            List<KsExpDTO> ksExpDTOs = ksDTO.getKsExps();
            for (KsExpDTO ksExpDTO : ksExpDTOs) {
                KsExp ksExp = new KsExp();
                ksExpDTO.setKsId(ksId);
                ksExpDTO.setCjr(userId);
                ksExpDTO.setGxr(userId);
                BeanUtils.copyProperties(ksExpDTO,ksExp);
                if (ksExpService.save(ksExp)) {
                    if (saveSzKs(ksExpDTO.getSzIds(),ksExp)) {
                        continue;
                    }
                    return false;
                }
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 更新考试
     * @param ksDTO
     * @return
     */
    @Override
    public boolean update(KsDTO ksDTO) {
        log.info("【人才培养 - 更新考试:{}】",ksDTO);
        Ks ks = new Ks();
        ksDTO.setGxr(getUserId());
        BeanUtils.copyProperties(ksDTO,ks);
        /*if (ksService.updateById(ks)) {
            List<Long> szIds = ksDTO.getSzIds();
            if (szIds != null) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("ks_id",ksDTO.getId());
                if (szKsService.removeByMap(map)) {
                    return saveSzKs(szIds,ks);
                }
                return false;
            }
            return false;
        }*/
        return false;
    }

    /**
     * 删除考试
     * @param ksDTO
     * @return
     */
    @Override
    public boolean delete(KsDTO ksDTO) {
        log.info("【人才培养 - 删除考试:{}】",ksDTO);
        //设置删除状态
        ksDTO.setSfsc(true);
        ksDTO.setGxr(getUserId());
        Ks ks = new Ks();
        BeanUtils.copyProperties(ksDTO,ks);
        if (ksService.updateById(ks)) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ks_id",ks.getId());
            return szKsExpService.removeByMap(map);
        }
        return false;
    }

    /**
     * 考试批量下发
     * @param ids
     * @return
     */
    @Override
    public boolean issueBatch(List<Long> ids) {
        log.info("【人才培养 - 考试批量下发,ids:{}】",ids);
        List<Ks> ksList = ksMapper.selectBatchIds(ids);
        Long userId = getUserId();
        for (Ks ks : ksList) {
            ks.setGxr(userId);
            ks.setSfxf(true);
        }
        return ksService.updateBatchById(ksList);
    }

    /**
     * 获取当前用户id
     * @return
     */
    private Long getUserId() {
        LoginUser loginUser = threadLocal.get();
        Long userId;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        } else {
            throw new ServiceException(GET_THREADLOCAL_ERROR);
        }
        return userId;
    }

    /**
     * 新增师资考试
     * @param szIds
     * @param ksExp
     * @return
     */
    private boolean saveSzKs(List<Long> szIds, KsExp ksExp) {
        List<SzKsExp> szKsExpList = new ArrayList<>();
        for (Long szId : szIds) {
            SzKsExp szKsExp = new SzKsExp();
            szKsExp.setSzId(szId);
            szKsExp.setKsExpId(ksExp.getId());
            szKsExpList.add(szKsExp);
        }
        return szKsExpService.saveBatch(szKsExpList);
    }
}
