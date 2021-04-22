package com.itts.userservice.model.yh;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.userservice.dto.YhExcelDTO;
import com.itts.userservice.mapper.yh.YhMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Data
@Component
public class YhListener extends AnalysisEventListener<YhExcelDTO> {
    private StringBuilder result=new StringBuilder();
    private Integer count=0;
    @Resource
    private YhMapper yhMapper;
    public static YhListener yhListener;

    @PostConstruct
    public void init(){
        yhListener=this;
    }

    @SneakyThrows
    @Override
    public void invoke(YhExcelDTO data, AnalysisContext context) {
        ReadRowHolder readRowHolder = context.readRowHolder();
        int rowIndex=readRowHolder.getRowIndex()+1;
        log.info("解析第"+rowIndex+"行数据：{}", JSON.toJSONString(data));
        Yh yh = new Yh();
        if(!StringUtils.isBlank(data.getYhbh())){
            yh.setYhm(data.getYhbh());
        }
        if(!StringUtils.isBlank(data.getYhbh())){
            yh.setMm(new BCryptPasswordEncoder().encode(data.getYhbh()));
        }
        if(!StringUtils.isBlank(data.getYhbh())){
            yh.setYhbh(data.getYhbh());
        }
        if(!StringUtils.isBlank(data.getZsxm())){
            yh.setZsxm(data.getZsxm());
        }
        if(!StringUtils.isBlank(data.getLxdh())){
            yh.setLxdh(data.getLxdh());
        }
        if(!StringUtils.isBlank(data.getYhlx())){
            yh.setYhlx(data.getYhlx());
        }
        if(!StringUtils.isBlank(data.getJgid().toString())){
            yh.setJgId(data.getJgid());
        }
        if(!StringUtils.isBlank(data.getYhyx())){
            yh.setYhyx(data.getYhyx());
        }
        save(yh);
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
    private void save(Yh yh){
        QueryWrapper<Yh> yhQueryWrapper = new QueryWrapper<>();
        yhQueryWrapper.eq("yhbh",yh.getYhbh())
                .eq("sfsc",false);
        Yh yh1 = yhMapper.selectOne(yhQueryWrapper);
        if(yh1!=null) {
            return;
        }
        yhMapper.insert(yh);
        count++;
    }
    public String  getResult(){
        return result.toString();
    }
}
