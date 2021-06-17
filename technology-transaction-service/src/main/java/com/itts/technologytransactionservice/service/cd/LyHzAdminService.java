package com.itts.technologytransactionservice.service.cd;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.technologytransactionservice.model.LyHz;
import com.itts.technologytransactionservice.model.LyHzDto;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yukai
 * @since 2021-05-18
 */
public interface LyHzAdminService extends IService<LyHz> {
    PageInfo findLyHzBack(Map<String, Object> params);

    Boolean saveHz(LyHzDto lyHzDto);
    Long getUserId();

    boolean updateHz(LyHzDto lyHzDto);
}
