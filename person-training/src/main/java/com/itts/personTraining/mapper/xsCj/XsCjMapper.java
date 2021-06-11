package com.itts.personTraining.mapper.xsCj;

import com.itts.personTraining.dto.XsCjDTO;
import com.itts.personTraining.model.xsCj.XsCj;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 学生成绩表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-06-01
 */
public interface XsCjMapper extends BaseMapper<XsCj> {

    /**
     * 查询所有学生成绩(学历学位)
     * @param pcId
     * @param xh
     * @param xm
     * @param yx
     * @param xsId
     * @return
     */
    List<XsCjDTO> findXsKcCj(@Param("pcId") Long pcId, @Param("xh") String xh, @Param("xm") String xm, @Param("yx") String yx, @Param("xsId") Long xsId);

    /**
     * 查询所有学生成绩(继续教育)
     * @param pcId
     * @param xh
     * @param xm
     * @param yx
     * @return
     */
    List<XsCjDTO> findXsCj(@Param("pcId") Long pcId, @Param("xh") String xh, @Param("xm") String xm, @Param("yx") String yx);

    /**
     * 根据批次id和学生id查询学生成绩
     * @param pcId
     * @param xsId
     * @return
     */
    XsCj selectByPcIdAndXsId(@Param("pcId") Long pcId, @Param("xsId") Long xsId);

    /**
     * 根据学生id查询成绩通知数量
     * @param xsId
     * @return
     */
    Long getNumByXsId(@Param("xsId") Long xsId);

    /**
     * 根据id查询XsCjDTO对象
     * @param id
     * @return
     */
    XsCjDTO getById(@Param("id") Long id);
}
