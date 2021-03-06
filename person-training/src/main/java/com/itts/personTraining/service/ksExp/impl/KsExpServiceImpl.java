package com.itts.personTraining.service.ksExp.impl;

import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.dto.KsExpDTO;
import com.itts.personTraining.enums.EduTypeEnum;
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
import static com.itts.personTraining.enums.EduTypeEnum.ACADEMIC_DEGREE_EDUCATION;
import static com.itts.personTraining.enums.EduTypeEnum.ADULT_EDUCATION;

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
    public List<KsExpDTO> get(Long id,Long ksId,String jylx) {
        log.info("【人才培养 - 根据考试扩展id:{},考试id:{},教育类型:{}查询考试扩展信息】",id,ksId,jylx);
        KsExp ksExp = ksExpMapper.selectById(id);
        List<KsExpDTO> ksExpDTOs;
        if (jylx.equals(ACADEMIC_DEGREE_EDUCATION.getKey())) {
            //学位学历教育
            ksExpDTOs = ksExpMapper.findByCondition(id,ksId);
            for (KsExpDTO ksExpDTO : ksExpDTOs) {
                List<Long> szIds = szKsExpMapper.selectByKsExpId(ksExpDTO.getId());
                ksExpDTO.setSzIds(szIds);
            }
        } else {
            //继续教育
            ksExpDTOs =ksExpMapper.getByCondition(id,ksId);
        }
        return ksExpDTOs;
    }

    /**
     * 根据考试扩展id删除考试扩展信息
     * @param ksExp
     * @return
     */
    @Override
    public boolean delete(KsExp ksExp) {
        log.info("【人才培养 - 根据考试扩展id:{}删除考试扩展信息】",ksExp.getId());
        ksExp.setSfsc(true);
        ksExp.setGxr(getUserId());
        if (ksExpService.updateById(ksExp)) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ks_exp_id",ksExp.getId());
            return szKsExpService.removeByMap(map);
        }
        return false;
    }

    /**
     * 更新考试扩展信息
     * @param ksExpDTOs
     * @return
     */
    @Override
    public boolean update(List<KsExpDTO> ksExpDTOs, String jylx) {
        log.info("【人才培养 - 更新考试扩展信息:{},id:{},教育类型:{}】", ksExpDTOs, jylx);
        if (ACADEMIC_DEGREE_EDUCATION.getKey().equals(jylx)) {
            for (KsExpDTO ksExpDTO : ksExpDTOs) {
                //学位学历教育
                KsExp ksExp = new KsExp();
                ksExpDTO.setGxr(getUserId());
                BeanUtils.copyProperties(ksExpDTO, ksExp);
                ksExpService.updateById(ksExp);
                List<Long> szIds = ksExpDTO.getSzIds();
                if (szIds != null && szIds.size() > 0) {
                    HashMap<String, Object> map = new HashMap<>();
                    Long ksExpId = ksExpDTO.getId();
                    map.put("ks_exp_id", ksExpId);
                    szKsExpService.removeByMap(map);
                    saveSzKsExp(szIds, ksExpId);
                }
                continue;
            }
            return true;
        } else {
            //继续教育
            List<KsExp> ksExps = new ArrayList<>();
            for (KsExpDTO ksExpDTO : ksExpDTOs) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("ks_id", ksExpDTO.getKsId());
                ksExpService.removeByMap(map);
                KsExp ksExp1 = new KsExp();
                ksExp1.setCjr(getUserId());
                ksExp1.setGxr(getUserId());
                ksExp1.setKsId(ksExpDTO.getKsId());
                ksExp1.setKcId(ksExpDTO.getKcId());
                ksExps.add(ksExp1);
            }
            return ksExpService.saveBatch(ksExps);
        }
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
