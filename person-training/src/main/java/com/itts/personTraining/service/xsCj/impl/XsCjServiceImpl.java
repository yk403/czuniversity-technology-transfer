package com.itts.personTraining.service.xsCj.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.dto.XsCjDTO;
import com.itts.personTraining.dto.XsKcCjDTO;
import com.itts.personTraining.dto.XsMsgDTO;
import com.itts.personTraining.enums.UserTypeEnum;
import com.itts.personTraining.mapper.kc.KcMapper;
import com.itts.personTraining.mapper.xs.XsMapper;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;
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
    @Autowired
    private PcService pcService;
    @Resource
    private XsMapper xsMapper;
    @Resource
    private KcMapper kcMapper;

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
                if (xsKcCjService.saveBatch(xsKcCjs)) {
                    //获得总分
                    xsCj.setZxf(totalNum);
                    //课程总分
                    xsCj.setJszykczf(jszykczf);
                    return xsCjService.updateById(xsCj);
                }
                return false;
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
     * @return
     */
    @Override
    public List<XsCjDTO> getByCategory() {
        Long userId = getUserId();
        String userCategory = getUserCategory();
        log.info("【人才培养 - 通过用户类别:{}查询学生成绩信息(综合信息)】",userId,userCategory);
        List<XsCjDTO> xsKcCj = null;
        switch (userCategory) {
            case "postgraduate":
                XsMsgDTO xsMsgDTO = xsMapper.getByYhId(userId);
                xsKcCj = xsCjMapper.findXsKcCj(null, null, null, null, xsMsgDTO.getId());
                break;
            case "broker":
                ;
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
