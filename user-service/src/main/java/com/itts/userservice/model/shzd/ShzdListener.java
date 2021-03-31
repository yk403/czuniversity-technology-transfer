package com.itts.userservice.model.shzd;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import com.itts.userservice.dto.ShzdDTO;
import com.itts.userservice.mapper.shzd.ShzdMapper;
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
public class ShzdListener extends AnalysisEventListener<ShzdDTO> {
    private StringBuilder result=new StringBuilder();
    private Integer count=0;
    @Resource
    private ShzdMapper shzdMapper;
    public static ShzdListener shzdListener;

    @PostConstruct
    public void init(){
        shzdListener=this;
    }
    @SneakyThrows
    @Override
    public void invoke(ShzdDTO data, AnalysisContext context) {
        ReadRowHolder readRowHolder = context.readRowHolder();
        int rowIndex=readRowHolder.getRowIndex()+1;
        log.info("解析第"+rowIndex+"行数据：{}", JSON.toJSONString(data));
        Shzd shzd = new Shzd();
        if(!StringUtils.isBlank(data.getZdmc())){
            shzd.setZdmc(data.getZdmc());
        }
        if(!StringUtils.isBlank(data.getZdbm())){
            shzd.setZdbm(data.getZdbm());
        }
        if(!StringUtils.isBlank(data.getFjzdCode())){
            shzd.setFjzdCode(data.getFjzdCode());
        }
        if(!StringUtils.isBlank(data.getZdcj())){
            shzd.setZdcj(data.getZdcj());
        }
        save(shzd);
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
    private void save(Shzd shzd){
        Shzd shzd1 = shzdMapper.selectByCode(shzd.getZdbm());
        if(shzd1!=null){
            shzd.setId(shzd1.getId());
            try {
                shzdMapper.updateById(shzd);
                count++;
            }catch (Exception e){
                log.info(e.getMessage());
            }
        }else{
            shzdMapper.insert(shzd);
            count++;
        }
    }
    public String  getResult(){
        return result.toString();
    }
}
