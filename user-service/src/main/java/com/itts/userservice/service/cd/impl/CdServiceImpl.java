package com.itts.userservice.service.cd.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.userservice.mapper.cd.CdMapper;
import com.itts.userservice.model.cd.Cd;
import com.itts.userservice.service.cd.CdService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author fl
 * @since 2021-03-24
 */
@Service
public class CdServiceImpl implements CdService {

    @Resource
    private CdMapper cdMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取列表 - 分页
     */
    @Override
    public PageInfo<Cd> findByPage(Integer pageNum, Integer pageSize, String name, String systemType, String modelType) {

        PageHelper.startPage(pageNum, pageSize);

        QueryWrapper<Cd> query = new QueryWrapper<>();

        query.eq("sfsc", false);
        if (StringUtils.isNotBlank(name)) {
            query.like("cdmc", name);
        }
        if (StringUtils.isNotBlank(systemType)) {
            query.eq("xtlx", systemType);
        }
        if (StringUtils.isNotBlank(modelType)) {
            query.eq("mklx", modelType);
        }

        List<Cd> cd = cdMapper.selectList(query);
        PageInfo<Cd> tJsPageInfo = new PageInfo<>(cd);

        return tJsPageInfo;
    }

    /**
     * 获取详情
     */
    @Override
    public Cd get(Long id) {

        Cd cd = cdMapper.selectById(id);
        return cd;
    }

    /**
     * 新增
     */
    @Override
    public Cd add(Cd cd) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser != null) {
            cd.setCjr(loginUser.getUserId());
            cd.setGxr(loginUser.getUserId());
        }

        Date now = new Date();
        cd.setCjsj(now);
        cd.setGxsj(now);

        //设置层级
        if (cd.getFjcdId() == 0L) {
            cd.setCj(cd.getCdbm());
        } else {

            Cd fjcd = cdMapper.selectById(cd.getFjcdId());
            cd.setCj(fjcd.getCj() + "-" + cd.getCdbm());
        }

        cdMapper.insert(cd);
        return cd;
    }

    /**
     * 更新
     */
    @Override
    public Cd update(Cd cd) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser != null) {
            cd.setGxr(loginUser.getUserId());
        }
        cd.setGxsj(new Date());

        //设置层级
        if (cd.getFjcdId() == 0L) {
            cd.setCj(cd.getCdbm());
        } else {

            Cd fjcd = cdMapper.selectById(cd.getFjcdId());
            cd.setCj(fjcd.getCj() + "-" + cd.getCdbm());
        }

        cdMapper.updateById(cd);
        return cd;
    }
}
