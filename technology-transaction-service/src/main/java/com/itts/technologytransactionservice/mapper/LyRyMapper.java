package com.itts.technologytransactionservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.technologytransactionservice.model.LyRy;
import com.itts.technologytransactionservice.model.LyRyDto;
import com.itts.technologytransactionservice.model.LyZw;
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
public interface LyRyMapper extends BaseMapper<LyRy> {
    /**
     * 分页条件查询需求
     *
     * @param map
     * @return
     */
    List<LyRy> findLyRyFront(@Param("map") Map map);
    /**
     * 分页条件查询需求(后台)
     *
     * @param map
     * @return
     */
    List<LyRy> findLyRyBack(@Param("map") Map map);
}
