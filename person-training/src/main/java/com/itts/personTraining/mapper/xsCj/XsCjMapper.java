package com.itts.personTraining.mapper.xsCj;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.personTraining.dto.XfDTO;
import com.itts.personTraining.dto.XsCjDTO;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.xsCj.XsCj;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 学生成绩表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-06-01
 */
@Repository
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

    List<XsCjDTO> findXs(@Param("pcId") Long pcId, @Param("xh") String xh, @Param("xm") String xm, @Param("yx") String yx, @Param("jylx") String jylx);

    /**
     * 查询所有学生成绩(继续教育)
     * @param pcId
     * @param xh
     * @param xm
     * @param yx
     * @param jylx
     * @return
     */
    List<XsCjDTO> findXsCj(@Param("pcId") Long pcId, @Param("xh") String xh, @Param("xm") String xm, @Param("yx") String yx, @Param("jylx") String jylx);

    /**
     * 根据批次id和学生id查询学生成绩
     * @param pcId
     * @param xsId
     * @return
     */
    XsCjDTO selectByPcIdAndXsId(@Param("pcId") Long pcId, @Param("xsId") Long xsId);

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

    /**
     * 根据学生ids和批次ids/学号/姓名查询学生成绩集合(前)
     * @param xsIds
     * @param pcIds
     * @param name
     * @return
     */
    List<XsCjDTO> findXsCjByXsIdsAndPcIds(@Param("xsIds") List<Long> xsIds, @Param("pcIds") List<Long> pcIds, @Param("name") String name);

    /**
     * 通过批次id和学号/姓名查询学生成绩集合(前)(学历学位教育)
     * @param pcId
     * @param name
     * @return
     */
    List<XsCjDTO> findXsKcCjByPcIdAndName(@Param("pcId") Long pcId, @Param("name") String name);

    /**
     * 通过批次id和学号/姓名查询学生成绩集合(前)(继续教育)
     * @param pcId
     * @param name
     * @return
     */
    List<XsCjDTO> findXsCjByPcIdAndName(@Param("pcId") Long pcId, @Param("name") String name);

    /**
     * 通过学生id查询学分
     * @param xsId
     * @return
     */
    XfDTO getXfByXsId(@Param("xsId") Long xsId);

    /**
     * 通过批次id和学生id查询学生成绩信息(前)(学历学位教育)
     * @param pcId
     * @param xsId
     * @return
     */
    XsCjDTO findXsKcCjByPcIdAndXsId(@Param("pcId") Long pcId, @Param("xsId") Long xsId);

    /**
     * 通过批次id和学生id查询学生成绩信息(前)(继续教育)
     * @param pcId
     * @param xsId
     * @return
     */
    XsCjDTO findXsCjByPcIdAndXsId(@Param("pcId") Long pcId, @Param("xsId") Long xsId);

    /**
     * 根据学生id查询批次信息(前)
     * @param xsId
     * @return
     */
    List<Pc> findPcByXsId(@Param("xsId") Long xsId);

    /**
     * 根据ids查询学生成绩集合
     * @param ids
     * @return
     */
    List<XsCjDTO> findXsCjByIds(@Param("ids") List<Long> ids);

    /**
     * 通过学生成绩id查询学生ids
     * @param xsCjId
     * @return
     */
    Long findXsIdsByXsCjId(@Param("xsCjId") Long xsCjId);
}
