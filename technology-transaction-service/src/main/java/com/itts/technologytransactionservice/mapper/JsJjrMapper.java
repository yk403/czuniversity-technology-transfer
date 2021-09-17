package com.itts.technologytransactionservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.technologytransactionservice.model.JsJjr;
import com.itts.technologytransactionservice.model.JsJjrDTO;
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
public interface JsJjrMapper extends BaseMapper<JsJjr> {

    List<JsJjrDTO> findPage(@Param("xslbmcArr") String[] xslbmcArr,
                            @Param("jgId") Long jgId,
                            @Param("zsxm") String zsxm);

    JsJjrDTO  getByJjrId(@Param("id") Long id);
}
