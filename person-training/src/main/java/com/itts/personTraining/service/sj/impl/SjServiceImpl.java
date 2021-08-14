package com.itts.personTraining.service.sj.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.DateUtils;
import com.itts.personTraining.dto.SjDTO;
import com.itts.personTraining.dto.XsMsgDTO;
import com.itts.personTraining.mapper.pc.PcMapper;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.mapper.pk.PkMapper;
import com.itts.personTraining.mapper.pyjh.PyJhMapper;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.sj.Sj;
import com.itts.personTraining.mapper.sj.SjMapper;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.model.tz.Tz;
import com.itts.personTraining.model.tzSz.TzSz;
import com.itts.personTraining.model.tzXs.TzXs;
import com.itts.personTraining.model.xs.Xs;
import com.itts.personTraining.model.xsCj.XsCj;
import com.itts.personTraining.service.sj.SjService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.service.tz.TzService;
import com.itts.personTraining.service.tzSz.TzSzService;
import com.itts.personTraining.service.tzXs.TzXsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.common.enums.ErrorCodeEnum.TEACHER_MSG_NOT_EXISTS_ERROR;

/**
 * <p>
 * 实践表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-05-28
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SjServiceImpl extends ServiceImpl<SjMapper, Sj> implements SjService {

    @Autowired
    private  SjService sjService;
    @Autowired
    private TzXsService tzXsService;
    @Autowired
    private TzService tzService;
    @Autowired
    private TzSzService tzSzService;
    @Resource
    private SjMapper sjMapper;
    @Resource
    private PcMapper pcMapper;
    @Resource
    private XsMapper xsMapper;

    @Resource
    private PcXsMapper pcXsMapper;
    @Resource
    private SzMapper szMapper;
    @Resource
    private PkMapper pkMapper;
    @Resource
    private PyJhMapper pyJhMapper;


    /**
     * 获取实践列表
     * @param pageNum
     * @param pageSize
     * @param pcId
     * @param sjlx
     * @return
     */
    @Override
    public PageInfo<SjDTO> findByPage(Integer pageNum, Integer pageSize, Long pcId, String sjlx, String name) {
        log.info("【人才培养 - 根据pcId:{},实践类型:{},姓名/学号:{}获取实践列表】",pcId,sjlx,name);
        PageHelper.startPage(pageNum, pageSize);
        List<SjDTO> sjDTOs = sjMapper.getByCondition(pcId, sjlx, name);
        return new PageInfo<>(sjDTOs);
    }

    /**
     * 删除实践
     * @param sjDTO
     * @return
     */
    @Override
    public boolean delete(SjDTO sjDTO) {
        log.info("【人才培养 - 查询所有实践】");
        sjDTO.setGxr(getUserId());
        sjDTO.setSfsc(true);
        Sj sj = new Sj();
        BeanUtils.copyProperties(sjDTO,sj);
        return sjService.updateById(sj);
    }

    /**
     * 实践批量下发
     * @param ids
     * @return
     */
    @Override
    public boolean issueBatch(List<Long> ids) {
        log.info("【人才培养 - 实践批量下发,ids:{}】",ids);
        List<Sj> sjList = sjMapper.selectBatchIds(ids);
        Long userId = getUserId();
        for (Sj sj : sjList) {
            sj.setGxr(userId);
            sj.setSfxf(true);
            Pc pc = pcMapper.getPcById(sj.getPcId());
            List<Tz> tzList = new ArrayList<>();
            Tz tz = new Tz();
            tz.setCjr(userId);
            tz.setGxr(userId);
            tz.setTzlx("实践通知");
            tz.setTzmc(pc.getPch() + "实践通知" + DateUtils.getDateFormat(new Date()));
            List<Long> xsIds = xsMapper.findXsIdsByPcId(pc.getId());
            tz.setNr("您好，您此批次："+pc.getPch()+"，实践单位为："+sj.getSjdw()+"，实践时间为："+DateUtils.getDateFormat(sj.getKsrq())+"—"+DateUtils.getDateFormat(sj.getJsrq())+"，请悉知！");
            tzList.add(tz);
            Xs xs = xsMapper.selectById(sj.getXsId());
            Tz tz1 = new Tz();;
            if (xs.getYzydsId() != null || xs.getQydsId() != null) {
                tz1.setCjr(userId);
                tz1.setGxr(userId);
                tz1.setTzlx("实践通知");
                tz1.setTzmc(pc.getPch() + "实践通知" + DateUtils.getDateFormat(new Date()));
                tz1.setNr("您好，您批次："+pc.getPch()+"，学生："+xs.getXm()+"的实践单位为："+sj.getSjdw()+"，实践时间为："+DateUtils.getDateFormat(sj.getKsrq())+"—"+DateUtils.getDateFormat(sj.getJsrq())+"，请悉知！");
                tzList.add(tz1);
            }
            if (tzService.saveBatch(tzList)) {
                TzXs tzXs = new TzXs();
                tzXs.setXsId(sj.getXsId());
                tzXs.setTzId(tz.getId());
                if (!tzXsService.save(tzXs)) {
                    throw new ServiceException(INSERT_FAIL);
                }
                if (xs.getYzydsId() != null || xs.getQydsId() != null) {
                    List<TzSz> tzSzList =  new ArrayList<>();
                    if (xs.getYzydsId() != null) {
                        TzSz tzSz = new TzSz();
                        tzSz.setTzId(tz1.getId());
                        tzSz.setSzId(xs.getYzydsId());
                        tzSzList.add(tzSz);
                    }
                    if (xs.getQydsId() != null) {
                        TzSz tzSz = new TzSz();
                        tzSz.setTzId(tz1.getId());
                        tzSz.setSzId(xs.getQydsId());
                        tzSzList.add(tzSz);
                    }
                    if (!tzSzService.saveBatch(tzSzList)) {
                        throw new ServiceException(INSERT_FAIL);
                    }
                }
            } else {
                throw new ServiceException(INSERT_FAIL);
            }
        }
        return sjService.updateBatchById(sjList);
    }

    /**
     * 根据用户id查询实践信息列表
     * @return
     */
    @Override
    public List<SjDTO> findByYhId() {
        Long userId = getUserId();
        log.info("【人才培养 - 根据用户id:{}查询实践信息列表】",userId);
        List<SjDTO> sjDTOs = sjMapper.findByCondition(userId,null);
        return sjDTOs;
    }

    /**
     * 新增实践(前)
     * @param sjDTO
     * @return
     */
    @Override
    public boolean userAdd(SjDTO sjDTO) {
        log.info("【人才培养 - 新增实践(前):{}】",sjDTO);
        Long userId = getUserId();
        String userCategory = getUserCategory();
        boolean flag = false;
        switch (userCategory) {
            case "postgraduate":
            case "corporate_mentor":
                sjDTO.setCjr(userId);
                sjDTO.setGxr(userId);
                Sj sj = new Sj();
                BeanUtils.copyProperties(sjDTO, sj);
                flag = sjService.save(sj);
                break;
            default:
                break;
        }
        return flag;
    }

    /**
     * 更新实践(前)
     * @param sjDTO
     * @return
     */
    @Override
    public boolean userUpdate(SjDTO sjDTO) {
        log.info("【人才培养 - 更新实践:{}】",sjDTO);
        sjDTO.setGxr(getUserId());
        Sj sj = new Sj();
        BeanUtils.copyProperties(sjDTO,sj);
        return sjService.updateById(sj);
    }

    /**
     * 根据用户类别查询实践信息(前)
     * @return
     */
    @Override
    public List<SjDTO> findByCategory(Long pcId) {
        Long userId = getUserId();
        String userCategory = getUserCategory();
        log.info("【人才培养 - 根据创建人:{}查询实践信息(前),类别:{}】",userId,userCategory);
        List<SjDTO> sjDTOs = new ArrayList<SjDTO>();
        List<Pc>  pcList = null;
        switch (userCategory) {
            case "postgraduate":
                if(pcId != null){
                    XsMsgDTO xsMsg = xsMapper.getByYhId(userId);
                    if (xsMsg == null) {
                        throw new ServiceException(STUDENT_MSG_NOT_EXISTS_ERROR);
                    }
                    QueryWrapper<Sj> sjQueryWrapper = new QueryWrapper<>();
                    sjQueryWrapper.eq("pc_id",pcId)
                            .eq("xs_id",xsMsg.getId())
                            .eq("sfsc",false)
                            .eq("sfxf",true);
                    Sj sj = sjMapper.selectOne(sjQueryWrapper);
                    SjDTO sjDTO = new SjDTO();
                    BeanUtils.copyProperties(sj,sjDTO);
                    sjDTO.setXh(xsMsg.getXh());
                    sjDTO.setXm(xsMsg.getXm());
                    sjDTOs.add(sjDTO);
                }else {
                    XsMsgDTO xsMsg = xsMapper.getByYhId(userId);
                    if (xsMsg == null) {
                        throw new ServiceException(STUDENT_MSG_NOT_EXISTS_ERROR);
                    }
                    pcList = pcXsMapper.findPcByXsId(xsMsg.getId());
                    if(pcList == null){
                        return sjDTOs;
                    }
                    QueryWrapper<Sj> sjQueryWrapper = new QueryWrapper<>();
                    sjQueryWrapper.eq("pc_id",pcList.get(0).getId())
                            .eq("xs_id",xsMsg.getId())
                            .eq("sfsc",false)
                            .eq("sfxf",true);
                    Sj sj = sjMapper.selectOne(sjQueryWrapper);
                    SjDTO sjDTO = new SjDTO();
                    BeanUtils.copyProperties(sj,sjDTO);
                    sjDTO.setXh(xsMsg.getXh());
                    sjDTO.setXm(xsMsg.getXm());
                    sjDTOs.add(sjDTO);
                }
                break;
            case "tutor":
                if(pcId != null){
                    sjDTOs = sjMapper.getCondition(pcId);
                }else {
                    Sz yzyds = szMapper.getSzByYhId(userId);
                    if (yzyds == null) {
                        throw new ServiceException(TEACHER_MSG_NOT_EXISTS_ERROR);
                    }
                    pcList = pcXsMapper.findByYzydsIdOrQydsId(yzyds.getId(),null);
                    if(pcList == null){
                        return sjDTOs;
                    }
                    sjDTOs = sjMapper.getCondition(pcList.get(0).getId());
                }

                break;
            //企业导师
            case "corporate_mentor":
                if(pcId != null){
                    sjDTOs = sjMapper.getCondition(pcId);
                }else {
                    Sz qyds = szMapper.getSzByYhId(userId);
                    if (qyds == null) {
                        throw new ServiceException(TEACHER_MSG_NOT_EXISTS_ERROR);
                    }
                    pcList = pcXsMapper.findByYzydsIdOrQydsId(null,qyds.getId());
                    if(pcList == null){
                        return sjDTOs;
                    }
                    sjDTOs = sjMapper.getCondition(pcList.get(0).getId());
                }

                break;
            case "teacher":
                if(pcId != null){
                    sjDTOs = sjMapper.getCondition(pcId);
                }else {
                    Sz skjs = szMapper.getSzByYhId(userId);
                    if (skjs == null) {
                        throw new ServiceException(TEACHER_MSG_NOT_EXISTS_ERROR);
                    }
                    pcList = pkMapper.findPcsBySzId(skjs.getId());
                    if(pcList == null){
                        return sjDTOs;
                    }
                    sjDTOs = sjMapper.getCondition(pcList.get(0).getId());
                }

                break;
            case "school_leader":
            case "administrator":
            case "professor":
            case "out_professor":
                if(pcId != null){
                    sjDTOs = sjMapper.getCondition(pcId);
                }else {
                    pcList = pyJhMapper.findAllPc();
                    if(pcList == null){
                        return sjDTOs;
                    }
                    sjDTOs = sjMapper.getCondition(pcList.get(0).getId());
                }

                break;
            default:
                break;
        }
        return sjDTOs;
    }

    /**
     * 查询所有实践
     * @return
     */
    @Override
    public List<SjDTO> getAll() {
        log.info("【人才培养 - 查询所有实践】");
        List<SjDTO> sjDTOs = sjMapper.getByCondition(null,null,null);
        return sjDTOs;
    }

    /**
     * 新增实践
     * @param sjDTO
     * @return
     */
    @Override
    public boolean add(SjDTO sjDTO) {
        log.info("【人才培养 - 新增实践:{}】",sjDTO);
        Long userId = getUserId();
        sjDTO.setCjr(userId);
        sjDTO.setGxr(userId);
        Sj sj = new Sj();
        BeanUtils.copyProperties(sjDTO,sj);
        return sjService.save(sj);
    }

    /**
     * 根据id查询实践详情
     * @param id
     * @return
     */
    @Override
    public SjDTO get(Long id) {
        log.info("【人才培养 - 根据id:{}查询实践详情】",id);
        SjDTO sjDTO = sjMapper.getById(id);
        return sjDTO;
    }

    /**
     * 更新实践
     * @param sjDTO
     * @return
     */
    @Override
    public boolean update(SjDTO sjDTO) {
        log.info("【人才培养 - 更新实践:{}】",sjDTO);
        sjDTO.setGxr(getUserId());
        Sj sj = new Sj();
        BeanUtils.copyProperties(sjDTO,sj);
        return sjService.updateById(sj);
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
