package com.itts.technologytransactionservice.MqListerner;


import com.itts.technologytransactionservice.model.JsMsg;
import com.itts.technologytransactionservice.service.JsMsgService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@RabbitListener(queues = "orderQueue")
@Component
public class MqListerner {
    @Resource
    private JsMsgService jsMsgService;
    @RabbitHandler
    public void OrderMessage(JsMsg jsMsg, Message message, Channel channel){
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        boolean save = jsMsgService.save(jsMsg);
        if(save){
            try {
                channel.basicAck(deliveryTag,false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                channel.basicNack(deliveryTag,false,false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
