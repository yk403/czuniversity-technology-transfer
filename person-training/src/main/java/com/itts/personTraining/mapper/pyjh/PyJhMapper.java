package com.itts.personTraining.mapper.pyjh;

import com.itts.personTraining.dto.PyJhDTO;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.pyjh.PyJh;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 培养计划表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
public interface PyJhMapper extends BaseMapper<PyJh> {

    /**
     * 分页查询培养计划列表
     * @param pch
     * @param jylx
     * @param jhmc
     * @param fjjgId
     * @return
     */
    List<PyJhDTO> findByPage(@Param("pch") String pch, @Param("jylx") String jylx, @Param("jhmc") String jhmc, @Param("fjjgId") Long fjjgId);

    /**
     * 根据批次id查询培养计划列表
     * @param pcId
     * @return
     */
    List<PyJhDTO> findByPcId(@Param("pcId") Long pcId);

    /**
     * 查询所有批次列表
     * @param fjjgId
     * @return
     */
    List<Pc> findAllPc(@Param("fjjgId") Long fjjgId);
}
