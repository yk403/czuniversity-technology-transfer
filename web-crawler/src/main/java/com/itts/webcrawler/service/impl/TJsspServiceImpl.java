package com.itts.webcrawler.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.webcrawler.mapper.TJsspMapper;
import com.itts.webcrawler.model.TJssp;
import com.itts.webcrawler.service.TJsspService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/*
 *@Auther: yukai
 *@Date: 2021/07/12/16:53
 *@Desription:
 */
@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class TJsspServiceImpl extends ServiceImpl<TJsspMapper, TJssp> implements TJsspService {
    @Autowired
    private TJsspMapper tJsspMapper;
    @Override
    public PageInfo findTJssp(Map<String, Object> params) {
        log.info("【技术交易 - 分页条件查询(前台)】");
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TJssp> list = tJsspMapper.findTJssp(query);
        return new PageInfo<>(list);
    }
}
