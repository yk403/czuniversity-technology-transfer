package com.itts.technologytransactionservice.service.cd;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.technologytransactionservice.model.LyZw;

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
public interface LyZwAdminService extends IService<LyZw> {
    PageInfo findLyZwBack(Map<String, Object> params);
    PageInfo findLyZwBackSelect(Map<String, Object> params);
    Boolean saveZw(LyZw lyZw);
    Long getUserId();

    /**
     * 查询所有展位
     * @return
     */
    List<LyZw> findAll();
}
