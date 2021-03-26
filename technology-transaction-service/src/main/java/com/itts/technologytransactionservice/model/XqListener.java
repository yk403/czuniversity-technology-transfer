package com.itts.technologytransactionservice.model;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.itts.technologytransactionservice.mapper.JsShMapper;
import com.itts.technologytransactionservice.mapper.JsXqMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Data
@Component
public class XqListener extends AnalysisEventListener<TJsXqDto> {

    private StringBuilder result = new StringBuilder();

    private Integer count= 0;

    @Autowired
    private JsXqMapper jsXqMapper;

    @Autowired
    private JsShMapper jsShMapper;
    public static XqListener xqListener;

    @PostConstruct
    public void init(){
        xqListener = this;
    }


    @SneakyThrows
    @Override
    public void invoke(TJsXqDto data, AnalysisContext analysisContext) {
        ReadRowHolder rrh = analysisContext.readRowHolder();
        int rowIndex = rrh.getRowIndex()+1;
        log.info("解析第"+rowIndex+"行数据:{}", JSON.toJSONString(data));
//        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        TJsXq tJsXq = new TJsXq();
        //技术需求名称
        if(!StringUtils.isBlank(data.getXqmc())){
            tJsXq.setXqmc(data.getXqmc());
        }
        //关键字
        if(!StringUtils.isBlank(data.getGjz())){
            tJsXq.setGjz(data.getGjz());
        }
        //有效期
        if(!StringUtils.isBlank(data.getYxq())){
            tJsXq.setYxq(data.getYxq());
        }
        //需求领域
        if(!StringUtils.isBlank(data.getLyId())){
            tJsXq.setLyId(data.getLyId());
        }
        //技术需求类别
        if(!StringUtils.isBlank(data.getLbId())){
            tJsXq.setLbId(data.getLbId());
        }
        //合作方式
        if(!StringUtils.isBlank(data.getHzfs())){
            tJsXq.setHzfs(data.getHzfs());
        }
        //合作价格
        if(!StringUtils.isBlank(data.getHzjg())){
            tJsXq.setHzjg(data.getHzjg());
        }
        //意向合作单位
        if(!StringUtils.isBlank(data.getYxhzdw())){
            tJsXq.setYxhzdw(data.getYxhzdw());
        }
        //意向合作专家
        if(!StringUtils.isBlank(data.getYxhzzj())){
            tJsXq.setYxhzzj(data.getYxhzzj());
        }
        //技术需求详情
        if(!StringUtils.isBlank(data.getXqxq())){
            tJsXq.setXqxq(data.getXqxq());
        }
        //技术指标
        if(!StringUtils.isBlank(data.getJszb())){
            tJsXq.setJszb(data.getJszb());
        }
        //需求图片
        if(!StringUtils.isBlank(data.getXqtp())){
            tJsXq.setXqtp(data.getXqtp());
        }
        //需求视频
        if(!StringUtils.isBlank(data.getXqsp())){
            tJsXq.setXqsp(data.getXqsp());
        }
        //单位名称
        if(!StringUtils.isBlank(data.getDwmc())){
            tJsXq.setDwmc(data.getDwmc());
        }
        //地址
        if(!StringUtils.isBlank(data.getDz())){
            tJsXq.setDz(data.getDz());
        }
        //法人
        if(!StringUtils.isBlank(data.getFr())){
            tJsXq.setFr(data.getFr());
        }
        //联系人
        if(!StringUtils.isBlank(data.getContracts())){
            tJsXq.setContracts(data.getContracts());
        }
        //联系人电话
        if(!StringUtils.isBlank(data.getLxrdh())){
            tJsXq.setLxrdh(data.getLxrdh());
        }
        //座机
        if(!StringUtils.isBlank(data.getZj())){
            tJsXq.setZj(data.getZj());
        }
        //电子邮箱
        if(!StringUtils.isBlank(data.getDzyx())){
            tJsXq.setDzyx(data.getDzyx());
        }
        //活动id
        if(!StringUtils.isBlank(data.getJshdId())){
            tJsXq.setJshdId(data.getJshdId());
        }
        //发布类型
        tJsXq.setReleaseType("技术需求");
        save(tJsXq);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        result.append("导入完成，成功导入");
        result.append(count);
        result.append("条数据");
        log.info("所有数据解析完成！");
    }

//    @Override
//    public void onException(Exception exception, AnalysisContext context) {
//        exception.printStackTrace();
//        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
//        // 如果是某一个单元格的转换异常 能获取到具体行号
//        // 如果要获取头的信息 配合invokeHeadMap使用
//        if (exception instanceof ExcelDataConvertException) {
//            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
//            log.error("第{}行，第{}列解析异常", excelDataConvertException.getRowIndex(),
//                    excelDataConvertException.getColumnIndex());
//        }
//    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        System.out.println("helloTwo");
        throw exception;
    }

    private void save(TJsXq tJsXq) {
        TJsXq tJsXqOld = jsXqMapper.selectByName(tJsXq.getXqmc());
        if(tJsXqOld != null){
            tJsXq.setId(tJsXqOld.getId());
            try {
                jsXqMapper.updateById(tJsXq);
                count++;
            }catch (Exception e){
                log.info(e.getMessage());
            }
        }else{

                if (tJsXq.getId() != null) {

                } else {
                    jsXqMapper.insert(tJsXq);
                    TJsSh tJsSh = new TJsSh();
                    tJsSh.setLx(2);
                    tJsSh.setXqId(tJsXq.getId());
                    tJsSh.setCjsj(new Date());
                    jsShMapper.insert(tJsSh);
                    count++;

                }

        }
    }

    public String  getResult(){
        return result.toString();
    }
}
