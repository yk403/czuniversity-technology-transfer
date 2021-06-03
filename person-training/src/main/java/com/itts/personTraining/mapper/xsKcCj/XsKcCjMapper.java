package com.itts.personTraining.mapper.xsKcCj;

import com.itts.personTraining.dto.XsKcCjDTO;
import com.itts.personTraining.model.xsKcCj.XsKcCj;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 学生课程成绩表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-06-01
 */
public interface XsKcCjMapper extends BaseMapper<XsKcCj> {

    /**
     * 根据学生成绩id和课程类型查询学生技术转移课程成绩集合
     * @param xsCjId
     * @param kclx
     * @return
     */
    List<XsKcCjDTO> selectByXsCjId(@Param("xsCjId") Long xsCjId, @Param("kclx") Integer kclx);

    /**
     * 根据学生成绩id和课程类型查询学生原专业课程成绩集合
     * @param xsCjId
     * @param kclx
     * @return
     */
    List<XsKcCjDTO> selectYzyByXsCjId(@Param("xsCjId") Long xsCjId, @Param("kclx") Integer kclx);
}
