package com.itts.personTraining.mapper.xxzy;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.personTraining.model.xxzy.Xxzy;
import com.itts.personTraining.model.xxzy.Xxzysc;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 学习资源收藏 Mapper 接口
 * </p>
 *
 * @author liuyingming
 * @since 2021-06-09
 */
@Repository
public interface XxzyscMapper extends BaseMapper<Xxzysc> {

    @Select("<script> " +
            "SELECT zy.* " +
            "FROM t_xxzysc sc " +
            "         LEFT JOIN t_xxzy zy ON sc.xxzy_id = zy.id " +
            "WHERE sc.yh_id = #{userId} " +
            "  AND zy.sfsc = false " +
            "   <if test='firstCategory != null and firstCategory != \"\"'> " +
            "       AND zy.zyyjfl = #{firstCategory}" +
            "   </if>" +
            "   <if test='secondCategory != null and secondCategory != \"\"'> " +
            "       AND zy.zyejfl = #{secondCategory}" +
            "   </if>" +
            "   <if test='category != null and category != \"\"'> " +
            "       AND zy.zylx = #{category}" +
            "   </if>" +
            "   <if test='fjjgId != null and fjjgId != \"\"'> " +
            "       AND zy.fjjgId = #{fjjgId}" +
            "   </if>" +
            "   <if test='direction != null and direction != \"\"'> " +
            "       AND zy.zyfx = #{direction}" +
            "   </if>" +
            "   <if test='id != null'> " +
            "       AND sc.xxzy_id = #{id} " +
            "   </if>" +
            "ORDER BY sc.cjsj DESC " +
            "</script> ")
    List<Xxzy> findScByPage(@Param("userId") Long userId, @Param("firstCategory") String firstCategory,
                            @Param("secondCategory") String secondCategory, @Param("category") String category,
                            @Param("direction") String direction, @Param("id") Long id,@Param("fjjgId")Long fjjgId);

}
