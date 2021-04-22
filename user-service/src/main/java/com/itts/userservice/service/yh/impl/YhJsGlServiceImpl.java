package com.itts.userservice.service.yh.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.userservice.mapper.js.JsMapper;
import com.itts.userservice.mapper.yh.YhJsGlMapper;
import com.itts.userservice.model.js.Js;
import com.itts.userservice.model.yh.YhJsGl;
import com.itts.userservice.service.yh.YhJsGlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author fl
 * @since 2021-03-24
 */
@Service
public class YhJsGlServiceImpl implements YhJsGlService {

    @Resource
    private YhJsGlMapper yhJsGlMapper;

    @Resource
    private JsMapper jsMapper;


    @Override
    public List<Long> fingByYhid(Long id) {
        QueryWrapper<YhJsGl> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("yh_id",id)
                .eq("sfsc",false);
        List<YhJsGl> yhJsGls = yhJsGlMapper.selectList(QueryWrapper);
        List<Long> collect = yhJsGls.stream().map(YhJsGl::getJsId).collect(Collectors.toList());
        return collect;
    }
}
