package com.itts.personTraining.mapper.tkzy;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.personTraining.model.tkzy.Tmxx;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 题目选项 Mapper 接口
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-13
 */
@Repository
public interface TmxxMapper extends BaseMapper<Tmxx> {

    /**
     * 通过题目ID获取选项
     */
    @Select("SELECT * " +
            "FROM t_tmxx " +
            "WHERE tm_id = #{tmId}")
    List<Tmxx> findByTmId(@Param("tmId") Long tmId);

}
