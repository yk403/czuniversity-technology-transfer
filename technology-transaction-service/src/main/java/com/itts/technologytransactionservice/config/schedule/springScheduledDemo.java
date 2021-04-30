package com.itts.technologytransactionservice.config.schedule;

import com.itts.technologytransactionservice.mapper.JsCgMapper;
import com.itts.technologytransactionservice.mapper.JsHdMapper;
import com.itts.technologytransactionservice.mapper.JsXqMapper;
import com.itts.technologytransactionservice.model.TJsCg;
import com.itts.technologytransactionservice.model.TJsHd;
import com.itts.technologytransactionservice.model.TJsLcKz;
import com.itts.technologytransactionservice.model.TJsXq;
import com.itts.technologytransactionservice.service.cd.JsLcKzAdminService;
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
    @Autowired
    private JsCgMapper jsCgMapper;
    @Autowired
    private JsXqMapper jsXqMapper;
    @Autowired
    private JsLcKzAdminService jsLcKzAdminService;
    //活动定时到开始时间如果是未开始状态置为进行中
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
                    if(item.getHdlx()== 0 || item.getHdlx()== 2){
                        Map<String,Object>cgmap=new HashMap<>();
                        cgmap.put("jshdId",item.getId());
                        cgmap.put("soft",1);
                        List<TJsCg> list1 = jsCgMapper.list(cgmap);
                        if(list1.size()>0){
                            if(list1.get(0).getAuctionStatus()==0){
                                list1.get(0).setAuctionStatus(1);
                                TJsLcKz tJsLcKz=new TJsLcKz();
                                tJsLcKz.setCgId(list1.get(0).getId());
                                tJsLcKz.setFdjg("1000");
                                jsLcKzAdminService.save(tJsLcKz);
                                jsCgMapper.updateTJsCg(list1.get(0));
                            }
                        }
                    }
                    if(item.getHdlx()== 1){
                        Map<String,Object>xqmap=new HashMap<>();
                        xqmap.put("jshdId",item.getId());
                        xqmap.put("soft",1);
                        List<TJsXq> list1 = jsXqMapper.list(xqmap);
                        if(list1.size()>0){
                            if(list1.get(0).getAuctionStatus()==0){
                                list1.get(0).setAuctionStatus(1);
                                TJsLcKz tJsLcKz=new TJsLcKz();
                                tJsLcKz.setXqId(list1.get(0).getId());
                                tJsLcKz.setFdjg("1000");
                                jsLcKzAdminService.save(tJsLcKz);
                                jsXqMapper.updateTJsXq(list1.get(0));
                            }
                        }
                    }


                }
            }
        }

    }
}
