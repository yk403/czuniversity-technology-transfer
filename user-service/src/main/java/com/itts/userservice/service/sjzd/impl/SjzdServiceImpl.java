package com.itts.userservice.service.sjzd.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.userservice.mapper.sjzd.SjzdMapper;
import com.itts.userservice.model.sjzd.Sjzd;
import com.itts.userservice.service.sjzd.SjzdService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fl
 * @since 2021-03-31
 */
@Service
public class SjzdServiceImpl implements SjzdService {

    @Resource
    private SjzdMapper sjzdMapper;

    /**
     * 获取列表
     */
    @Override
    public PageInfo<Sjzd> findByPage(Integer pageNum, Integer pageSize, String model, String systemType) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Sjzd> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("sfsc", false);

        if(StringUtils.isNotBlank(model)){
            objectQueryWrapper.eq("ssmk", model);
        }

        if(StringUtils.isNotBlank(systemType)){
            objectQueryWrapper.eq("xtlb", systemType);
        }

        objectQueryWrapper.orderByDesc("cjsj");

        List<Sjzd> sjzds = sjzdMapper.selectList(objectQueryWrapper);
        PageInfo<Sjzd> shzdPageInfo = new PageInfo<>(sjzds);
        return shzdPageInfo;
    }

    /**
     * 获取通过id
     *
     * @param id
     * @return
     */
    @Override
    public Sjzd get(Long id) {
        Sjzd sjzd = sjzdMapper.selectById(id);
        return sjzd;
    }

    /**
     * 查询通过名称或编码
     *
     * @param pageNum
     * @param pageSize
     * @param string
     * @param ssmk
     * @return
     */
    @Override
    public PageInfo<Sjzd> selectByString(Integer pageNum, Integer pageSize, String string, String ssmk) {

        PageHelper.startPage(pageNum, pageSize);
        List<Sjzd> sjzdList = sjzdMapper.selectByNameOrCode(string, ssmk);
        PageInfo<Sjzd> shzdPageInfo = new PageInfo<>(sjzdList);
        return shzdPageInfo;
    }


    /**
     * 添加
     *
     * @param sjzd
     * @return
     */
    @Override
    public Sjzd add(Sjzd sjzd) {
        LoginUser loginUser = threadLocal.get();
        Long userId = null;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }
        //新增时设置更新时间和更新人
        sjzd.setCjsj(new Date());
        sjzd.setCjr(userId);
        sjzd.setGxsj(new Date());
        sjzd.setGxr(userId);
        sjzdMapper.insert(sjzd);
        return sjzd;
    }

    /**
     * 更新
     *
     * @param sjzd
     * @return
     */
    @Override
    public Sjzd update(Sjzd sjzd) {
        LoginUser loginUser = threadLocal.get();
        Long userId = null;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }
        //更新时设置更新时间和更新人
        sjzd.setGxsj(new Date());
        sjzd.setGxr(userId);
        sjzdMapper.updateById(sjzd);
        return sjzd;
    }
}
