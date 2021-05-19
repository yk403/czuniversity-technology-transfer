package com.itts.technologytransactionservice.mapper;

import com.itts.technologytransactionservice.model.LyBm;
import com.itts.technologytransactionservice.model.LyHd;
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
public interface LyHdMapper extends BaseMapper<LyHd> {
    /**
     * 分页条件查询需求
     *
     * @param map
     * @return
     */
    List<LyHd> findLyHdFront(@Param("map") Map map);
}
