package com.itts.personTraining.service.xs.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.DateUtils;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.*;
import com.itts.personTraining.enums.SsmkEnum;
import com.itts.personTraining.feign.userservice.SjzdFeignService;
import com.itts.personTraining.feign.userservice.UserFeignService;
import com.itts.personTraining.mapper.ksXs.KsXsMapper;
import com.itts.personTraining.mapper.pc.PcMapper;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.mapper.sj.SjMapper;
import com.itts.personTraining.mapper.tzXs.TzXsMapper;
import com.itts.personTraining.mapper.xsCj.XsCjMapper;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.pcXs.PcXs;
import com.itts.personTraining.model.sjzd.Sjzd;
import com.itts.personTraining.model.xs.Xs;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.model.yh.GetYhVo;
import com.itts.personTraining.model.yh.Yh;
import com.itts.personTraining.request.feign.UpdateUserRequest;
import com.itts.personTraining.service.pc.PcService;
import com.itts.personTraining.service.pcXs.PcXsService;
import com.itts.personTraining.service.sj.SjService;
import com.itts.personTraining.service.xs.XsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.service.xsCj.XsCjService;
import com.itts.personTraining.service.yh.YhService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.personTraining.enums.EduTypeEnum.ACADEMIC_DEGREE_EDUCATION;
import static com.itts.personTraining.enums.EduTypeEnum.ADULT_EDUCATION;
import static com.itts.personTraining.enums.UserTypeEnum.*;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class XsServiceImpl extends ServiceImpl<XsMapper, Xs> implements XsService {
    
    @Resource
    private XsMapper xsMapper;
    @Autowired
    private XsService xsService;
    @Autowired
    private PcXsService pcXsService;
    @Resource
    private PcXsMapper pcXsMapper;
    @Autowired
    private YhService yhService;
    @Autowired
    private PcService pcService;
    @Autowired
    private UserFeignService userFeignService;
    @Resource
    private KsXsMapper ksXsMapper;
    @Resource
    private XsCjMapper xsCjMapper;
    @Resource
    private SjMapper sjMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private PcMapper pcMapper;
    @Resource
    private TzXsMapper tzXsMapper;
    @Autowired
    private XsCjService xsCjService;
    @Autowired
    private SjService sjService;

    @Resource
    private SjzdFeignService sjzdFeignService;
    /**
     * 查询学员列表
     * @param pageNum
     * @param pageSize
     * @param pcId
     * @param jyxs
     * @return
     */
    @Override
    public PageInfo<StuDTO> findByPage(Integer pageNum, Integer pageSize, Long pcId, String xslbmc, String jyxs, String name,Long qydsId,Long yzydsId) {
        log.info("【人才培养 - 分页条件查询学员列表,批次id:{},学生类别名称:{},教育形式:{},学号/姓名:{}】",pcId,xslbmc,jyxs,name);
        PageHelper.startPage(pageNum, pageSize);
        List<StuDTO> stuDTOList = xsMapper.findXsList(pcId,xslbmc,jyxs,name,qydsId,yzydsId);
        for (StuDTO stuDTO : stuDTOList) {
            List<Long> pcIds = pcXsMapper.selectByXsId(stuDTO.getId());
            stuDTO.setPcIds(pcIds);
        }
        return new PageInfo<>(stuDTOList);
    }

    @Override
    public List<StuDTO> findExport(Long pcId, String xslbmc, String jyxs, String name) {
        List<StuDTO> stuDTOList = xsMapper.findXsList(pcId,xslbmc,jyxs,name,null,null);

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

        for (StuDTO stuDTO : stuDTOList) {
            List<Long> pcIds = pcXsMapper.selectByXsId(stuDTO.getId());
            for (Sjzd sjzd : sjzd1) {
                if(Objects.equals(sjzd.getZdbm(),stuDTO.getXslbId())){
                    stuDTO.setXslbId(sjzd.getZdmc());
                    break;
                }
            }
            for (Sjzd sjzd : sjzd2) {
                if(Objects.equals(sjzd.getZdbm(),stuDTO.getZzmm())){
                    stuDTO.setZzmm(sjzd.getZdmc());
                    break;
                }
            }
            for (Sjzd sjzd : sjzd3) {
                if(Objects.equals(sjzd.getZdbm(),stuDTO.getRxfs())){
                    stuDTO.setRxfs(sjzd.getZdmc());
                    break;
                }
            }
            for (Sjzd sjzd : data4) {
                if(Objects.equals(sjzd.getZdbm(),stuDTO.getXxxs())){
                    stuDTO.setXxxs(sjzd.getZdmc());
                    break;
                }
            }
            stuDTO.setPcIds(pcIds);
        }
        /*List<StuDTO> stuDTOS = deleteNullData(stuDTOList);*/
        return stuDTOList;
    }
    /*//替换null
    public List<StuDTO> deleteNullData(List<StuDTO> newList){
        //反射获取类
        Class c = StuDTO.class;
        //获取DTO对象的所有定义的属性信息Filed
        Field[] fileds = c.getDeclaredFields();
        for(int i=0;i<newList.size();i++){
            StuDTO stuDTO = newList.get(i);
            for(int j=0;j<fileds.length;j++){
                Field f = fileds[j];
                try {
                    //对象中的属性是private的需要设置accessible才可用
                    f.setAccessible(true);
                    //遍历每个属性值
                    if(f.get(stuDTO) == null){
                        //拼接set方法
                        String methodName = "set" + f.getName().substring(0,1).toUpperCase()+f.getName().substring(1,f.getName().length());
                        //获取方法
                        Method method = c.getDeclaredMethod(methodName, f.getType().getCanonicalName().getClass());
                        //直接给属性赋值null会报空指针
                        String nullName = "";
                        //调用方法
                        method.invoke(stuDTO,nullName);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return newList;
    }*/



    /**
     * 根据id查询学员信息
     * @param id
     * @return
     */
    @Override
    public StuDTO get(Long id) {
        log.info("【人才培养 - 根据id:{}查询学员信息】",id);
        QueryWrapper<Xs> xsQueryWrapper = new QueryWrapper<>();
        xsQueryWrapper.eq("sfsc",false)
                      .eq("id",id);
        Xs xs = xsMapper.selectOne(xsQueryWrapper);
        if (xs == null) {
            throw new ServiceException(STUDENT_MSG_NOT_EXISTS_ERROR);
        }
        StuDTO stuDTO = new StuDTO();
        BeanUtils.copyProperties(xs,stuDTO);
        List<Long> pcIds = pcXsMapper.selectByXsId(id);
        stuDTO.setPcIds(pcIds);
        return stuDTO;
    }
    /**
     * 根据条件查询学员信息
     * @param xh
     * @return
     */
    @Override
    public StuDTO selectByCondition(String xh,String lxdh, Long yhId) {
        log.info("【人才培养 - 根据学号:{},;联系电话:{},用户id:{}查询学员信息】",xh,lxdh,yhId);
        QueryWrapper<Xs> xsQueryWrapper = new QueryWrapper<>();
        xsQueryWrapper.eq("sfsc",false)
                .eq(StringUtils.isNotBlank(xh),"xh",xh)
                .eq(StringUtils.isNotBlank(lxdh),"lxdh",lxdh)
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
     * 查询所有学员详情
     * @return
     */
    @Override
    public List<StuDTO> getAll() {
        log.info("【人才培养 - 查询所有学员详情】");
        QueryWrapper<Xs> xsQueryWrapper = new QueryWrapper<>();
        xsQueryWrapper.eq("sfsc",false);
        List<Xs> xsList = xsMapper.selectList(xsQueryWrapper);
        List<StuDTO> stuDTOList = new ArrayList<>();
        for (Xs xs : xsList) {
            StuDTO stuDTO = new StuDTO();
            BeanUtils.copyProperties(xs,stuDTO);
            List<Long> pcIds = pcXsMapper.selectByXsId(xs.getId());
            stuDTO.setPcIds(pcIds);
            stuDTOList.add(stuDTO);
        }
        return stuDTOList;
    }

    /**
     * 查询学生综合信息
     * @return
     */
    @Override
    public XsMsgDTO getByYhId() {
        log.info("【人才培养 - 查询学生综合信息】");
        XsMsgDTO xsMsgDTO = xsMapper.getByYhId(getUserId());
        if (xsMsgDTO == null) {
            throw new ServiceException(STUDENT_MSG_NOT_EXISTS_ERROR);
        }
        Long xsId = xsMsgDTO.getId();
        xsMsgDTO.setXylx(pcMapper.findXylxByXsId(xsId));
        ResponseUtil response = userFeignService.get();
        if(response.getErrCode() != 0 ){
            throw new ServiceException(USER_NOT_FIND_ERROR);
        }
        GetYhVo vo = response.conversionData(new TypeReference<GetYhVo>() {
        });
        xsMsgDTO.setKstz(tzXsMapper.getTzCountByXsIdAndTzlx(xsId,"考试通知",false));
        xsMsgDTO.setCjtz(tzXsMapper.getTzCountByXsIdAndTzlx(xsId,"成绩通知",false));
        xsMsgDTO.setSjtz(tzXsMapper.getTzCountByXsIdAndTzlx(xsId,"实践通知",false));
        //TODO: 暂时假数据
        xsMsgDTO.setXftz(0L);
        xsMsgDTO.setQttz(0L);
        xsMsgDTO.setYhMsg(vo);
        return xsMsgDTO;
    }

    /**
     * 根据用户id查询批次信息(前)
     * @return
     */
    @Override
    public List<Pc> getPcByYhId() {
        Long userId = getUserId();
        log.info("【人才培养 - 根据用户id:{}查询批次信息(前)】",userId);
        List<Pc> pcList = pcMapper.findPcByYhId(userId);
        return pcList;
    }

    /**
     * 更新学生信息(前)
     * @param stuDTO
     * @return
     */
    @Override
    public boolean updateUser(StuDTO stuDTO) {
        log.info("【人才培养 - 更新学生信息(前):{}】",stuDTO);
        Long userId = getUserId();
        stuDTO.setGxr(userId);
        Xs xs = new Xs();
        BeanUtils.copyProperties(stuDTO,xs);
        if (xsService.updateById(xs)) {
            UpdateUserRequest updateUserRequest = new UpdateUserRequest();
            updateUserRequest.setLxdh(stuDTO.getLxdh());
            updateUserRequest.setYhyx(stuDTO.getYhMsg().getYhyx());
            updateUserRequest.setYhtx(stuDTO.getYhMsg().getYhtx());
            return userFeignService.update(updateUserRequest).getErrCode() == 0;
        }
        return false;
    }

    /**
     * 更新学员(外部调用)
     * @param xs
     * @return
     */
    @Override
    public boolean updateXs(Xs xs) {
        log.info("【人才培养 - 更新学员(外部调用):{}】",xs);
        xs.setGxr(getUserId());
        return xsService.updateById(xs);
    }

    /**
     * 新增学员
     * @param stuDTO
     * @return
     */
    @Override
    public boolean add(StuDTO stuDTO,String token) {
        log.info("【人才培养 - 新增学员:{}】",stuDTO);
        Long userId = getUserId();
        stuDTO.setCjr(userId);
        stuDTO.setGxr(userId);
        String jylx = stuDTO.getJylx();
        if (ACADEMIC_DEGREE_EDUCATION.getKey().equals(jylx)) {
            //学历学位教育(研究生)
            String dtoXh = stuDTO.getXh();
            if (dtoXh != null) {
                Object data = yhService.getByCode(dtoXh, token).getData();
                Yh yh = new Yh();
                String xm = stuDTO.getXm();
                Long jgId = stuDTO.getJgId();
                String lxdh = stuDTO.getLxdh();
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
                    if (dto != null) {
                        //说明学生表存在,则更新
                        stuDTO.setId(dto.getId());
                        //在学生表存在的情况下添加学生成绩表
                        addXscjAndSj(dto);
                        return updateXsAndAddPcXs(stuDTO);
                    } else {
                        //说明学生表不存在
                        stuDTO.setYhId(getYhVo.getId());
                        if (addXsAndPcXs(stuDTO)) {
                            //在学生表不存在的情况下添加学生成绩表
                            StuDTO dto1 = selectByCondition(null, null, stuDTO.getYhId());
                            addXscjAndSj(dto1);
                            yhService.update(yh, token);
                            return true;
                        }
                        return false;
                    }
                } else {
                    //说明用户表不存在该用户信息,则用户表新增,学生表查询判断是否存在
                    String xh = stuDTO.getXh();
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
                    stuDTO.setYhId(yh1Id);
                    StuDTO dto = xsService.selectByCondition(xh, null, null);
                    if (dto != null) {
                        //存在,则更新
                        stuDTO.setId(dto.getId());
                        boolean b=updateXsAndAddPcXs(stuDTO);
                        //在学生表存在的情况下添加学生成绩表
                        addXscjAndSj(dto);
                        return b;
                    } else {
                        //不存在.则新增
                        boolean b=addXsAndPcXs(stuDTO);
                        //在学生表存在的情况下添加学生成绩表
                        StuDTO dto1 = selectByCondition(null, null, dto.getYhId());
                        addXscjAndSj(dto1);
                        return b;
                    }
                }
            } else {
                throw new ServiceException(STUDENT_NUMBER_ISEMPTY_ERROR);
            }
        } else if (ADULT_EDUCATION.getKey().equals(jylx)) {
            //继续教育(经纪人)
            String phone = stuDTO.getLxdh();
            if (phone != null) {
                Object data = yhService.getByPhone(phone, token).getData();
                //生成经纪人学号
                Pc pc = pcService.get(stuDTO.getPcIds().get(0));
                String bh = redisTemplate.opsForValue().increment(pc.getPch()).toString();
                String xh = pc.getJylx() + StringUtils.replace(DateUtils.toString(pc.getRxrq()),"/","") + String.format("%03d", Long.parseLong(bh));
                stuDTO.setXh(xh);
                String xm = stuDTO.getXm();
                Long jgId = stuDTO.getJgId();
                String lxdh = stuDTO.getLxdh();
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
                    stuDTO.setId(dto.getId());
                    boolean b = updateXsAndAddPcXs(stuDTO);
                    //新增关联关系到学生成绩表
                    addXsCj(dto);
                    return b;
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
                    stuDTO.setYhId(yh1Id);
                    StuDTO dto = xsService.selectByCondition(null, lxdh, null);
                    if (dto != null) {
                        stuDTO.setId(dto.getId());
                        //存在,则更新
                        boolean b = updateXsAndAddPcXs(stuDTO);
                        //新增关联关系到学生成绩表
                        addXsCj(dto);
                        return b;
                    } else {
                        //不存在.则新增
                        boolean b = addXsAndPcXs(stuDTO);
                        addXsCj(dto);
                        return b;
                    }
                }
            } else {
                throw new ServiceException(PHONE_NUMBER_ISEMPTY_ERROR);
            }
        } else {
            throw new ServiceException(EDU_TYPE_ERROR);
        }
    }

    /**
     * 新增学生成绩关联关系
     * @param dto
     */
    private void addXsCj(StuDTO dto) {
        XsCjDTO xsCjDTO = new XsCjDTO();
        xsCjDTO.setXsId(dto.getId());
        xsCjDTO.setPcId(dto.getPcIds().get(0));
        xsCjDTO.setType(2);
        xsCjService.add(xsCjDTO);
    }

    /**
     * 新增学员(外部调用)
     * @param stuDTO
     * @return
     */
    @Override
    public boolean addUser(StuDTO stuDTO) {
        log.info("【人才培养 - 新增学员(外部调用):{}】",stuDTO);
        Long userId = getUserId();
        stuDTO.setCjr(userId);
        stuDTO.setGxr(userId);
        Xs xs = new Xs();
        BeanUtils.copyProperties(stuDTO,xs);
        return xsService.save(xs);
    }

    /**
     * 更新学员
     * @param stuDTO
     * @return
     */
    @Override
    public boolean update(StuDTO stuDTO,String token) {
        log.info("【人才培养 - 更新学员:{}】",stuDTO);
        ResponseUtil response = yhService.getByPhone(stuDTO.getLxdh(), token);
        if(response.getErrCode() != 0 ){
            throw new ServiceException(USER_NOT_FIND_ERROR);
        }
        GetYhVo vo = response.conversionData(new TypeReference<GetYhVo>() {
        });
        if (vo != null) {
            Yh yh = new Yh();
            yh.setId(vo.getId());
            yh.setYhbh(stuDTO.getXh());
            yh.setYhm(stuDTO.getXh());
            yh.setMm(stuDTO.getXh());
            yh.setZsxm(stuDTO.getXm());
            yh.setLxdh(stuDTO.getLxdh());
            yh.setYhlx(IN.getKey());
            yh.setJgId(stuDTO.getJgId());
            yhService.update(yh,token);
        }
        Long userId = getUserId();
        stuDTO.setGxr(userId);
        Xs xs = new Xs();
        BeanUtils.copyProperties(stuDTO,xs);
        if (xsService.updateById(xs)) {
            List<Long> pcIds = stuDTO.getPcIds();
            if (!CollectionUtils.isEmpty(pcIds)) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("xs_id",stuDTO.getId());
                List<PcXs> pcXsList = new ArrayList<>();
                if (pcXsService.removeByMap(map)) {
                    for (Long pcId : pcIds) {
                        PcXs pcXs = new PcXs();
                        pcXs.setPcId(pcId);
                        pcXs.setXsId(xs.getId());
                        pcXsList.add(pcXs);
                    }
                    return pcXsService.saveBatch(pcXsList);
                }
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 删除学员
     * @param stuDTO
     * @return
     */
    @Override
    public boolean delete(StuDTO stuDTO) {
        log.info("【人才培养 - 删除学员:{}】",stuDTO);
        //设置删除状态
        stuDTO.setSfsc(true);
        stuDTO.setGxr(getUserId());
        Xs xs = new Xs();
        BeanUtils.copyProperties(stuDTO,xs);
        if (xsService.updateById(xs)) {
            List<Long> pcIds = stuDTO.getPcIds();
            if (pcIds != null && pcIds.size() > 0) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("xs_id",stuDTO.getId());
                if (pcXsService.removeByMap(map)) {
                    ResponseUtil responseUtil = userFeignService.delete(xs.getYhId());
                    return responseUtil.getErrMsg().equals("success");
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public Boolean addKcXs(Long id, Long kcId) {
        return xsMapper.addKcList(id, kcId);
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
     * 更新
     * @param stuDTO
     * @return
     */
    private boolean updateXsAndAddPcXs(StuDTO stuDTO) {
        Xs xs = new Xs();
        stuDTO.setGxr(getUserId());
        BeanUtils.copyProperties(stuDTO,xs);
        if (xsService.updateById(xs)) {
            Long pcId = stuDTO.getPcIds().get(0);
            if (pcId != null) {
                PcXs pcXs = new PcXs();
                pcXs.setXsId(xs.getId());
                pcXs.setPcId(pcId);
                return pcXsService.save(pcXs);
            }
        }
        return false;
    }

    /**
     * 新增
     * @param stuDTO
     * @return
     */
    private boolean addXsAndPcXs(StuDTO stuDTO) {
        Xs xs = new Xs();
        stuDTO.setCjr(getUserId());
        stuDTO.setGxr(getUserId());
        BeanUtils.copyProperties(stuDTO,xs);
        if (xsService.save(xs)) {
            Long pcId = stuDTO.getPcIds().get(0);
            if (pcId != null) {
                PcXs pcXs = new PcXs();
                pcXs.setXsId(xs.getId());
                pcXs.setPcId(pcId);
                return pcXsService.save(pcXs);
            }
        }
        return false;
    }
    private void addXscjAndSj(StuDTO dto){
        //添加学生成绩表和实践表
        XsCjDTO xsCjDTO=new XsCjDTO();
        xsCjDTO.setXsId(dto.getId());
        xsCjDTO.setPcId(dto.getPcIds().get(0));
        xsCjDTO.setType(1);
        xsCjService.add(xsCjDTO);
        SjDTO sjDTO=new SjDTO();
        sjDTO.setXsId(dto.getId());
        sjDTO.setPcId(dto.getPcIds().get(0));
        sjService.add(sjDTO);
    }
}
