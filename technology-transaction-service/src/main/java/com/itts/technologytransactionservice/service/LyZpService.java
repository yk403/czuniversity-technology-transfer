package com.itts.technologytransactionservice.service;

import com.github.pagehelper.PageInfo;
import com.itts.technologytransactionservice.model.LyZp;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yukai
 * @since 2021-05-18
 */
public interface LyZpService extends IService<LyZp> {
    PageInfo findLyZpFront(Map<String, Object> params);

    Boolean saveZp(LyZp lyZp);
    Long getUserId();
    /**
     * 个人发布审核展品申请(0待提交;1待审核;2通过;3整改;4拒绝)
     *
     * @param params
     * @return
     */
    boolean audit(Map<String, Object> params, Integer fbshzt);

}
