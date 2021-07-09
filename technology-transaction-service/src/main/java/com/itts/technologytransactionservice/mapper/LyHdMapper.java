package com.itts.technologytransactionservice.mapper;

import com.itts.technologytransactionservice.model.LyBm;
import com.itts.technologytransactionservice.model.LyHd;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.technologytransactionservice.model.LyHdDto;
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
    List<LyHdDto> findLyHd(@Param("map") Map map);
    /**
     * 分页条件查询需求
     *
     * @param map
     * @return
     */
    List<LyHdDto> findLyHdFront(@Param("map") Map map);
    /**
    * @Description: 分页查询当前用户双创路演活动
    * @Param: [map]
    * @return: java.util.List<com.itts.technologytransactionservice.model.LyHdDto>
    * @Author: yukai
    * @Date: 2021/6/18
    */
    List<LyHdDto> findLyHdFrontUser(@Param("map") Map map);
    /**
     * 分页条件查询需求(后台)
     *
     * @param map
     * @return
     */
    List<LyHdDto> findLyHdBack(@Param("map") Map map);
    /**
    * @Description: 后台会展查询专用
    * @Param: [map]
    * @return: java.util.List<com.itts.technologytransactionservice.model.LyHdDto>
    * @Author: yukai
    * @Date: 2021/6/16
    */
    List<LyHdDto> findLyHzBack(@Param("map") Map map);
}
