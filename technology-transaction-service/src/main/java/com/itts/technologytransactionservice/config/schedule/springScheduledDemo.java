package com.itts.technologytransactionservice.config.schedule;

import com.itts.technologytransactionservice.mapper.JsHdMapper;
import com.itts.technologytransactionservice.model.TJsHd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.print.attribute.HashAttributeSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class springScheduledDemo {
    @Autowired
    private JsHdMapper jsHdMapper;
    @Scheduled(cron = "0 0/1 * * * ?")
    public void testScheduled(){
        //System.out.println("springScheduled run:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        Map<String,Object> map=new HashMap<>();
        List<TJsHd> list = jsHdMapper.list(map);
        for (TJsHd item:list) {
           Date startTime= item.getHdkssj();
            Date nowDate = new Date();
            if(startTime.before(nowDate)){
                if(item.getHdzt()==0){
                    item.setHdzt(1);
                    jsHdMapper.updateById(item);
                }
            }
        }

    }
}
