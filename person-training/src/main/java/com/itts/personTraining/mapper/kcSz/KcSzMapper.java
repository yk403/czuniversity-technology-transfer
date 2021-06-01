package com.itts.personTraining.mapper.kcSz;

import com.itts.personTraining.model.kcSz.KcSz;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.personTraining.model.sz.Sz;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程师资关系表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-04-25
 */
public interface KcSzMapper extends BaseMapper<KcSz> {

    /**
     * 根据kcId查询师资ids
     * @param kcId
     * @return
     */
    List<Sz> getByKcId(@Param("kcId") Long kcId);
}
