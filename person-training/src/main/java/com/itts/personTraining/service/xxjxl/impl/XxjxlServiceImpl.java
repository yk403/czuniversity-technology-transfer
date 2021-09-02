package com.itts.personTraining.service.xxjxl.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.mapper.xxjxl.XxjxlMapper;
import com.itts.personTraining.mapper.xxjxl.XxjxlMapper;
import com.itts.personTraining.model.xxjxl.Xxjxl;
import com.itts.personTraining.service.xxjxl.XxjxlService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Date;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 * 学校教学楼表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-09-01
 */
@Service
@Slf4j
@Transactional
public class XxjxlServiceImpl extends ServiceImpl<XxjxlMapper, Xxjxl> implements XxjxlService {

    @Resource
    private XxjxlMapper xxjxlMapper;

    @Autowired
    private XxjxlService xxjxlService;

    /**
     * 查询学校教学楼列表
     * @param pageNum
     * @param pageSize
     * @param jxlmc
     * @return
     */
    @Override
    public PageInfo<Xxjxl> findByPage(Integer pageNum, Integer pageSize, String jxlmc,Long fjjgId) {
        log.info("【人才培养 - 查询学校教学楼列表,教学楼名称:{}】",jxlmc);
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Xxjxl> xxjxlQueryWrapper = new QueryWrapper<>();
        xxjxlQueryWrapper.eq("sfsc",false)
        .eq("fjjg_id",fjjgId)
                .like(StringUtils.isNotBlank(jxlmc), "jxlmc", jxlmc);
        return new PageInfo<>(xxjxlMapper.selectList(xxjxlQueryWrapper));
    }

    /**
     * 根据id查询学校教学楼详情
     * @param id
     * @return
     */
    @Override
    public Xxjxl get(Long id) {
        log.info("【人才培养 - 根据id:{}查询学校教学楼信息】",id);
        QueryWrapper<Xxjxl> xxjxlQueryWrapper = new QueryWrapper<>();
        xxjxlQueryWrapper.eq("sfsc",false)
                .eq("id",id);
        return xxjxlMapper.selectOne(xxjxlQueryWrapper);
    }

    /**
     * 新增学校教学楼
     * @param xxjxl
     * @return
     */
    @Override
    public boolean add(Xxjxl xxjxl) {
        log.info("【人才培养 - 新增学校教学楼:{}】",xxjxl);
        Long userId = getUserId();
        Date now = new Date();
        xxjxl.setSfsc(false);
        xxjxl.setCjr(userId);
        xxjxl.setGxr(userId);
        xxjxl.setGxr(userId);
        xxjxl.setGxsj(now);
        return xxjxlService.save(xxjxl);
    }

    /**
     * 更新学校教学楼
     * @param xxjxl
     * @return
     */
    @Override
    public boolean update(Xxjxl xxjxl) {
        log.info("【人才培养 - 更新学校教学楼:{}】",xxjxl);
        Date now = new Date();
        xxjxl.setGxr(getUserId());
        xxjxl.setGxsj(now);
        return xxjxlService.updateById(xxjxl);
    }

    /**
     * 删除学校教学楼
     * @param xxjxl
     * @return
     */
    @Override
    public boolean delete(Xxjxl xxjxl) {
        log.info("【人才培养 - 删除学校教学楼:{}】",xxjxl);
        Date now = new Date();
        //设置删除状态
        xxjxl.setSfsc(true);
        xxjxl.setGxr(getUserId());
        xxjxl.setGxsj(now);
        return xxjxlService.updateById(xxjxl);
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
