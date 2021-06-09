package com.itts.technologytransactionservice.service.cd;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.technologytransactionservice.model.LyHd;
import com.itts.technologytransactionservice.model.LyHdDto;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yukai
 * @since 2021-05-18
 */
public interface LyHdAdminService extends IService<LyHd> {
    PageInfo findLyHdBack(Map<String, Object> params);

    Boolean saveHd(LyHdDto lyHdDto);
    Long getUserId();

    boolean updateHd(LyHdDto lyHdDto);

    boolean issueBatch(List<Long> ids);
}
