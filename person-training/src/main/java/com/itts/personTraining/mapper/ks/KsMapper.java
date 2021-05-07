package com.itts.personTraining.mapper.ks;

import com.itts.personTraining.dto.KsDTO;
import com.itts.personTraining.model.ks.Ks;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 考试表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-05-06
 */
public interface KsMapper extends BaseMapper<Ks> {

    /**
     * 查询考试列表
     * @param kslx
     * @param pcId
     * @param pclx
     * @param kcmc
     * @return
     */
    List<KsDTO> findByPage(@Param("kslx") String kslx, @Param("pcId") Long pcId, @Param("pclx") String pclx, @Param("kcmc") String kcmc);

    /**
     * 根据考试id查询ksDTO
     * @param id
     * @return
     */
    KsDTO findById(@Param("id") Long id);
}