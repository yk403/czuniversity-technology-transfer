package com.itts.personTraining.mapper.zj;

import com.itts.personTraining.model.zj.Zj;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 专家表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-05-25
 */
public interface ZjMapper extends BaseMapper<Zj> {

    /**
     * 根据条件查询专家信息
     * @param dh
     * @return
     */
    Zj getByCondition(@Param("dh") String dh);

    /**
     * 根据用户id查询专家信息
     * @param yhId
     * @return
     */
    Zj getZjByYhId(@Param("yhId") Long yhId);
}
