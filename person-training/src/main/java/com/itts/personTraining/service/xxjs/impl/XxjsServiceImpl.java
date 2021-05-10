package com.itts.personTraining.service.xxjs.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.model.xxjs.Xxjs;
import com.itts.personTraining.mapper.xxjs.XxjsMapper;
import com.itts.personTraining.service.xxjs.XxjsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 * 学校教室表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-04-22
 */
@Service
@Slf4j
@Transactional
public class XxjsServiceImpl extends ServiceImpl<XxjsMapper, Xxjs> implements XxjsService {
    @Resource
    private XxjsMapper xxjsMapper;

    @Autowired
    private XxjsService xxjsService;

    /**
     * 查询学校教室列表
     * @param pageNum
     * @param pageSize
     * @param jxlmc
     * @return
     */
    @Override
    public PageInfo<Xxjs> findByPage(Integer pageNum, Integer pageSize, String jxlmc) {
        log.info("【人才培养 - 查询学校教室列表,教学楼名称:{}】",jxlmc);
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Xxjs> xxjsQueryWrapper = new QueryWrapper<>();
        xxjsQueryWrapper.eq("sfsc",false)
                      .eq(StringUtils.isNotBlank(jxlmc), "jxlmc", jxlmc);
        return new PageInfo<>(xxjsMapper.selectList(xxjsQueryWrapper));
    }

    /**
     * 根据id查询学校教室详情
     * @param id
     * @return
     */
    @Override
    public Xxjs get(Long id) {
        log.info("【人才培养 - 根据id:{}查询学校教室信息】",id);
        QueryWrapper<Xxjs> xxjsQueryWrapper = new QueryWrapper<>();
        xxjsQueryWrapper.eq("sfsc",false)
                .eq("id",id);
        return xxjsMapper.selectOne(xxjsQueryWrapper);
    }

    /**
     * 新增学校教室
     * @param xxjs
     * @return
     */
    @Override
    public boolean add(Xxjs xxjs) {
        log.info("【人才培养 - 新增学校教室:{}】",xxjs);
        Long userId = getUserId();
        xxjs.setCjr(userId);
        xxjs.setGxr(userId);
        return xxjsService.save(xxjs);
    }

    /**
     * 更新学校教室
     * @param xxjs
     * @return
     */
    @Override
    public boolean update(Xxjs xxjs) {
        log.info("【人才培养 - 更新学校教室:{}】",xxjs);
        xxjs.setGxr(getUserId());
        return xxjsService.updateById(xxjs);
    }

    /**
     * 删除学校教室
     * @param xxjs
     * @return
     */
    @Override
    public boolean delete(Xxjs xxjs) {
        log.info("【人才培养 - 删除学校教室:{}】",xxjs);
        //设置删除状态
        xxjs.setSfsc(true);
        xxjs.setGxr(getUserId());
        return xxjsService.updateById(xxjs);
    }

    /**
     * 查询学校教室是否存在
     * @param xxjs
     * @return
     */
    @Override
    public boolean selectExists(Xxjs xxjs) {
        log.info("【人才培养 - 查询学校教室是否存在:{}】",xxjs);
        QueryWrapper<Xxjs> xxjsQueryWrapper = new QueryWrapper<>();
        xxjsQueryWrapper.eq("sfsc",false);
        List<Xxjs> xxjsList = xxjsMapper.selectList(xxjsQueryWrapper);
        for (Xxjs xxjs1 : xxjsList) {
            if (xxjs1.getJxlmc().equals(xxjs.getJxlmc()) && xxjs1.getJsbh().equals(xxjs.getJsbh())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据教学楼名称查询学校教室
     * @param jxlmc
     * @return
     */
    @Override
    public List<Xxjs> getByJxlmc(String jxlmc) {
        log.info("【人才培养 - 根据教学楼名称:{}查询学校教室信息】",jxlmc);
        QueryWrapper<Xxjs> xxjsQueryWrapper = new QueryWrapper<>();
        xxjsQueryWrapper.eq("sfsc",false)
                .eq("jxlmc",jxlmc);
        return xxjsMapper.selectList(xxjsQueryWrapper);
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
