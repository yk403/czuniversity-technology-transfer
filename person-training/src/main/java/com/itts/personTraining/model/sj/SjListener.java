package com.itts.personTraining.model.sj;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.dto.SjDrDTO;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.model.xs.Xs;
import com.itts.personTraining.model.zj.Zj;
import com.itts.personTraining.model.zj.ZjListener;
import com.itts.personTraining.service.sj.SjService;
import com.itts.personTraining.service.xy.XyService;
import com.itts.personTraining.service.yh.YhService;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * @Author: Austin
 * @Data: 2021/6/15
 * @Version: 1.0.0
 * @Description: 实践解析
 */
@Slf4j
@Data
@Component
public class SjListener extends AnalysisEventListener<SjDrDTO> {
    private StringBuilder result = new StringBuilder();
    private Integer count = 0;
    private String token;
    @Resource
    private XsMapper xsMapper;
    @Resource
    private SjService sjService;
    @Resource
    private XyService xyService;
    @Resource
    private YhService yhService;

    public static SjListener sjListener;

    @PostConstruct
    public void init() {
        sjListener = this;
    }

    @SneakyThrows
    @Override
    public void invoke(SjDrDTO data, AnalysisContext analysisContext) {
        ReadRowHolder rrh = analysisContext.readRowHolder();
        int rowIndex = rrh.getRowIndex() + 1;
        log.info("解析第" + rowIndex + "行数据:{}", JSON.toJSONString(data));
        Sj sj = new Sj();
        //姓名
        if (!StringUtils.isBlank(data.getXh()) && !StringUtils.isBlank(data.getXm())) {
            Xs xs = xsMapper.getByXhAndXm(data.getXh(),data.getXm());
            if (xs.getId() != null) {
                sj.setXsId(xs.getId());
            }

        }

        save(sj);
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

    private void save(Sj sj) {

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
