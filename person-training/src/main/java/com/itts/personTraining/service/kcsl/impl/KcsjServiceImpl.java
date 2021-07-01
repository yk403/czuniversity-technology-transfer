package com.itts.personTraining.service.kcsl.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.dto.KcXsXfDTO;
import com.itts.personTraining.dto.XsMsgDTO;
import com.itts.personTraining.mapper.kcsj.KcsjMapper;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.model.kcsj.Kcsj;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.service.kcsl.KcsjService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.common.enums.ErrorCodeEnum.BATCH_NUMBER_ISEMPTY_NO_MSG_ERROR;

/**
 * <p>
 * 课程时间表 服务实现类
 * </p>
 *
 * @author liuyingming
 * @since 2021-04-28
 */
@Service
@Slf4j
public class KcsjServiceImpl extends ServiceImpl<KcsjMapper, Kcsj> implements KcsjService {

    @Autowired
    private KcsjMapper kcsjMapper;

    @Autowired
    private XsMapper xsMapper;
    @Autowired
    private PcXsMapper pcXsMapper;
    /**
     * 列表 - 分页
     */
    @Override
    public PageInfo<Kcsj> fingByPage(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        QueryWrapper query = new QueryWrapper();
        query.orderByAsc("kssj");
        List<Kcsj> kcsjs = kcsjMapper.selectList(query);

        PageInfo<Kcsj> pageInfo = new PageInfo<>(kcsjs);

        return pageInfo;
    }

    /**
     * 新增
     */
    @Override
    public Kcsj add(Kcsj kcsj) {

        Long userId = null;

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        Date now = new Date();

        kcsj.setCjr(userId);
        kcsj.setCjsj(now);
        kcsj.setGxr(userId);
        kcsj.setGxsj(now);

        kcsjMapper.insert(kcsj);

        return kcsj;
    }

    /**
     * 更新
     */
    @Override
    public Kcsj update(Kcsj kcsj) {

        Long userId = null;

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        kcsj.setGxsj(new Date());
        kcsj.setGxr(userId);

        kcsjMapper.updateById(kcsj);
        return kcsj;
    }

    /**
     * 删除
     */
    @Override
    public void delete(Kcsj kcsj) {

        Long userId = null;

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        kcsj.setGxsj(new Date());
        kcsj.setGxr(userId);
        kcsj.setSfsc(true);

        kcsjMapper.updateById(kcsj);
    }
}
