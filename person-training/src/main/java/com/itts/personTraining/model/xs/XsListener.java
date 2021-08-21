package com.itts.personTraining.model.xs;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.exception.WebException;
import com.itts.common.utils.DateUtils;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.SjDTO;
import com.itts.personTraining.dto.StuDTO;
import com.itts.personTraining.dto.XsCjDTO;
import com.itts.personTraining.dto.XsDTO;
import com.itts.personTraining.enums.SsmkEnum;
import com.itts.personTraining.feign.userservice.SjzdFeignService;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.pcXs.PcXs;
import com.itts.personTraining.model.sj.Sj;
import com.itts.personTraining.model.sjzd.Sjzd;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.model.xsCj.XsCj;
import com.itts.personTraining.model.yh.GetYhVo;
import com.itts.personTraining.model.yh.Yh;
import com.itts.personTraining.service.pcXs.PcXsService;
import com.itts.personTraining.service.sj.SjService;
import com.itts.personTraining.service.sz.SzService;
import com.itts.personTraining.service.xs.XsService;
import com.itts.personTraining.service.xsCj.XsCjService;
import com.itts.personTraining.service.xy.XyService;
import com.itts.personTraining.service.yh.YhService;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
@Transactional(rollbackFor = Exception.class)
public class XsListener extends AnalysisEventListener<XsDTO> {
    private StringBuilder result=new StringBuilder();
    private Integer count=0;
    private Long pcId;
    private String jylx;
    private String pch;
    private String token;
    private Long jgId;
    private Date rxrq;
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
    private SzService szService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private PcXsService pcXsService;
    @Resource
    private XsCjService xsCjService;
    @Resource
    private SjService sjService;
    @Resource
    private SjzdFeignService sjzdFeignService;

    public static XsListener xsListener;

