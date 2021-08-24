package com.itts.personTraining.mapper.xsKcCj;

import com.itts.personTraining.dto.XsKcCjDTO;
import com.itts.personTraining.model.xsCj.XixwExcel;
import com.itts.personTraining.model.xsCj.XsCjExcel;
import com.itts.personTraining.model.xsCj.YzyCjExcel;
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
     * 根据学生id和课程类型查询学生原专业课程成绩集合
     * @param xsId
     * @param kclx
     * @return
     */
    List<XsKcCjDTO> selectYzyByXsId(@Param("xsId") Long xsId, @Param("kclx") Integer kclx);

    /**
     * 根据学生id查询学生课程成绩(原专业)
     * @param xsId
     * @return
     */
    List<XsKcCjDTO> findXsKcCjByXsId(@Param("xsId") Long xsId);

    /**
     * 统计原专业学分(获得)
     * @param xsId
     * @return
     */
    Integer getCountYzy(@Param("xsId") Long xsId);

    /**
     * 根据学生成绩id查询当前学分总分(技术转移)
     * @param xsCjId
     * @return
     */
    Integer getCountDqxf(@Param("xsCjId") Long xsCjId);

    /**
     * 根据学生成绩id查询技术转移课程总分(前)
     * @param xsCjId
     * @return
     */
    Integer getCountJszykczf(@Param("xsCjId") Long xsCjId);

    /**
     * 统计原专业成绩
     * @param xsId
     * @return
     */
    Integer getAvgYzy(@Param("xsId") Long xsId);

    /**
     * 统计辅修专业成绩(理论,实训,实践)
     * @param xsCjId
     * @param kclx
     * @return
     */
    Integer getAvgfxcj(@Param("xsCjId") Long xsCjId, @Param("kclx") String kclx);

    /**
     * 根据学生成绩id和课程类型查询当前学分总和
     * @param xsCjId
     * @param kclx
     * @return
     */
    Integer getCountXf(@Param("xsCjId") Long xsCjId, @Param("kclx") String kclx);

    List<XsCjExcel> findByJxjy(@Param("pcId") Long pcId, @Param("jylx") String jylx);

    List<XixwExcel> findByXixw(@Param("pcId")Long pcId);

    List<YzyCjExcel> findByYzy(@Param("pcId")Long pcId);
}
