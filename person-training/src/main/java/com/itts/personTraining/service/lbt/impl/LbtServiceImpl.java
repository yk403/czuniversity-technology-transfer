package com.itts.personTraining.service.lbt.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.enums.FbztEnum;
import com.itts.personTraining.mapper.lbt.LbtMapper;
import com.itts.personTraining.model.ggtz.Ggtz;
import com.itts.personTraining.model.lbt.Lbt;
import com.itts.personTraining.service.lbt.LbtService;
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
public class LbtServiceImpl extends ServiceImpl<LbtMapper, Lbt> implements LbtService {

    @Resource
    private LbtMapper lbtMapper;

    @Override
    public List<Lbt> findList(Long jgId) {
        QueryWrapper<Lbt> lbtQueryWrapper = new QueryWrapper<>();
        lbtQueryWrapper.eq("sfsc",false)
                .eq("jg_id",jgId)
                .orderByDesc("cjsj");
        List<Lbt> lbts = lbtMapper.selectList(lbtQueryWrapper);
        return lbts;
    }

    @Override
    public Lbt add(Lbt lbt) {
        Long userId = getUserId();
        lbt.setCjr(userId);
        lbt.setCjsj(new Date());
        lbt.setGxr(userId);
        lbt.setGxsj(new Date());
        lbtMapper.insert(lbt);
        return lbt;
    }

    @Override
    public Lbt update(Lbt lbt) {
        Long userId = getUserId();
        lbt.setGxsj(new Date());
        lbt.setGxr(userId);
        lbtMapper.updateById(lbt);
        return lbt;
    }

    @Override
    public Lbt get(Long id) {
        QueryWrapper<Lbt> lbtQueryWrapper = new QueryWrapper<>();
        lbtQueryWrapper.eq("sfsc",false)
                .eq("id",id);
        Lbt lbt = lbtMapper.selectOne(lbtQueryWrapper);
        return lbt;
    }

    @Override
    public boolean delete(Long id) {
        Lbt lbt = get(id);
        lbt.setSfsc(true);
        lbt.setGxr(getUserId());
        lbt.setGxsj(new Date());
        lbtMapper.updateById(lbt);
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
