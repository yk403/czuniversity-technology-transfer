package com.itts.userservice.model.sjzd;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import com.itts.userservice.dto.SjzdDTO;
import com.itts.userservice.mapper.sjzd.SjzdMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Data
@Component
public class SjzdListener extends AnalysisEventListener<SjzdDTO> {
    private StringBuilder result=new StringBuilder();
    private Integer count=0;
    @Resource
    private SjzdMapper sjzdMapper;
    public static SjzdListener sjzdListener;

    @PostConstruct
    public void init(){
        sjzdListener =this;
    }
    @SneakyThrows
    @Override
    public void invoke(SjzdDTO data, AnalysisContext context) {
        ReadRowHolder readRowHolder = context.readRowHolder();
        int rowIndex=readRowHolder.getRowIndex()+1;
        log.info("解析第"+rowIndex+"行数据：{}", JSON.toJSONString(data));
        Sjzd sjzd = new Sjzd();
        if(!StringUtils.isBlank(data.getZdmc())){
            sjzd.setZdmc(data.getZdmc());
        }
        if(!StringUtils.isBlank(data.getZdbm())){
            sjzd.setZdbm(data.getZdbm());
        }
        if(!StringUtils.isBlank(data.getFjzdbm())){
            sjzd.setFjzdbm(data.getFjzdbm());
        }
        if(!StringUtils.isBlank(data.getZdcj())){
            sjzd.setZdcj(data.getZdcj());
        }
        if(!StringUtils.isBlank(data.getXtlb())){
            sjzd.setXtlx(data.getXtlb());
        }
        if(!StringUtils.isBlank(data.getMklx())){
            sjzd.setMklx(data.getMklx());
        }
        if(!StringUtils.isBlank(data.getSsmk())){
            sjzd.setSsmk(data.getXtlb());
        }
        save(sjzd);
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
    private void save(Sjzd sjzd){
        Sjzd sjzd1 = sjzdMapper.selectByCode(sjzd.getZdbm());
        if(sjzd1 !=null){
            sjzd.setId(sjzd1.getId());
            try {
                sjzdMapper.updateById(sjzd);
                count++;
            }catch (Exception e){
                log.info(e.getMessage());
            }
        }else{
            sjzdMapper.insert(sjzd);
            count++;
        }
    }
    public String  getResult(){
        return result.toString();
    }
}
