package com.itts.userservice.mapper.cd;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.userservice.model.cd.Cd;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author fl
 * @since 2021-03-24
 */
public interface CdMapper extends BaseMapper<Cd> {

    /**
     * 通过父级ID获取所有子级菜单
     */
    @Select({"<script>",
            "SELECT * " +
                    "FROM t_cd " +
                    "WHERE sfsc = false" +
                    "<if test=\"parentId != null\"> " +
                    "   AND fjcd_id = #{parentId} " +
                    "</if>" +
                    "<if test=\"systemType != null and systemType != ''\"> " +
                    "   AND xtlx = #{systemType}" +
                    "</if>",
            " </script> "
    })
    List<Cd> findByParentId(@Param("parentId") Long parentId, @Param("systemType") String systemType);

    /**
     * 通过菜单编码获取当前菜单和所有子菜单
     */
    @Select("SELECT * FROM t_cd WHERE sfsc = false AND cj LIKE CONCAT(#{code}, '%')")
    List<Cd> findThisAndAllChildrenByCode(@Param("code") String code);

    /**
     * 通过父级ID获取所有子级数量
     */
    @Select("SELECT COUNT(id) " +
            "FROM t_cd " +
            "WHERE sfsc = false " +
            "AND fjcd_id = #{parentId}")
    Long countByParentId(@Param("parentId") Long parentId);

    List<Cd> selectByParameterList(@Param("parameter")String parameter,@Param("systemType") String systemType,@Param("modelType") String modelType);

    @Select("<script> " +
            "SELECT cd.* FROM t_cd cd " +
            "    LEFT JOIN t_js_cd_gl jcg on cd.id = jcg.cd_id " +
            "WHERE cd.sfsc = false " +
            "   <if test='jsIds != null and jsIds.size() > 0'> " +
            "       AND jcg.js_id IN " +
            "       <foreach item='jsId' collection='jsIds' separator=',' open='(' close=')'> " +
            "           #{jsId} " +
            "     </foreach>" +
            "   </if> " +
            "</script>")
    List<Cd> findByJsId(List<Long> jsIds);
}
