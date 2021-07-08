package com.itts.personTraining.model.xsCj;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.itts.personTraining.dto.XlXwCjDTO;
import com.itts.personTraining.mapper.xsCj.XsCjMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Austin
 * @Data: 2021/6/3
 * @Version: 1.0.0
 * @Description: 学历学位成绩解析
 */
@Slf4j
@Data
@Component
public class XlXwCjListener extends AnalysisEventListener<XlXwCjDTO> {

    private StringBuilder result = new StringBuilder();
    private Integer count = 0;
    @Resource
    private XsCjMapper XsCjMapper;


    public static XlXwCjListener xlXwCjListener;

    private static final Logger LOGGER = LoggerFactory.getLogger(XlXwCjListener.class);
    /**
     * 正文起始行
     */
    private Integer headRowNumber;
    /**
     * 合并单元格
     */
    private List<CellExtra> extraMergeInfoList = new ArrayList<>();
    public XlXwCjListener() {}
    public XlXwCjListener(Integer headRowNumber) {
        this.headRowNumber = headRowNumber;
    }
    /**
     * 解析的数据
     */
    List<XlXwCjDTO> list = new ArrayList<>();

    @PostConstruct
    public void init() {
        xlXwCjListener = this;
    }

    @SneakyThrows
    @Override
    public void invoke(XlXwCjDTO data, AnalysisContext analysisContext) {
        ReadRowHolder rrh = analysisContext.readRowHolder();
        int rowIndex = rrh.getRowIndex() + 1;
        log.info("解析第" + rowIndex + "行数据:{}", JSON.toJSONString(data));
        list.add(data);
    }
    /**
     * 加上存储数据库
     */
    public List<XlXwCjDTO> getData() {
        return list;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
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
    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        log.info("读取到了一条额外信息:{}", JSON.toJSONString(extra));
        switch (extra.getType()) {
            case COMMENT: {
                LOGGER.info("额外信息是批注,在rowIndex:{},columnIndex;{},内容是:{}", extra.getRowIndex(), extra.getColumnIndex(),
                        extra.getText());
                break;
            }
            case HYPERLINK: {
                if ("Sheet1!A1".equals(extra.getText())) {
                    LOGGER.info("额外信息是超链接,在rowIndex:{},columnIndex;{},内容是:{}", extra.getRowIndex(),
                            extra.getColumnIndex(), extra.getText());
                } else if ("Sheet2!A1".equals(extra.getText())) {
                    LOGGER.info(
                            "额外信息是超链接,而且覆盖了一个区间,在firstRowIndex:{},firstColumnIndex;{},lastRowIndex:{},lastColumnIndex:{},"
                                    + "内容是:{}",
                            extra.getFirstRowIndex(), extra.getFirstColumnIndex(), extra.getLastRowIndex(),
                            extra.getLastColumnIndex(), extra.getText());
                } else {
                    Assert.fail("Unknown hyperlink!");
                }
                break;
            }
            case MERGE: {
                LOGGER.info(
                        "额外信息是合并单元格,而且覆盖了一个区间,在firstRowIndex:{},firstColumnIndex;{},lastRowIndex:{},lastColumnIndex:{}",
                        extra.getFirstRowIndex(), extra.getFirstColumnIndex(), extra.getLastRowIndex(),
                        extra.getLastColumnIndex());
                if (extra.getRowIndex() >= headRowNumber) {
                    extraMergeInfoList.add(extra);
                }
                break;
            }
            default: {
            }
        }
    }



    public String getResult() {
        return result.toString();
    }

    public List<CellExtra> getExtraMergeInfoList() {
        return extraMergeInfoList;
    }


}
