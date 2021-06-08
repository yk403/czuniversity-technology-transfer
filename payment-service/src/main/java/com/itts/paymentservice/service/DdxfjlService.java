package com.itts.paymentservice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itts.common.bean.LoginUser;
import com.itts.paymentservice.model.ddxfjl.Ddxfjl;
import com.itts.paymentservice.request.ddxfjl.AddDdxfjlRequest;

/**
 * <p>
 * 订单消费记录 服务类
 * </p>
 *
 * @author liuyingming
 * @since 2021-06-02
 */
public interface DdxfjlService extends IService<Ddxfjl> {

    /**
     * 新增
     */
    Ddxfjl add(AddDdxfjlRequest addDdxfjlRequest, LoginUser loginUser);

    /**
     * 更新状态
     */
    Ddxfjl updateStatus(Ddxfjl old, String status);

}
