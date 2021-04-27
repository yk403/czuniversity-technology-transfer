package com.itts.personTraining.service.zy.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.personTraining.model.xy.Xy;
import com.itts.personTraining.model.zy.Zy;
import com.itts.personTraining.mapper.zy.ZyMapper;
import com.itts.personTraining.service.zy.ZyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 专业表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-04-26
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ZyServiceImpl extends ServiceImpl<ZyMapper, Zy> implements ZyService {
    @Autowired
    private ZyService zyService;
    @Resource
    private ZyMapper zyMapper;

    /**
     * 根据学院id查询专业信息
     * @param xyId
     * @return
     */
    @Override
    public List<Zy> getByXyId(Long xyId) {
        log.info("【人才培养 - 根据学院id:{}查询专业信息】",xyId);
        QueryWrapper<Zy> zyQueryWrapper = new QueryWrapper<>();
        zyQueryWrapper.eq("sfsc",false)
                      .eq("xy_id",xyId);
        return zyMapper.selectList(zyQueryWrapper);
    }

    /**
     * 查询所有专业信息
     * @return
     */
    @Override
    public List<Zy> getAll() {
        log.info("【人才培养 - 查询所有专业信息】");
        QueryWrapper<Zy> zyQueryWrapper = new QueryWrapper<>();
        zyQueryWrapper.eq("sfsc",false);
        return zyMapper.selectList(zyQueryWrapper);
    }

    /**
     * 新增专业
     * @param zy
     * @return
     */
    @Override
    public boolean add(Zy zy) {
        log.info("【人才培养 - 新增专业:{}】",zy);
        return zyService.save(zy);
    }

    /**
     * 根据id查询专业信息
     * @param id
     * @return
     */
    @Override
    public Zy get(Long id) {
        log.info("【人才培养 - 根据id:{}查询专业信息】",id);
        QueryWrapper<Zy> zyQueryWrapper = new QueryWrapper<>();
        zyQueryWrapper.eq("sfsc",false)
                .eq("id",id);
        return zyMapper.selectOne(zyQueryWrapper);
    }

    /**
     * 更新专业
     * @param zy
     * @return
     */
    @Override
    public boolean update(Zy zy) {
        log.info("【人才培养 - 更新专业:{}】",zy);
        return zyService.updateById(zy);
    }

    /**
     * 删除专业
     * @param zy
     * @return
     */
    @Override
    public boolean delete(Zy zy) {
        log.info("【人才培养 - 删除专业:{}】",zy);
        //设置删除状态
        zy.setSfsc(true);
        return zyService.updateById(zy);
    }
}
