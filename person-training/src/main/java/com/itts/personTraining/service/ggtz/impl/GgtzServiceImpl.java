package com.itts.personTraining.service.ggtz.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.enums.FbztEnum;
import com.itts.personTraining.mapper.ggtz.GgtzMapper;
import com.itts.personTraining.model.ggtz.Ggtz;
import com.itts.personTraining.model.xwgl.Xwgl;
import com.itts.personTraining.service.ggtz.GgtzService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class GgtzServiceImpl extends ServiceImpl<GgtzMapper, Ggtz> implements GgtzService {

    @Resource
    private GgtzMapper ggtzMapper;
    @Autowired
    private GgtzService ggtzService;

    @Override
    public PageInfo<Ggtz> findByPage(Integer pageNum, Integer pageSize, Long jgId, String zt, String lx,String tzbt) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Ggtz> ggtzQueryWrapper = new QueryWrapper<>();
        ggtzQueryWrapper.eq("sfsc",false)
                .orderByDesc("cjsj");
        if(jgId != null){
            ggtzQueryWrapper.eq("jg_id",jgId);
        }
        if(StringUtils.isNotBlank(zt)){
            ggtzQueryWrapper.eq("zt",zt);
        }
        if(StringUtils.isNotBlank(zt)){
            ggtzQueryWrapper.eq("lx",lx);
        }
        if(StringUtils.isNotBlank(tzbt)){
            ggtzQueryWrapper.likeRight("tzbt",tzbt);
        }
        List<Ggtz> ggtzs = ggtzMapper.selectList(ggtzQueryWrapper);
        PageInfo<Ggtz> ggtzPageInfo = new PageInfo<>(ggtzs);
        return ggtzPageInfo;
    }

    @Override
    public Ggtz add(Ggtz ggtz) {
        Long userId = getUserId();
        ggtz.setZt(FbztEnum.UN_RELEASE.getKey());
        ggtz.setCjr(userId);
        ggtz.setCjsj(new Date());
        ggtz.setGxr(userId);
        ggtz.setGxsj(new Date());
        ggtzMapper.insert(ggtz);
        return ggtz;
    }

    @Override
    public Ggtz update(Ggtz ggtz) {
        Long userId = getUserId();
        ggtz.setGxsj(new Date());
        ggtz.setGxr(userId);
        ggtzMapper.updateById(ggtz);
        return ggtz;
    }

    @Override
    public Ggtz get(Long id) {
        QueryWrapper<Ggtz> ggtzQueryWrapper = new QueryWrapper<>();
        ggtzQueryWrapper.eq("sfsc",false)
                .eq("id",id);
        Ggtz ggtz = ggtzMapper.selectOne(ggtzQueryWrapper);
        return ggtz;
    }

    @Override
    public boolean release(Long id) {
        Ggtz ggtz = get(id);
        ggtz.setZt(FbztEnum.RELEASE.getKey());
        ggtz.setFbsj(new Date());
        ggtz.setGxsj(new Date());
        ggtz.setGxr(getUserId());
        ggtzMapper.updateById(ggtz);
        return true;
    }

    @Override
    public boolean out(Long id) {
        Ggtz ggtz = get(id);
        ggtz.setZt(FbztEnum.STOP_USING.getKey());
        ggtz.setFbsj(new Date());
        ggtz.setGxsj(new Date());
        ggtz.setGxr(getUserId());
        ggtzMapper.updateById(ggtz);
        return true;
    }

    @Override
    public boolean issueBatch(List<Long> ids) {
        ArrayList<Ggtz> ggtzs = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            Ggtz ggtz = get(ids.get(i));
            ggtz.setZt(FbztEnum.RELEASE.getKey());
            ggtz.setFbsj(new Date());
            ggtz.setGxsj(new Date());
            ggtz.setGxr(getUserId());
            ggtzs.add(ggtz);
        }
        return ggtzService.updateBatchById(ggtzs);
    }

    @Override
    public boolean delete(Long id) {
        Ggtz ggtz = get(id);
        ggtz.setSfsc(true);
        ggtz.setGxr(getUserId());
        ggtz.setGxsj(new Date());
        ggtzMapper.updateById(ggtz);
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
