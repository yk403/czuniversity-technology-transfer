package com.itts.technologytransactionservice.mapper;

import com.itts.technologytransactionservice.model.LyLy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yukai
 * @since 2021-05-18
 */
public interface LyLyMapper extends BaseMapper<LyLy> {
    /**
     * 分页条件查询需求
     *
     * @param map
     * @return
     */
    List<LyLy> findLyLyFront(@Param("map") Map map);
}
