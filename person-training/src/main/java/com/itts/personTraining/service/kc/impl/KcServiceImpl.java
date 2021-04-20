package com.itts.personTraining.service.kc.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.mapper.kc.KcMapper;
import com.itts.personTraining.service.kc.KcService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jcajce.BCFKSStoreParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.itts.common.enums.ErrorCodeEnum.INSERT_FAIL;
import static com.itts.common.enums.ErrorCodeEnum.UPDATE_FAIL;

/**
 * <p>
 * 课程表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Service
@Slf4j
@Transactional
public class KcServiceImpl extends ServiceImpl<KcMapper, Kc> implements KcService {

    @Resource
    private KcMapper kcMapper;

    @Autowired
    private KcService kcService;

    /**
     * 分页条件查询课程列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Kc> findByPage(Integer pageNum, Integer pageSize, String kclx, String name) {
        log.info("【人才培养 - 分页条件查询课程列表,课程类型:{},课程代码/名称:{}】",kclx,name);
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Kc> kcQueryWrapper = new QueryWrapper<>();
        kcQueryWrapper.eq(StringUtils.isNotBlank(kclx),"kclx", kclx)
                      .eq("sczt",false)
                      .like(StringUtils.isNotBlank(name), "kcmc", name)
                      .or().like(StringUtils.isNotBlank(name), "kcdm", name);
        List<Kc> kcs = kcMapper.selectList(kcQueryWrapper);
        PageInfo<Kc> PageInfo = new PageInfo<>(kcs);
        return PageInfo;
    }

    /**
     * 根据id查询课程信息
     * @param id
     * @return
     */
    @Override
    public Kc get(Long id) {
        log.info("【人才培养 - 根据id:{}查询课程信息】",id);
        QueryWrapper<Kc> kcQueryWrapper = new QueryWrapper<>();
        kcQueryWrapper.eq("sczt",false)
                      .eq("id",id);
        return kcMapper.selectOne(kcQueryWrapper);
    }

    /**
     * 课程批量下发
     * @param ids
     * @return
     */
    @Override
    public boolean issueBatch(List<Long> ids) {
        log.info("【人才培养 - 课程批量下发,ids:{}】",ids);
        List<Kc> kcs = kcMapper.selectBatchIds(ids);
        for (Kc kc : kcs) {
            kc.setXfzt(true);
        }
        return kcService.updateBatchById(kcs);
    }

    /**
     * 新增课程
     * @param kc
     * @return
     */
    @Override
    public boolean add(Kc kc) {
        log.info("【人才培养 - 新增课程:{}】",kc);
        return kcService.save(kc);
    }

    /**
     * 更新课程
     * @param kc
     * @return
     */
    @Override
    public boolean update(Kc kc) {
        log.info("【人才培养 - 更新课程:{}】",kc);
        return kcService.updateById(kc);
    }


}
