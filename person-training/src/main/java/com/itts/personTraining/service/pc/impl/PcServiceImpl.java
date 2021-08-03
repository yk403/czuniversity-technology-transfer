package com.itts.personTraining.service.pc.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.XsMsgDTO;
import com.itts.personTraining.mapper.pc.PcMapper;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.mapper.pk.PkMapper;
import com.itts.personTraining.mapper.pyjh.PyJhMapper;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.service.pc.PcService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.service.sjzd.SjzdService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;

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
    @Resource
    private PcXsMapper pcXsMapper;
    @Resource
    private XsMapper xsMapper;
    @Resource
    private SzMapper szMapper;
    @Resource
    private PkMapper pkMapper;
    @Resource
    private PyJhMapper pyJhMapper;


    /**
     * 获取分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Pc> findByPage(Integer pageNum, Integer pageSize, String name) {
        log.info("【人才培养 - 分页查询批次】");
        if (pageNum == -1) {
            QueryWrapper<Pc> pcQueryWrapper = new QueryWrapper<>();
            pcQueryWrapper.eq("sfsc",false)
                          .orderByDesc("cjsj");
            List<Pc> pcs = pcMapper.selectList(pcQueryWrapper);
            return new PageInfo<>(pcs);
        }
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Pc> pcQueryWrapper = new QueryWrapper<>();
        pcQueryWrapper.eq("sfsc",false)
                      .like(StringUtils.isNotBlank(name),"pch",name).or()
                      .like(StringUtils.isNotBlank(name),"pcmc",name)
                      .orderByDesc("cjsj");
        List<Pc> pcs = pcMapper.selectList(pcQueryWrapper);
        return new PageInfo<>(pcs);
    }

    /**
     * 通过id获取详情
     * @param id
     * @return
     */
    @Override
    public Pc get(Long id) {
        log.info("【人才培养 - 根据id:{}查询详情】",id);
        QueryWrapper<Pc> pcQueryWrapper = new QueryWrapper<>();
        pcQueryWrapper.eq("sfsc",false)
                      .eq("id",id);
        return pcMapper.selectOne(pcQueryWrapper);
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
     * 获取所有批次详情
     * @return
     */
    @Override
    public List<Pc> getAll() {
        log.info("【人才培养 - 获取所有批次详情】");
        QueryWrapper<Pc> pcQueryWrapper = new QueryWrapper<>();
        pcQueryWrapper.eq("sfsc",false)
                      .orderByDesc("cjsj");
        return pcMapper.selectList(pcQueryWrapper);
    }

    /**
     * 根据id查询课程信息列表
     * @param id
     * @return
     */
    @Override
    public List<Kc> getKcListById(Long id) {
        log.info("【人才培养 - 根据id:{}查询课程信息列表】",id);
        List<Kc> kcList = pcMapper.findKcListById(id);
        return kcList;
    }

    /**
     * 根据用户查询批次列表(前)
     * @return
     */
    @Override
    public List<Pc> findByYh() {
        Long userId = getUserId();
        log.info("【人才培养 - 根据用户:{}查询批次列表(前)】",userId);
        String userCategory = getUserCategory();
        List<Pc>  pcList = null;
        switch (userCategory) {
            case "postgraduate":
            case "broker":
                XsMsgDTO xsMsg = xsMapper.getByYhId(userId);
                if (xsMsg == null) {
                    throw new ServiceException(STUDENT_MSG_NOT_EXISTS_ERROR);
                }
                pcList = pcXsMapper.findPcByXsId(xsMsg.getId());
                break;
            case "tutor":
                Sz yzyds = szMapper.getSzByYhId(userId);
                if (yzyds == null) {
                    throw new ServiceException(TEACHER_MSG_NOT_EXISTS_ERROR);
                }
                pcList = pcXsMapper.findByYzydsIdOrQydsId(yzyds.getId(),null);
                break;
            //企业导师
            case "corporate_mentor":
                Sz qyds = szMapper.getSzByYhId(userId);
                if (qyds == null) {
                    throw new ServiceException(TEACHER_MSG_NOT_EXISTS_ERROR);
                }
                pcList = pcXsMapper.findByYzydsIdOrQydsId(null,qyds.getId());
                break;
            case "teacher":
                Sz skjs = szMapper.getSzByYhId(userId);
                if (skjs == null) {
                    throw new ServiceException(TEACHER_MSG_NOT_EXISTS_ERROR);
                }
                pcList = pkMapper.findPcsBySzId(skjs.getId());
                break;
            case "school_leader":
            case "administrator":
                pcList = pyJhMapper.findAllPc();
                break;
            default:
                break;
        }
        return pcList;
    }

    /**
     * 查询未录入批次
     * @return
     */
    @Override
    public List<Pc> findPcs() {
        log.info("【人才培养 - 查询未录入批次】");
        List<Pc> pcList = pcMapper.findPcs();
        return pcList;
    }

    /**
     * 根据教育类型查询批次信息
     * @param jylx
     * @return
     */
    @Override
    public List<Pc> getByJylx(String jylx) {
        log.info("【人才培养 - 根据教育类型:{}查询批次信息】",jylx);
        QueryWrapper<Pc> pcQueryWrapper = new QueryWrapper<>();
        pcQueryWrapper.eq("sfsc",false)
                .eq("jylx",jylx);
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

    /**
     * 获取当前用户id所属类别
     * @return
     */
    private String getUserCategory() {
        LoginUser loginUser = threadLocal.get();
        String userCategory;
        if (loginUser != null) {
            userCategory = loginUser.getUserCategory();
        } else {
            throw new ServiceException(GET_THREADLOCAL_ERROR);
        }
        return userCategory;
    }

}
