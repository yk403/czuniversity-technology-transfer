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
     * 分页查询实践信息
     * @param pcId
     * @param sjlx
     * @return
     */
    List<SjDTO> getByCondition(Long pcId, String sjlx);

    /**
     * 查询所有实践
     * @return
     */
    List<SjDTO> getByCondition();

    /**
     * 根据id查询实践详情
     * @param id
     * @return
     */
    SjDTO getByCondition(@Param("id") Long id);
}
