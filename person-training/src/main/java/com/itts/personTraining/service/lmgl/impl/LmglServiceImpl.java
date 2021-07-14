package com.itts.personTraining.service.lmgl.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.mapper.lmgl.LmglMapper;
import com.itts.personTraining.model.lbt.Lbt;
import com.itts.personTraining.model.lmgl.Lmgl;
import com.itts.personTraining.service.lmgl.LmglService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fuli
 * @since 2021-07-13
 */
@Service
public class LmglServiceImpl extends ServiceImpl<LmglMapper, Lmgl> implements LmglService {

    @Resource
    private LmglMapper lmglMapper;
    @Override
    public List<Lmgl> findList(Long jgId) {
        QueryWrapper<Lmgl> lmglQueryWrapper = new QueryWrapper<>();
        lmglQueryWrapper.eq("sfsc",false)
                .eq("jg_id",jgId)
                .orderByAsc("px");
        List<Lmgl> lmgls = lmglMapper.selectList(lmglQueryWrapper);
        return lmgls;
    }

    @Override
    public Lmgl add(Lmgl lmgl) {
        Long userId = getUserId();
        lmgl.setCjr(userId);
        lmgl.setCjsj(new Date());
        lmgl.setGxr(userId);
        lmgl.setGxsj(new Date());
        lmglMapper.insert(lmgl);
        return lmgl;
    }

    @Override
    public Lmgl update(Lmgl lmgl) {
        Long userId = getUserId();
        lmgl.setGxsj(new Date());
        lmgl.setGxr(userId);
        lmglMapper.updateById(lmgl);
        return lmgl;
    }

    @Override
    public Lmgl get(Long id) {
        QueryWrapper<Lmgl> lmglQueryWrapper = new QueryWrapper<>();
        lmglQueryWrapper.eq("sfsc",false)
                .eq("id",id);
        Lmgl lmgl = lmglMapper.selectOne(lmglQueryWrapper);
        return lmgl;
    }

    @Override
    public boolean delete(Long id) {
        Lmgl lmgl = get(id);
        lmgl.setSfsc(true);
        lmgl.setGxr(getUserId());
        lmgl.setGxsj(new Date());
        lmglMapper.updateById(lmgl);
        return true;
    }
    /**
     * 获取当前用户id
     * @return
     */
    private Long getUserId() {
        LoginUser loginUser = threadLocal.get();
        Long userId;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        } else {
            throw new ServiceException(GET_THREADLOCAL_ERROR);
        }
        return userId;
    }
}