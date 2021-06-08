package com.itts.personTraining.service.zj.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.zj.Zj;
import com.itts.personTraining.mapper.zj.ZjMapper;
import com.itts.personTraining.service.zj.ZjService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 * 专家表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-05-25
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ZjServiceImpl extends ServiceImpl<ZjMapper, Zj> implements ZjService {
    @Autowired
    private ZjService zjService;
    @Resource
    private ZjMapper zjMapper;

    /**
     * 分页查询专家列表
     * @param pageNum
     * @param pageSize
     * @param yjly
     * @param name
     * @return
     */
    @Override
    public PageInfo<Zj> findByPage(Integer pageNum, Integer pageSize, String yjly, String name) {
        log.info("【人才培养 - 分页条件查询专家列表,研究类型:{},姓名:{}】",yjly,name);
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Zj> zjQueryWrapper = new QueryWrapper<>();
        zjQueryWrapper.eq("sfsc",false)
                      .eq(StringUtils.isNotBlank(yjly),"yjly",yjly)
                      .like(StringUtils.isNotBlank(name),"xm",name);
        List<Zj> zjList = zjMapper.selectList(zjQueryWrapper);
        return new PageInfo<>(zjList);
    }

    /**
     * 查询所有专家
     * @return
     */
    @Override
    public List<Zj> getAll() {
        log.info("【人才培养 - 查询所有专家】");
        QueryWrapper<Zj> zjQueryWrapper = new QueryWrapper<>();
        zjQueryWrapper.eq("sfsc",false);
        return zjMapper.selectList(zjQueryWrapper);
    }

    /**
     * 新增专家
     * @param zj
     * @return
     */
    @Override
    public boolean add(Zj zj) {
        log.info("【人才培养 - 新增专家:{}】",zj);
        Long userId = getUserId();
        zj.setCjr(userId);
        zj.setGxr(userId);
        return zjService.save(zj);
    }

    /**
     * 根据id查询专家信息
     * @param id
     * @return
     */
    @Override
    public Zj get(Long id) {
        log.info("【人才培养 - 根据id:{}查询专家信息】",id);
        QueryWrapper<Zj> zjQueryWrapper = new QueryWrapper<>();
        zjQueryWrapper.eq("sfsc",false)
                      .eq("id",id);
        return zjMapper.selectOne(zjQueryWrapper);
    }

    /**
     * 更新专家信息
     * @param zj
     * @return
     */
    @Override
    public boolean update(Zj zj) {
        log.info("【人才培养 - 更新专家:{}信息】",zj);
        zj.setGxr(getUserId());
        return zjService.updateById(zj);
    }

    /**
     * 删除专家信息
     * @param zj
     * @return
     */
    @Override
    public boolean delete(Zj zj) {
        log.info("【人才培养 - 更新专家:{}信息】",zj);
        zj.setGxr(getUserId());
        zj.setSfsc(true);
        return zjService.updateById(zj);
    }

    /**
     * 根据姓名电话查询专家信息
     * @param xm
     * @param dh
     * @return
     */
    @Override
    public Zj getByXmDh(String xm, String dh) {
        log.info("【人才培养 - 根据姓名:{},电话:{}查询专家信息】",xm,dh);
        QueryWrapper<Zj> zjQueryWrapper = new QueryWrapper<>();
        zjQueryWrapper.eq("sfsc",false)
                .eq(StringUtils.isNotBlank(xm),"xm",xm)
                .eq(StringUtils.isNotBlank(dh),"dh",dh);
        return zjMapper.selectOne(zjQueryWrapper);
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
