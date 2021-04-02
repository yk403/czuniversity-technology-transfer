package com.itts.technologytransactionservice.service.cd.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsLyMapper;
import com.itts.technologytransactionservice.model.TJsLy;
import com.itts.technologytransactionservice.service.cd.JsLyAdminService;
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
 * @Description: 技术领域具体业务逻辑
 */
@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class JsLyAdminServiceImpl extends ServiceImpl<JsLyMapper, TJsLy> implements JsLyAdminService {
    @Autowired
    private JsLyMapper jsLyMapper;
    @Override
    public PageInfo page(Query query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TJsLy> list = jsLyMapper.list(query);
        PageInfo<TJsLy> page = new PageInfo<>(list);
        return page;
    }
}
