package com.itts.personTraining.service.kcSz.impl;

import com.itts.personTraining.model.kcSz.KcSz;
import com.itts.personTraining.mapper.kcSz.KcSzMapper;
import com.itts.personTraining.service.kcSz.KcSzService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 课程师资关系表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-04-25
 */
@Service
public class KcSzServiceImpl extends ServiceImpl<KcSzMapper, KcSz> implements KcSzService {

    @Autowired
    private KcSzService kcSzService;

}
