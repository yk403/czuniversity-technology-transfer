package com.itts.technologytransactionservice.service.cd.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsLbMapper;
import com.itts.technologytransactionservice.model.TJsLb;
import com.itts.technologytransactionservice.service.cd.JsLbAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Austin
 * @Data: 2021/3/31
 * @Version: 1.0.0
 * @Description: 技术类别业务逻辑
 */
@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class JsLbAdminServiceImpl extends ServiceImpl<JsLbMapper, TJsLb> implements JsLbAdminService {
    @Autowired
    private JsLbMapper jsLbMapper;
    @Override
    public PageInfo page(Query query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TJsLb> list = jsLbMapper.list(query);
        PageInfo<TJsLb> page = new PageInfo<>(list);
        return page;
    }
}
