package com.itts.personTraining.model.xs;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import com.itts.personTraining.dto.XsDTO;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.service.xs.XsService;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: Austin
 * @Data: 2021/4/21
 * @Version: 1.0.0
 * @Description: 学生解析
 */
@Slf4j
@Data
@Component
public class XsListener extends AnalysisEventListener<XsDTO> {
    private StringBuilder result=new StringBuilder();
    private Integer count=0;
    @Resource
    private XsMapper xsMapper;
    @Autowired
    private XsService xsService;

    public static XsListener xsListener;

    @PostConstruct
    public void init(){
        xsListener=this;
    }
    @SneakyThrows
    @Override
    public void invoke(XsDTO data, AnalysisContext context) {
        ReadRowHolder readRowHolder = context.readRowHolder();
        int rowIndex=readRowHolder.getRowIndex()+1;
        log.info("解析第"+rowIndex+"行数据：{}", JSON.toJSONString(data));
        Xs xs = new Xs();
        if(!StringUtils.isBlank(data.getXh())){
            xs.setXh(data.getXh());
        }
        if(!StringUtils.isBlank(data.getXm())){
            xs.setXm(data.getXm());
        }
        if(!StringUtils.isBlank(data.getNj())){
            xs.setNj(data.getNj());
        }
        if(!StringUtils.isBlank(data.getXb())){
            xs.setXb(data.getXb());
        }
        if(!StringUtils.isBlank(data.getCsrq().toString())){
            xs.setCsrq(data.getCsrq());
        }
        if(!StringUtils.isBlank(data.getXslbId())){
            xs.setXslbId(data.getXslbId());
        }
        if(!StringUtils.isBlank(data.getXslbmc())){
            xs.setXslbmc(data.getXslbmc());
        }
        if(!StringUtils.isBlank(data.getSfzh())){
            xs.setSfzh(data.getSfzh());
        }
        if(!StringUtils.isBlank(data.getByzh())){
            xs.setSfzh(data.getByzh());
        }
        if(!StringUtils.isBlank(data.getXwzh())){
            xs.setXwzh(data.getXwzh());
        }
        if(!StringUtils.isBlank(data.getYjfx())){
            xs.setYjfx(data.getYjfx());
        }
        if(!StringUtils.isBlank(data.getPylbId())){
            xs.setPylbId(data.getPylbId());
        }
        if(!StringUtils.isBlank(data.getPylbmc())){
            xs.setPylbmc(data.getPylbmc());
        }
        if(!StringUtils.isBlank(data.getJg())){
            xs.setJg(data.getJg());
        }
        if(!StringUtils.isBlank(data.getMz())){
            xs.setMz(data.getMz());
        }
        if(!StringUtils.isBlank(data.getZzmm())){
            xs.setZzmm(data.getZzmm());
        }
        if(!StringUtils.isBlank(data.getRxfs())){
            xs.setRxfs(data.getRxfs());
        }
        if(!StringUtils.isBlank(data.getYbyyx())){
            xs.setYbyyx(data.getYbyyx());
        }
        if(!StringUtils.isBlank(data.getYgzdw())){
            xs.setYgzdw(data.getYgzdw());
        }
        if(!StringUtils.isBlank(data.getYgzdwdz())){
            xs.setYgzdwdz(data.getYgzdwdz());
        }
        if(!StringUtils.isBlank(data.getXgzdw())){
            xs.setXgzdw(data.getXgzdw());
        }
        if(!StringUtils.isBlank(data.getXgzdwdz())){
            xs.setXgzdwdz(data.getXgzdwdz());
        }
        if(!StringUtils.isBlank(data.getXgzdwyb())){
            xs.setXgzdwyb(data.getXgzdwyb());
        }
        if(!StringUtils.isBlank(data.getXz())){
            xs.setXz(data.getXz());
        }
        if(!StringUtils.isBlank(data.getXxdd())){
            xs.setXxdd(data.getXxdd());
        }
        if(!StringUtils.isBlank(data.getBkbyzh())){
            xs.setBkbyzh(data.getBkbyzh());
        }
        if(!StringUtils.isBlank(data.getBkxwzh())){
            xs.setBkxwzh(data.getBkxwzh());
        }
        if(!StringUtils.isBlank(data.getLwtm())){
            xs.setLwtm(data.getLwtm());
        }
        if(!StringUtils.isBlank(data.getLwztc())){
            xs.setLwztc(data.getLwztc());
        }
        if(!StringUtils.isBlank(data.getDbrq().toString())){
            xs.setDbrq(data.getDbrq());
        }
        if(!StringUtils.isBlank(data.getByrq().toString())){
            xs.setByrq(data.getByrq());
        }
        if(!StringUtils.isBlank(data.getSxwrq().toString())){
            xs.setSxwrq(data.getSxwrq());
        }
        if(!StringUtils.isBlank(data.getXwlx())){
            xs.setXwlx(data.getXwlx());
        }
        if(!StringUtils.isBlank(data.getDsbh())){
            xs.setDsbh(data.getDsbh());
        }
        if(!StringUtils.isBlank(data.getDsxm())){
            xs.setDsxm(data.getDsxm());
        }
        if(!StringUtils.isBlank(data.getYx())){
            xs.setYx(data.getYx());
        }
        if(!StringUtils.isBlank(data.getJwzydm())){
            xs.setJwzydm(data.getJwzydm());
        }
        if(!StringUtils.isBlank(data.getXxxs())){
            xs.setXxxs(data.getXxxs());
        }
        if(!StringUtils.isBlank(data.getByjl())){
            xs.setByjl(data.getByjl());
        }
        if(!StringUtils.isBlank(data.getXzxm())){
            xs.setXzxm(data.getXzxm());
        }
        if(!StringUtils.isBlank(data.getBxlx())){
            xs.setBxlx(data.getBxlx());
        }
        if(!StringUtils.isBlank(data.getPydwm())){
            xs.setPydwm(data.getPydwm());
        }
        if(!StringUtils.isBlank(data.getPydw())){
            xs.setPydw(data.getPydw());
        }
        if(!StringUtils.isBlank(data.getZsjj())){
            xs.setZsjj(data.getZsjj());
        }
        if(!StringUtils.isBlank(data.getRxrq().toString())){
            xs.setRxrq(data.getRxrq());
        }
        if(!StringUtils.isBlank(data.getXzTwo())){
            xs.setXzTwo(data.getXzTwo());
        }
        if(!StringUtils.isBlank(data.getZkzh())){
            xs.setZkzh(data.getZkzh());
        }
        if(!StringUtils.isBlank(data.getLxdh())){
            xs.setLxdh(data.getLxdh());
        }
        if(!StringUtils.isBlank(data.getJtdz())){
            xs.setJtdz(data.getJtdz());
        }
        if(!StringUtils.isBlank(data.getBz())){
            xs.setBz(data.getBz());
        }
        if(!StringUtils.isBlank(data.getZydm())){
            xs.setZydm(data.getZydm());
        }
        if(!StringUtils.isBlank(data.getZy())){
            xs.setZy(data.getZy());
        }
        if(!StringUtils.isBlank(data.getJyxs())){
            xs.setJyxs(data.getJyxs());
        }
        save(xs);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        result.append("导入完成，成功导入");
        result.append(count);
        result.append("条数据");
        log.info("所有数据解析完成！");
    }
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        System.out.println("helloTwo");
        throw exception;
    }
    private void save(Xs xs){
        Xs xsOld = xsService.selectByXh(xs.getXh());
        if (xsOld != null) {
            xs.setId(xsOld.getId());
            xs.setGxsj(new Date());
            try {
                xsMapper.updateById(xs);
                count++;
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        } else {
            try {
                xs.setCjsj(new Date());
                xs.setGxsj(new Date());
                xsMapper.insert(xs);
                count++;
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
    }
    public String  getResult(){
        return result.toString();
    }
}
