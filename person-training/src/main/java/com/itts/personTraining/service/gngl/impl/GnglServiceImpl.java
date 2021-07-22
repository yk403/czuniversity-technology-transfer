package com.itts.personTraining.service.gngl.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.mapper.gngl.GnglMapper;
import com.itts.personTraining.model.gngl.Gngl;
import com.itts.personTraining.service.gngl.GnglService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fuli
 * @since 2021-07-21
 */
@Service
public class GnglServiceImpl extends ServiceImpl<GnglMapper, Gngl> implements GnglService {

    @Resource
    private GnglMapper gnglMapper;
    @Override
    public List<Gngl> find() {
        QueryWrapper<Gngl> gnglQueryWrapper = new QueryWrapper<>();
        List<Gngl> gngls = gnglMapper.selectList(gnglQueryWrapper);
        return gngls;
    }

    @Override
    public Gngl get(Long id) {
        Gngl gngl = gnglMapper.selectById(id);
        return gngl;
    }

    @Override
    public boolean use(Long id) {
        Gngl gngl = get(id);
        Long userId = getUserId();
        Boolean sfsy = gngl.getSfsy();
        if(sfsy){
            gngl.setSfsy(false);
            gngl.setGxr(userId);
            gnglMapper.updateById(gngl);
        }else{
            gngl.setSfsy(true);
            gngl.setGxr(userId);
            gnglMapper.updateById(gngl);
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
