package com.itts.personTraining.service.ksExp.impl;

import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.dto.KsExpDTO;
import com.itts.personTraining.mapper.szKsExp.SzKsExpMapper;
import com.itts.personTraining.model.ksExp.KsExp;
import com.itts.personTraining.mapper.ksExp.KsExpMapper;
import com.itts.personTraining.model.szKsExp.SzKsExp;
import com.itts.personTraining.service.ksExp.KsExpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * 考试扩展表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-05-13
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class KsExpServiceImpl extends ServiceImpl<KsExpMapper, KsExp> implements KsExpService {

    @Autowired
    private KsExpService ksExpService;
    @Resource
    private KsExpMapper ksExpMapper;
    @Autowired
    private SzKsExpService szKsExpService;
    @Resource
    private SzKsExpMapper szKsExpMapper;


    /**
     * 根据条件查询考试扩展信息
     * @param id
     * @param ksId
     * @return
     */
    @Override
    public List<KsExpDTO> get(Long id,Long ksId) {
        log.info("【人才培养 - 根据考试扩展id:{},考试id:{}查询考试扩展信息】",id,ksId);
        List<KsExpDTO> ksExpDTOs = ksExpMapper.findByCondition(id,ksId);
        for (KsExpDTO ksExpDTO : ksExpDTOs) {
            List<Long> szIds = szKsExpMapper.selectByKsExpId(ksExpDTO.getId());
            ksExpDTO.setSzIds(szIds);
        }
        return ksExpDTOs;
    }

    /**
     * 根据考试扩展id删除考试扩展信息
     * @param ksExpDTO
     * @return
     */
    @Override
    public boolean delete(KsExpDTO ksExpDTO) {
        log.info("【人才培养 - 根据考试扩展id:{}删除考试扩展信息】",ksExpDTO.getId());
        ksExpDTO.setSfsc(true);
        ksExpDTO.setGxr(getUserId());
        KsExp ksExp = new KsExp();
        BeanUtils.copyProperties(ksExpDTO,ksExp);
        if (ksExpService.updateById(ksExp)) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ks_exp_id",ksExp.getId());
            return szKsExpService.removeByMap(map);
        }
        return false;
    }

    /**
     * 更新考试扩展信息
     * @param ksExpDTO
     * @return
     */
    @Override
    public boolean update(KsExpDTO ksExpDTO) {
        log.info("【人才培养 - 更新考试扩展信息:{}】",ksExpDTO);
        KsExp ksExp = new KsExp();
        ksExpDTO.setGxr(getUserId());
        BeanUtils.copyProperties(ksExpDTO,ksExp);
        if (ksExpService.updateById(ksExp)) {
            List<Long> szIds = ksExpDTO.getSzIds();
            if (szIds != null || szIds.size() > 0) {
                HashMap<String, Object> map = new HashMap<>();
                Long ksExpId = ksExpDTO.getId();
                map.put("ks_exp_id",ksExpId);
                if (szKsExpService.removeByMap(map)) {
                    return saveSzKsExp(szIds,ksExpId);
                }
                return false;
            }
            return false;
        }
        return false;
    }

    /**
     * 根据条件查询考试扩展信息(继续教育)
     * @param id
     * @param ksId
     * @return
     */
    @Override
    public List<KsExpDTO> getByCondition(Long id, Long ksId) {
        log.info("【人才培养 - 根据考试扩展id:{},考试id:{}查询考试扩展信息(继续教育)】",id,ksId);
        List<KsExpDTO> ksExpDTOs = ksExpMapper.getByCondition(id,ksId);
        return ksExpDTOs;
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
     * 新增师资考试扩展
     * @param szIds
     * @param ksExpId
     * @return
     */
    private boolean saveSzKsExp(List<Long> szIds, Long ksExpId) {
        List<SzKsExp> szKsExpList = new ArrayList<>();
        for (Long szId : szIds) {
            SzKsExp szKsExp = new SzKsExp();
            szKsExp.setSzId(szId);
            szKsExp.setKsExpId(ksExpId);
            szKsExpList.add(szKsExp);
        }
        return szKsExpService.saveBatch(szKsExpList);
    }

}
