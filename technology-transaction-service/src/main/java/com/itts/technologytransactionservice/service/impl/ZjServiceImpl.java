package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.ZjMapper;
import com.itts.technologytransactionservice.model.TZj;
import com.itts.technologytransactionservice.service.ZjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Primary
public class ZjServiceImpl extends ServiceImpl<ZjMapper, TZj> implements ZjService {
    @Autowired
    private ZjMapper zjMapper;

    @Override
    public IPage page(Query query) {
        Page<TZj> p = new Page<>(query.getPageNum(), query.getPageSize());
        List<TZj> list = zjMapper.list(p, query);
        p.setRecords(list);
        return p;
    }

}
