package com.itts.userservice.model.jggl;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import com.itts.userservice.dto.JgglDTO;
import com.itts.userservice.mapper.jggl.JgglMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Data
@Component
public class JgglListener extends AnalysisEventListener<JgglDTO> {
    private StringBuilder result=new StringBuilder();
    private Integer count=0;
    @Resource
    private JgglMapper jgglMapper;

    public static JgglListener jgglListener;

    @PostConstruct
    public void init(){
        jgglListener=this;
    }

    @SneakyThrows
    @Override
    public void invoke(JgglDTO data, AnalysisContext context) {
        ReadRowHolder readRowHolder = context.readRowHolder();
        int rowIndex=readRowHolder.getRowIndex()+1;
        log.info("解析第"+rowIndex+"行数据：{}", JSON.toJSONString(data));
        Jggl jggl = new Jggl();
        //名称
        if(!StringUtils.isBlank(data.getJgmc())){
            jggl.setJgmc(data.getJgmc());
        }
        //编码
        if(!StringUtils.isBlank(data.getJgbm())){
            jggl.setJgbm(data.getJgbm());
        }
        //机构类别code
        if(!StringUtils.isBlank(data.getJglbbm())){
            jggl.setJglbbm(data.getJglbbm());
        }
        //机构类别
        if(!StringUtils.isBlank(data.getJglb())){
            jggl.setJglb(data.getJglb());
        }
        //父机构code
        if (!StringUtils.isBlank(data.getFjbm())){
            jggl.setFjbm(data.getFjbm());
        }
        //菜单code层级, 以“-”分隔
        if(!StringUtils.isBlank(data.getCj())){
            jggl.setCj(data.getCj());
        }
        save(jggl);
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


    private void save(Jggl jggl){
        Jggl jgglOld = jgglMapper.selectByCode(jggl.getJgbm());
        //数据库有数据，更新
        if(jgglOld!=null){
            jggl.setId(jgglOld.getId());
            try {
                jgglMapper.updateById(jggl);
                count++;
            }catch (Exception e){
                log.info(e.getMessage());
            }

        }else {
            //数据库没有数据，插入
            jgglMapper.insert(jggl);
            count++;
        }
    }
    public String  getResult(){
        return result.toString();
    }
}
