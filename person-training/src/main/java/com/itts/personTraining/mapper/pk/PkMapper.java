package com.itts.personTraining.mapper.pk;

import com.itts.personTraining.dto.PkDTO;
import com.itts.personTraining.model.pk.Pk;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 排课表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
public interface PkMapper extends BaseMapper<Pk> {

    PkDTO getById(@Param("id") Long id);

    List<PkDTO> findPage(@Param("skqsnyr") String skqsnyr,@Param("skjsnyr") String skjsnyr,@Param("pcId") Long pcId);
}
