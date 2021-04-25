package com.itts.personTraining.service.xyKc.impl;

import com.itts.personTraining.model.xyKc.XyKc;
import com.itts.personTraining.mapper.xyKc.XyKcMapper;
import com.itts.personTraining.service.xyKc.XyKcService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.ws.ResponseWrapper;

/**
 * <p>
 * 学院课程关系表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-04-23
 */
@Service
@Slf4j
@Transactional
public class XyKcServiceImpl extends ServiceImpl<XyKcMapper, XyKc> implements XyKcService {

    @Resource
    private XyKcService xyKcService;

    /**
     * 新增学院课程关系
     * @param xyKc
     * @return
     */
    @Override
    public boolean add(XyKc xyKc) {
        log.info("【人才培养 - 新增学院课程关系:{}】",xyKc);
        return xyKcService.save(xyKc);
    }
}
