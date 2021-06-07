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

/**
 * @Author: Austin
 * @Data: 2021/6/4
 * @Version: 1.0.0
 * @Description: 原专业成绩解析
 */
@Slf4j
@Data
@Component
public class YzyCjListener extends AnalysisEventListener<YzyCjDTO> {
    private StringBuilder result = new StringBuilder();
    private Integer count = 0;
    private String token;
    private Long pcId;
    private String jylx;

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
        //原专业


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

    private void save(XsCj xsCj) {
        XsCj xsCj1 = xsCjMapper.selectByPcIdAndXsId(xsCj.getPcId(),xsCj.getXsId());
        if (xsCj1 == null) {
            //新增
            xsCj.setCjr(getUserId());
            xsCj.setGxr(getUserId());
            xsCjMapper.insert(xsCj);
        } else {
            //更新
            xsCj.setId(xsCj1.getId());
            xsCj.setGxr(getUserId());
            xsCjMapper.updateById(xsCj);
        }

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
