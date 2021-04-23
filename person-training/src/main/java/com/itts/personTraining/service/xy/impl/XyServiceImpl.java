package com.itts.personTraining.service.xy.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.personTraining.model.pk.Pk;
import com.itts.personTraining.model.xy.Xy;
import com.itts.personTraining.mapper.xy.XyMapper;
import com.itts.personTraining.service.xy.XyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 学院表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-04-23
 */
@Service
@Slf4j
@Transactional
public class XyServiceImpl extends ServiceImpl<XyMapper, Xy> implements XyService {

    @Resource
    private XyMapper xyMapper;
    @Autowired
    private XyService xyService;
    /**
     * 查询所有学院
     * @return
     */
    @Override
    public List<Xy> getAll() {
        QueryWrapper<Xy> xyQueryWrapper = new QueryWrapper<>();
        xyQueryWrapper.eq("sfsc",false);
        List<Xy> xyList = xyMapper.selectList(xyQueryWrapper);
        return xyList;
    }

    /**
     * 新增学院
     * @param xy
     * @return
     */
    @Override
    public boolean add(Xy xy) {
        log.info("【人才培养 - 新增学院:{}】",xy);
        return xyService.save(xy);
    }

    /**
     * 根据id查询学院
     * @param id
     * @return
     */
    @Override
    public Xy get(Long id) {
        log.info("【人才培养 - 根据id:{}查询学院信息】",id);
        QueryWrapper<Xy> xyQueryWrapper = new QueryWrapper<>();
        xyQueryWrapper.eq("sfsc",false)
                .eq("id",id);
        return xyMapper.selectOne(xyQueryWrapper);
    }

    /**
     * 更新学院
     * @param xy
     * @return
     */
    @Override
    public boolean update(Xy xy) {
        log.info("【人才培养 - 更新学院:{}】",xy);
        return xyService.updateById(xy);
    }

    /**
     * 删除学院
     * @param xy
     * @return
     */
    @Override
    public boolean delete(Xy xy) {
        log.info("【人才培养 - 删除学院:{}】",xy);
        //设置删除状态
        xy.setSfsc(true);
        return xyService.updateById(xy);
    }
}
