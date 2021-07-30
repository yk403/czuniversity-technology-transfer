package com.itts.personTraining.service.sz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.SzMsgDTO;
import com.itts.personTraining.enums.SsmkEnum;
import com.itts.personTraining.feign.userservice.JgglFeignService;
import com.itts.personTraining.feign.userservice.SjzdFeignService;
import com.itts.personTraining.feign.userservice.UserFeignService;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.mapper.tzSz.TzSzMapper;
import com.itts.personTraining.model.sjzd.Sjzd;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.model.yh.GetYhVo;
import com.itts.personTraining.model.yh.Yh;
import com.itts.personTraining.service.sz.SzService;
import com.itts.personTraining.service.yh.YhService;
import com.itts.personTraining.vo.jggl.JgglVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.personTraining.enums.UserTypeEnum.IN;

/**
 * <p>
 * 师资表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SzServiceImpl extends ServiceImpl<SzMapper, Sz> implements SzService {

    @Autowired
    private SzService szService;
    @Autowired
    private YhService yhService;
    @Autowired
    private UserFeignService userFeignService;
    @Autowired
    private JgglFeignService jgglFeignService;
    @Resource
    private SzMapper szMapper;
    @Resource
    private TzSzMapper tzSzMapper;
    @Resource
    private SjzdFeignService sjzdFeignService;

    /**
     * 获取师资列表
     * @param pageNum
     * @param pageSize
     * @param dsxm
     * @param dslb
     * @param hyly
     * @return
     */
    @Override
    public PageInfo<Sz> findByPage(Integer pageNum, Integer pageSize, String dsxm, String dslb, String hyly, Long groupId) {
        log.info("【人才培养 - 分页条件查询师资列表,导师姓名:{},导师类别:{},行业领域:{}】",dsxm,dslb,hyly);
        QueryWrapper<Sz> szQueryWrapper = new QueryWrapper<>();
        if (pageNum == -1) {
            szQueryWrapper.eq("sfsc",false)
                    .orderByDesc("cjsj");
        } else {
            PageHelper.startPage(pageNum, pageSize);
            szQueryWrapper.eq("sfsc",false)
                    .like(StringUtils.isNotBlank(dsxm),"dsxm", dsxm)
                    .eq(StringUtils.isNotBlank(dslb),"dslb", dslb)
                    .eq(StringUtils.isNotBlank(hyly),"hyly", hyly)
                    .eq(groupId != null, "ssjg_id", groupId)
                    .orderByDesc("cjsj");
        }
        return new PageInfo<>(szMapper.selectList(szQueryWrapper));
    }

    @Override
    public List<Sz> findExport(String dsxm, String dslb, String hyly) {
        QueryWrapper<Sz> szQueryWrapper = new QueryWrapper<>();
        szQueryWrapper.eq("sfsc",false)
                .like(StringUtils.isNotBlank(dsxm),"dsxm", dsxm)
                .eq(StringUtils.isNotBlank(dslb),"dslb", dslb)
                .eq(StringUtils.isNotBlank(hyly),"hyly", hyly)
                .orderByDesc("cjsj");
        List<Sz> szs = szMapper.selectList(szQueryWrapper);

        ResponseUtil list2 = sjzdFeignService.getList(null, null, SsmkEnum.POLITICS_STATUS.getKey());
        List<Sjzd> sjzd2 = new ArrayList<>();
        if(list2.getErrCode().intValue() == 0){
            sjzd2= list2.conversionData(new TypeReference<List<Sjzd>>() {});
        }
        Map<String, String> HashMap1 = new HashMap<>();
        HashMap1.put("tutor","研究生导师");
        HashMap1.put("corporate_mentor","企业导师");
        HashMap1.put("teacher","授课教师");
        HashMap1.put("school_leader","领导");

        for (Sz sz : szs) {
            for (Sjzd sjzd : sjzd2) {
                if(Objects.equals(sjzd.getZdbm(),sz.getZzmm())){
                    sz.setZzmm(sjzd.getZdbm());
                }
            }
            for (String s : HashMap1.keySet()) {
                if(Objects.equals(s,sz.getDslb())){
                    sz.setDslb(HashMap1.get(s));
                }
            }
        }
        return szs;
    }

    /**
     * 根据id查询师资详情
     * @param id
     * @return
     */
    @Override
    public Sz get(Long id) {
        log.info("【人才培养 - 根据id:{}查询师资信息】",id);
        QueryWrapper<Sz> szQueryWrapper = new QueryWrapper<>();
        szQueryWrapper.eq("sfsc",false)
                      .eq("id",id);
        return szMapper.selectOne(szQueryWrapper);
    }

    /**
     * 新增师资
     * @param sz
     * @return
     */
    @Override
    public boolean add(Sz sz,String token) {
        log.info("【人才培养 - 新增师资:{}】",sz);
        if (szMapper.selectById(sz.getId()) != null) {
            return false;
        }
        Long userId = getUserId();
        sz.setGxr(userId);
        //通过用户编号查询
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
            yh.setYhbh(dsbh);
            yh.setYhm(dsbh);
            yh.setMm(dsbh);
            yh.setZsxm(dsxm);
            yh.setYhlx(yhlx);
            yh.setYhlb(yhlb);
            yh.setJgId(ssjgId);
            yhService.update(yh,token);
            sz.setYhId(getYhVo.getId());
            Sz sz1 = szService.selectByCondition(dsbh,null, null,null);
            if (sz1 != null) {
                //存在,则更新
                sz.setId(sz1.getId());
                return szService.updateById(sz);
            } else {
                //不存在,则新增
                sz.setCjr(userId);
                return szService.save(sz);
            }
        } else {
            //用户表没有用户信息,新增用户信息,师资表查询是否存在
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
            Sz sz1 = szService.selectByCondition(dsbh,null, null,null);
            if (sz1 != null) {
                //存在,则更新
                sz.setId(sz1.getId());
                return updateById(sz);
            } else {
                //不存在.则新增
                sz.setCjr(userId);
                return save(sz);
            }
        }
    }

    /**
     * 新增师资(外部调用)
     * @param sz
     * @return
     */
    @Override
    public boolean addSz(Sz sz) {
        log.info("【人才培养 - 新增师资(外部调用):{}】",sz);
        if (szMapper.selectById(sz.getId()) != null) {
            return false;
        }
        Long userId = getUserId();
        sz.setCjr(userId);
        sz.setGxr(userId);
        return szService.save(sz);
    }

    /**
     * 根据条件查询师资信息
     * @param dsbh
     * @param yhId
     * @param groupId
     * @return
     */
    @Override
    public Sz selectByCondition(String dsbh, String xb, Long yhId, Long groupId) {
        log.info("【人才培养 - 根据师资编号:{},性别:{},用户id:{},机构id:{}查询师资信息】",dsbh,yhId,groupId);
        QueryWrapper<Sz> szQueryWrapper = new QueryWrapper<>();
        szQueryWrapper.eq("sfsc",false)
                .eq(StringUtils.isNotBlank(dsbh),"dsbh",dsbh)
                .eq(StringUtils.isNotBlank(xb),"xb",xb)
                .eq(yhId != null,"yh_id",yhId)
                .eq(groupId != null,"ssjg_id",groupId);
        return szMapper.selectOne(szQueryWrapper);
    }

    /**
     * 根据用户id查询师资综合信息
     * @return
     */
    @Override
    public SzMsgDTO getByYhId() {
        Long userId = getUserId();
        SzMsgDTO szMsgDTO = new SzMsgDTO();
        Sz szByYhId = szMapper.getSzByYhId(userId);
        if (szByYhId == null) {
            throw new ServiceException(TEACHER_MSG_NOT_EXISTS_ERROR);
        }
        Long szId = szByYhId.getId();
        BeanUtils.copyProperties(szByYhId,szMsgDTO);
        log.info("【人才培养 - 根据用户id:{}查询师资综合信息】",userId);
        String userCategory = getUserCategory();
        ResponseUtil response = userFeignService.get();
        if(response.getErrCode() != 0 ){
            throw new ServiceException(USER_NOT_FIND_ERROR);
        }
        GetYhVo vo = response.conversionData(new TypeReference<GetYhVo>() {
        });
        switch (userCategory) {
            case "tutor":
            case "corporate_mentor":
            case "teacher":
                szMsgDTO.setKstz(tzSzMapper.getTzCountBySzIdAndTzlx(szId,"考试通知",false));
                szMsgDTO.setCjtz(tzSzMapper.getTzCountBySzIdAndTzlx(szId,"成绩通知",false));
                szMsgDTO.setSjtz(tzSzMapper.getTzCountBySzIdAndTzlx(szId,"实践通知",false));
                //TODO: 暂时假数据
                szMsgDTO.setXftz(0L);
                szMsgDTO.setQttz(0L);
                szMsgDTO.setYhMsg(vo);
                break;
            default:
                break;
        }
        return szMsgDTO;
    }

    /**
     * 根据机构编号查询师资信息
     * @param code
     * @return
     */
    @Override
    public List<Sz> getByJgBh(String code) {

        ResponseUtil response = jgglFeignService.getByCode(code);
        if (response == null || response.getErrCode().intValue() != 0) {
            throw new ServiceException(SYSTEM_NOT_FIND_ERROR);
        }

        JgglVO jggl = response.conversionData(new TypeReference<JgglVO>() {
        });
        if (jggl != null) {
            QueryWrapper<Sz> szQueryWrapper = new QueryWrapper<Sz>().eq("ssjg_id", jggl.getId()).eq("sfsc", false);
            return szMapper.selectList(szQueryWrapper);
        }
        return null;
    }

    /**
     * 更新师资
     * @param sz
     * @return
     */
    @Override
    public boolean update(Sz sz,String token) {
        log.info("【人才培养 - 更新学员:{}】",sz);
        Long userId = getUserId();
        sz.setGxr(userId);
        //通过用户编号查询
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
            yh.setYhbh(dsbh);
            yh.setYhm(dsbh);
            yh.setMm(dsbh);
            yh.setZsxm(dsxm);
            yh.setYhlx(yhlx);
            yh.setYhlb(yhlb);
            yh.setJgId(ssjgId);
            yhService.update(yh,token);
            Sz sz1 = szService.selectByCondition(dsbh,null, null,null);
            if (sz1 != null) {
                //存在,则更新
                sz.setId(sz1.getId());
                return szService.updateById(sz);
            } else {
                //不存在,则新增
                return szService.save(sz);
            }
        } else {
            //用户表没有用户信息,暂不作处理,师资表查询是否存在
            /*Yh yh = new Yh();
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
            sz.setYhId(yh1Id);*/
            Sz sz1 = szService.selectByCondition(dsbh,null, null,null);
            if (sz1 != null) {
                //存在,则更新
                sz.setId(sz1.getId());
                return updateById(sz);
            } else {
                //不存在.则新增
                return save(sz);
            }
        }
    }



    /**
     * 删除师资
     * @param sz
     * @return
     */
    @Override
    public boolean delete(Sz sz) {
        log.info("【人才培养 - 删除师资:{}】",sz);
        //设置删除状态
        sz.setSfsc(true);
        sz.setGxr(getUserId());
        if (szService.updateById(sz)) {
            ResponseUtil responseUtil = userFeignService.delete(sz.getYhId());
            return responseUtil.getErrMsg().equals("success");
        }
        return false;
    }

    /**
     * 根据导师编号查询导师信息
     * @param dsbh
     * @return
     */
    @Override
    public Sz selectByDsbh(String dsbh) {
        log.info("【人才培养 - 根据导师编号:{}查询师资信息】",dsbh);
        QueryWrapper<Sz> szQueryWrapper = new QueryWrapper<>();
        szQueryWrapper.eq("sfsc",false)
                      .eq("dsbh",dsbh);
        return szMapper.selectOne(szQueryWrapper);
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
     * 获取当前用户id所属类别
     * @return
     */
    private String getUserCategory() {
        LoginUser loginUser = threadLocal.get();
        String userCategory;
        if (loginUser != null) {
            userCategory = loginUser.getUserCategory();
        } else {
            throw new ServiceException(GET_THREADLOCAL_ERROR);
        }
        return userCategory;
    }

}
