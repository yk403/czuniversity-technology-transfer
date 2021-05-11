package com.itts.personTraining.mapper.jhKc;

import com.itts.personTraining.model.jhKc.JhKc;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 计划课程关系表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-05-11
 */
public interface JhKcMapper extends BaseMapper<JhKc> {

    /**
     * 根据jhId查询课程ids
     * @param jhId
     * @return
     */
    List<Long> selectByJhId(@Param("jhId") Long jhId);
}
