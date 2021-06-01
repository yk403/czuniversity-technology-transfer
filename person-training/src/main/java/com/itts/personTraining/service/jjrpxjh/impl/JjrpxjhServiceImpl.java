package com.itts.personTraining.service.jjrpxjh.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.personTraining.mapper.jjrpxjh.JjrpxjhMapper;
import com.itts.personTraining.model.jjrpxjh.Jjrpxjh;
import com.itts.personTraining.request.jjrpxjh.AddJjrpxjhRequest;
import com.itts.personTraining.request.jjrpxjh.UpdateJjrpxjhRequest;
import com.itts.personTraining.service.jjrpxjh.JjrpxjhService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 经纪人培训计划表 服务实现类
 * </p>
 *
 * @author liuyingming
 * @since 2021-06-01
 */
@Service
public class JjrpxjhServiceImpl extends ServiceImpl<JjrpxjhMapper, Jjrpxjh> implements JjrpxjhService {

    @Autowired
    private JjrpxjhMapper jjrpxjhMapper;

    /**
     * 新增
     */
    @Override
    public Jjrpxjh add(AddJjrpxjhRequest addJjrpxjhRequest) {

        Jjrpxjh jjrpxjh = new Jjrpxjh();
        BeanUtils.copyProperties(addJjrpxjhRequest, jjrpxjh);

        Date now = new Date();

        LoginUser loginUser = SystemConstant.threadLocal.get();
        Long userId = null;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        jjrpxjh.setCjr(userId);
        jjrpxjh.setGxr(userId);
        jjrpxjh.setCjsj(now);
        jjrpxjh.setGxsj(now);

        jjrpxjhMapper.insert(jjrpxjh);
        return jjrpxjh;
    }

    /**
     * 更新
     */
    @Override
    public Jjrpxjh update(Jjrpxjh old, UpdateJjrpxjhRequest updateJjrpxjhRequest) {

        BeanUtils.copyProperties(updateJjrpxjhRequest, old, "id", "cjsj", "cjr", "sjsj", "sfsj", "sfsc", "bmrs");

        old.setGxsj(new Date());
        if (SystemConstant.threadLocal.get() != null) {
            old.setGxr(SystemConstant.threadLocal.get().getUserId());
        }

        jjrpxjhMapper.updateById(old);

        return old;
    }

    /**
     * 更新状态
     */
    @Override
    public Jjrpxjh updateStatus(Jjrpxjh old, Boolean sfsj) {

        Date now = new Date();

        old.setGxsj(now);
        old.setSjsj(now);
        old.setSfsj(sfsj);
        if (SystemConstant.threadLocal.get() != null) {
            old.setGxr(SystemConstant.threadLocal.get().getUserId());
        }

        jjrpxjhMapper.updateById(old);

        return old;
    }

    /**
     * 删除
     */
    @Override
    public void delete(Jjrpxjh old) {

        old.setGxsj(new Date());
        old.setSfsc(true);
        if (SystemConstant.threadLocal.get() != null) {
            old.setGxr(SystemConstant.threadLocal.get().getUserId());
        }

        jjrpxjhMapper.updateById(old);
    }
}
