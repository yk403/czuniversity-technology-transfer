package com.itts.personTraining.mapper.pc;

import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.pc.Pc;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 批次表 Mapper 接口
 * </p>
 *
 * @author FL
 * @since 2021-04-20
 */
public interface PcMapper extends BaseMapper<Pc> {


    List<Pc> selectPcList(List<Long> ids);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Boolean updatePcList(List<Long> ids);

    /**
     * 根据id查询课程信息列表
     * @return
     */
    List<Kc> findKcListById(@Param("id") Long id);

    /**
     * 根据批次号查询批次信息
     * @param pch
     * @return
     */
    Pc getByPch(@Param("pch") String pch);

    /**
     * 根据用户id查询批次信息(前)
     * @param userId
     * @return
     */
    List<Pc> findPcByYhId(@Param("userId") Long userId);

    /**
     * 根据年份查询批次ids(前)
     * @param currentYear
     * @return
     */
    List<Long> findPcIdsByYear(@Param("currentYear") String currentYear);
}
