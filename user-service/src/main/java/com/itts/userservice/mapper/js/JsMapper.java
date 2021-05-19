package com.itts.userservice.mapper.js;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.userservice.model.js.Js;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-19
 */
public interface JsMapper extends BaseMapper<Js> {

	@Select("SELECT * " +
            "FROM t_js js " +
            "         LEFT JOIN t_yh_js_gl yjg ON js.id = yjg.js_id " +
            "WHERE yjg.sfsc = false " +
            "  AND js.sfsc = false " +
            "  AND yjg.yh_id = #{yhId} ")
    List<Js> findByYhId(@Param("yhId") Long yhId);

    @Select({"<script> ",
            "SELECT * " +
            "FROM t_js " +
            "WHERE sfsc = false " +
            "AND sfmr = true " +
            "AND yhjslx = #{type} " +
            "<if test='category != null and category != \"\"'> " +
            "   AND jslb = #{category} " +
            "</if> " ,
            "</script> "})
    Js getDefault(@Param("type") String type, @Param("category")String category);

}
