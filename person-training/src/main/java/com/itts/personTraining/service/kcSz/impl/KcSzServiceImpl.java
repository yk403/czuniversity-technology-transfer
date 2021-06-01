package com.itts.personTraining.service.kcSz.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.personTraining.model.kcSz.KcSz;
import com.itts.personTraining.mapper.kcSz.KcSzMapper;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.service.kcSz.KcSzService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程师资关系表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-04-25
 */
@Service
@Slf4j
@Transactional
public class KcSzServiceImpl extends ServiceImpl<KcSzMapper, KcSz> implements KcSzService {

    @Autowired
    private KcSzService kcSzService;
    @Resource
    private KcSzMapper kcSzMapper;

    /**
     * 根据kcId查询师资ids
     * @param kcId
     * @return
     */
    @Override
    public List<Sz> get(Long kcId) {
        log.info("【人才培养 - 根据kcId:{}查询师资ids】", kcId);
        List<Sz> szList = kcSzMapper.getByKcId(kcId);
        return szList;
    }
}
