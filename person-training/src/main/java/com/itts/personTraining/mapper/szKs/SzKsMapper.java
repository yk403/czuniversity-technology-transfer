package com.itts.personTraining.mapper.szKs;

import com.itts.personTraining.model.szKs.SzKs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 师资考试关系表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-05-06
 */
public interface SzKsMapper extends BaseMapper<SzKs> {

    /**
     * 根据考试id查询师资ids
     * @param ksId
     * @return
     */
    List<Long> selectByKsId(@Param("ksId") Long ksId);
}