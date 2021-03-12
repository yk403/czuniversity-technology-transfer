package com.itts.technologytransactionservice.cd.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.technologytransactionservice.cd.mapper.TCdMapper;
import com.itts.technologytransactionservice.cd.model.TCd;
import com.itts.technologytransactionservice.cd.service.TCdService;
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

    @Override
    public List<TCd> getList() {

        QueryWrapper<TCd> query = new QueryWrapper<>();
        List<TCd> list = mapper.selectList(query);

        return list;
    }

}
