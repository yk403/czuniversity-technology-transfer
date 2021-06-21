package com.itts.personTraining.mapper.sz;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.personTraining.model.sz.Sz;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 师资表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Repository
public interface SzMapper extends BaseMapper<Sz> {

    /**
     * 通过用户id查询师资信息
     * @param yhId
     * @return
     */
    Sz getSzByYhId(@Param("yhId") Long yhId);
}
