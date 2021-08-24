package com.itts.personTraining.service.xsKcCj.impl;

import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.dto.XsCjDTO;
import com.itts.personTraining.dto.XsKcCjDTO;
import com.itts.personTraining.model.xsCj.XixwExcel;
import com.itts.personTraining.model.xsCj.XsCj;
import com.itts.personTraining.model.xsCj.XsCjExcel;
import com.itts.personTraining.model.xsKcCj.XsKcCj;
import com.itts.personTraining.mapper.xsKcCj.XsKcCjMapper;
import com.itts.personTraining.service.xsKcCj.XsKcCjService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;
import static com.itts.personTraining.enums.CourseTypeEnum.TECHNOLOGY_TRANSFER_COURSE;

/**
 * <p>
 * 学生课程成绩表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-06-01
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class XsKcCjServiceImpl extends ServiceImpl<XsKcCjMapper, XsKcCj> implements XsKcCjService {

    @Autowired
    private XsKcCjService xsKcCjService;
    @Resource
    private XsKcCjMapper xsKcCjMapper;

    /**
     * 根据学生成绩id和课程类型查询学生课程成绩集合
     * @param xsCjId
     * @param kclx
     * @param xsId
     * @return
     */
    @Override
    public XsCjDTO getByXsCjId(Long xsCjId, Integer kclx, Long xsId) {
        log.info("【人才培养 - 根据学生成绩id:{},课程类型:{}查询学生课程成绩集合】",xsCjId,kclx);
        XsCjDTO xsCjDTO = new XsCjDTO();
        List<XsKcCjDTO> xsKcCjDTOs;
        if (TECHNOLOGY_TRANSFER_COURSE.getKey().equals(kclx.toString())) {
            //技术转移专业课程
            xsKcCjDTOs = xsKcCjMapper.selectByXsCjId(xsCjId,kclx);
            xsCjDTO.setZxf(xsKcCjMapper.getCountDqxf(xsCjId));
            xsCjDTO.setJszykczf(xsKcCjMapper.getCountJszykczf(xsCjId));
        } else {
            //原专业课程
            xsKcCjDTOs = xsKcCjMapper.selectYzyByXsId(xsId,kclx);
            xsCjDTO.setZxzyzxf(xsKcCjMapper.getCountYzy(xsId));
        }
        xsCjDTO.setXsKcCjDTOList(xsKcCjDTOs);
        return xsCjDTO;
    }

    /**
     * 更新学生课程成绩
     * @param xsKcCjDTOs
     * @return
     */
    @Override
    public boolean update(List<XsKcCjDTO> xsKcCjDTOs) {
        log.info("【人才培养 - 更新学生课程成绩:{}】",xsKcCjDTOs);
        List<XsKcCj> xsKcCjs = new ArrayList<>();
        for (XsKcCjDTO xsKcCjDTO : xsKcCjDTOs) {
            XsKcCj xsKcCj = new XsKcCj();
            xsKcCjDTO.setGxr(getUserId());
            BeanUtils.copyProperties(xsKcCjDTO,xsKcCj);
            xsKcCjs.add(xsKcCj);
        }
        return xsKcCjService.updateBatchById(xsKcCjs);
    }

    /**
     * @author fuli
     * @param pcId
     * @param jylx
     * @return
     */
    @Override
    public List<XsCjExcel> getByPcId(Long pcId,String jylx) {
        List<XsCjExcel> jxjy = xsKcCjMapper.findByJxjy(pcId,jylx);
        return jxjy;
    }

    /**
     * @author fuli
     * @param pcId
     * @return
     */
    @Override
    public List<XixwExcel> findByXixw(Long pcId) {
        List<XixwExcel> byXixw = xsKcCjMapper.findByXixw(pcId);
        return byXixw;
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
