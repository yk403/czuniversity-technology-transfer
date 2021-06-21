package com.itts.technologytransactionservice.service.cd;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.technologytransactionservice.model.LyBm;
import com.itts.technologytransactionservice.model.TJsXq;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yukai
 * @since 2021-05-17
 */
public interface LyBmAdminService extends IService<LyBm> {

    PageInfo findLyBmBack(Map<String, Object> params);

    Boolean saveBm(LyBm lyBm);
    Long getUserId();
    boolean updateLyBm(LyBm lyBm);
}
