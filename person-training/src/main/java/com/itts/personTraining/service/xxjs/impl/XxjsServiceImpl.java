package com.itts.personTraining.service.xxjs.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.personTraining.model.kc.Kc;
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
     * @param mc
     * @return
     */
    @Override
    public PageInfo<Xxjs> findByPage(Integer pageNum, Integer pageSize, String mc) {
        log.info("【人才培养 - 查询学校教室列表,教学楼名称:{}】",mc);
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Xxjs> xxjsQueryWrapper = new QueryWrapper<>();
        xxjsQueryWrapper.eq("sfsc",false)
                      .eq(StringUtils.isNotBlank(mc), "mc", mc);
        List<Xxjs> xxjs = xxjsMapper.selectList(xxjsQueryWrapper);
        PageInfo<Xxjs> PageInfo = new PageInfo<>(xxjs);
        return PageInfo;
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
            if (xxjs1.getMc().equals(xxjs.getMc()) && xxjs1.getJsbh().equals(xxjs.getJsbh())) {
                return false;
            }
        }
        return true;
    }

}
