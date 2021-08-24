package com.itts.personTraining.model.xsCj;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.dto.*;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.mapper.xsCj.XsCjMapper;
import com.itts.personTraining.service.xs.XsService;
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

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;

/**
 * @Author: fuli
 * @Data: 2021/8/23
 * @Version: 1.0.0
 * @Description: 原专业成绩解析
 */
@Slf4j
@Data
@Component
public class YzyCjListener extends AnalysisEventListener<YzyCjDTO> {
    private StringBuilder result = new StringBuilder();
    private Integer count = 0;

    @Resource
    private XsService xsService;
    @Resource
    private PcXsMapper pcXsMapper;
    @Resource
    private XsCjMapper xsCjMapper;

    public static YzyCjListener YzyCjListener;

    private static final Logger LOGGER = LoggerFactory.getLogger(YzyCjListener.class);
    @PostConstruct
    public void init() {
        YzyCjListener = this;
    }
    /**
     * 正文起始行
     */
    private Integer headRowNumber;
    /**
     * 解析的数据
     */
    List<YzyCjDTO> list = new ArrayList<>();
    /**
     * 合并单元格
     */
    private List<CellExtra> extraMergeInfoList = new ArrayList<>();
    public YzyCjListener() {}
    public YzyCjListener(Integer headRowNumber) {
        this.headRowNumber = headRowNumber;
    }

    @SneakyThrows
    @Override
    public void invoke(YzyCjDTO data, AnalysisContext analysisContext) {
        ReadRowHolder rrh = analysisContext.readRowHolder();
        int rowIndex = rrh.getRowIndex() + 1;
        log.info("解析第" + rowIndex + "行数据:{}", JSON.toJSONString(data));

        list.add(data);


    }

    /**
     * 加上存储数据库
     */
    public List<YzyCjDTO> getData() {
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
    /**
     * 获取当前用户id
     * @return
     */
    private Long getUserId() {
        LoginUser loginUser = threadLocal.get();
        Long userId;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        } else {
            throw new ServiceException(GET_THREADLOCAL_ERROR);
        }
        return userId;
    }
}
