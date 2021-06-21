package com.itts.technologytransactionservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.technologytransactionservice.model.LyMsg;
import com.itts.technologytransactionservice.model.LyMsgDto;
import com.itts.technologytransactionservice.model.LyRy;
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
public interface LyMsgMapper extends BaseMapper<LyMsg> {
    /**
     * 分页条件查询需求
     *
     * @param map
     * @return
     */
    List<LyMsgDto> findLyMsgFront(@Param("map") Map map);
    /**
     * 分页条件查询需求(后台)
     *
     * @param map
     * @return
     */
    List<LyMsgDto> findLyMsgBack(@Param("map") Map map);
}
