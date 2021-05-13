package com.itts.personTraining.service.xxzy;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.personTraining.model.xxzy.Xxzy;
import com.itts.personTraining.request.xxzy.AddXxzyRequest;

/**
 * <p>
 * 学习资源 服务类
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-12
 */
public interface XxzyService extends IService<Xxzy> {

    /**
     * 获取列表 - 分页
     */
    PageInfo<Xxzy> list(Integer pageNum, Integer pageSize, String type, String firstCategory,
                        String secondCategory, String courseId, String condition);

    /**
     * 新增
     */
    Xxzy add(AddXxzyRequest addXxzyRequest);
}
