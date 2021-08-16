package com.itts.personTraining.mapper.pk;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.personTraining.dto.PkDTO;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.pk.Pk;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 排课表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Repository
public interface PkMapper extends BaseMapper<Pk> {

    PkDTO getPkById(@Param("id") Long id);

    List<PkDTO> findPkInfo(@Param("pcId") Long pcId);

    List<PkDTO> findPk(@Param("pcId") Long pcId);
    /**
     * 根据开学日期查询所有排课信息
     * @param kxrq
     * @return
     */
    List<PkDTO> findPkByCondition(@Param("kxrq") Date kxrq);

    /**
     * 根据师资id(授课教师)查询批次列表(前)
     * @param szId
     * @return
     */
    List<Pc> findPcsBySzId(@Param("szId") Long szId);

    /**
     * 根据师资id查询批次id
     * @param szId
     * @return
     */
    List<Long> findPcIdsBySzId(@Param("szId") Long szId);
}
