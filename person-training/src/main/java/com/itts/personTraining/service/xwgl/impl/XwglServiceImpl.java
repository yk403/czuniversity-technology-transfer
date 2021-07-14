package com.itts.personTraining.service.xwgl.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.enums.FbztEnum;
import com.itts.personTraining.mapper.xwgl.XwglMapper;
import com.itts.personTraining.model.xwgl.Xwgl;
import com.itts.personTraining.service.xwgl.XwglService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * @since 2021-07-09
 */
@Service
@Slf4j
public class XwglServiceImpl extends ServiceImpl<XwglMapper, Xwgl> implements XwglService {


    @Resource
    private XwglMapper xwglMapper;

    @Override
    public PageInfo<Xwgl> findByPage(Integer pageNum, Integer pageSize, Long jgId, String zt, String lx) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Xwgl> xwglQueryWrapper = new QueryWrapper<>();
        xwglQueryWrapper.eq("sfsc",false)
        .orderByDesc("cjsj");
        if(jgId != null){
            xwglQueryWrapper.eq("jg_id",jgId);
        }
        if (StringUtils.isNotBlank(zt)) {
            xwglQueryWrapper.eq("zt", zt);
        }
        if (StringUtils.isNotBlank(lx)) {
            xwglQueryWrapper.eq("lx", lx);
        }
        List<Xwgl> xwgls = xwglMapper.selectList(xwglQueryWrapper);
        PageInfo<Xwgl> xwglPageInfo = new PageInfo<>(xwgls);
        return xwglPageInfo;
    }

    @Override
    public Xwgl add(Xwgl xwgl) {
        Long userId = getUserId();
        xwgl.setZt(FbztEnum.UN_RELEASE.getKey());
        xwgl.setCjr(userId);
        xwgl.setCjsj(new Date());
        xwgl.setGxr(userId);
        xwgl.setGxsj(new Date());
        xwglMapper.insert(xwgl);
        return xwgl;
    }

    @Override
    public Xwgl update(Xwgl xwgl) {
        Long userId = getUserId();
        xwgl.setGxsj(new Date());
        xwgl.setGxr(userId);
        xwglMapper.updateById(xwgl);
        return xwgl;
    }

    @Override
    public Xwgl get(Long id) {
        QueryWrapper<Xwgl> xwglQueryWrapper = new QueryWrapper<>();
        xwglQueryWrapper.eq("sfsc",false)
                .eq("id",id);
        Xwgl xwgl = xwglMapper.selectOne(xwglQueryWrapper);
        return xwgl;
    }

    @Override
    public boolean release(Long id) {
        Xwgl xwgl = get(id);
        xwgl.setZt(FbztEnum.RELEASE.getKey());
        xwgl.setFbsj(new Date());
        xwgl.setGxsj(new Date());
        xwgl.setGxr(getUserId());
        xwglMapper.updateById(xwgl);
        return true;
    }

    @Override
    public boolean out(Long id) {
        Xwgl xwgl = get(id);
        xwgl.setZt(FbztEnum.STOP_USING.getKey());
        xwgl.setFbsj(new Date());
        xwgl.setGxsj(new Date());
        xwgl.setGxr(getUserId());
        xwglMapper.updateById(xwgl);
        return true;
    }

    @Override
    public boolean issueBatch(List<Long> ids) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        Xwgl xwgl = get(id);
        xwgl.setSfsc(true);
        xwgl.setGxr(getUserId());
        xwgl.setGxsj(new Date());
        xwglMapper.updateById(xwgl);
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
