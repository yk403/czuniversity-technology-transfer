package com.itts.personTraining.model.zj;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.dto.ZjDTO;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.mapper.zj.ZjMapper;
import com.itts.personTraining.model.yh.GetYhVo;
import com.itts.personTraining.model.yh.Yh;
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

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;
import static com.itts.common.enums.ErrorCodeEnum.USER_INSERT_ERROR;
import static com.itts.personTraining.enums.UserTypeEnum.IN;
import static com.itts.personTraining.enums.UserTypeEnum.PROFESSOR;
import static com.itts.personTraining.enums.ZzmmEnum.*;
import static com.itts.personTraining.enums.jslyEnum.*;

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
    @Resource
    private SzMapper szMapper;
    @Resource
    private ZjService zjService;
    @Resource
    private XyService xyService;
    @Resource
    private YhService yhService;
    @Resource
    private ZjMapper zjMapper;

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
        //编号
        if (!StringUtils.isBlank(data.getBh())) {
            zj.setBh(data.getBh());
        }
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
        if (data.getSr() != null) {
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
        //电子邮件
        if (!StringUtils.isBlank(data.getDzyj())) {
            zj.setDzyj(data.getDzyj());
        }
        //研究领域
        String yjly = data.getYjly();
        if (!StringUtils.isBlank(yjly)) {
            if (BIOLOGY_AND_MEDICAL_TECHNOLOGY.getMsg().equals(yjly)) {
                zj.setYjly(BIOLOGY_AND_MEDICAL_TECHNOLOGY.getKey());
            } else if (ELECTRONIC_TECHNOLOGY.getMsg().equals(yjly)) {
                zj.setYjly(ELECTRONIC_TECHNOLOGY.getKey());
            } else if (AEROSPACE_TECHNOLOGY.getMsg().equals(yjly)) {
                zj.setYjly(AEROSPACE_TECHNOLOGY.getKey());
            } else if (NEW_MATERIAL_TECHNOLOGY.getMsg().equals(yjly)) {
                zj.setYjly(NEW_MATERIAL_TECHNOLOGY.getKey());
            } else if (HIGH_TECH_SERVICE_INDUSTRY.getMsg().equals(yjly)) {
                zj.setYjly(HIGH_TECH_SERVICE_INDUSTRY.getKey());
            } else if (EQUIPMENT_AND_MANUFACTURING.getMsg().equals(yjly)) {
                zj.setYjly(EQUIPMENT_AND_MANUFACTURING.getKey());
            } else if (RESOURCE_AND_ENVIRONMENT_TECHNOLOGY.getMsg().equals(yjly)) {
                zj.setYjly(RESOURCE_AND_ENVIRONMENT_TECHNOLOGY.getKey());
            } else if (CHEMICAL_ENGINEERING.getMsg().equals(yjly)) {
                zj.setYjly(CHEMICAL_ENGINEERING.getKey());
            } else if (MODERN_AGRICULTURE_TECHNOLOGY.getMsg().equals(yjly)) {
                zj.setYjly(MODERN_AGRICULTURE_TECHNOLOGY.getKey());
            }
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
        Long userId = getUserId();
        zj.setGxr(userId);
        //通过手机号查询
        Object data = yhService.getByPhone(zj.getDh(), token).getData();
        String yhlx = IN.getKey();
        String yhlb = PROFESSOR.getKey();
        String bh = zj.getBh();
        String xm = zj.getXm();
        String lxdh = zj.getDh();
        if (data != null) {
            //用户表存在用户信息,更新用户信息,专家表判断是否存在
            GetYhVo getYhVo = JSONObject.parseObject(JSON.toJSON(data).toString(), GetYhVo.class);
            Yh yh = new Yh();
            yh.setId(getYhVo.getId());
            yh.setYhbh(bh);
            yh.setYhm(bh);
            yh.setMm(bh);
            yh.setZsxm(xm);
            yh.setYhlx(yhlx);
            yh.setYhlb(yhlb);
            yh.setLxdh(lxdh);
            yhService.update(yh,token);
            Zj zj1 = zjMapper.getByCondition(zj.getDh());
            zj.setYhId(getYhVo.getId());
            if (zj1 != null) {
                //存在,则更新
                zj.setId(zj1.getId());
                zjService.updateById(zj);
                count++;
            } else {
                //不存在,则新增
                zj.setCjr(userId);
                zjService.save(zj);
                count++;
            }
        } else {
            //用户表没有用户信息,新增用户信息,专家表查询是否存在
            Yh yh = new Yh();
            yh.setYhbh(bh);
            yh.setYhm(bh);
            yh.setMm(bh);
            yh.setZsxm(xm);
            yh.setYhlx(yhlx);
            yh.setYhlb(yhlb);
            yh.setLxdh(lxdh);
            Object data1 = yhService.rpcAdd(yh, token).getData();
            if (data1 == null) {
                throw new ServiceException(USER_INSERT_ERROR);
            }
            Yh yh1 = JSONObject.parseObject(JSON.toJSON(data1).toString(), Yh.class);
            Long yh1Id = yh1.getId();
            zj.setYhId(yh1Id);
            Zj zj1 = zjMapper.getByCondition(zj.getDh());
            if (zj1 != null) {
                //存在,则更新
                zj.setId(zj1.getId());
                zjService.updateById(zj);
                count++;
            } else {
                //不存在.则新增
                zj.setCjr(userId);
                zjService.save(zj);
                count++;
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
