package com.itts.technologytransactionservice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.technologytransactionservice.model.JsJjr;
import com.itts.technologytransactionservice.model.JsJjrDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fuli
 * @since 2021-09-17
 */
public interface JsJjrService extends IService<JsJjr> {

    PageInfo<JsJjrDTO> findPage(Integer pageNum, Integer pageSize, String xslbmcArr, Long jgId, String zsxm);

    JsJjrDTO  getByJjrId(Long id);

}
