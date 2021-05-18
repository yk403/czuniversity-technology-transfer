package com.itts.personTraining.mapper.ksExp;

import com.itts.personTraining.dto.KsExpDTO;
import com.itts.personTraining.model.ksExp.KsExp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 考试扩展表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-05-13
 */
public interface KsExpMapper extends BaseMapper<KsExp> {

    /**
     * 根据条件查询考试扩展DTO
     * @param id
     * @param ksId
     * @return
     */
    List<KsExpDTO> findByCondition(@Param("id") Long id,@Param("ksId") Long ksId);

    KsExpDTO findByKsExpId(@Param("id") Long id);
}
