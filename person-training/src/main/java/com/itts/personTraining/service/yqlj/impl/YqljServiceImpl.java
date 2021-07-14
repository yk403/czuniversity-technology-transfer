package com.itts.personTraining.service.yqlj.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.enums.FbztEnum;
import com.itts.personTraining.mapper.yqlj.YqljMapper;
import com.itts.personTraining.model.xwgl.Xwgl;
import com.itts.personTraining.model.yqlj.Yqlj;
import com.itts.personTraining.service.yqlj.YqljService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * @since 2021-07-12
 */
@Service
public class YqljServiceImpl extends ServiceImpl<YqljMapper, Yqlj> implements YqljService {

    @Resource
    private YqljMapper yqljMapper;

    @Override
    public PageInfo<Yqlj> findByPage(Integer pageNum, Integer pageSize, Long jgId, String zt, String lx) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Yqlj> yqljQueryWrapper = new QueryWrapper<>();
        yqljQueryWrapper.eq("jg_id",jgId)
                .eq("sfsc",false)
                .orderByDesc("cjsj");
        if (StringUtils.isNotBlank(zt)) {
            yqljQueryWrapper.eq("zt", zt);
        }
        if (StringUtils.isNotBlank(lx)) {
            yqljQueryWrapper.eq("lx", lx);
        }
        List<Yqlj> yqljs = yqljMapper.selectList(yqljQueryWrapper);
        PageInfo<Yqlj> yqljPageInfo = new PageInfo<>(yqljs);
        return yqljPageInfo;
    }

    @Override
    public Yqlj add(Yqlj yqlj) {
        Long userId = getUserId();
        yqlj.setZt(FbztEnum.UN_RELEASE.getKey());
        yqlj.setCjr(userId);
        yqlj.setCjsj(new Date());
        yqlj.setGxr(userId);
        yqlj.setGxsj(new Date());
        yqljMapper.insert(yqlj);
        return yqlj;
    }

    @Override
    public Yqlj update(Yqlj yqlj) {
        Long userId = getUserId();
        yqlj.setGxsj(new Date());
        yqlj.setGxr(userId);
        yqljMapper.updateById(yqlj);
        return yqlj;
    }

    @Override
    public Yqlj get(Long id) {
        QueryWrapper<Yqlj> yqljQueryWrapper = new QueryWrapper<>();
        yqljQueryWrapper.eq("sfsc",false)
                .eq("id",id);
        Yqlj yqlj = yqljMapper.selectOne(yqljQueryWrapper);
        return yqlj;
    }

    @Override
    public boolean release(Long id) {
        Yqlj yqlj = get(id);
        yqlj.setZt(FbztEnum.RELEASE.getKey());
        yqlj.setFbsj(new Date());
        yqlj.setGxsj(new Date());
        yqlj.setGxr(getUserId());
        yqljMapper.updateById(yqlj);
        return true;
    }

    @Override
    public boolean out(Long id) {
        Yqlj yqlj = get(id);
        yqlj.setZt(FbztEnum.STOP_USING.getKey());
        yqlj.setFbsj(new Date());
        yqlj.setGxsj(new Date());
        yqlj.setGxr(getUserId());
        yqljMapper.updateById(yqlj);
        return true;
    }

    @Override
    public boolean issueBatch(List<Long> ids) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        Yqlj yqlj = get(id);
        yqlj.setSfsc(true);
        yqlj.setGxr(getUserId());
        yqlj.setGxsj(new Date());
        yqljMapper.updateById(yqlj);
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
