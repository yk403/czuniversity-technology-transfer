package com.itts.technologytransactionservice.config.schedule;

import com.itts.technologytransactionservice.mapper.*;
import com.itts.technologytransactionservice.model.*;
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
    private LyHdMapper lyHdMapper;
    @Autowired
    private JsCgMapper jsCgMapper;
    @Autowired
    private JsXqMapper jsXqMapper;
    @Autowired
    private JsLcKzAdminService jsLcKzAdminService;
    @Autowired
    private JsLcKzMapper jsLcKzMapper;
    //技术交易活动定时到开始时间如果是未开始状态置为进行中
    @Scheduled(cron = "0 0/1 * * * ?")
    public void testScheduled(){
        //System.out.println("springScheduled run:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        Map<String,Object> map=new HashMap<>();
        List<TJsHd> list = jsHdMapper.list(map);
        if(list.size()>0){
            for (TJsHd item:list) {
                Date startTime= item.getHdkssj();
                Date nowDate = new Date();
                //判断如果活动开始时
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
                                    jsXqMapper.updateTJsXq(list1.get(0));
                                }
                            }
                        }


                    }else if(item.getHdzt()==1){
                        if(item.getHdjssj()!=null){
                            if(item.getHdlx()==2){
                                //如果活动时间已结束则置活动状态为已结束
                                if(item.getHdjssj().before(nowDate)){
                                    item.setHdzt(2);
                                    jsHdMapper.updateById(item);
                                }
                            }
                        }

                    }
                }
            }
        }

    }
    //双创路演活动定时到活动开始时间就开始活动
    @Scheduled(cron = "5 0/1 * * * ?")
    public void startRoadShow(){
        Map<String,Object> map=new HashMap<>();
        List<LyHd> lyHdBack = lyHdMapper.findLyHdBack(map);
        if(lyHdBack.size()>0){
            for (LyHd item:lyHdBack){
                Date startTime=item.getHdkssj();
                Date nowDate = new Date();
                //判断如果活动开始时
                if(item.getHdkssj()!=null){
                    if(startTime.before(nowDate)){
                        if(item.getHdzt()==0 && item.getHdfbzt() == 1){
                            item.setHdzt(1);
                            lyHdMapper.updateById(item);
                        }else if(item.getHdzt()==1 && item.getHdfbzt() == 1){
                            if(item.getHdjssj()!=null){
                                //如果活动时间已结束则置活动状态为已结束
                                if(item.getHdjssj().before(nowDate)){
                                    item.setHdzt(2);
                                    lyHdMapper.updateById(item);
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}
