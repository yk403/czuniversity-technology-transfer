package com.itts.userservice.mapper.sjzd;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.userservice.model.sjzd.Sjzd;
import com.itts.userservice.vo.SjzdModelVO;
import com.itts.userservice.vo.SjzdVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author fl
 * @since 2021-03-31
 */
public interface SjzdMapper extends BaseMapper<Sjzd> {

    @Select("<script>" +
            "SELECT ssmk, ssmkmc, xtlb, mklx " +
            "FROM t_sjzd  " +
            "<where> " +
            "   <if test=\"model != null and model != ''\"> " +
            "       AND mklx = #{model} " +
            "   </if>" +
            "   <if test=\"systemType != null and systemType != ''\"> " +
            "       AND xtlb = #{systemType} " +
            "   </if>" +
            "   <if test=\"condition != null and condition != ''\"> " +
            "       AND (ssmkmc LIKE CONCAT('%', #{condition}, '%') OR ssmk LIKE CONCAT('%', #{condition}, '%'))" +
            "   </if>" +
            "</where> " +
            "GROUP BY ssmk " +
            "ORDER BY cjsj DESC" +
            "</script>")
    List<SjzdModelVO> findDictionaryModel(@Param("model") String model, @Param("systemType") String systemType, @Param("condition") String condition);

    @Select("<script>" +
            "SELECT * " +
            "FROM t_sjzd  " +
            "WHERE sfsc = false " +
            "   <if test=\"mklx != null and mklx != ''\"> " +
            "       AND mklx = #{mklx} " +
            "   </if>" +
            "   <if test=\"xtlb != null and xtlb != ''\"> " +
            "       AND xtlb = #{xtlb} " +
            "   </if>" +
            "   <if test=\"ssmk != null and ssmk != ''\"> " +
            "       AND ssmk = #{ssmk} " +
            "   </if>" +
            "ORDER BY px" +
            "</script>")
    List<Sjzd> findBySsmk(@Param("xtlb") String xtlb, @Param("mklx") String mklx, @Param("ssmk") String ssmk);

    Sjzd selectByCode(String zdbm);

    List<Sjzd> selectByNameOrCode(@Param("string") String string, @Param("ssmk") String ssmk);

    List<SjzdVO> selectSjzd();
}
