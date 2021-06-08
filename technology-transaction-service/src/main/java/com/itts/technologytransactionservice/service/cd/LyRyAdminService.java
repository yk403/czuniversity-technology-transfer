package com.itts.technologytransactionservice.service.cd;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.technologytransactionservice.model.LyRy;
import com.itts.technologytransactionservice.model.LyRyDto;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yukai
 * @since 2021-05-18
 */
public interface LyRyAdminService extends IService<LyRy> {
    PageInfo findLyRyBack(Map<String, Object> params);

    Boolean saveRy(LyRy lyRy);
    Long getUserId();
}
