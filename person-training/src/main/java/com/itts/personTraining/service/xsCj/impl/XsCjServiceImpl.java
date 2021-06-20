package com.itts.personTraining.service.xsCj.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.dto.XfDTO;
import com.itts.personTraining.dto.XsCjDTO;
import com.itts.personTraining.dto.XsKcCjDTO;
import com.itts.personTraining.dto.XsMsgDTO;
import com.itts.personTraining.enums.UserTypeEnum;
import com.itts.personTraining.mapper.kc.KcMapper;
import com.itts.personTraining.mapper.pc.PcMapper;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.mapper.xsKcCj.XsKcCjMapper;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.ks.Ks;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.xsCj.XsCj;
import com.itts.personTraining.mapper.xsCj.XsCjMapper;
import com.itts.personTraining.model.xsKcCj.XsKcCj;
import com.itts.personTraining.service.pc.PcService;
import com.itts.personTraining.service.xsCj.XsCjService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.service.xsKcCj.XsKcCjService;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.personTraining.enums.CourseTypeEnum.TECHNOLOGY_TRANSFER_COURSE;
import static com.itts.personTraining.enums.EduTypeEnum.ACADEMIC_DEGREE_EDUCATION;
import static com.itts.personTraining.enums.EduTypeEnum.ADULT_EDUCATION;

