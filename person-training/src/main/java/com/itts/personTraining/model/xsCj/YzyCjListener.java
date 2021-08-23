package com.itts.personTraining.model.xsCj;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.exception.WebException;
import com.itts.personTraining.dto.JxjyCjDTO;
import com.itts.personTraining.dto.StuDTO;
import com.itts.personTraining.dto.XsCjDTO;
import com.itts.personTraining.dto.YzyCjDTO;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.mapper.xsCj.XsCjMapper;
import com.itts.personTraining.service.xs.XsService;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.personTraining.enums.EduTypeEnum.ACADEMIC_DEGREE_EDUCATION;
import static com.itts.personTraining.enums.EduTypeEnum.ADULT_EDUCATION;

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

    @PostConstruct
    public void init() {
        YzyCjListener = this;
    }

    @SneakyThrows
    @Override
    public void invoke(YzyCjDTO data, AnalysisContext analysisContext) {
        ReadRowHolder rrh = analysisContext.readRowHolder();
        int rowIndex = rrh.getRowIndex() + 1;
        log.info("解析第" + rowIndex + "行数据:{}", JSON.toJSONString(data));
        //判断是否是学历学位



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



    public String getResult() {
        return result.toString();
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
