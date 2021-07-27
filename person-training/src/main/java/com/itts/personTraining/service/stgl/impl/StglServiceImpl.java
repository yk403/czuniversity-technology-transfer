package com.itts.personTraining.service.stgl.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.mapper.stgl.StglMapper;
import com.itts.personTraining.model.lmgl.Lmgl;
import com.itts.personTraining.model.rmdt.Rmdt;
import com.itts.personTraining.model.stgl.Stgl;
import com.itts.personTraining.service.stgl.StglService;
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
public class StglServiceImpl extends ServiceImpl<StglMapper, Stgl> implements StglService {

    @Resource
    private StglMapper stglMapper;
    @Override
    public List<Stgl> findList(Long jgId) {
        QueryWrapper<Stgl> stglQueryWrapper = new QueryWrapper<>();
        stglQueryWrapper.eq("sfsc",false)
                .eq("jg_id",jgId)
                .orderByAsc("px");
        List<Stgl> lmgls = stglMapper.selectList(stglQueryWrapper);
        return lmgls;
    }

    @Override
    public Stgl add(Stgl stgl) {
        Long userId = getUserId();
        stgl.setCjr(userId);
        stgl.setCjsj(new Date());
        stgl.setGxr(userId);
        stgl.setGxsj(new Date());
        stglMapper.insert(stgl);
        return stgl;
    }

    @Override
    public Stgl update(Stgl stgl) {
        Long userId = getUserId();
        stgl.setGxsj(new Date());
        stgl.setGxr(userId);
        stglMapper.updateById(stgl);
        return stgl;
    }

    @Override
    public Stgl get(Long id) {
        QueryWrapper<Stgl> stglQueryWrapper = new QueryWrapper<>();
        stglQueryWrapper.eq("sfsc",false)
                .eq("id",id);
        Stgl stgl = stglMapper.selectOne(stglQueryWrapper);
        return stgl;
    }

    @Override
    public boolean delete(Long id) {
        Stgl stgl = get(id);
        stgl.setSfsc(true);
        stgl.setGxr(getUserId());
        stgl.setGxsj(new Date());
        stglMapper.updateById(stgl);
        return true;
    }

    @Override
    public boolean use(Long id) {
        Stgl stgl = get(id);
        Long userId = getUserId();
        Boolean sfxs = stgl.getSfxs();
        if(sfxs){
            stgl.setSfxs(false);
            stgl.setGxr(userId);
            stglMapper.updateById(stgl);
        }else {
            stgl.setSfxs(true);
            stgl.setGxr(userId);
            stglMapper.updateById(stgl);
        }
        return true;
    }

    @Override
    public Boolean up(Long jgId, Long id) {
        Stgl stgl = stglMapper.selectById(id);
        String px = stgl.getPx();
        List<Stgl> stgls = stglMapper.selectList(new QueryWrapper<Stgl>().eq("jg_id", jgId)
                .orderByDesc("px"));
        int s = 0;
        for (int i = 0; i < stgls.size(); i++) {
            if(stgls.get(i).getId()==stgl.getId()){
                s=i;
            }
        }
        Stgl two = stgls.get(s + 1);
        String px1 = two.getPx();
        two.setPx(px);
        stgl.setPx(px1);
        stglMapper.updateById(stgl);
        stglMapper.updateById(two);
        return true;
    }

    @Override
    public Boolean down(Long jgId, Long id) {
        Stgl stgl = stglMapper.selectById(id);
        String px = stgl.getPx();
        List<Stgl> stgls = stglMapper.selectList(new QueryWrapper<Stgl>().eq("jg_id", jgId)
                .orderByAsc("px"));
        int s=0;
        for (int i = 0; i < stgls.size(); i++) {
            if(stgls.get(i).getId()==stgl.getId()){
                s=i;
            }
        }
        Stgl two = stgls.get(s + 1);
        String px1 = two.getPx();
        two.setPx(px);
        stgl.setPx(px1);
        stglMapper.updateById(stgl);
        stglMapper.updateById(two);
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
