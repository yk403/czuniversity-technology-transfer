package com.itts.personTraining.service.sz.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.model.xs.Xs;
import com.itts.personTraining.service.sz.SzService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.service.xs.XsService;
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
 * 师资表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SzServiceImpl extends ServiceImpl<SzMapper, Sz> implements SzService {

    @Resource
    private SzMapper szMapper;

    @Autowired
    private SzService szService;

    /**
     * 获取师资列表
     * @param pageNum
     * @param pageSize
     * @param dsxm
     * @param dslb
     * @param hyly
     * @return
     */
    @Override
    public PageInfo<Sz> findByPage(Integer pageNum, Integer pageSize, String dsxm, String dslb, String hyly) {
        log.info("【人才培养 - 分页条件查询师资列表,导师姓名:{},导师类别:{},行业领域:{}】",dsxm,dslb,hyly);
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Sz> szQueryWrapper = new QueryWrapper<>();
        szQueryWrapper.eq("sfsc",false)
                      .eq(StringUtils.isNotBlank(dsxm),"dsxm", dsxm)
                      .eq(StringUtils.isNotBlank(dslb),"dslb", dslb)
                      .eq(StringUtils.isNotBlank(hyly),"hyly", hyly);
        List<Sz> szs = szMapper.selectList(szQueryWrapper);
        return new PageInfo<>(szs);
    }

    /**
     * 根据id查询师资详情
     * @param id
     * @return
     */
    @Override
    public Sz get(Long id) {
        log.info("【人才培养 - 根据id:{}查询师资信息】",id);
        QueryWrapper<Sz> szQueryWrapper = new QueryWrapper<>();
        szQueryWrapper.eq("sfsc",false)
                      .eq("id",id);
        return szMapper.selectOne(szQueryWrapper);
    }

    /**
     * 新增师资
     * @param sz
     * @return
     */
    @Override
    public boolean add(Sz sz) {
        log.info("【人才培养 - 新增师资:{}】",sz);
        if (szMapper.selectById(sz.getId()) != null) {
            return false;
        }
        Long userId = getUserId();
        sz.setCjr(userId);
        sz.setGxr(userId);
        return szService.save(sz);
    }

    /**
     * 更新师资
     * @param sz
     * @return
     */
    @Override
    public boolean update(Sz sz) {
        log.info("【人才培养 - 更新学员:{}】",sz);
        sz.setGxr(getUserId());
        return szService.updateById(sz);
    }

    /**
     * 删除师资
     * @param sz
     * @return
     */
    @Override
    public boolean delete(Sz sz) {
        log.info("【人才培养 - 删除师资:{}】",sz);
        //设置删除状态
        sz.setSfsc(true);
        sz.setGxr(getUserId());
        return szService.updateById(sz);
    }

    /**
     * 根据导师编号查询导师信息
     * @param dsbh
     * @return
     */
    @Override
    public Sz selectByDsbh(String dsbh) {
        log.info("【人才培养 - 根据导师编号:{}查询师资信息】",dsbh);
        QueryWrapper<Sz> szQueryWrapper = new QueryWrapper<>();
        szQueryWrapper.eq("sfsc",false)
                      .eq("dsbh",dsbh);
        return szMapper.selectOne(szQueryWrapper);
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
