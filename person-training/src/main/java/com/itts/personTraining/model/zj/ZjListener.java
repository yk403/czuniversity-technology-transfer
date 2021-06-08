package com.itts.personTraining.model.zj;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.exception.WebException;
import com.itts.personTraining.dto.SzDTO;
import com.itts.personTraining.dto.ZjDTO;
import com.itts.personTraining.enums.ZzmmEnum;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.model.sz.SzListener;
import com.itts.personTraining.model.xy.Xy;
import com.itts.personTraining.model.yh.GetYhVo;
import com.itts.personTraining.model.yh.Yh;
import com.itts.personTraining.service.sz.SzService;
import com.itts.personTraining.service.xy.XyService;
import com.itts.personTraining.service.yh.YhService;
import com.itts.personTraining.service.zj.ZjService;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.personTraining.enums.UserTypeEnum.IN;
import static com.itts.personTraining.enums.ZzmmEnum.*;

/**
 * @Author: Austin
 * @Data: 2021/6/7
 * @Version: 1.0.0
 * @Description: 专家解析
 */
@Slf4j
@Data
@Component
public class ZjListener extends AnalysisEventListener<ZjDTO> {
    private StringBuilder result = new StringBuilder();
    private Integer count = 0;
    private String token;
    private Long jgId;
    @Resource
    private SzMapper szMapper;
    @Resource
    private ZjService zjService;
    @Resource
    private XyService xyService;
    @Resource
    private YhService yhService;

    public static ZjListener zjListener;

    @PostConstruct
    public void init() {
        zjListener = this;
    }

    @SneakyThrows
    @Override
    public void invoke(ZjDTO data, AnalysisContext analysisContext) {
        ReadRowHolder rrh = analysisContext.readRowHolder();
        int rowIndex = rrh.getRowIndex() + 1;
        log.info("解析第" + rowIndex + "行数据:{}", JSON.toJSONString(data));
        Zj zj = new Zj();
        //姓名
        if (!StringUtils.isBlank(data.getXm())) {
            zj.setXm(data.getXm());
        }
        //身份证号
        if (!StringUtils.isBlank(data.getSfzh())) {
            zj.setSfzh(data.getSfzh());
        }
        //性别
        if (!StringUtils.isBlank(data.getXb())) {
            zj.setXb(data.getXb());
        }
        //生日
        if (!StringUtils.isBlank(data.getSr().toString())) {
            zj.setSr(data.getSr());
        }
        //民族
        if (!StringUtils.isBlank(data.getMz())) {
            zj.setMz(data.getMz());
        }
        //政治面貌
        String zzmm = data.getZzmm();
        if (!StringUtils.isBlank(zzmm)) {
            if (MEMBER.getMsg().equals(zzmm)) {
                zj.setZzmm(MEMBER.getKey());
            } else if (PARTY_MEMBER.getMsg().equals(zzmm)) {
                zj.setZzmm(PARTY_MEMBER.getKey());
            } else if (ACTIVIST.getMsg().equals(zzmm)) {
                zj.setZzmm(ACTIVIST.getKey());
            } else if (PRO_PARTY_MEMBER.getMsg().equals(zzmm)) {
                zj.setZzmm(PRO_PARTY_MEMBER.getKey());
            }
        }
        //学历
        if (!StringUtils.isBlank(data.getXl())) {
            zj.setXl(data.getXl());
        }
        //类型(校内;校外)
        if (!StringUtils.isBlank(data.getLx())) {
            zj.setLx(data.getLx());
        }
        //专业技术职位
        if (!StringUtils.isBlank(data.getZyjszw())) {
            zj.setZyjszw(data.getZyjszw());
        }
        //单位(大学)
        if (!StringUtils.isBlank(data.getDw())) {
            zj.setDw(data.getDw());
        }
        //地址
        if (!StringUtils.isBlank(data.getDz())) {
            zj.setDz(data.getDz());
        }
        //电话
        if (!StringUtils.isBlank(data.getDh())) {
            zj.setDh(data.getDh());
        }
        //座机号
        if (!StringUtils.isBlank(data.getZjh())) {
            zj.setZjh(data.getZjh());
        }
        //所属行业
        if (!StringUtils.isBlank(data.getSshy())) {
            zj.setSshy(data.getSshy());
        }
        //从事学科
        if (!StringUtils.isBlank(data.getCsxk())) {
            zj.setCsxk(data.getCsxk());
        }
        //专长方向
        if (!StringUtils.isBlank(data.getZcfx())) {
            zj.setZcfx(data.getZcfx());
        }
        //研究成果
        if (!StringUtils.isBlank(data.getYjcg())) {
            zj.setYjcg(data.getYjcg());
        }
        //知识产权
        if (!StringUtils.isBlank(data.getZscq())) {
            zj.setZscq(data.getZscq());
        }
        //在建项目
        if (!StringUtils.isBlank(data.getZjxm())) {
            zj.setZjxm(data.getZjxm());
        }
        //论文
        if (!StringUtils.isBlank(data.getLw())) {
            zj.setLw(data.getLw());
        }
        //专利号
        if (!StringUtils.isBlank(data.getZlh())) {
            zj.setZlh(data.getZlh());
        }
        //备注
        if (!StringUtils.isBlank(data.getBz())) {
            zj.setBz(data.getBz());
        }
        save(zj);
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

    private void save(Zj zj) {
        Zj byXmDh = zjService.getByXmDh(zj.getXm(), zj.getDh());
        if (byXmDh == null) {
            //新增
            zj.setCjr(getUserId());
            zj.setGxr(getUserId());
            zj.setCjsj(new Date());
            zj.setGxsj(new Date());
            zjService.save(zj);
        } else {
            //更新
            zj.setGxr(getUserId());
            zj.setGxsj(new Date());
            zjService.update(zj);
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
