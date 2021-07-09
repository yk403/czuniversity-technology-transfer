package com.itts.personTraining.model.sz;

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
import com.itts.personTraining.dto.XsDTO;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.model.xy.Xy;
import com.itts.personTraining.model.yh.GetYhVo;
import com.itts.personTraining.model.yh.Yh;
import com.itts.personTraining.service.sz.SzService;
import com.itts.personTraining.service.xy.XyService;
import com.itts.personTraining.service.yh.YhService;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.personTraining.enums.UserTypeEnum.*;
import static com.itts.personTraining.enums.ZzmmEnum.*;
import static com.itts.personTraining.enums.ZzmmEnum.PRO_PARTY_MEMBER;

/**
 * @Author: Austin
 * @Data: 2021/4/21
 * @Version: 1.0.0
 * @Description: 师资解析
 */
@Slf4j
@Data
@Component
public class SzListener extends AnalysisEventListener<SzDTO> {
    private StringBuilder result = new StringBuilder();
    private Integer count = 0;
    private String token;
    private Long jgId;
    @Resource
    private SzMapper szMapper;
    @Resource
    private SzService szService;
    @Resource
    private XyService xyService;
    @Resource
    private YhService yhService;

    public static SzListener szListener;

    @PostConstruct
    public void init() {
        szListener = this;
    }

    @SneakyThrows
    @Override
    public void invoke(SzDTO data, AnalysisContext analysisContext) {
        ReadRowHolder rrh = analysisContext.readRowHolder();
        int rowIndex = rrh.getRowIndex() + 1;
        log.info("解析第" + rowIndex + "行数据:{}", JSON.toJSONString(data));
        Sz sz = new Sz();
        //所属机构Id
        //导师编号
        if (!StringUtils.isBlank(data.getDsbh())) {
            sz.setDsbh(data.getDsbh());
        }
        //导师姓名
        if (!StringUtils.isBlank(data.getDsxm())) {
            sz.setDsxm(data.getDsxm());
        }
        //电话
        if (!StringUtils.isBlank(data.getDh())) {
            sz.setDh(data.getDh());
        }
        //性别
        if (!StringUtils.isBlank(data.getXb())) {
            sz.setXb(data.getXb());
        }
        //身份证号
        if (!StringUtils.isBlank(data.getSfzh())) {
            sz.setSfzh(data.getSfzh());
        }
        //籍贯
        if (!StringUtils.isBlank(data.getJg())) {
            sz.setJg(data.getJg());
        }
        //出生日期
        if (data.getCsrq() != null) {
            sz.setCsrq(data.getCsrq());
        }
        //民族
        if (!StringUtils.isBlank(data.getMz())) {
            sz.setMz(data.getMz());
        }
        //政治面貌
        String zzmm = data.getZzmm();
        if (!StringUtils.isBlank(zzmm)) {
            if (MEMBER.getMsg().equals(zzmm)) {
                sz.setZzmm(MEMBER.getKey());
            } else if (PARTY_MEMBER.getMsg().equals(zzmm)) {
                sz.setZzmm(PARTY_MEMBER.getKey());
            } else if (ACTIVIST.getMsg().equals(zzmm)) {
                sz.setZzmm(ACTIVIST.getKey());
            } else if (PRO_PARTY_MEMBER.getMsg().equals(zzmm)) {
                sz.setZzmm(PRO_PARTY_MEMBER.getKey());
            }
        }
        //文化程度
        if (!StringUtils.isBlank(data.getWhcd())) {
            sz.setWhcd(data.getWhcd());
        }
        //院系
        if (!StringUtils.isBlank(data.getYx())) {
            sz.setYx(data.getYx());
        }
        //干部职务
        if (!StringUtils.isBlank(data.getGbzw())) {
            sz.setGbzw(data.getGbzw());
        }
        //从事专业1
        if (!StringUtils.isBlank(data.getCszyOne())) {
            sz.setCszyOne(data.getCszyOne());
        }
        //从事专业2
        if (!StringUtils.isBlank(data.getCszyTwo())) {
            sz.setCszyTwo(data.getCszyTwo());
        }
        //从事专业3
        if (!StringUtils.isBlank(data.getCszyThree())) {
            sz.setCszyThree(data.getCszyThree());
        }
        //技术职称
        if (!StringUtils.isBlank(data.getJszc())) {
            sz.setJszc(data.getJszc());
        }
        //单位电话
        if (!StringUtils.isBlank(data.getDwdh())) {
            sz.setDwdh(data.getDwdh());
        }
        //批准硕导年月
        if (!StringUtils.isBlank(data.getPzsdny())) {
            sz.setPzsdny(data.getPzsdny());
        }
        //批准博导年月
        if (!StringUtils.isBlank(data.getPzbdny())) {
            sz.setPzbdny(data.getPzbdny());
        }
        //在岗状态
        if (!StringUtils.isBlank(data.getZgzt())) {
            sz.setZgzt(data.getZgzt());
        }
        //导师类别
        String dslb = data.getDslb();
        if (!StringUtils.isBlank(dslb)) {
            if (TUTOR.getMsg().equals(dslb)) {
                sz.setDslb(TUTOR.getKey());
            } else if (CORPORATE_MENTOR.getMsg().equals(dslb)) {
                sz.setDslb(CORPORATE_MENTOR.getKey());
            } else if (TEACHER.getMsg().equals(dslb)) {
                sz.setDslb(TEACHER.getKey());
            }
        }
       /* //个人照片
        if (!StringUtils.isBlank(data.getGrzp())) {
            sz.setGrzp(data.getGrzp());
        }
        //行业领域
        if (!StringUtils.isBlank(data.getHyly())) {
            sz.setHyly(data.getHyly());
        }
        //所属机构
        if (!StringUtils.isBlank(data.getSsjg())) {
            sz.setSsjg(data.getSsjg());
        }
        //专属资格证
        if (!StringUtils.isBlank(data.getZszgz())) {
            sz.setZszgz(data.getZszgz());
        }
        //驻入时间
        if (!StringUtils.isBlank(data.getZrsj().toString())) {
            sz.setZrsj(data.getZrsj());
        }*/
        sz.setSsjgId(jgId);
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
        Long userId = getUserId();
        String yhlx = IN.getKey();
        String yhlb = sz.getDslb();
        Long ssjgId = sz.getSsjgId();
        String dsbh = sz.getDsbh();
        String dsxm = sz.getDsxm();
        String lxdh = sz.getDh();
        sz.setGxr(userId);
        if (data != null) {
            //用户表存在用户信息,更新用户信息,师资表判断是否存在
            GetYhVo getYhVo = JSONObject.parseObject(JSON.toJSON(data).toString(), GetYhVo.class);
            Yh yh = new Yh();
            yh.setId(getYhVo.getId());
            yh.setZsxm(dsxm);
            yh.setYhlx(yhlx);
            yh.setYhlb(yhlb);
            yh.setJgId(ssjgId);
            yh.setLxdh(lxdh);
            yhService.update(yh,token);
            Sz sz1 = szService.selectByCondition(dsbh,null, null);
            if (sz1 != null) {
                //存在,则更新
                sz.setId(sz1.getId());
                if (szService.updateById(sz)) {
                    count++;
                } else {
                    throw new WebException(INSERT_FAIL);
                }
            } else {
                //不存在,则新增
                sz.setCjr(userId);
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
            yh.setLxdh(lxdh);
            sz.setGxr(userId);
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
                if (szService.updateById(sz)) {
                    count++;
                } else {
                    throw new WebException(INSERT_FAIL);
                }
            } else {
                //不存在.则新增
                sz.setCjr(userId);
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
