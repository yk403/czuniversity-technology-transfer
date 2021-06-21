package com.itts.personTraining.mapper.szKsExp;

import com.itts.personTraining.model.szKsExp.SzKsExp;
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
public interface SzKsExpMapper extends BaseMapper<SzKsExp> {

    /**
     * 根据考试扩展id查询师资ids
     * @param ksExpId
     * @return
     */
    List<Long> selectByKsExpId(@Param("ksExpId") Long ksExpId);
}
