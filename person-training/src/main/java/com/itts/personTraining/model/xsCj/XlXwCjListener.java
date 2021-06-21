package com.itts.personTraining.model.xsCj;

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
import com.itts.personTraining.dto.XlXwCjDTO;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.model.sz.SzListener;
import com.itts.personTraining.model.xy.Xy;
import com.itts.personTraining.model.yh.GetYhVo;
import com.itts.personTraining.model.yh.Yh;
import com.itts.personTraining.service.sz.SzService;
import com.itts.personTraining.service.xy.XyService;
import com.itts.personTraining.service.yh.YhService;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.personTraining.enums.UserTypeEnum.IN;

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
    private String token;
    private Long pcId;
    private String jylx;
    @Resource
    private SzMapper szMapper;
    @Resource
    private SzService szService;
    @Resource
    private XyService xyService;
    @Resource
    private YhService yhService;

    public static XlXwCjListener xlXwCjListener;

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
        Sz sz = new Sz();
        //所属机构Id

        save(sz);
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

    private void save(Sz sz) {
        Object data = yhService.getByCode(sz.getDsbh(), token).getData();
        String yhlx = IN.getKey();
        String yhlb = sz.getDslb();
        Long ssjgId = sz.getSsjgId();
        String dsbh = sz.getDsbh();
        String dsxm = sz.getDsxm();
        if (data != null) {
            //用户表存在用户信息,更新用户信息,师资表判断是否存在
            GetYhVo getYhVo = JSONObject.parseObject(JSON.toJSON(data).toString(), GetYhVo.class);
            Yh yh = new Yh();
            yh.setId(getYhVo.getId());
            yh.setZsxm(dsxm);
            yh.setYhlx(yhlx);
            yh.setYhlb(yhlb);
            yh.setJgId(ssjgId);
            yhService.update(yh,token);
            Sz sz1 = szService.selectByCondition(dsbh,null, null);
            if (sz1 != null) {
                //存在,则更新
                sz.setId(sz1.getId());
                sz.setGxr(getUserId());
                if (szService.updateById(sz)) {
                    count++;
                } else {
                    throw new WebException(INSERT_FAIL);
                }
            } else {
                //不存在,则新增
                if (szService.save(sz)) {
                    count++;
                } else {
                    throw new WebException(INSERT_FAIL);
                }
            }
        } else {
            //用户表没有用户信息,新增用户信息,学员表查询是否存在
            Yh yh = new Yh();
            yh.setYhbh(dsbh);
            yh.setYhm(dsbh);
            yh.setMm(dsbh);
            yh.setZsxm(dsxm);
            yh.setYhlx(yhlx);
            yh.setYhlb(yhlb);
            yh.setJgId(ssjgId);
            Object data1 = yhService.rpcAdd(yh, token).getData();
            if (data1 == null) {
                throw new ServiceException(USER_INSERT_ERROR);
            }
            Yh yh1 = JSONObject.parseObject(JSON.toJSON(data1).toString(), Yh.class);
            Long yh1Id = yh1.getId();
            sz.setYhId(yh1Id);
            Sz sz1 = szService.selectByCondition(dsbh,null, null);
            if (sz1 != null) {
                //存在,则更新
                sz.setId(sz1.getId());
                sz.setGxr(getUserId());
                if (szService.updateById(sz)) {
                    count++;
                } else {
                    throw new WebException(INSERT_FAIL);
                }
            } else {
                //不存在.则新增
                sz.setCjr(getUserId());
                sz.setGxr(getUserId());
                if (szService.save(sz)) {
                    count++;
                } else {
                    throw new WebException(INSERT_FAIL);
                }
            }
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
