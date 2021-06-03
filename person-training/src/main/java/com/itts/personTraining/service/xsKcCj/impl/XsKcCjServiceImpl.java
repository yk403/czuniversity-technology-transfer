package com.itts.personTraining.service.xsKcCj.impl;

import com.itts.personTraining.dto.XsKcCjDTO;
import com.itts.personTraining.model.xsKcCj.XsKcCj;
import com.itts.personTraining.mapper.xsKcCj.XsKcCjMapper;
import com.itts.personTraining.service.xsKcCj.XsKcCjService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
     * @return
     */
    @Override
    public List<XsKcCjDTO> getByXsCjId(Long xsCjId,Integer kclx) {
        log.info("【人才培养 - 根据学生成绩id:{},课程类型:{}查询学生课程成绩集合】",xsCjId,kclx);
        List<XsKcCjDTO> xsKcCjDTOs;
        if (TECHNOLOGY_TRANSFER_COURSE.getKey().equals(kclx)) {
            //技术转移专业课程
            xsKcCjDTOs = xsKcCjMapper.selectByXsCjId(xsCjId,kclx);
        } else {
            //原专业课程
            xsKcCjDTOs = xsKcCjMapper.selectYzyByXsCjId(xsCjId,kclx);
        }
        return xsKcCjDTOs;
    }
}