    @PostConstruct
    public void init(){
        xsListener=this;
    }
    @SneakyThrows
    @Override
    public void invoke(XsDTO data, AnalysisContext context) {
        if (pcId == null) {
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
        if(!StringUtils.isBlank(data.getYzydsbh())){
            Sz sz = szService.selectByCondition(data.getYzydsbh(), null, null,null);
            if (sz != null) {
                xs.setYzydsId(sz.getId());
            }
            //TODO:如果为空则跳过
        }

        if(!StringUtils.isBlank(data.getYx())){
            xs.setYx(data.getYx());
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
        xs.setBmfs("线下");
        save(xs);
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
        ResponseUtil  list1= sjzdFeignService.getList(null, null, SsmkEnum.USER_TYPE.getKey());
        List<Sjzd> sjzd1 = new ArrayList<>();
        if(list1.getErrCode().intValue() == 0){
            sjzd1= list1.conversionData(new TypeReference<List<Sjzd>>() {});
        }

        ResponseUtil list2 = sjzdFeignService.getList(null, null, SsmkEnum.POLITICS_STATUS.getKey());
        List<Sjzd> sjzd2 = new ArrayList<>();
        if(list2.getErrCode().intValue() == 0){
            sjzd2= list2.conversionData(new TypeReference<List<Sjzd>>() {});
        }

        ResponseUtil list3 = sjzdFeignService.getList(null, null, SsmkEnum.STUDY_TYPE.getKey());
        List<Sjzd> sjzd3 = new ArrayList<>();
        if(list3.getErrCode().intValue() == 0){
            sjzd3= list3.conversionData(new TypeReference<List<Sjzd>>() {});
        }

        ResponseUtil response4 = sjzdFeignService.getList(null, null, SsmkEnum.STUDY_MODE.getKey());
        List<Sjzd> data4=new ArrayList<Sjzd>();
        if(response4.getErrCode().intValue() == 0){
            data4= response4.conversionData(new TypeReference<List<Sjzd>>() {});
        }
        for (Sjzd sjzd : sjzd1) {
            if(Objects.equals(sjzd.getZdmc(),xs.getXslbmc())){
                xs.setXslbmc(sjzd.getZdbm());
                break;
            }
        }
        for (Sjzd sjzd : sjzd2) {
            if(Objects.equals(sjzd.getZdmc(),xs.getZzmm())){
                xs.setZzmm(sjzd.getZdbm());
                break;
            }
        }
        for (Sjzd sjzd : sjzd3) {
            if(Objects.equals(sjzd.getZdmc(),xs.getRxfs())){
                xs.setRxfs(sjzd.getZdbm());
                break;
            }
        }
        for (Sjzd sjzd : data4) {
            if(Objects.equals(sjzd.getZdmc(),xs.getXxxs())){
                xs.setXxxs(sjzd.getZdbm());
                break;
            }
        }

        if (ACADEMIC_DEGREE_EDUCATION.getKey().equals(jylx)) {
            //说明是学历学位教育
            xs.setXslbmc(POSTGRADUATE.getKey());
            String xsXh = xs.getXh();
            String lxdh = xs.getLxdh();
            if (xsXh != null) {
                ResponseUtil result = yhService.getByCode(xsXh, token);
                Object data = result.getData();

                Yh yh = new Yh();
                String xm = xs.getXm();
                //String lxdh = stuDTO.getLxdh();
                String yhlx = IN.getKey();
                String yhlb = POSTGRADUATE.getKey();
                if (data != null) {
                    //说明用户表存在该用户信息
                    GetYhVo getYhVo = result.conversionData(new TypeReference<GetYhVo>() {});
                    //作更新操作
                    yh.setId(getYhVo.getId());
                    yh.setZsxm(xm);
                    //yh.setLxdh(lxdh);
                    yh.setYhlx(yhlx);
                    yh.setYhlb(yhlb);
                    yh.setJgId(jgId);
                    yh.setLxdh(lxdh);
                    StuDTO dto = xsService.selectByCondition(null, null, getYhVo.getId());
                    if (dto != null) {
                        //学生表存在
                        xs.setId(dto.getId());
                        try {
                            updateXsAndAddPcXs(xs,pcId);
                            addXscjAndSj(dto);
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
                        //学生表不存在
                        //不存在.则新增
                        xs.setCjsj(new Date());
                        xs.setGxsj(new Date());
                        if (addXsAndPcXs(xs, pcId)) {
                            StuDTO dto1 = xsService.selectByCondition(null, null, getYhVo.getId());
                            addXscjAndSj(dto1);
                            count++;
                        } else {
                            throw new WebException(INSERT_FAIL);
                        }
                    }
                } else {
                    //说明用户表不存在该用户信息,则用户表新增,学生表查询判断是否存在
                    String xh = xs.getXh();
                    yh.setYhbh(xh);
                    yh.setYhm(xh);
                    yh.setMm(xh);
                    yh.setZsxm(xm);
                    yh.setLxdh(lxdh);
                    yh.setYhlx(yhlx);
                    yh.setYhlb(yhlb);
                    yh.setJgId(jgId);
                    yh.setLxdh(lxdh);
                    Object data1=1;
                    try {
                        data1 = yhService.rpcAdd(yh, token).getData();
                        if (data1 == null) {
                            throw new WebException(USER_INSERT_ERROR);
                        }
                    } catch (Exception e) {
                        log.info(e.getMessage());
                    }

                    Yh yh1 = JSONObject.parseObject(JSON.toJSON(data1).toString(), Yh.class);
                    Long yh1Id = yh1.getId();
                    xs.setYhId(yh1Id);
                    StuDTO dto = xsService.selectByCondition(xh, null, null);
                    if (dto != null) {
                        //存在,则更新
                        xs.setId(dto.getId());
                        xs.setGxsj(new Date());
                        if (updateXsAndAddPcXs(xs, pcId)) {
                            addXscjAndSj(dto);
                            count++;
                        } else {
                            throw new WebException(INSERT_FAIL);
                        }
                    } else {
                        //不存在.则新增
                        xs.setCjsj(new Date());
                        xs.setGxsj(new Date());
                        if (addXsAndPcXs(xs, pcId)) {
                            StuDTO dto1 = xsService.selectByCondition(xh, null, null);
                            addXscjAndSj(dto1);
                            count++;
                        } else {
                            throw new WebException(INSERT_FAIL);
                        }
                    }
                }
            }
        } else if (ADULT_EDUCATION.getKey().equals(jylx)) {
                //说明是继续教育
            xs.setXslbmc(BROKER.getKey());
            String phone = xs.getLxdh();
            if (phone != null) {
                ResponseUtil response = yhService.getByPhone(phone, token);
                Object data = response.getData();
                //生成经纪人学号
                String bh = redisTemplate.opsForValue().increment(pch).toString();
                String xh = jylx + org.apache.commons.lang3.StringUtils.replace(DateUtils.toString(rxrq),"/","") + String.format("%03d", Long.parseLong(bh));
                xs.setXh(xh);
                String xm = xs.getXm();

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
                    insertOrUpfateXs(xs, yhId, dto);
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
                    ResponseUtil responseUtil = yhService.rpcAdd(yh, token);
                    if (responseUtil.getData() == null) {
                        throw new ServiceException(INSERT_FAIL);
                    }
                    GetYhVo yh1 = responseUtil.conversionData(new TypeReference<GetYhVo>() {
                    });
                    Long yh1Id = yh1.getId();
                    xs.setYhId(yh1Id);
                    StuDTO dto = xsService.selectByCondition(null, lxdh, null);
                    insertOrUpfateXs(xs, yh1Id, dto);
                }
            } else {
                throw new ServiceException(PHONE_NUMBER_ISEMPTY_ERROR);
            }
        } else {
            throw new WebException(EDU_TYPE_ERROR);
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
                PcXs one = pcXsService.getOne(new QueryWrapper<PcXs>().eq("pc_id", pcId).eq("xs_id", xs.getId()));
                if(one !=null){
                    return true;
                }else {
                    return pcXsMapper.insert(pcXs) > 0;
                }

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
                PcXs one = pcXsService.getOne(new QueryWrapper<PcXs>().eq("pc_id", pcId).eq("xs_id", xs.getId()));
                if(one !=null){
                    return true;
                }else {
                    return pcXsMapper.insert(pcXs) > 0;
                }
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


    /**
     * 新增
     * @param stuDTO
     * @return
     */
    private boolean addXsAndPcXs(Xs stuDTO) {
        Xs xs = new Xs();
        stuDTO.setCjr(getUserId());
        stuDTO.setGxr(getUserId());
        BeanUtils.copyProperties(stuDTO,xs);
        if (xsService.save(xs)) {

            if (pcId != null) {
                PcXs pcXs = new PcXs();
                pcXs.setXsId(xs.getId());
                pcXs.setPcId(pcId);
                PcXs one = pcXsService.getOne(new QueryWrapper<PcXs>().eq("pc_id", pcId).eq("xs_id", xs.getId()));
                if(one !=null){
                    return true;
                }else {
                    return pcXsMapper.insert(pcXs) > 0;
                }
            }
        }
        return false;
    }
    private void addXscjAndSj(StuDTO dto){
        //添加学生成绩表和实践表
        XsCjDTO xsCjDTO=new XsCjDTO();
        xsCjDTO.setXsId(dto.getId());
        xsCjDTO.setPcId(pcId);
        xsCjDTO.setType(1);
        XsCj xsCj = new XsCj();
        BeanUtils.copyProperties(xsCjDTO,xsCj);
        XsCj one = xsCjService.getOne(new QueryWrapper<XsCj>().eq("pc_id", pcId).eq("xs_id", dto.getId()).eq("sfsc", false));
        if(one !=null){
            xsCjService.updateById(xsCj);
        }else {
            xsCjService.add(xsCjDTO);
        }
        Sj one1 = sjService.getOne(new QueryWrapper<Sj>().eq("pc_id", pcId).eq("xs_id", dto.getId()).eq("sfsc", false));

        SjDTO sjDTO=new SjDTO();
        sjDTO.setXsId(dto.getId());
        sjDTO.setPcId(pcId);
        Sj sj = new Sj();
        BeanUtils.copyProperties(sjDTO,sj);
        if(one1 != null){
            sjService.updateById(sj);
        }else {
            sjService.add(sjDTO);
        }

    }
    /**
     * 新增/更新学生
     * @param stuDTO
     * @param yhId
     * @param dto
     * @return
     */
    private boolean insertOrUpfateXs(Xs stuDTO, Long yhId, StuDTO dto) {
        if (dto != null) {
            stuDTO.setId(dto.getId());
            //存在,则更新
            boolean b = updateXsAndAddPcXs(stuDTO);
            //新增关联关系到学生成绩表
            addXsCj(dto);
            return b;
        } else {
            //不存在.则新增
            stuDTO.setYhId(yhId);
            if (addXsAndPcXs(stuDTO)) {
                StuDTO dto1 = selectByCondition(null, null, stuDTO.getYhId());
                addXsCj(dto1);
                return true;
            }
            return false;
        }
    }
    /**
     * 新增学生成绩关联关系
     * @param dto
     */
    private void addXsCj(StuDTO dto) {
        XsCjDTO xsCjDTO = new XsCjDTO();
        xsCjDTO.setXsId(dto.getId());
        xsCjDTO.setPcId(pcId);
        xsCjDTO.setType(2);
        XsCj xsCj = new XsCj();
        BeanUtils.copyProperties(xsCjDTO,xsCj);
        XsCj one = xsCjService.getOne(new QueryWrapper<XsCj>().eq("pc_id", pcId).eq("xs_id", dto.getId()).eq("sfsc", false));
        if(one !=null){
            xsCjService.updateById(xsCj);
        }else {
            xsCjService.add(xsCjDTO);
        }
    }
    private StuDTO selectByCondition(String xh,String lxdh, Long yhId) {
        log.info("【人才培养 - 根据学号:{},;联系电话:{},用户id:{}查询学员信息】",xh,lxdh,yhId);
        QueryWrapper<Xs> xsQueryWrapper = new QueryWrapper<>();
        xsQueryWrapper.eq("sfsc",false)
                .eq(org.apache.commons.lang3.StringUtils.isNotBlank(xh),"xh",xh)
                .eq(org.apache.commons.lang3.StringUtils.isNotBlank(lxdh),"lxdh",lxdh)
                .eq(yhId != null,"yh_id",yhId);
        Xs xs = xsMapper.selectOne(xsQueryWrapper);
        if (xs == null) {
            return null;
        }
        StuDTO stuDTO = new StuDTO();
        BeanUtils.copyProperties(xs,stuDTO);
        List<Long> pcIds = pcXsMapper.selectByXsId(xs.getId());
        stuDTO.setPcIds(pcIds);
        return stuDTO;
    }
    /**
     * 更新
     * @param stuDTO
     * @return
     */
    private boolean updateXsAndAddPcXs(Xs stuDTO) {
        Xs xs = new Xs();
        stuDTO.setGxr(getUserId());
        BeanUtils.copyProperties(stuDTO,xs);
        if (xsService.updateById(xs)) {

            if (pcId != null) {
                PcXs pcXs = new PcXs();
                pcXs.setXsId(xs.getId());
                pcXs.setPcId(pcId); PcXs one = pcXsService.getOne(new QueryWrapper<PcXs>().eq("pc_id", pcId).eq("xs_id", xs.getId()));
                if(one !=null){
                    return true;
                }else {
                    return pcXsMapper.insert(pcXs) > 0;
                }
            }
        }
        return false;
    }
}
