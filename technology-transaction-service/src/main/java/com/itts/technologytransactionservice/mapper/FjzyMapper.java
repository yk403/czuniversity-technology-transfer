package com.itts.technologytransactionservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.technologytransactionservice.model.Fjzy;
import com.itts.technologytransactionservice.model.TJsBm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 附件资源表 Mapper 接口
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-21
 */
@Repository
public interface FjzyMapper extends BaseMapper<Fjzy> {
    List<Fjzy> list(@Param("map") Map map);
}
