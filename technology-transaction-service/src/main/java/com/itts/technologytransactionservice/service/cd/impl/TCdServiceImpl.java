package com.itts.technologytransactionservice.service.cd.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.common.utils.ResponseUtil;
import com.itts.technologytransactionservice.feign.payment.PaymentService;
import com.itts.technologytransactionservice.mapper.cd.TCdMapper;
import com.itts.technologytransactionservice.model.cd.TCd;
import com.itts.technologytransactionservice.service.cd.TCdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lym
 * @since 2021-03-12
 */
@Service
@Primary
public class TCdServiceImpl implements TCdService {

    @Resource
    private TCdMapper mapper;

    @Autowired
    private PaymentService paymentService;

    @Override
    public List<TCd> getList() {

        QueryWrapper<TCd> query = new QueryWrapper<>();
        List<TCd> list = mapper.selectList(query);

        return list;
    }

    @Override
    public ResponseUtil testFeign() {

        ResponseUtil test = paymentService.test();
        return test;

    }


}
