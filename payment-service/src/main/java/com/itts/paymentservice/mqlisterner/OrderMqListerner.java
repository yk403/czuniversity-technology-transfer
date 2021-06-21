package com.itts.paymentservice.mqlisterner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.paymentservice.enums.OrderStatusEnum;
import com.itts.paymentservice.model.ddxfjl.Ddxfjl;
import com.itts.paymentservice.service.DdxfjlService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/10
 */
@RabbitListener(queues = "${mqconfig.order_release_queue}")
@Component
@Slf4j
public class OrderMqListerner {

    @Autowired
    private DdxfjlService ddxfjlService;

    /**
     * 检查订单是否已支付，如果未支付半小时自动取消订单
     */
    @RabbitHandler
    public void releaseOrderMessage(String orderNo, Message message, Channel channel) throws IOException {

        Ddxfjl ddxfjl = ddxfjlService.getOne(new QueryWrapper<Ddxfjl>().eq("bh", orderNo));

        long msgTag = message.getMessageProperties().getDeliveryTag();

        if (ddxfjl == null) {

            log.info("【订单支付服务：检查订单是否已支付】订单不存在， 订单编号{}", orderNo);
            //消费消息
            channel.basicAck(msgTag, false);
            return;
        }

        if (Objects.equals(OrderStatusEnum.PAID.getKey(), ddxfjl.getZt())) {

            log.info("【订单支付服务：检查订单是否已支付】订单已支付， 订单编号{}", orderNo);
            //消费消息
            channel.basicAck(msgTag, false);
            return;
        }

        if (Objects.equals(OrderStatusEnum.PENDING.getKey(), ddxfjl.getZt())) {

            log.info("【订单支付服务：检查订单是否已支付】订单未支付， 订单编号{}", orderNo);

            //调用第三方支付接口查询订单状态，如果支付成功，更新订单状态， 否则取消订单
            Boolean payFlag = true;

            if (payFlag) {

                ddxfjl.setZt(OrderStatusEnum.COMPLETED.getKey());
                ddxfjl.setZfsj(new Date());
            } else {

                ddxfjl.setZt(OrderStatusEnum.CANCELLED.getKey());
                ddxfjl.setQxsj(new Date());
            }

            ddxfjlService.updateById(ddxfjl);

            //消费消息
            channel.basicAck(msgTag, false);
            return;
        }

        //消费消息
        channel.basicAck(msgTag, false);
    }
}