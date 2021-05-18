package com.itts.technologytransactionservice.mapper;

import com.itts.technologytransactionservice.model.LyZp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface LyZpMapper extends BaseMapper<LyZp> {
    /**
     * 分页条件查询需求
     *
     * @param map
     * @return
     */
    List<LyZp> findLyZpFront(@Param("map") Map map);
}
