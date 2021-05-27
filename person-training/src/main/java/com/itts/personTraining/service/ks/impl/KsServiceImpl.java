package com.itts.personTraining.service.ks.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.dto.KsDTO;
import com.itts.personTraining.dto.KsExpDTO;
import com.itts.personTraining.mapper.ksExp.KsExpMapper;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.mapper.szKs.SzKsMapper;
import com.itts.personTraining.mapper.szKsExp.SzKsExpMapper;
import com.itts.personTraining.model.ks.Ks;
import com.itts.personTraining.mapper.ks.KsMapper;
import com.itts.personTraining.model.ksExp.KsExp;
import com.itts.personTraining.model.ksXs.KsXs;
import com.itts.personTraining.model.szKs.SzKs;
import com.itts.personTraining.model.szKsExp.SzKsExp;
import com.itts.personTraining.service.ks.KsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.service.ksExp.KsExpService;
import com.itts.personTraining.service.ksXs.KsXsService;
import com.itts.personTraining.service.szKs.SzKsService;
import com.itts.personTraining.service.szKsExp.SzKsExpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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
    @Resource
    private KsExpMapper ksExpMapper;
    @Autowired
    private SzKsService szKsService;
    @Autowired
    private KsXsService ksXsService;
    @Resource
    private SzKsMapper szKsMapper;
    @Resource
    private PcXsMapper pcXsMapper;

    /**
     * 查询考试列表
     * @param pageNum
     * @param pageSize
     * @param pcId
     * @param ksmc
     * @param kslx
     * @return
     */
    @Override
    public PageInfo<KsDTO> findByPage(Integer pageNum, Integer pageSize, Long pcId, String ksmc, String kslx) {
        log.info("【人才培养 - 分页条件查询考试列表,,批次id:{},考试名称:{},考试类型:{}】",pcId,ksmc,kslx);
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(ksMapper.findByPage(pcId,ksmc,kslx));
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
        if (ksDTO != null) {
            List<Long> szIds = szKsMapper.getByKsId(ksDTO.getId());
            ksDTO.setSzIds(szIds);
            List<KsExpDTO> ksExpDTOs = ksExpMapper.findByCondition(null,ksDTO.getId());
            for (KsExpDTO ksExpDTO : ksExpDTOs) {
                List<Long> expsSzIds = szKsExpMapper.selectByKsExpId(ksExpDTO.getId());
                ksExpDTO.setSzIds(expsSzIds);
            }
            ksDTO.setKsExps(ksExpDTOs);
        }
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
            List<Long> szIds = ksDTO.getSzIds();
            if (szIds != null && szIds.size() > 0) {
                saveSzKs(ks, szIds);
            }
            List<KsExpDTO> ksExpDTOs = ksDTO.getKsExps();
            for (KsExpDTO ksExpDTO : ksExpDTOs) {
                KsExp ksExp = new KsExp();
                ksExpDTO.setKsId(ksId);
                ksExpDTO.setCjr(userId);
                ksExpDTO.setGxr(userId);
                BeanUtils.copyProperties(ksExpDTO,ksExp);
                if (ksExpService.save(ksExp)) {
                    List<Long> expszIds = ksExpDTO.getSzIds();
                    if (expszIds != null && expszIds.size() > 0) {
                        saveSzKs(expszIds,ksExp);
                        continue;
                    }
                    continue;
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
        if (ksService.updateById(ks)) {
            List<Long> szIds = ksDTO.getSzIds();
            if (szIds != null && szIds.size() > 0) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("ks_id",ksDTO.getId());
                if (szKsService.removeByMap(map)) {
                    return saveSzKs(ks, szIds);
                }
                return false;
            }
        }
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
        Long userId = getUserId();
        //设置删除状态
        ksDTO.setSfsc(true);
        ksDTO.setGxr(userId);
        Ks ks = new Ks();
        BeanUtils.copyProperties(ksDTO,ks);
        List<Long> szIds = ksDTO.getSzIds();
        if (szIds != null && szIds.size() > 0) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ks_id",ksDTO.getId());
            return szKsService.removeByMap(map);
        }
        if (ksService.updateById(ks)) {
            List<KsExpDTO> ksExpDTOs = ksExpService.get(null, ksDTO.getId());
            List<KsExp> ksExps = getKsExps(userId, ksExpDTOs);
            if (ksExpService.updateBatchById(ksExps)) {
                for (KsExpDTO ksExpDTO : ksExpDTOs) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("ks_exp_id",ksExpDTO.getId());
                    if (szKsExpService.removeByMap(map)) {
                        continue;
                    }
                    return false;
                }
                return true;
            }
            return false;
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
            ks.setXfsj(new Date());
        }
        if (ksService.updateBatchById(ksList)) {
            for (Long id : ids) {
                //根据考试id查询批次id
                Ks ks = ksService.getById(id);
                Long pcId = ks.getPcId();
                List<Long> xsIds = pcXsMapper.selectByPcId(pcId);
                List<KsXs> ksXsList = new ArrayList<>();
                for (Long xsId : xsIds) {
                    KsXs ksXs = new KsXs();
                    ksXs.setXsId(xsId);
                    ksXs.setKsId(id);
                    ksXsList.add(ksXs);
                }
                return ksXsService.saveBatch(ksXsList);
            }
        }
        return false;
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

    /**
     * 获取考试扩展集合信息
     * @param userId
     * @param ksExpDTOS
     * @return
     */
    private List<KsExp> getKsExps(Long userId, List<KsExpDTO> ksExpDTOS) {
        List<KsExp> ksExps = new ArrayList<>();
        for (KsExpDTO ksExpDTO : ksExpDTOS) {
            KsExp ksExp = new KsExp();
            ksExpDTO.setGxr(userId);
            ksExpDTO.setSfsc(true);
            BeanUtils.copyProperties(ksExpDTO,ksExp);
            ksExps.add(ksExp);
        }
        return ksExps;
    }

    /**
     * 新增师资考试(监考老师)
     * @param ksDTO
     * @param szIds
     * @return
     */
    private boolean saveSzKs(Ks ks, List<Long> szIds) {
        List<SzKs> szKsList = new ArrayList<>();
        for (Long szId : szIds) {
            SzKs szKs = new SzKs();
            szKs.setSzId(szId);
            szKs.setKsId(ks.getId());
            szKsList.add(szKs);
        }
        return szKsService.saveBatch(szKsList);
    }

}