/**
 * <p>
 * 学生成绩表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-06-01
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class XsCjServiceImpl extends ServiceImpl<XsCjMapper, XsCj> implements XsCjService {
    @Resource
    private XsCjMapper xsCjMapper;
    @Autowired
    private XsCjService xsCjService;
    @Autowired
    private XsKcCjService xsKcCjService;
    @Resource
    private XsKcCjMapper xsKcCjMapper;
    @Autowired
    private PcService pcService;
    @Resource
    private XsMapper xsMapper;
    @Resource
    private KcMapper kcMapper;
    @Resource
    private PcMapper pcMapper;

    /**
     * 根据批次id查询所有学生成绩
     * @return
     */
    @Override
    public List<XsCjDTO> getByPcId(Long pcId) {
        log.info("【人才培养 - 查询所有学生成绩】");
        //通过批次id查询教育类型
        List<XsCjDTO> xsCjDTOs = null;
        Pc pc = pcService.get(pcId);
        if (ACADEMIC_DEGREE_EDUCATION.getKey().equals(pc.getJylx())) {
            //学历学位教育
            xsCjDTOs = xsCjMapper.findXsKcCj(pcId,null,null,null,null);
        } else if (ADULT_EDUCATION.getKey().equals(pc.getJylx())) {
            //继续教育
            xsCjDTOs = xsCjMapper.findXsCj(pcId,null,null,null,pc.getJylx());
        }
        return xsCjDTOs;
    }

    /**
     * 新增学生成绩
     * @param xsCjDTO
     * @return
     */
    @Override
    public boolean add(XsCjDTO xsCjDTO) {
        log.info("【人才培养 - 新增学生成绩:{}】",xsCjDTO);
        Long userId = getUserId();
        xsCjDTO.setCjr(userId);
        xsCjDTO.setGxr(userId);
        XsCj xsCj = new XsCj();
        BeanUtils.copyProperties(xsCjDTO,xsCj);
        if (xsCjService.save(xsCj)) {
            List<XsKcCjDTO> xsKcCjDTOList = xsCjDTO.getXsKcCjDTOList();
            if (xsKcCjDTOList != null && xsKcCjDTOList.size() > 0) {
                Long id = xsCj.getId();
                List<XsKcCj> xsKcCjs = new ArrayList<>();
                int totalNum = 0;
                int jszykczf = 0;
                for (XsKcCjDTO xsKcCjDTO : xsKcCjDTOList) {
                    XsKcCj xsKcCj = new XsKcCj();
                    xsKcCjDTO.setXsCjId(id);
                    xsKcCjDTO.setKclx(TECHNOLOGY_TRANSFER_COURSE.getKey());
                    xsKcCjDTO.setCjr(userId);
                    xsKcCjDTO.setGxr(userId);
                    Integer dqxf = xsKcCjDTO.getDqxf();
                    totalNum += dqxf;
                    BeanUtils.copyProperties(xsKcCjDTO,xsKcCj);
                    xsKcCjs.add(xsKcCj);
                    Kc kc = kcMapper.selectById(xsKcCjDTO.getKcId());
                    jszykczf += kc.getKcxf();
                }
                return xsKcCjService.saveBatch(xsKcCjs);
            }
            return true;
        }
        return false;
    }

    /**
     * 删除学生成绩
     * @param xsCjDTO
     * @return
     */
    @Override
    public boolean delete(XsCjDTO xsCjDTO) {
        log.info("【人才培养 - 删除学生成绩:{}】",xsCjDTO);
        XsCj xsCj = new XsCj();
        BeanUtils.copyProperties(xsCjDTO,xsCj);
        xsCj.setSfsc(true);
        xsCj.setGxr(getUserId());
        return xsCjService.updateById(xsCj);
    }

    /**
     * 根据id查询XsCjDTO对象
     * @param id
     * @return
     */
    @Override
    public XsCjDTO get(Long id) {
        log.info("【人才培养 - 根据id:{}查询XsCj对象】",id);
        XsCjDTO xsCjDTO = xsCjMapper.getById(id);
        return xsCjDTO;
    }

    /**
     * 根据pcId查询XsCj对象
     * @param pcId
     * @return
     */
    @Override
    public List<XsCj> getXsCjByPcId(Long pcId) {
        log.info("【人才培养 - 根据piId查询XsCj对象:{}】",pcId);
        QueryWrapper<XsCj> xsCjQueryWrapper = new QueryWrapper<>();
        xsCjQueryWrapper.eq("sfsc",false)
                        .eq("pc_id",pcId);
        List<XsCj> xsCjs = xsCjMapper.selectList(xsCjQueryWrapper);
        return xsCjs;
    }

    /**
     * 分页条件查询学生成绩
     * @param pageNum
     * @param pageSize
     * @param pcId
     * @param xh
     * @param xm
     * @param yx
     * @return
     */
    @Override
    public PageInfo<XsCjDTO> findByPage(Integer pageNum, Integer pageSize, Long pcId, String xh, String xm, String yx, String jylx) {
        log.info("【人才培养 - 根据条件批次ID:{},学号:{},姓名:{},学院名称:{},教育类型:{}分页查询学生成绩】",pcId,xh,xm,yx,jylx);
        //前台第一次展示暂无数据,以后批次ID必传
        PageHelper.startPage(pageNum,pageSize);
        List<XsCjDTO> xsCjDTOs = null;
        if (ACADEMIC_DEGREE_EDUCATION.getKey().equals(jylx)) {
            //学历学位教育
            xsCjDTOs = xsCjMapper.findXsKcCj(pcId,xh,xm,yx,null);
        } else if (ADULT_EDUCATION.getKey().equals(jylx)) {
            //继续教育
            xsCjDTOs = xsCjMapper.findXsCj(pcId,xh,xm,yx,jylx);
        }
        return new PageInfo<>(xsCjDTOs);
    }

    /**
     * 更新学生成绩
     * @param xsCjDTO
     * @return
     */
    @Override
    public boolean update(XsCjDTO xsCjDTO) {
        log.info("【人才培养 - 更新学生成绩:{}】",xsCjDTO);
        //学历学位:可修改论文成绩;继续教育:可修改综合成绩
        XsCj xsCj = new XsCj();
        xsCjDTO.setGxr(getUserId());
        BeanUtils.copyProperties(xsCjDTO,xsCj);
        return xsCjService.updateById(xsCj);
    }

    /**
     * 学生成绩批量下发
     * @param ids
     * @return
     */
    @Override
    public boolean issueBatch(List<Long> ids) {
        log.info("【人才培养 - 学生成绩批量下发,ids:{}】",ids);
        List<XsCj> xsCjList = xsCjMapper.selectBatchIds(ids);
        Long userId = getUserId();
        for (XsCj xsCj : xsCjList) {
            xsCj.setGxr(userId);
            xsCj.setSfxf(true);
        }
        return xsCjService.updateBatchById(xsCjList);
    }

    /**
     * 根据用户id查询学生成绩信息列表(前)
     * @return
     */
    @Override
    public List<XsCjDTO> getByYhId() {
        Long userId = getUserId();
        String userCategory = getUserCategory();
        log.info("【人才培养 - 根据用户id:{}查询学生成绩信息,用户所属分类:{}】",userId,userCategory);
        List<XsCjDTO> xsKcCj = null;
        switch (userCategory) {
            case "postgraduate":
            case "broker":
                XsMsgDTO xsMsgDTO = xsMapper.getByYhId(userId);
                xsKcCj = xsCjMapper.findXsKcCj(null, null, null, null, xsMsgDTO.getId());
                break;
            case "tutor":
                ;
                break;
            case "corporate_mentor":
                ;
                break;
            case "teacher":
                ;
                break;
            case "administrator":
                ;
                break;
            case "school_leader":
                ;
                break;
            default:
                break;
        }
        return xsKcCj;
    }

    /**
     * 查询学生成绩信息(综合信息)
     * @param pageNum
     * @param pageSize
     * @param pcId
     * @param name
     * @return
     */
    @Override
    public Map<String, Object> getByCategory(Integer pageNum,Integer pageSize,Long pcId,String name) {
        log.info("【人才培养 - 查询学生成绩信息(综合信息)】");
        PageHelper.startPage(pageNum,pageSize);
        Map<String, Object> map = new HashMap<>();
        Long userId = getUserId();
        String userCategory = getUserCategory();
        log.info("【人才培养 - 通过用户id:{},用户类别:{},批次id:{},学号/姓名:{}查询学生成绩信息(综合信息)】",userId,userCategory,pcId,name);
        List<XsCjDTO> xsKcCj;
        switch (userCategory) {
            //研究生
            case "postgraduate":
                XsMsgDTO xsMsgDTO = xsMapper.getByYhId(userId);
                Long xsId = xsMsgDTO.getId();
                if (xsId != null) {
                    if (pcId != null) {
                        Pc pc = pcMapper.selectById(pcId);
                        XfDTO xsDTO = new XfDTO();
                        //TODO:实践和实训待确认
                        xsDTO.setSxxf(0);
                        xsDTO.setSjxf(0);
                        //TODO:互认学分暂时为0
                        xsDTO.setHrxf(0);
                        XsCjDTO xsCjDTO = null;
                        if (ACADEMIC_DEGREE_EDUCATION.getKey().equals(pc.getJylx())) {
                            //学历学位教育
                            xsCjDTO = xsCjMapper.findXsKcCjByPcIdAndXsId(pcId, xsId);
                            List<XsKcCjDTO> xsKcCjDTOs = xsKcCjMapper.findXsKcCjByXsId(xsId);
                            if (xsCjDTO != null) {
                                xsCjDTO.getXsKcCjDTOList().addAll(xsKcCjDTOs);
                            }
                            //统计原专业学分(获得)
                            Integer zxzyxf = xsKcCjMapper.getCountYzy(xsId);
                            xsDTO.setZxzyxf(zxzyxf);
                            XsCjDTO xsCjDTO1 = xsCjMapper.selectByPcIdAndXsId(pcId, xsId);
                            if (xsCjDTO1 != null) {
                                //统计技术转移专业获得学分
                                Integer jszylyxf = xsKcCjMapper.getCountDqxf(xsCjDTO1.getId());
                                xsDTO.setJszylyxf(jszylyxf);
                            } else {
                                throw new ServiceException(NO_STUDENT_MSG_ERROR);
                            }
                        } else if (ADULT_EDUCATION.getKey().equals(pc.getJylx())) {
                            //继续教育
                            xsCjDTO = xsCjMapper.findXsCjByPcIdAndXsId(pcId, xsId);
                        }
                        Integer zxf = xsDTO.getZxzyxf() + xsDTO.getHrxf() + xsDTO.getJszylyxf() + xsDTO.getSjxf() + xsDTO.getSxxf();
                        xsDTO.setZxf(zxf);
                        map.put("xf", xsDTO);
                        map.put("postgraduate", xsCjDTO);
                    } else {
                        throw new ServiceException(BATCH_NUMBER_ISEMPTY_NO_MSG_ERROR);
                    }
                } else {
                    throw new ServiceException(NO_STUDENT_MSG_ERROR);
                }
                break;
            //经纪人
            case "broker":
                if (pcId == null) {
                    throw new ServiceException(BATCH_NUMBER_ISEMPTY_NO_MSG_ERROR);
                }
                XsMsgDTO xsMsgDTO1 = xsMapper.getByYhId(userId);
                if (xsMsgDTO1 != null) {
                    XsCjDTO xsCjDTO = xsCjMapper.selectByPcIdAndXsId(pcId, xsMsgDTO1.getId());
                    if (xsCjDTO != null) {
                        List<Kc> kcList = kcMapper.findKcByPcId(pcId);
                        List<XsKcCjDTO> xsKcCjDTOList = new ArrayList<>();
                        for (Kc kc : kcList) {
                            XsKcCjDTO xsKcCjDTO = new XsKcCjDTO();
                            BeanUtils.copyProperties(kc,xsKcCjDTO,"kclx");
                            xsKcCjDTOList.add(xsKcCjDTO);
                        }
                        xsCjDTO.setXsKcCjDTOList(xsKcCjDTOList);
                        map.put("broker",xsCjDTO);
                    }
                } else {
                    map.put("broker",null);
                }
                break;
            //研究生导师
            case "tutor":
            //企业导师
            case "corporate_mentor":
                //根据师资用户id查询学生ids
                List<Long> xsIds = xsMapper.findXsIdsBySzYhId(userId);
                //默认查询当前年份的批次号
                String currentYear = getCurrentYear();
                List<Long> pcIds = pcMapper.findPcIdsByYear(currentYear);
                if (CollectionUtils.isNotEmpty(xsIds) && CollectionUtils.isNotEmpty(pcIds)) {
                    List<XsCjDTO> xsCjDTOs = xsCjMapper.findXsCjByXsIdsAndPcIds(xsIds,pcIds,name);
                    PageInfo<XsCjDTO> xsCjPageInfo = new PageInfo<>(xsCjDTOs);
                    map.put("tutor",xsCjPageInfo);
                } else {
                    map.put("tutor",null);
                }
                break;
            //任课教师
            case "teacher":
                if (pcId == null) {
                    throw new ServiceException(BATCH_NUMBER_ISEMPTY_ERROR);
                }
                //先调排课接口查询该任课教师所有的批次号,通过批次号查询对应成绩列表
                Pc pc = pcMapper.selectById(pcId);
                List<XsCjDTO> xsCjDTOs = null;
                if (ACADEMIC_DEGREE_EDUCATION.getKey().equals(pc.getJylx())) {
                    xsCjDTOs = xsCjMapper.findXsKcCjByPcIdAndName(pcId,name);
                    PageInfo<XsCjDTO> xsCjPageInfo = new PageInfo<>(xsCjDTOs);
                    map.put("teacher",xsCjPageInfo);
                } else if (ADULT_EDUCATION.getKey().equals(pc.getJylx())) {
                    xsCjDTOs = xsCjMapper.findXsCjByPcIdAndName(pcId,name);
                }
                PageInfo<XsCjDTO> xsCjPageInfo = new PageInfo<>(xsCjDTOs);
                map.put("teacher",xsCjPageInfo);
                break;
            //管理员
            case "administrator":
                ;
                break;
            //校领导
            case "school_leader":
                ;
                break;
            default:
                break;
        }
        return map;
    }

    /**
     * 根据用户id查询批次集合
     * @return
     */
    @Override
    public List<Pc> findPcByYhId() {
        Long userId = getUserId();
        log.info("【人才培养 - 根据用户id:{}查询批次集合】",userId);
        XsMsgDTO xsMsgDTO = xsMapper.getByYhId(userId);
        String userCategory = getUserCategory();
        List<Pc> pcs = null;
        switch (userCategory) {
            //研究生
            case "postgraduate":
                if (xsMsgDTO.getId() != null) {
                    pcs = xsCjMapper.findPcByXsId(xsMsgDTO.getId());
                }
                break;
            default:
                break;
        }
        return pcs;
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


    /**
     * 获取当前年份
     * @return
     */
    public static String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }
}
