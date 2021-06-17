package com.itts.technologytransactionservice.mapper;

import com.itts.technologytransactionservice.model.LyHz;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.technologytransactionservice.model.LyHzDto;
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
public interface LyHzMapper extends BaseMapper<LyHz> {
    /**
     * 分页条件查询需求
     *
     * @param map
     * @return
     */
    List<LyHzDto> findLyHzFront(@Param("map") Map map);
    /**
     * 分页条件查询需求(后台)
     *
     * @param map
     * @return
     */
    List<LyHzDto> findLyHzBack(@Param("map") Map map);
}
