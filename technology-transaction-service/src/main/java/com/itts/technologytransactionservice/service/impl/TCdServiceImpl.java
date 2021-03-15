package com.itts.technologytransactionservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.ResponseUtil;
import com.itts.technologytransactionservice.feign.payment.PaymentService;
import com.itts.technologytransactionservice.mapper.TCdMapper;
import com.itts.technologytransactionservice.model.TCd;
import com.itts.technologytransactionservice.service.TCdService;
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
    public PageInfo<TCd> getByPage(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<TCd> query = new QueryWrapper<>();
        List<TCd> list = mapper.selectList(query);

        PageInfo<TCd> page = new PageInfo<>(list);
        return page;
    }

    @Override
    public ResponseUtil testFeign() {

        ResponseUtil test = paymentService.test();
        return test;

    }


}
