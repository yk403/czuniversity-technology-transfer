package com.itts.personTraining.mapper.tkzy;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.personTraining.model.tkzy.Tkzy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 题库资源 Mapper 接口
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-13
 */
@Repository
public interface TkzyMapper extends BaseMapper<Tkzy> {

    @Select("SELECT tkzy.* " +
            "FROM t_tkzy tkzy " +
            "LEFT JOIN t_sj_tm_gl sjtmgl ON tkzy.id = sjtmgl.kssj_id " +
            "WHERE sjtmgl.kssj_id = #{sjId} " +
            "AND tkzy.sfsc = false " +
            "AND tkzy.sfsj = true")
    List<Tkzy> findBySjId(@Param("sjId") Long sjId);
}
