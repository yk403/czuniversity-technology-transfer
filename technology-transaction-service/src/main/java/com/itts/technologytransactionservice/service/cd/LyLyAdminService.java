package com.itts.technologytransactionservice.service.cd;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.technologytransactionservice.model.LyLy;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yukai
 * @since 2021-05-18
 */
public interface LyLyAdminService extends IService<LyLy> {
    PageInfo findLyLyBack(Map<String, Object> params);

    Boolean saveLy(LyLy lyLy);
    Long getUserId();
}
