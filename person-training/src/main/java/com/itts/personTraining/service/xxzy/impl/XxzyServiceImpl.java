package com.itts.personTraining.service.xxzy.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.personTraining.mapper.xxzy.XxzyMapper;
import com.itts.personTraining.model.xxzy.Xxzy;
import com.itts.personTraining.request.xxzy.AddXxzyRequest;
import com.itts.personTraining.service.xxzy.XxzyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 学习资源 服务实现类
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-12
 */
@Service
public class XxzyServiceImpl extends ServiceImpl<XxzyMapper, Xxzy> implements XxzyService {

    @Autowired
    private XxzyMapper xxzyMapper;

    /**
     * 获取列表 - 分页
     */
    @Override
    public PageInfo<Xxzy> list(Integer pageNum, Integer pageSize, String type,
                               String firstCategory, String secondCategory, Long courseId, String condition) {

        PageHelper.startPage(pageNum, pageSize);

        QueryWrapper query = new QueryWrapper();

        query.eq("zylb", type);
        query.eq("sfsc", false);

        if (StringUtils.isNotBlank(firstCategory)) {
            query.eq("zyyjfl", firstCategory);
        }

        if (StringUtils.isNotBlank(secondCategory)) {
            query.eq("zyejfl", secondCategory);
        }

        if (courseId != null) {
            query.eq("kc_id", courseId);
        }

        if (StringUtils.isNotBlank(condition)) {
            query.like("mc", condition);
        }

        List xxzys = xxzyMapper.selectList(query);

        PageInfo pageInfo = new PageInfo(xxzys);

        return pageInfo;
    }

    /**
     * 新增
     */
    @Override
    public Xxzy add(AddXxzyRequest addXxzyRequest) {

        LoginUser loginUser = SystemConstant.threadLocal.get();

        Long userId = null;
        if(loginUser != null){
            userId = loginUser.getUserId();
        }
        
        Xxzy xxzy = new Xxzy();
        BeanUtils.copyProperties(addXxzyRequest, xxzy);

        Date now = new Date();

        xxzy.setCjr(userId);
        xxzy.setGxr(userId);
        xxzy.setCjsj(now);
        xxzy.setGxsj(now);

        xxzyMapper.insert(xxzy);
        return xxzy;
    }
}
