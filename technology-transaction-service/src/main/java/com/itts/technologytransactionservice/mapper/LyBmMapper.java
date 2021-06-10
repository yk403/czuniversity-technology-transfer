package com.itts.technologytransactionservice.mapper;

import com.itts.technologytransactionservice.model.LyBm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.technologytransactionservice.model.LyBmDto;
import com.itts.technologytransactionservice.model.TJsXq;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yukai
 * @since 2021-05-17
 */
public interface LyBmMapper extends BaseMapper<LyBm> {
    /**
     * 分页条件查询需求
     *
     * @param map
     * @return
     */
    List<LyBmDto> findLyBmFront(@Param("map") Map map);
    /**
     * 分页条件查询需求(后台)
     *
     * @param map
     * @return
     */
    List<LyBmDto> findLyBmBack(@Param("map") Map map);
    /**
     * 更新报名表
     *
     * @param tJsXq
     */
    boolean updateLyBm(LyBm lyBm);
}
