package com.itts.personTraining.mapper.pcXs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.personTraining.dto.StuDTO;
import com.itts.personTraining.model.pcXs.PcXs;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 批次学生关系表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-05-18
 */
@Repository
public interface PcXsMapper extends BaseMapper<PcXs> {

    /**
     * 根据学员id查询批次ids
     *
     * @param xsId
     * @return
     */
    List<Long> selectByXsId(@Param("xsId") Long xsId);

    /**
     * 根据pcId查询xsIds
     *
     * @param pcId
     * @return
     */
    List<Long> selectByPcId(@Param("pcId") Long pcId);

    /**
     * 根据pcId查询学生信息
     *
     * @param pcId
     * @return
     */
    List<StuDTO> selectStuByPcId(@Param("pcId") Long pcId);

    @Select("select pcxs.pc_id AS pcId from  t_pc_xs pcxs " +
            "   LEFT JOIN t_pc pc ON pc.id = pcxs.pc_id " +
            "   WHERE pcxs.xs_id = #{xsId} " +
            "   AND pc.xylx = #{xslx}")
    Long findByXsIdAndXslx(@Param("xsId") Long xsId, @Param("xslx") String xslx);

}
