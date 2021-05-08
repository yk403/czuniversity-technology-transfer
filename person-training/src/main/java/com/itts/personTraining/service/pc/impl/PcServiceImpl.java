package com.itts.personTraining.service.pc.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.mapper.pc.PcMapper;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.service.pc.PcService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 * 批次表 服务实现类
 * </p>
 *
 * @author FL
 * @since 2021-04-20
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class PcServiceImpl implements PcService {

    @Resource
    private PcMapper pcMapper;

    /**
     * 获取分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Pc> findByPage(Integer pageNum, Integer pageSize) {
        log.info("【人才培养 - 分页查询批次】");
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Pc> pcQueryWrapper = new QueryWrapper<>();
        pcQueryWrapper.eq("sfsc",false);
        List<Pc> pcs = pcMapper.selectList(pcQueryWrapper);
        PageInfo<Pc> pcPageInfo = new PageInfo<>(pcs);
        return pcPageInfo;
    }

    /**
     * 通过id获取详情
     * @param id
     * @return
     */
    @Override
    public Pc get(Long id) {
        log.info("【人才培养 - 根据id:{}查询详情】",id);
        return pcMapper.selectById(id);
    }

    /**
     * 根据ids查询批次集合
     * @param ids
     * @return
     */
    @Override
    public List<Pc> getList(List<Long> ids) {
        log.info("【人才培养 - 根据ids:{}查询批次信息】",ids);
        return pcMapper.selectPcList(ids);
    }

    /**
     * 新增
     * @param pc
     * @return
     */
    @Override
    public boolean add(Pc pc) {
        log.info("【人才培养 - 新增批次:{}】", pc);
        Long userId = getUserId();
        pc.setCjr(userId);
        pc.setGxr(userId);
        return pcMapper.insert(pc) > 0;
    }

    /**
     * 更新
     * @param pc
     * @return
     */
    @Override
    public boolean update(Pc pc) {
        log.info("【人才培养 - 更新批次:{}】",pc);
        pc.setGxr(getUserId());
        return pcMapper.updateById(pc) > 0;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @Override
    public boolean updateBatch(List<Long> ids) {
        log.info("【人才培养 - 批量删除批次ids:{}】",ids);
        return pcMapper.updatePcList(ids);
    }

    /**
     * 删除批次
     * @param pc
     * @return
     */
    @Override
    public boolean delete(Pc pc) {
        log.info("【人才培养 - 删除批次pc:{}】",pc);
        pc.setSfsc(true);
        pc.setGxr(getUserId());
        return pcMapper.updateById(pc) > 0;
    }

    /**
     * 根据数据字典ID查询批次信息
     * @param sjzdId
     * @return
     */
    @Override
    public List<Pc> getBySjzdId(Long sjzdId) {
        log.info("【人才培养 - 根据数据字典ID:{}查询批次信息】",sjzdId);
        QueryWrapper<Pc> pcQueryWrapper = new QueryWrapper<>();
        pcQueryWrapper.eq("sfsc",false)
                .eq("sjzd_id",sjzdId);
        return pcMapper.selectList(pcQueryWrapper);
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
