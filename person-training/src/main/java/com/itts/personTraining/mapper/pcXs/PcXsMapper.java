package com.itts.personTraining.mapper.pcXs;

import com.itts.personTraining.model.pcXs.PcXs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 批次学生关系表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-05-18
 */
public interface PcXsMapper extends BaseMapper<PcXs> {

    /**
     * 根据学员id查询批次ids
     * @param xsId
     * @return
     */
    List<Long> selectByXsId(@Param("xsId") Long xsId);
}
