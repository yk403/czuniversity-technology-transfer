package com.itts.technologytransactionservice.mapper;

import com.itts.technologytransactionservice.model.LyLy;
import com.itts.technologytransactionservice.model.LyZw;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.technologytransactionservice.model.LyZwDto;
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
public interface LyZwMapper extends BaseMapper<LyZw> {
    /**
     * 分页条件查询需求
     *
     * @param map
     * @return
     */
    List<LyZwDto> findLyZwFront(@Param("map") Map map);
    /**
     * 分页条件查询展位(选择用)
     *
     * @param map
     * @return
     */
    List<LyZwDto> findLyZwFrontSelect(@Param("map") Map map);
    /**
     * 分页条件查询需求(后台)
     *
     * @param map
     * @return
     */
    List<LyZwDto> findLyZwBack(@Param("map") Map map);
    /**
     * 分页条件查询展位(选择用)
     *
     * @param map
     * @return
     */
    List<LyZwDto> findLyZwBackSelect(@Param("map") Map map);
}
