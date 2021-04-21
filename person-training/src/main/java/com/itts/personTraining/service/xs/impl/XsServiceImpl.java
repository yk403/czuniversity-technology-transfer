package com.itts.personTraining.service.xs.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.xs.Xs;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.service.xs.XsService;
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
 * 学生表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Service
@Slf4j
@Transactional
public class XsServiceImpl extends ServiceImpl<XsMapper, Xs> implements XsService {
    
    @Resource
    private XsMapper xsMapper;

    @Autowired
    private XsService xsService;

    /**
     * 查询学员列表
     * @param pageNum
     * @param pageSize
     * @param pcId
     * @param jyxs
     * @return
     */
    @Override
    public PageInfo<Xs> findByPage(Integer pageNum, Integer pageSize, Long pcId, String xslbId, String jyxs) {
        log.info("【人才培养 - 分页条件查询学员列表,批次id:{},学生类别id:{},教育形式:{}】",pcId,xslbId,jyxs);
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Xs> xsQueryWrapper = new QueryWrapper<>();
        xsQueryWrapper.eq("sczt",false)
                      .eq(pcId != null,"pc_id", pcId)
                      .eq(StringUtils.isNotBlank(xslbId),"xslb_id", xslbId)
                      .eq(StringUtils.isNotBlank(jyxs),"jyxs", jyxs);
        List<Xs> xss = xsMapper.selectList(xsQueryWrapper);
        return new PageInfo<>(xss);
    }

    /**
     * 根据id查询学员信息
     * @param id
     * @return
     */
    @Override
    public Xs get(Long id) {
        log.info("【人才培养 - 根据id:{}查询学员信息】",id);
        QueryWrapper<Xs> xsQueryWrapper = new QueryWrapper<>();
        xsQueryWrapper.eq("sczt",false)
                      .eq("id",id);
        return xsMapper.selectOne(xsQueryWrapper);
    }

    /**
     * 新增学员
     * @param xs
     * @return
     */
    @Override
    public boolean add(Xs xs) {
        log.info("【人才培养 - 新增学员:{}】",xs);
        if (xsMapper.selectById(xs.getId()) != null) {
            return false;
        }
        return xsService.save(xs);
    }

    /**
     * 更新学员
     * @param xs
     * @return
     */
    @Override
    public boolean update(Xs xs) {
        log.info("【人才培养 - 更新学员:{}】",xs);
        return xsService.updateById(xs);
    }

    /**
     * 删除学员
     * @param xs
     * @return
     */
    @Override
    public boolean delete(Xs xs) {
        log.info("【人才培养 - 删除学员:{}】",xs);
        //设置删除状态
        xs.setSczt(true);
        return xsService.updateById(xs);
    }

    /**
     * 根据学号查询学员信息
     * @param xh
     * @return
     */
    @Override
    public Xs selectByXh(String xh) {
        log.info("【人才培养 - 根据学号:{}查询学员信息】",xh);
        QueryWrapper<Xs> xsQueryWrapper = new QueryWrapper<>();
        xsQueryWrapper.eq("sczt",false)
                .eq("xh",xh);
        return xsMapper.selectOne(xsQueryWrapper);
    }
}
