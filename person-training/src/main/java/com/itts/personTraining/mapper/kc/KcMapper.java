package com.itts.personTraining.mapper.kc;

import com.itts.personTraining.dto.KcDTO;
import com.itts.personTraining.model.kc.Kc;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
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
     * @param kclx
     * @param name
     * @param jylx
     * @prram xylx
     * @return
     */
    List<KcDTO> findByPage(@Param("kclx") String kclx, @Param("name") String name, @Param("jylx") String jylx, @Param("xylx") String xylx);

    /**
     * 根据批次id查询课程集合
     * @param pcId
     * @return
     */
    List<Kc> findKcByPcId(@Param("pcId") Long pcId);
}
