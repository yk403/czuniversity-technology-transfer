package com.itts.personTraining.service.xsCj.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.DateUtils;
import com.itts.personTraining.dto.*;
import com.itts.personTraining.mapper.kc.KcMapper;
import com.itts.personTraining.mapper.ks.KsMapper;
import com.itts.personTraining.mapper.pc.PcMapper;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.mapper.sj.SjMapper;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.mapper.xsKcCj.XsKcCjMapper;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.tz.Tz;
import com.itts.personTraining.model.tzSz.TzSz;
import com.itts.personTraining.model.tzXs.TzXs;
import com.itts.personTraining.model.xs.Xs;
import com.itts.personTraining.model.xsCj.XsCj;
import com.itts.personTraining.mapper.xsCj.XsCjMapper;
import com.itts.personTraining.model.xsKcCj.XsKcCj;
import com.itts.personTraining.service.pc.PcService;
import com.itts.personTraining.service.sj.SjService;
import com.itts.personTraining.service.tz.TzService;
import com.itts.personTraining.service.tzSz.TzSzService;
import com.itts.personTraining.service.tzXs.TzXsService;
import com.itts.personTraining.service.xsCj.XsCjService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.service.xsKcCj.XsKcCjService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.personTraining.enums.BmfsEnum.ON_LINE;
import static com.itts.personTraining.enums.CourseTypeEnum.*;
import static com.itts.personTraining.enums.EduTypeEnum.ACADEMIC_DEGREE_EDUCATION;
import static com.itts.personTraining.enums.EduTypeEnum.ADULT_EDUCATION;
import static com.itts.personTraining.enums.UserTypeEnum.BROKER;
import static com.itts.personTraining.enums.UserTypeEnum.POSTGRADUATE;

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
    @Autowired
    private TzService tzService;
    @Autowired
    private TzXsService tzXsService;
    @Autowired
    private TzSzService tzSzService;
    @Autowired
    private SjService sjService;
    @Resource
    private SjMapper sjMapper;
    @Resource
    private XsMapper xsMapper;
    @Resource
    private KcMapper kcMapper;
    @Resource
    private PcMapper pcMapper;
    @Resource
    private KsMapper ksMapper;
    @Resource
    private PcXsMapper pcXsMapper;

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
            Long id = xsCj.getId();
            List<XsKcCj> xsKcCjs = new ArrayList<>();
            if (xsKcCjDTOList != null && xsKcCjDTOList.size() > 0) {
                for (XsKcCjDTO xsKcCjDTO : xsKcCjDTOList) {
                    XsKcCj xsKcCj = new XsKcCj();
                    xsKcCjDTO.setXsCjId(id);
                    xsKcCjDTO.setKclx(Integer.parseInt(TECHNOLOGY_TRANSFER_COURSE.getKey()));
                    xsKcCjDTO.setCjr(userId);
                    xsKcCjDTO.setGxr(userId);
                    BeanUtils.copyProperties(xsKcCjDTO,xsKcCj);
                    xsKcCjs.add(xsKcCj);
                }
                return xsKcCjService.saveBatch(xsKcCjs);
            } else {
                Long pcId = xsCjDTO.getPcId();
                //Pc pcById = pcMapper.getPcById(pcId);
                List<Long> kcIds = kcMapper.findKcByPcId(pcId).stream().map(Kc::getId).collect(Collectors.toList());
                for (Long kcId : kcIds) {
                    XsKcCj xsKcCj = new XsKcCj();
                    xsKcCj.setXsCjId(id);
                    xsKcCj.setKcId(kcId);
                    xsKcCj.setKclx(Integer.parseInt(TECHNOLOGY_TRANSFER_COURSE.getKey()));
                    xsKcCj.setCjr(userId);
                    xsKcCj.setGxr(userId);
                    xsKcCjs.add(xsKcCj);
                }
                return xsKcCjService.saveBatch(xsKcCjs);
            }
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
        //前台不传教育类型和批次id,默认给学历学位成绩
        PageHelper.startPage(pageNum,pageSize);
        List<XsCjDTO> xsCjDTOs = null;
        if (pcId == null && jylx == null) {
            xsCjDTOs = getXsCjDTOS(pcId, xh, xm, yx);
        } else {
            if (ACADEMIC_DEGREE_EDUCATION.getKey().equals(jylx)) {
                //学历学位教育
                xsCjDTOs = getXsCjDTOS(pcId, xh, xm, yx);
            } else if (ADULT_EDUCATION.getKey().equals(jylx)) {
                //继续教育
                xsCjDTOs = xsCjMapper.findXsCj(pcId,xh,xm,yx,jylx);
            }
        }
        if (CollectionUtils.isEmpty(xsCjDTOs)) {
            return new PageInfo<>(Collections.EMPTY_LIST);
        }

        return new PageInfo<>(xsCjDTOs);
    }

    @Override
    public PageInfo<XsCjDTO> findPage(Integer pageNum, Integer pageSize, Long pcId, String xh, String xm, String yx, String jylx) {
        PageHelper.startPage(pageNum,pageSize);
        if(jylx == null){
            jylx = ACADEMIC_DEGREE_EDUCATION.getKey();
        }
        List<XsCjDTO> xs = xsCjMapper.findXs(pcId, xh, xm, yx,jylx);

        if (pcId == null && jylx == null) {
            for (XsCjDTO x : xs) {
                XsCjDTO byXsCjId = xsKcCjService.getByXsCjId(x.getId(), Integer.parseInt(TECHNOLOGY_TRANSFER_COURSE.getKey()), x.getXsId());
                x.setXsKcCjDTOList(byXsCjId.getXsKcCjDTOList());
                Integer zxf = x.getXsKcCjDTOList().stream().collect(Collectors.summingInt(XsKcCjDTO::getDqxf));
                x.setZxf(zxf);
            }
        } else {
            if (ACADEMIC_DEGREE_EDUCATION.getKey().equals(jylx)) {
                //学历学位教育
                for (XsCjDTO x : xs) {
                    XsCjDTO byXsCjId = xsKcCjService.getByXsCjId(x.getId(), Integer.parseInt(TECHNOLOGY_TRANSFER_COURSE.getKey()), x.getXsId());
                    x.setXsKcCjDTOList(byXsCjId.getXsKcCjDTOList());
                    Integer zxf = x.getXsKcCjDTOList().stream().collect(Collectors.summingInt(XsKcCjDTO::getDqxf));
                    x.setZxf(zxf);
                }

            } else if (ADULT_EDUCATION.getKey().equals(jylx)) {
                //继续教育
                List<XsCjDTO> xsCjDTOs = xsCjMapper.findXsCj(pcId, xh, xm, yx, jylx);
                if (CollectionUtils.isNotEmpty(xsCjDTOs)) {
                    for (XsCjDTO x : xs) {
                        for (XsCjDTO xsCjDTO : xsCjDTOs) {
                            if(Objects.equals(x.getXsId(),xsCjDTO.getXsId())) {

                                x.setZxf(xsCjDTO.getZxf());
                                x.setSfxf(xsCjDTO.getSfxf());
                                x.setType(xsCjDTO.getType());
                                x.setSfsc(xsCjDTO.getSfsc());
                                x.setZhcj(xsCjDTO.getZhcj());
                            }
                        }
                    }
                }
            }
        }
        if (CollectionUtils.isEmpty(xs)) {
            return new PageInfo<>(Collections.EMPTY_LIST);
        }

        return new PageInfo<>(xs);
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
        List<XsCj> xsCjList = new ArrayList<>();
        List<XsCjDTO> xsCjDTOList = xsCjMapper.findXsCjByIds(ids);
        Long userId = getUserId();
        for (XsCjDTO xsCjDTO : xsCjDTOList) {
            XsCj xsCj = new XsCj();
            xsCjDTO.setGxr(userId);
            xsCjDTO.setSfxf(true);
            BeanUtils.copyProperties(xsCjDTO,xsCj);
            xsCjList.add(xsCj);
            List<Tz> tzList = new ArrayList<>();
            Tz tz = new Tz();
            tz.setCjr(userId);
            tz.setGxr(userId);
            tz.setTzlx("成绩通知");
            tz.setTzmc(xsCjDTO.getPch() + "成绩通知" + DateUtils.getDateFormat(new Date()));
            tz.setNr("您好，您的批次："+ xsCjDTO.getPch() +"成绩结果已出，请悉知！");
            tzList.add(tz);
            //根据学生成绩Id查询学生id
            Long xsId = xsCjMapper.findXsIdsByXsCjId(xsCjDTO.getId());
            Xs xs = xsMapper.selectById(xsId);
            Tz tz1 = new Tz();
            if (xs.getYzydsId() != null || xs.getQydsId() != null) {
                tz1.setCjr(userId);
                tz1.setGxr(userId);
                tz1.setTzlx("成绩通知");
                tz1.setTzmc(xsCjDTO.getPch() + "成绩通知" + DateUtils.getDateFormat(new Date()));
                tz1.setNr("您好，您的"+xs.getXm()+"学生的批次："+ xsCjDTO.getPch() +"的成绩结果已出，请悉知！");
                tzList.add(tz1);
            }
            if (tzService.saveBatch(tzList)) {
                TzXs tzXs = new TzXs();
                tzXs.setXsId(xsId);
                tzXs.setTzId(tz.getId());
                if (!tzXsService.save(tzXs)) {
                    throw new ServiceException(INSERT_FAIL);
                }
                if (xs.getYzydsId() != null || xs.getQydsId() != null) {
                    List<TzSz> tzSzList = new ArrayList<>();
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
                    if(!tzSzService.saveBatch(tzSzList)) {
                        throw new ServiceException(INSERT_FAIL);
                    }
                }
            } else {
                throw new ServiceException(INSERT_FAIL);
            }
        }
        if (!xsCjService.updateBatchById(xsCjList)) {
            throw new ServiceException(UPDATE_FAIL);
        }
        return true;
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
                        //TODO:互认学分暂时为0
                        xsDTO.setHrxf(0);
                        XsCjDTO xsCjDTO = null;
                        if (ACADEMIC_DEGREE_EDUCATION.getKey().equals(pc.getJylx())) {
                            //学历学位教育
                            xsCjDTO = xsCjMapper.findXsKcCjByPcIdAndXsId(pcId, xsId);
                            List<XsKcCjDTO> xsKcCjDTOs = xsKcCjMapper.findXsKcCjByXsId(xsId);
                            if (xsCjDTO != null) {
                                xsCjDTO.getXsKcCjDTOList().addAll(xsKcCjDTOs);
                                //实训学分
                                xsDTO.setSxxf(xsKcCjMapper.getCountXf(xsCjDTO.getId(),PRACTICAL_TRAINING.getKey()));
                                //实践学分
                                xsDTO.setSjxf(xsKcCjMapper.getCountXf(xsCjDTO.getId(),PRACTICE_COURSE.getKey()));
                                //统计原专业学分(获得)
                                Integer zxzyxf = xsKcCjMapper.getCountYzy(xsId);
                                xsDTO.setZxzyxf(zxzyxf);
                                //统计技术转移专业获得学分
                                Integer jszylyxf = xsKcCjMapper.getCountDqxf(xsCjDTO.getId());
                                xsDTO.setJszylyxf(jszylyxf);
                            } else {
                                throw new ServiceException(NO_STUDENT_MSG_ERROR);
                            }
                        } else if (ADULT_EDUCATION.getKey().equals(pc.getJylx())) {
                            //继续教育
                            xsCjDTO = xsCjMapper.findXsCjByPcIdAndXsId(pcId, xsId);
                            //通过批次id查询出对应所有课程
                            List<XsKcCjDTO> xsKcCjDTOs = kcMapper.findXsKcCjByPcId(pcId);
                            xsCjDTO.setXsKcCjDTOList(xsKcCjDTOs);
                        }
                        Integer zxf = xsDTO.getZxzyxf() + xsDTO.getHrxf() + xsDTO.getJszylyxf();
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
                    Long kssjId = null;
                    if (ON_LINE.getMsg().equals(xsMsgDTO1.getBmfs())) {
                        kssjId = ksMapper.getByPcIdAndKslb(pcId,ON_LINE.getMsg());
                    }
                    XsCjDTO xsCjDTO = xsCjMapper.selectByPcIdAndXsId(pcId, xsMsgDTO1.getId());
                    PageInfo<XsKcCjDTO> xsKcCjDTOPageInfo = null;
                    if (xsCjDTO != null) {
                        xsCjDTO.setKssjId(kssjId);
                        //通过批次id查询出对应所有课程
                        PageHelper.startPage(pageNum,pageSize);
                        List<XsKcCjDTO> xsKcCjDTOs = kcMapper.findXsKcCjByPcId(pcId);
                        xsKcCjDTOPageInfo = new PageInfo<>(xsKcCjDTOs);
                    }
                    if (xsKcCjDTOPageInfo != null) {
                        map.put("pageInfo",xsKcCjDTOPageInfo);
                    }
                    map.put("broker",xsCjDTO);
                } else {
                    map.put("broker",null);
                }
                break;
            //研究生导师
            case "tutor":
                //根据师资用户id查询学生ids
                List<Long> xsIds = xsMapper.findXsIdsBySzYhId(userId);
                PageHelper.startPage(pageNum,pageSize);
                List<XsCjDTO> xsCjDTOs = xsCjMapper.findXsCjByXsIdsAndPcIds(xsIds,pcId,name);
                if (CollectionUtils.isNotEmpty(xsCjDTOs)) {
                    PageInfo<XsCjDTO> xsCjPageInfo = new PageInfo<>(xsCjDTOs);
                    map.put("tutor",xsCjPageInfo);
                } else {
                    map.put("tutor",null);
                }
                break;
            //企业导师
            case "corporate_mentor":
                //根据师资用户id查询学生ids
                List<Long> xsList = xsMapper.findXsIdsByQydsYhId(userId);
                PageHelper.startPage(pageNum,pageSize);
                List<XsCjDTO> xsCjDTOList = xsCjMapper.findXsCjByXsIdsAndPcIds(xsList,pcId,name);
                if (CollectionUtils.isNotEmpty(xsCjDTOList)) {
                    PageInfo<XsCjDTO> xsCjPageInfo = new PageInfo<>(xsCjDTOList);
                    map.put("corporate_mentor",xsCjPageInfo);
                } else {
                    map.put("corporate_mentor",null);
                }
                break;
            //任课教师
            case "teacher":
                if (pcId == null) {
                    throw new ServiceException(BATCH_NUMBER_ISEMPTY_ERROR);
                }
                //根据用户id查询师资(调了单独接口)
                /*Sz sz = szMapper.getSzByYhId(getUserId());
                List<Long> pcIdList = pkMapper.findPcIdsBySzId(sz.getId());
                if (CollectionUtils.isEmpty(pcIdList)) {
                    throw new ServiceException(SYSTEM_NOT_FIND_ERROR);
                }
                if (pcId == null) {
                    //默认查询最新批次信息
                    pcId = pcIdList.get(0);
                }*/
                PageHelper.startPage(pageNum,pageSize);
                List<XsCjDTO> xsCjlist = xsCjMapper.findXsCjByPcIdAndName(pcId,name);
                if (CollectionUtils.isEmpty(xsCjlist)) {
                    map.put("teacher",null);
                } else {
                    List<Long> xsIdList = new ArrayList<>();
                    for (XsCjDTO xsCjDTO : xsCjlist) {
                        xsIdList.add(xsCjDTO.getXsId());
                    }
                    //查询出报名方式是线上的学生ids
                    List<Long> xsxsIds = xsMapper.findXsIdsByBmfs(xsIdList, ON_LINE.getMsg());
                    //通过批次id和考试类别查出对应的试卷id
                    Long kssjId = ksMapper.getByPcIdAndKslb(pcId,ON_LINE.getMsg());
                    for (XsCjDTO xsCjDTO : xsCjlist) {
                        for (Long xsxsId : xsxsIds) {
                            if (xsxsId.equals(xsCjDTO.getXsId())) {
                                xsCjDTO.setKssjId(kssjId);
                            }
                        }
                    }
                    PageInfo<XsCjDTO> xsCjPageInfo = new PageInfo<>(xsCjlist);
                    map.put("teacher",xsCjPageInfo);
                }
                break;
            //管理员
            case "administrator":
            case "school_leader":
            case "professor":
            case "out_professor":
                PageHelper.startPage(pageNum,pageSize);
                //根据批次查询学生成绩
                List<XsCjDTO> xsCjDTOS = xsCjMapper.findXsCjBYPcId(pcId,name);
                PageInfo<XsCjDTO> xsCjPageInfo = new PageInfo<>(xsCjDTOS);
                map.put("other",xsCjPageInfo);
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
            case "broker":
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
     * 查询学生能力提升信息
     * @param pageNum
     * @param pageSize
     * @param pcId
     * @param xsId
     * @return
     */
    @Override
    public AbilityInfoDTO getAbilityByCategory(Integer pageNum, Integer pageSize, Long pcId, Long xsId) {
        Long userId = getUserId();
        log.info("【人才培养 - 根据用户id:{}查询学生能力提升信息】",userId);
        AbilityInfoDTO abilityInfoDTO = new AbilityInfoDTO();
        String userCategory;
        Pc pc;
        if (xsId == null) {
            XsMsgDTO byYhId = xsMapper.getByYhId(userId);
            xsId = byYhId.getId();
            userCategory = getUserCategory();
            if (xsId == null) {
                throw new ServiceException(NO_STUDENT_MSG_ERROR);
            }
            if (pcId == null) {
                List<Pc>  pcList = pcXsMapper.findPcByXsId(xsId);
                if (CollectionUtils.isEmpty(pcList)) {
                    throw new ServiceException(BATCH_NUMBER_ISEMPTY_NO_MSG_ERROR);
                }
                //批次为空,则默认最新批次
                pc = pcList.get(0);
                pcId = pc.getId();
            }
        } else {
            if (pcId == null) {
                List<Pc>  pcList = pcXsMapper.findPcByXsId(xsId);
                if (CollectionUtils.isEmpty(pcList)) {
                    throw new ServiceException(BATCH_NUMBER_ISEMPTY_NO_MSG_ERROR);
                }
                //批次为空,则默认最新批次
                pc = pcList.get(0);
                pcId = pc.getId();
            } else {
                pc = pcMapper.selectOne(new QueryWrapper<Pc>().eq("sfsc", false).eq("id", pcId));
            }
            String jylx = pc.getJylx();
            if (jylx.equals(ACADEMIC_DEGREE_EDUCATION.getKey())) {
                userCategory = POSTGRADUATE.getKey();
            } else {
                userCategory = BROKER.getKey();
            }
        }
        XsCjDTO xsCjDTO1 = xsCjMapper.selectByPcIdAndXsId(pcId, xsId);
        switch (userCategory) {
            //研究生
            case "postgraduate":
                //统计原专业成绩
                Integer zycj = xsKcCjMapper.getAvgYzy(xsId);
                abilityInfoDTO.setZycj(zycj);
                if (xsCjDTO1 != null) {
                    //统计辅修专业成绩(技术转移理论成绩)
                    Integer fxcj = xsKcCjMapper.getAvgfxcj(xsCjDTO1.getId(), THEORY_CLASS.getKey());
                    abilityInfoDTO.setFxcj(fxcj);
                } else {
                    throw new ServiceException(NO_STUDENT_MSG_ERROR);
                }
                //实践成绩
                abilityInfoDTO.setSjcj(Integer.parseInt(sjMapper.selectSjcjByXsIdAndPcId(xsId,pcId)));
                //实训成绩
                abilityInfoDTO.setSxcj(xsKcCjMapper.getAvgfxcj(xsCjDTO1.getId(), PRACTICAL_TRAINING.getKey()));
                abilityInfoDTO.setXl(30);
                break;
            //经纪人
            case "broker":
                if (xsCjDTO1 != null) {
                    //统计辅修专业成绩(技术转移理论成绩)
                    abilityInfoDTO.setFxcj(Integer.parseInt(xsCjDTO1.getZhcj()));
                } else {
                    throw new ServiceException(NO_STUDENT_MSG_ERROR);
                }
                //实训成绩
                abilityInfoDTO.setSxcj(xsKcCjMapper.getAvgfxcj(xsCjDTO1.getId(), PRACTICAL_TRAINING.getKey()));
                break;
            default:
                break;
        }
        return abilityInfoDTO;
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

    /**
     * 通过条件查询学生成绩列表(方法)
     * @param pcId
     * @param xh
     * @param xm
     * @param yx
     * @return
     */
    private List<XsCjDTO> getXsCjDTOS(Long pcId, String xh, String xm, String yx) {
        List<XsCjDTO> xsCjDTOs;//学历学位教育
        xsCjDTOs = xsCjMapper.findXsKcCj(pcId, xh, xm, yx, null);
        if (CollectionUtils.isNotEmpty(xsCjDTOs)) {
            for (XsCjDTO xsCjDTO : xsCjDTOs) {
                List<XsKcCjDTO> xsKcCjDTOList = xsCjDTO.getXsKcCjDTOList();
                Integer zxf = xsKcCjDTOList.stream().collect(Collectors.summingInt(XsKcCjDTO::getDqxf));
                xsCjDTO.setZxf(zxf);
            }
        } else {
            return null;
        }
        return xsCjDTOs;
    }
}
