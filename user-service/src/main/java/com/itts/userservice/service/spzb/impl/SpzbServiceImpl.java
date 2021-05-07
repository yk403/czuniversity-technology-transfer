package com.itts.userservice.service.spzb.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.userservice.enmus.VideoEnum;
import com.itts.userservice.mapper.spzb.SpzbMapper;
import com.itts.userservice.model.spzb.Spzb;
import com.itts.userservice.service.spzb.SpzbService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 视频直播 服务实现类
 * </p>
 *
 * @author liuyingming
 * @since 2021-04-27
 */
@Service
public class SpzbServiceImpl extends ServiceImpl<SpzbMapper, Spzb> implements SpzbService {

    @Autowired
    private SpzbMapper spzbMapper;

    /**
     * 获取列表 - 分页
     *
     * @param
     * @return
     * @author liuyingming
     */
    @Override
    public PageInfo<Spzb> findByPage(Integer pageNum, Integer pageSize, String name, String videoType) {

        PageHelper.startPage(pageNum, pageSize);

        QueryWrapper query = new QueryWrapper();
        query.eq("sfsc", false);

        if (StringUtils.isNotBlank(name)) {
            query.eq("zbspmc", name);
        }

        if (StringUtils.isNotBlank(videoType)) {
            query.eq("splx", videoType);
        }

        query.orderByDesc("cjsj");

        List spabs = spzbMapper.selectList(query);

        PageInfo<Spzb> pageInfo = new PageInfo<>(spabs);

        return pageInfo;
    }

    /**
     * 新增
     *
     * @param
     * @return
     * @author liuyingming
     */
    @Override
    public Spzb add(Spzb spzb) {

        LoginUser loginUser = SystemConstant.threadLocal.get();

        Long userId = null;

        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        Date now = new Date();

        if (Objects.equals(spzb.getSplx(), VideoEnum.LIVE_BROADCAST.getCode())) {

            //生成直播密钥
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String code = dateTimeFormatter.format(localDateTime);
            spzb.setZbspmy(VideoEnum.LIVE_BROADCAST.getCode() + "_" + code);
        }

        spzb.setCjr(userId);
        spzb.setGxr(userId);
        spzb.setCjsj(now);
        spzb.setGxsj(now);

        spzbMapper.insert(spzb);

        return spzb;
    }

    /**
     * 更新
     *
     * @param
     * @return
     * @author liuyingming
     */
    @Override
    public Spzb update(Spzb spzb) {

        LoginUser loginUser = SystemConstant.threadLocal.get();

        Long userId = null;

        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        spzb.setGxr(userId);
        spzb.setGxsj(new Date());

        spzbMapper.updateById(spzb);

        return spzb;
    }

    /**
     * 删除
     *
     * @param
     * @return
     * @author liuyingming
     */
    @Override
    public void delete(Spzb spzb) {

        LoginUser loginUser = SystemConstant.threadLocal.get();

        Long userId = null;

        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        spzb.setGxr(userId);
        spzb.setGxsj(new Date());
        spzb.setSfsc(true);

        spzbMapper.updateById(spzb);
    }
}
