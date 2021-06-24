package com.itts.personTraining.mapper.kc;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.personTraining.dto.KcDTO;
import com.itts.personTraining.model.kc.Kc;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 课程表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Repository
public interface KcMapper extends BaseMapper<Kc> {

    /**
     * 分页条件查询课程信息
     *
     * @param kclx
     * @param name
     * @param jylx
     * @return
     * @prram xylx
     */
    List<KcDTO> findByPage(@Param("kclx") String kclx, @Param("name") String name, @Param("jylx") String jylx, @Param("xylx") String xylx);

    /**
     * 根据批次id查询课程集合
     *
     * @param pcId
     * @return
     */
    List<Kc> findKcByPcId(@Param("pcId") Long pcId);

    @Select("<script> " +
            "SELECT * FROM t_kc kc " +
            "   WHERE id IN (SELECT kc_id FROM t_kc_sz ks WHERE ks.sz_id = #{szId}) " +
            "   AND kc.sfsc = false " +
            "   <if test='educationType != null and educationType != \"\"'> " +
            "       AND jylx = #{educationType} " +
            "   </if>" +
            "   <if test='studentType != null and studentType != \"\"'> " +
            "       AND xylx = #{studentType} " +
            "   </if>" +
            "</script>")
    List<Kc> findBySzId(@Param("szId") Long szId, @Param("educationType") String educationType, @Param("studentType") String studentType);

    @Select("<script> " +
            "SELECT * FROM t_kc " +
            "   WHERE sfsc = false " +
            "   <if test='educationType != null and educationType != \"\"'> " +
            "       AND jylx = #{educationType}" +
            "   </if>" +
            "   <if test='null != studentTypes and studentTypes.size > 0'> " +
            "       AND xylx IN " +
            "       <foreach collection=\"studentTypes\" item=\"studentType\" open=\"(\" separator=\",\"  close=\")\"> " +
            "           #{studentType} " +
            "       </foreach> " +
            "   </if>" +
            "</script>")
    List<Kc> findByType(@Param("educationType") String educationType, @Param("studentTypes") List<String> studentTypes);
}
