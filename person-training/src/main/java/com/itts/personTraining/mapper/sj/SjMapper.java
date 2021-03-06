package com.itts.personTraining.mapper.sj;

import com.itts.personTraining.dto.SjDTO;
import com.itts.personTraining.model.sj.Sj;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 实践表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-05-28
 */
public interface SjMapper extends BaseMapper<Sj> {


    /**
     * 条件查询实践信息
     * @param pcId
     * @param sjlx
     * @param export
     * @return
     */
    List<SjDTO> getByCondition(@Param("pcId") Long pcId, @Param("sjlx") String sjlx, @Param("name") String name, @Param("export") Integer export, @Param("fjjgId") Long fjjgId);

    /**
     * 条件查询实践信息
     *
     * @return
     */
    List<SjDTO> getCondition(@Param("pcId") Long pcId);
    /**
     * 根据id查询实践详情
     * @param id
     * @return
     */
    SjDTO getById(@Param("id") Long id);

    /**
     * 根据学生id查询实践通知数量
     * @param xsId
     * @return
     */
    Long getNumByXsId(@Param("xsId") Long xsId);

    /**
     * 根据条件查询实践信息列表
     * @param yhId
     * @param cjr
     * @return
     */
    List<SjDTO> findByCondition(@Param("yhId") Long yhId, @Param("cjr") Long cjr);

    /**
     * 根据批次id和学生id查询实践信息
     * @param pcId
     * @param xsId
     * @return
     */
    Sj getByPcIdAndXsId(@Param("pcId") Long pcId, @Param("xsId") Long xsId);

    /**
     * 根据条件查询实践信息列表(前)
     * @param yhId
     * @param cjr
     * @return
     */
    /*List<SjDTO> findByCategory(@Param("yhId") Long yhId, @Param("cjr") Long cjr);*/

    /**
     * 根据学生id和批次id查询实践成绩
     * @param xsId
     * @param pcId
     * @return
     */
    String getSjcjByXsIdAndPcId(@Param("xsId") Long xsId, @Param("pcId") Long pcId);

    /**
     * 根据学生id和批次id查询实践成绩
     * @param xsId
     * @param pcId
     * @return
     */
    String selectSjcjByXsIdAndPcId(@Param("xsId") Long xsId, @Param("pcId") Long pcId);

}
