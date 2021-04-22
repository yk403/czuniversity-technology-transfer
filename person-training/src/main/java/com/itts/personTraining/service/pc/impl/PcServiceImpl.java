package com.itts.personTraining.service.pc.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.personTraining.mapper.pc.PcMapper;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.service.pc.PcService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;

/**
 * <p>
 * 批次表 服务实现类
 * </p>
 *
 * @author FL
 * @since 2021-04-20
 */
@Service
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
        Pc pc = pcMapper.selectById(id);
        return pc;
    }

    @Override
    public List<Pc> getList(List<Long> ids) {
        List<Pc> pcs = pcMapper.selectPcList(ids);
        return pcs;
    }

    /**
     * 新增
     * @param pc
     * @return
     */
    @Override
    public Pc add(Pc pc) {
        LoginUser loginUser = threadLocal.get();
        Long userId=null;
        if(loginUser!=null){
            userId = loginUser.getUserId();
        }
        pc.setCjsj(new Date());
        pc.setGxsj(new Date());
        pc.setCjr(userId);
        pc.setGxr(userId);
        int insert = pcMapper.insert(pc);
        return pc;
    }

    /**
     * 更新
     * @param pc
     * @return
     */
    @Override
    public Pc update(Pc pc) {
        LoginUser loginUser = threadLocal.get();
        Long userId=null;
        if(loginUser!=null){
            userId = loginUser.getUserId();
        }
        pc.setGxsj(new Date());
        pc.setGxr(userId);
        int i = pcMapper.updateById(pc);
        return pc;
    }

    /**
     * 批量更新
     * @param ids
     * @return
     */
    @Override
    public Boolean updateBatch(List<Long> ids) {
        Boolean aBoolean = pcMapper.updatePcList(ids);
        return aBoolean;
    }
}
