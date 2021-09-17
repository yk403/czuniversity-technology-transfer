package com.itts.technologytransactionservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.technologytransactionservice.model.JsMsg;
import com.itts.technologytransactionservice.model.JsMsgDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fuli
 * @since 2021-09-17
 */
@Repository
public interface JsMsgMapper extends BaseMapper<JsMsg> {

    List<JsMsgDTO> findPage(@Param("userId") Long userId);
}
