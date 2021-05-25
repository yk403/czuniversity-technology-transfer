package com.itts.personTraining.model.xs;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.exception.WebException;
import com.itts.common.utils.DateUtils;
import com.itts.personTraining.dto.StuDTO;
import com.itts.personTraining.dto.XsDTO;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.pcXs.PcXs;
import com.itts.personTraining.model.xy.Xy;
import com.itts.personTraining.model.yh.GetYhVo;
import com.itts.personTraining.model.yh.Yh;
import com.itts.personTraining.service.xs.XsService;
import com.itts.personTraining.service.xy.XyService;
import com.itts.personTraining.service.yh.YhService;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.personTraining.enums.EduTypeEnum.ACADEMIC_DEGREE_EDUCATION;
import static com.itts.personTraining.enums.EduTypeEnum.ADULT_EDUCATION;
import static com.itts.personTraining.enums.UserTypeEnum.*;

/**
 * @Author: Austin
 * @Data: 2021/4/21
 * @Version: 1.0.0
 * @Description: 学生解析
 */
@Slf4j
@Data
@Component
public class XsListener extends AnalysisEventListener<XsDTO> {
    private StringBuilder result=new StringBuilder();
    private Integer count=0;
    private Pc pc;
    private String token;
    private Long jgId;
    @Resource
    private XsMapper xsMapper;
    @Autowired
    private XsService xsService;
    @Resource
    private PcXsMapper pcXsMapper;
    @Resource
    private XyService xyService;
    @Autowired
    private YhService yhService;
    @Resource
    private RedisTemplate redisTemplate;

    public static XsListener xsListener;

