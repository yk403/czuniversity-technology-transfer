package com.itts.personTraining.model.xsCj;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itts.common.bean.LoginUser;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.ServiceException;
import com.itts.common.exception.WebException;
import com.itts.personTraining.dto.JxjyCjDTO;
import com.itts.personTraining.dto.StuDTO;
import com.itts.personTraining.dto.XlXwCjDTO;
import com.itts.personTraining.enums.EduTypeEnum;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.mapper.xsCj.XsCjMapper;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.model.yh.GetYhVo;
import com.itts.personTraining.model.yh.Yh;
import com.itts.personTraining.service.sz.SzService;
import com.itts.personTraining.service.xs.XsService;
import com.itts.personTraining.service.xy.XyService;
import com.itts.personTraining.service.yh.YhService;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.personTraining.enums.EduTypeEnum.ADULT_EDUCATION;
import static com.itts.personTraining.enums.UserTypeEnum.IN;

/**
 * @Author: Austin
 * @Data: 2021/6/3
 * @Version: 1.0.0
 * @Description: 继续教育成绩解析
 */
@Slf4j
@Data
@Component
public class JxjyCjListener extends AnalysisEventListener<JxjyCjDTO> {
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

    public static JxjyCjListener jxjyCjListener;

    @PostConstruct
    public void init() {
        jxjyCjListener = this;
    }

    @SneakyThrows
    @Override
    public void invoke(JxjyCjDTO data, AnalysisContext analysisContext) {
        ReadRowHolder rrh = analysisContext.readRowHolder();
        int rowIndex = rrh.getRowIndex() + 1;
        log.info("解析第" + rowIndex + "行数据:{}", JSON.toJSONString(data));
        //判断是否是继续教育
        if (!ADULT_EDUCATION.getKey().equals(jylx)) {
            throw new WebException(EDU_TYPE_ERROR);
        }
        XsCj xsCj = new XsCj();
        StuDTO stuDTO = xsService.selectByCondition(data.getXh(), null, null);
        if (stuDTO == null) {
            throw new WebException(STUDENT_MSG_NOT_EXISTS_ERROR);
        }
        List<StuDTO> stuDTOList = pcXsMapper.selectStuByPcId(pcId);
        if (stuDTOList.contains(data.getXh())) {
            //综合成绩
            if (!StringUtils.isBlank(data.getZhcj())) {
                xsCj.setZhcj(data.getZhcj());
            }
            xsCj.setPcId(pcId);
            xsCj.setXsId(stuDTO.getId());
            save(xsCj);
        }
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
        } else {
            //更新
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
