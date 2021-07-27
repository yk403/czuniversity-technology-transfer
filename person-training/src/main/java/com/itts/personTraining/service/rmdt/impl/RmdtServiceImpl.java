package com.itts.personTraining.service.rmdt.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.mapper.rmdt.RmdtMapper;
import com.itts.personTraining.model.rmdt.Rmdt;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.service.rmdt.RmdtService;
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

    @Override
    public Boolean up(Long jgId,Long id) {
        Rmdt rmdt = rmdtMapper.selectById(id);
        String px = rmdt.getPx();
        List<Rmdt> rmdts = rmdtMapper.selectList(new QueryWrapper<Rmdt>().eq("jg_id", jgId)
                .orderByDesc("px"));
        int s = 0;
        for (int i = 0; i < rmdts.size(); i++) {
            if(rmdts.get(i).getId()==rmdt.getId()){
                s=i;
            }
        }
        Rmdt two = rmdts.get(s + 1);
        String px1 = two.getPx();
        two.setPx(px);
        rmdt.setPx(px1);
        rmdtMapper.updateById(rmdt);
        rmdtMapper.updateById(two);
        return true;
    }

    @Override
    public Boolean down(Long jgId,Long id) {
        Rmdt rmdt = rmdtMapper.selectById(id);
        String px = rmdt.getPx();
        List<Rmdt> rmdts = rmdtMapper.selectList(new QueryWrapper<Rmdt>().eq("jg_id", jgId)
                .orderByAsc("px"));
        int s=0;
        for (int i = 0; i < rmdts.size(); i++) {
            if(rmdts.get(i).getId()==rmdt.getId()){
                s=i;
            }
        }
        Rmdt two = rmdts.get(s + 1);
        String px1 = two.getPx();
        two.setPx(px);
        rmdt.setPx(px1);
        rmdtMapper.updateById(rmdt);
        rmdtMapper.updateById(two);
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