    @PostConstruct
    public void init(){
        xsListener=this;
    }
    @SneakyThrows
    @Override
    public void invoke(XsDTO data, AnalysisContext context) {
        if (pc.getId() == null) {
            throw new WebException(BATCH_NUMBER_ISEMPTY_ERROR);
        }
        ReadRowHolder readRowHolder = context.readRowHolder();
        int rowIndex=readRowHolder.getRowIndex()+1;
        log.info("解析第"+rowIndex+"行数据：{}", JSON.toJSONString(data));
        Xs xs = new Xs();
        if(!StringUtils.isBlank(data.getXh())){
            xs.setXh(data.getXh());
        }
        if(!StringUtils.isBlank(data.getXm())){
            xs.setXm(data.getXm());
        }
        if(!StringUtils.isBlank(data.getNj())){
            xs.setNj(data.getNj());
        }
        if(!StringUtils.isBlank(data.getXb())){
            xs.setXb(data.getXb());
        }
        if(!StringUtils.isBlank(data.getCsrq().toString())){
            xs.setCsrq(data.getCsrq());
        }
        if(!StringUtils.isBlank(data.getXslbId())){
            xs.setXslbId(data.getXslbId());
        }
        if(!StringUtils.isBlank(data.getXslbmc())){
            xs.setXslbmc(data.getXslbmc());
        }
        if(!StringUtils.isBlank(data.getSfzh())){
            xs.setSfzh(data.getSfzh());
        }
        if(!StringUtils.isBlank(data.getYjfx())){
            xs.setYjfx(data.getYjfx());
        }
        if(!StringUtils.isBlank(data.getJg())){
            xs.setJg(data.getJg());
        }
        if(!StringUtils.isBlank(data.getMz())){
            xs.setMz(data.getMz());
        }
        if(!StringUtils.isBlank(data.getZzmm())){
            xs.setZzmm(data.getZzmm());
        }
        if(!StringUtils.isBlank(data.getRxfs())){
            xs.setRxfs(data.getRxfs());
        }
        if(!StringUtils.isBlank(data.getYbyyx())){
            xs.setYbyyx(data.getYbyyx());
        }
        if(!StringUtils.isBlank(data.getXz())){
            xs.setXz(data.getXz());
        }
        if(!StringUtils.isBlank(data.getDsbh())){
            xs.setDsbh(data.getDsbh());
        }
        if(!StringUtils.isBlank(data.getDsxm())){
            xs.setDsxm(data.getDsxm());
        }
        if(!StringUtils.isBlank(data.getYx())){
            Xy xy = xyService.getByCondition(data.getYx());
            xs.setXyId(xy.getId());
        }
        if(!StringUtils.isBlank(data.getXxxs())){
            xs.setXxxs(data.getXxxs());
        }
        if(!StringUtils.isBlank(data.getByjl())){
            xs.setByjl(data.getByjl());
        }
        if(!StringUtils.isBlank(data.getRxrq().toString())){
            xs.setRxrq(data.getRxrq());
        }
        if(!StringUtils.isBlank(data.getLxdh())){
            xs.setLxdh(data.getLxdh());
        }
        if(!StringUtils.isBlank(data.getJtdz())){
            xs.setJtdz(data.getJtdz());
        }
        if(!StringUtils.isBlank(data.getYzydm())){
            xs.setYzydm(data.getYzydm());
        }
        if(!StringUtils.isBlank(data.getYzy())){
            xs.setYzy(data.getYzy());
        }
        if(!StringUtils.isBlank(data.getJyxs())){
            xs.setJyxs(data.getJyxs());
        }
        xs.setJgId(jgId);
        save(xs);
        PcXs pcXs = new PcXs();
        pcXs.setXsId(xs.getId());
        pcXs.setPcId(pc.getId());
        pcXsMapper.insert(pcXs);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
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
    private void save(Xs xs) {
        if (ACADEMIC_DEGREE_EDUCATION.getKey().equals(pc.getJylx())) {
            //说明是学历学位教育
            xs.setXslbmc(POSTGRADUATE.getKey());
            String xsXh = xs.getXh();
            if (xsXh != null) {
                Object data = yhService.getByCode(xsXh, token).getData();
                Yh yh = new Yh();
                String xm = xs.getXm();
                Long jgId = xs.getJgId();
                //String lxdh = stuDTO.getLxdh();
                String yhlx = IN.getKey();
                String yhlb = POSTGRADUATE.getKey();
                if (data != null) {
                    //说明用户表存在该用户信息
                    GetYhVo getYhVo = JSONObject.parseObject(JSON.toJSON(data).toString(), GetYhVo.class);
                    //作更新操作
                    yh.setId(getYhVo.getId());
                    yh.setZsxm(xm);
                    //yh.setLxdh(lxdh);
                    yh.setYhlx(yhlx);
                    yh.setYhlb(yhlb);
                    yh.setJgId(jgId);
                    StuDTO dto = xsService.selectByCondition(null, null, getYhVo.getId());
                    xs.setId(dto.getId());
                    try {
                        updateXsAndAddPcXs(xs,pc.getId());
                        //TODO 后期优化选择用mq同步
                        try {
                            yhService.update(yh, token);
                            count++;
                        } catch (Exception e) {
                            log.info(e.getMessage());
                        }
                    } catch (Exception e) {
                        log.info(e.getMessage());
                    }

                } else {
                    //说明用户表不存在该用户信息,则用户表新增,学生表查询判断是否存在
                    String xh = xs.getXh();
                    yh.setYhbh(xh);
                    yh.setYhm(xh);
                    yh.setMm(xh);
                    yh.setZsxm(xm);
                    //yh.setLxdh(lxdh);
                    yh.setYhlx(yhlx);
                    yh.setYhlb(yhlb);
                    yh.setJgId(jgId);
                    Object data1 = yhService.rpcAdd(yh, token).getData();
                    if (data1 == null) {
                        throw new WebException(USER_INSERT_ERROR);
                    }
                    Yh yh1 = JSONObject.parseObject(JSON.toJSON(data1).toString(), Yh.class);
                    Long yh1Id = yh1.getId();
                    xs.setYhId(yh1Id);
                    StuDTO dto = xsService.selectByCondition(xh, null, null);
                    if (dto != null) {
                        //存在,则更新
                        xs.setId(dto.getId());
                        xs.setGxsj(new Date());
                        if (updateXsAndAddPcXs(xs, pc.getId())) {
                            count++;
                        } else {
                            throw new WebException(INSERT_FAIL);
                        }
                    } else {
                        //不存在.则新增
                        xs.setCjsj(new Date());
                        xs.setGxsj(new Date());
                        if (addXsAndPcXs(xs, pc.getId())) {
                            count++;
                        } else {
                            throw new WebException(INSERT_FAIL);
                        }
                    }
                }
            }
        } else if (ADULT_EDUCATION.getKey().equals(pc.getJylx())) {
                //说明是继续教育
            xs.setXslbmc(BROKER.getKey());
            String phone = xs.getLxdh();
            if (phone != null) {
                Object data = yhService.getByPhone(phone, token).getData();
                //生成经纪人学号
                String bh = redisTemplate.opsForValue().increment(pc.getPch()).toString();
                String xh = pc.getJylx() + org.apache.commons.lang3.StringUtils.replace(DateUtils.toString(pc.getRxrq()),"/","") + String.format("%03d", Long.parseLong(bh));
                xs.setXh(xh);
                String xm = xs.getXm();
                //TODO 暂时是前端传,批量导入都是同一个机构id
                Long jgId = xs.getJgId();
                String lxdh = xs.getLxdh();
                String yhlx = IN.getKey();
                String yhlb = BROKER.getKey();
                if (data != null) {
                    //说明用户服务存在用户信息
                    Yh yh = JSONObject.parseObject(JSON.toJSON(data).toString(), Yh.class);
                    Long yhId = yh.getId();
                    yh.setYhbh(xh);
                    yh.setZsxm(xm);
                    yh.setLxdh(lxdh);
                    yh.setYhlx(yhlx);
                    yh.setYhlb(yhlb);
                    yh.setJgId(jgId);
                    yhService.update(yh,token);
                    StuDTO dto = xsService.selectByCondition(null, null, yhId);
                    xs.setId(dto.getId());
                    xs.setGxsj(new Date());
                    if (updateXsAndAddPcXs(xs, pc.getId())) {
                        count++;
                    } else {
                        throw new WebException(INSERT_FAIL);
                    }
                } else {
                    //说明用户表不存在该用户信息,则用户表新增,学生表查询判断是否存在
                    Yh yh = new Yh();
                    yh.setYhbh(xh);
                    yh.setYhm(xh);
                    yh.setMm(xh);
                    yh.setZsxm(xm);
                    yh.setLxdh(lxdh);
                    yh.setYhlx(yhlx);
                    yh.setYhlb(yhlb);
                    yh.setJgId(jgId);
                    Object data1 = yhService.rpcAdd(yh, token).getData();
                    if (data1 == null) {
                        throw new ServiceException(USER_INSERT_ERROR);
                    }
                    Yh yh1 = JSONObject.parseObject(JSON.toJSON(data1).toString(), Yh.class);
                    Long yh1Id = yh1.getId();
                    xs.setYhId(yh1Id);
                    StuDTO dto = xsService.selectByCondition(null, lxdh, null);
                    if (dto != null) {
                        //存在,则更新
                        xs.setId(dto.getId());
                        xs.setGxsj(new Date());
                        if (updateXsAndAddPcXs(xs,pc.getId())) {
                            count++;
                        } else {
                            throw new WebException(INSERT_FAIL);
                        }
                    } else {
                        //不存在.则新增
                        xs.setCjsj(new Date());
                        xs.setGxsj(new Date());
                        if (addXsAndPcXs(xs,pc.getId())) {
                            count++;
                        } else {
                            throw new WebException(INSERT_FAIL);
                        }
                    }
                }
            } else {
                throw new ServiceException(PHONE_NUMBER_ISEMPTY_ERROR);
            }
        } else {
            throw new WebException(EDU_TYPE_ERROR);
        }
            StuDTO stuDTO = xsService.selectByCondition(xs.getXh(), null, null);
            if (stuDTO != null) {
                xs.setId(stuDTO.getId());
                xs.setGxsj(new Date());
                try {
                    xsMapper.updateById(xs);
                    count++;
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
            } else {
                try {
                    xs.setCjsj(new Date());
                    xs.setGxsj(new Date());
                    xsMapper.insert(xs);
                    count++;
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
            }
        }

    public String  getResult(){
        return result.toString();
    }

    /**
     * 更新
     * @param xs
     * @param pcId
     * @return
     */
    private boolean updateXsAndAddPcXs(Xs xs,Long pcId) {
        xs.setGxr(getUserId());
        if (xsService.updateById(xs)) {
            if (pcId != null) {
                PcXs pcXs = new PcXs();
                pcXs.setXsId(xs.getId());
                pcXs.setPcId(pcId);
                return pcXsMapper.insert(pcXs) > 0;
            }
        }
        return false;
    }

    /**
     * 新增
     * @param xs
     * @param pcId
     * @return
     */
    private boolean addXsAndPcXs(Xs xs, Long pcId) {
        xs.setCjr(getUserId());
        xs.setGxr(getUserId());
        if (xsService.save(xs)) {
            if (pcId != null) {
                PcXs pcXs = new PcXs();
                pcXs.setXsId(xs.getId());
                pcXs.setPcId(pcId);
                return pcXsMapper.insert(pcXs) > 0;
            }
        }
        return false;
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
