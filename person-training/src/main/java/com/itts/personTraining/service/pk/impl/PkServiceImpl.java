package com.itts.personTraining.service.pk.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.pk.Pk;
import com.itts.personTraining.mapper.pk.PkMapper;
import com.itts.personTraining.service.pk.PkService;
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
 * 排课表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Service
@Slf4j
@Transactional
public class PkServiceImpl extends ServiceImpl<PkMapper, Pk> implements PkService {

    @Resource
    private PkMapper pkMapper;
    @Autowired
    private PkService pkService;
    /**
     * 查询排课列表
     * @param pageNum
     * @param pageSize
     * @param pch
     * @return
     */
    @Override
    public PageInfo<Pk> findByPage(Integer pageNum, Integer pageSize, String pch) {
        log.info("【人才培养 - 分页条件查询排课列表,批次号:{}】",pch);
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Pk> pkQueryWrapper = new QueryWrapper<>();
        pkQueryWrapper.eq("sfsc",false)
                      .eq(StringUtils.isNotBlank(pch),"pch", pch);
        List<Pk> pks = pkMapper.selectList(pkQueryWrapper);
        PageInfo<Pk> PageInfo = new PageInfo<>(pks);
        return PageInfo;
    }

    /**
     * 根据id查询排课详情
     * @param id
     * @return
     */
    @Override
    public Pk get(Long id) {
        log.info("【人才培养 - 根据id:{}查询排课信息】",id);
        QueryWrapper<Pk> pkQueryWrapper = new QueryWrapper<>();
        pkQueryWrapper.eq("sfsc",false)
                      .eq("id",id);
        return pkMapper.selectOne(pkQueryWrapper);
    }

    /**
     * 新增排课
     * @param pk
     * @return
     */
    @Override
    public boolean add(Pk pk) {
        log.info("【人才培养 - 新增排课:{}】",pk);
        return pkService.save(pk);
    }

    /**
     * 更新排课
     * @param pk
     * @return
     */
    @Override
    public boolean update(Pk pk) {
        log.info("【人才培养 - 更新排课:{}】",pk);
        return pkService.updateById(pk);
    }

    /**
     * 删除排课
     * @param pk
     * @return
     */
    @Override
    public boolean delete(Pk pk) {
        log.info("【人才培养 - 删除排课:{}】",pk);
        //设置删除状态
        pk.setSfsc(true);
        return pkService.updateById(pk);
    }

    /**
     * 批量新增排课
     * @param pks
     * @return
     */
    @Override
    public boolean addList(List<Pk> pks) {
        log.info("【人才培养 - 批量新增排课:{}】",pks);
        return pkService.saveBatch(pks);
    }

}
