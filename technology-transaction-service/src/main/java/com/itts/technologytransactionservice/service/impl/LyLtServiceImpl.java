package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsLbMapper;
import com.itts.technologytransactionservice.mapper.LyLtMapper;
import com.itts.technologytransactionservice.model.LyLt;
import com.itts.technologytransactionservice.model.TJsLb;
import com.itts.technologytransactionservice.service.JsLbService;
import com.itts.technologytransactionservice.service.LyLtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Primary
public class LyLtServiceImpl extends ServiceImpl<LyLtMapper, LyLt> implements LyLtService {
    @Autowired
    private LyLtMapper lyLtMapper;

    @Override
    public PageInfo page(Query query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<LyLt> list = lyLtMapper.list(query);
        PageInfo<LyLt> page = new PageInfo<>(list);
        return page;
    }

}
