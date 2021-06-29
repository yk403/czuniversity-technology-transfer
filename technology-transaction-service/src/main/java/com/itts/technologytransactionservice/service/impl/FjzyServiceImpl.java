package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.FjzyMapper;
import com.itts.technologytransactionservice.mapper.JsBmMapper;
import com.itts.technologytransactionservice.model.Fjzy;
import com.itts.technologytransactionservice.model.TJsBm;
import com.itts.technologytransactionservice.service.FjzyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 附件资源表 服务实现类
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-21
 */
@Service
public class FjzyServiceImpl extends ServiceImpl<FjzyMapper, Fjzy> implements FjzyService {
    @Autowired
    private FjzyMapper fjzyMapper;
    @Override
    public PageInfo page(Query query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<Fjzy> list = fjzyMapper.list(query);
        PageInfo<Fjzy> page = new PageInfo<>(list);
        return page;
    }
}
