package com.itts.technologytransactionservice.service.cd.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.ResponseUtil;
import com.itts.technologytransactionservice.feign.payment.PaymentService;
import com.itts.technologytransactionservice.mapper.TCdMapper;
import com.itts.technologytransactionservice.model.TCd;
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

    /**
     * 获取列表 - 分页
     */
    @Override
    public PageInfo<TCd> findByPage(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<TCd> query = new QueryWrapper<>();
        List<TCd> list = mapper.selectList(query);

        PageInfo<TCd> page = new PageInfo<>(list);
        return page;
    }

    /**
     * 获取详情
     */
    @Override
    public TCd get(Long id) {
        TCd tcd = mapper.selectById(id);
        return tcd;
    }

    /**
     * 新增
     */
    @Override
    public TCd add(TCd tCd) {

        mapper.insert(tCd);
        return tCd;
    }

    /**
     * 更新
     */
    @Override
    public TCd update(TCd tCd) {
        mapper.updateById(tCd);
        return tCd;
    }

    @Override
    public ResponseUtil testFeign() {

        ResponseUtil test = paymentService.test();
        return test;

    }


}
