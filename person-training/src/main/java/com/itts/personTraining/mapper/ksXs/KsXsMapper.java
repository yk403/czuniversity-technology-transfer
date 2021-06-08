package com.itts.personTraining.mapper.ksXs;

import com.itts.personTraining.model.ksXs.KsXs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 考试学生关系表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-05-06
 */
public interface KsXsMapper extends BaseMapper<KsXs> {

    /**
     * 根据考试id查询学生ids
     * @param ksId
     * @return
     */
    List<Long> selectByKsId(@Param("ksId") Long ksId);

    /**
     * 通过学生id查询考试通知数量
     * @param xsId
     * @return
     */
    Long getNumByXsId(@Param("xsId") Long xsId);

}
