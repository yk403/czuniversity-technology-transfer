package com.itts.personTraining.service.rmdt.impl;

import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.mapper.rmdt.RmdtMapper;
import com.itts.personTraining.model.rmdt.Rmdt;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.service.rmdt.RmdtService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Date;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fuli
 * @since 2021-07-26
 */
@Service
public class RmdtServiceImpl extends ServiceImpl<RmdtMapper, Rmdt> implements RmdtService {

    @Resource
    private RmdtMapper rmdtMapper;

    @Override
    public Boolean use(Long id) {
        Rmdt rmdt = rmdtMapper.selectById(id);
        Long userId = getUserId();
        Date date = new Date();
        Boolean sfxs = rmdt.getSfxs();
        if (sfxs){
            rmdt.setZtsysj(date);
            rmdt.setSfxs(false);
            rmdt.setGxr(userId);
            rmdtMapper.updateById(rmdt);
        }else {
            rmdt.setKssysj(date);
            rmdt.setSfxs(true);
            rmdt.setGxr(userId);
            rmdtMapper.updateById(rmdt);
        }
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
