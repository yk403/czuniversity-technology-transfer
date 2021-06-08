package com.itts.paymentservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.bean.LoginUser;
import com.itts.paymentservice.enums.OrderStatusEnum;
import com.itts.paymentservice.mapper.ddxfjl.DdxfjlMapper;
import com.itts.paymentservice.model.ddxfjl.Ddxfjl;
import com.itts.paymentservice.request.ddxfjl.AddDdxfjlRequest;
import com.itts.paymentservice.service.DdxfjlService;
import com.itts.paymentservice.utils.PaymentUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 订单消费记录 服务实现类
 * </p>
 *
 * @author liuyingming
 * @since 2021-06-02
 */
@Service
public class DdxfjlServiceImpl extends ServiceImpl<DdxfjlMapper, Ddxfjl> implements DdxfjlService {

    @Autowired
    private DdxfjlMapper ddxfjlMapper;

    /**
     * 新增
     */
    @Override
    public Ddxfjl add(AddDdxfjlRequest addDdxfjlRequest, LoginUser loginUser) {

        Ddxfjl ddxfjl = new Ddxfjl();
        BeanUtils.copyProperties(addDdxfjlRequest, ddxfjl);

        Date now = new Date();

        ddxfjl.setCjr(loginUser.getUserId());
        ddxfjl.setGxr(loginUser.getUserId());
        ddxfjl.setCjsj(now);
        ddxfjl.setGxsj(now);

        ddxfjl.setBh(PaymentUtils.generateOrderNo());
        ddxfjl.setZt(OrderStatusEnum.PENDING.getKey());

        ddxfjlMapper.insert(ddxfjl);

        return ddxfjl;
    }

    /**
     * 更新状态
     */
    @Override
    public Ddxfjl updateStatus(Ddxfjl old, String status) {

        Date now = new Date();

        old.setZt(status);
        old.setGxsj(now);

        switch (status){

            case "paid":
                old.setZfsj(now);
                break;
            case "cancelled":
                old.setQxsj(now);
                break;
            case "apply_refund":
                old.setSqtksj(now);
                break;
            case "refunded":
                old.setTksj(now);
                break;
        }

        ddxfjlMapper.updateById(old);

        return old;
    }
}
