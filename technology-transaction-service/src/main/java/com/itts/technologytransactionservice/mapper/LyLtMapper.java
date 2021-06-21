package com.itts.technologytransactionservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.technologytransactionservice.model.LyLt;
import com.itts.technologytransactionservice.model.TCd;
import com.itts.technologytransactionservice.model.TJsLb;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author lym
 * @since 2021-03-12
 */
public interface LyLtMapper extends BaseMapper<LyLt> {
    List<LyLt> list(@Param("map") Map map);
}
