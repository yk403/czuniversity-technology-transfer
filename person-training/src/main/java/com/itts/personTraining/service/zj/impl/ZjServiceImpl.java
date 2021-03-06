package com.itts.personTraining.service.zj.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.SzMsgDTO;
import com.itts.personTraining.dto.ZjInfoDTO;
import com.itts.personTraining.enums.SsmkEnum;
import com.itts.personTraining.enums.UserTypeEnum;
import com.itts.personTraining.feign.userservice.SjzdFeignService;
import com.itts.personTraining.feign.userservice.UserFeignService;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.sjzd.Sjzd;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.model.yh.GetYhVo;
import com.itts.personTraining.model.yh.Yh;
import com.itts.personTraining.model.zj.Zj;
import com.itts.personTraining.mapper.zj.ZjMapper;
import com.itts.personTraining.service.yh.YhService;
import com.itts.personTraining.service.zj.ZjService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import static com.itts.personTraining.enums.UserTypeEnum.*;
import static com.itts.personTraining.enums.UserTypeEnum.OUT_PROFESSOR;

/**
 * <p>
 * 专家表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-05-25
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ZjServiceImpl extends ServiceImpl<ZjMapper, Zj> implements ZjService {
    @Autowired
    private ZjService zjService;
    @Autowired
    private YhService yhService;
    @Autowired
    private UserFeignService userFeignService;
    @Resource
    private ZjMapper zjMapper;
    @Resource
    private SjzdFeignService sjzdFeignService;

    /**
     * 分页查询专家列表
     * @param pageNum
     * @param pageSize
     * @param yjly
     * @param name
     * @param lx
     * @param fjjgId
     * @return
     */
    @Override
    public PageInfo<Zj> findByPage(Integer pageNum, Integer pageSize, String yjly, String name, String lx, Long fjjgId) {
        log.info("【人才培养 - 分页条件查询专家列表,研究类型:{},姓名:{},类型:{},父级机构ID:{}】",yjly,name,lx,fjjgId);
        QueryWrapper<Zj> zjQueryWrapper = new QueryWrapper<>();
        if (pageNum == -1) {
            zjQueryWrapper.eq("sfsc",false)
                          .eq(fjjgId != null,"fjjg_id",fjjgId)
                          .orderByDesc("cjsj");
        } else {
            PageHelper.startPage(pageNum, pageSize);
            zjQueryWrapper.eq("sfsc",false)
                    .eq(fjjgId != null,"fjjg_id",fjjgId)
                    .eq(StringUtils.isNotBlank(yjly),"yjly",yjly)
                    .eq(StringUtils.isNotBlank(lx),"lx",lx)
                    .like(StringUtils.isNotBlank(name),"xm",StringUtils.isNotBlank(name)?name.trim():name).or().like(StringUtils.isNotBlank(name),"bh",StringUtils.isNotBlank(name)?name.trim():name)
                    .orderByDesc("cjsj");
        }
        return new PageInfo<>(zjMapper.selectList(zjQueryWrapper));
    }

    @Override
    public List<Zj> findExport(String yjly, String name, Long fjjgId) {
        QueryWrapper<Zj> zjQueryWrapper = new QueryWrapper<>();
        zjQueryWrapper.eq("sfsc",false)
                .eq(fjjgId != null,"fjjg_id",fjjgId)
                .eq(StringUtils.isNotBlank(yjly),"yjly",yjly)
                .like(StringUtils.isNotBlank(name),"xm",name)
                .orderByDesc("cjsj");
        List<Zj> zjs = zjMapper.selectList(zjQueryWrapper);

        ResponseUtil list2 = sjzdFeignService.getList(null, null, SsmkEnum.POLITICS_STATUS.getKey());
        List<Sjzd> sjzd2 = new ArrayList<>();
        if(list2.getErrCode().intValue() == 0){
            sjzd2= list2.conversionData(new TypeReference<List<Sjzd>>() {});
        }
        ResponseUtil list3 = sjzdFeignService.getList(null, null, SsmkEnum.TECHNICAL_FIELD.getKey());
        List<Sjzd> sjzd3 = new ArrayList<>();
        if(list3.getErrCode().intValue() == 0){
            sjzd3= list3.conversionData(new TypeReference<List<Sjzd>>() {});
        }
        Map<String, String> HashMap1 = new HashMap<>();
        HashMap1.put("professor","内部");
        HashMap1.put("school_leader","外部");

        for (Zj zj : zjs) {
            for (Sjzd sjzd : sjzd2) {
                if(Objects.equals(sjzd.getZdbm(),zj.getZzmm())){
                    zj.setZzmm(sjzd.getZdmc());
                    break;
                }
            }
            for (Sjzd sjzd : sjzd3) {
                if(Objects.equals(sjzd.getZdbm(),zj.getYjly())){
                    zj.setYjly(sjzd.getZdmc());
                    break;
                }
            }
            for (String s : HashMap1.keySet()) {
                if(Objects.equals(s,zj.getLx())){
                    zj.setLx(HashMap1.get(s));
                    break;
                }
            }
        }
        return zjs;
    }

    /**
     * 查询所有专家
     * @return
     */
    @Override
    public List<Zj> getAll() {
        log.info("【人才培养 - 查询所有专家】");
        QueryWrapper<Zj> zjQueryWrapper = new QueryWrapper<>();
        zjQueryWrapper.eq("sfsc",false);
        return zjMapper.selectList(zjQueryWrapper);
    }

    /**
     * 新增专家
     * @param zj
     * @return
     */
    @Override
    public boolean add(Zj zj,String token) {
        log.info("【人才培养 - 新增专家:{}】",zj);
        if (zjMapper.selectById(zj.getId()) != null) {
            return false;
        }
        Long userId = getUserId();
        zj.setGxr(userId);
        //通过手机号查询
        Object data = yhService.getByPhone(zj.getDh(), token).getData();
        String yhlx = IN.getKey();
        String yhlb ;
        if (PROFESSOR.getMsg().equals(zj.getLx())) {
            yhlb = PROFESSOR.getKey();
        } else if (OUT_PROFESSOR.getMsg().equals(zj.getLx())) {
            yhlb = OUT_PROFESSOR.getKey();
        } else {
            throw new ServiceException(PROFESSOR_TYPE_ERROR);
        }
        String bh = zj.getBh();
        String xm = zj.getXm();
        String dh = zj.getDh();
        Long jgId = zj.getJgId();
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
            yh.setLxdh(dh);
            yh.setJgId(jgId);
            yhService.updateYh(yh,token);
            Zj zj1 = zjMapper.getByCondition(zj.getDh());
            zj.setYhId(getYhVo.getId());
            if (zj1 != null) {
                //存在,则更新
                zj.setId(zj1.getId());
                return zjService.updateById(zj);
            } else {
                //不存在,则新增
                zj.setCjr(userId);
                return zjService.save(zj);
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
            yh.setLxdh(dh);
            yh.setJgId(jgId);
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
                return updateById(zj);
            } else {
                //不存在.则新增
                zj.setCjr(userId);
                return save(zj);
            }
        }
    }

    /**
     * 根据id查询专家信息
     * @param id
     * @return
     */
    @Override
    public Zj get(Long id) {
        log.info("【人才培养 - 根据id:{}查询专家信息】",id);
        QueryWrapper<Zj> zjQueryWrapper = new QueryWrapper<>();
        zjQueryWrapper.eq("sfsc",false)
                      .eq("id",id);
        return zjMapper.selectOne(zjQueryWrapper);
    }

    /**
     * 更新专家信息
     * @param zj
     * @return
     */
    @Override
    public boolean update(Zj zj,ZjInfoDTO zjInfoDTO, String token) {
        log.info("【人才培养 - 更新专家:{}信息】",zj);
        Zj zjOld = zjService.getById(zj.getId());
        ResponseUtil response = yhService.getById(zj.getYhId());
        //ResponseUtil response = yhService.getByPhone(zjOld.getDh(), token);
        String yhlb = zj.getLx();
        if(response.getErrCode() != 0 ){
            throw new ServiceException(USER_NOT_FIND_ERROR);
        }
        GetYhVo vo = response.conversionData(new TypeReference<GetYhVo>() {
        });
        if (PROFESSOR.getMsg().equals(yhlb)) {
            yhlb = PROFESSOR.getKey();
        } else if (OUT_PROFESSOR.getMsg().equals(yhlb)) {
            yhlb = OUT_PROFESSOR.getKey();
        } else {
            throw new ServiceException(PROFESSOR_TYPE_ERROR);
        }
        String yhlx = IN.getKey();
        if (vo != null) {
            Yh yh = new Yh();
            yh.setId(vo.getId());
            yh.setYhbh(zj.getBh());
            yh.setYhm(zj.getBh());
            yh.setMm(zj.getBh());
            yh.setZsxm(zj.getXm());
            yh.setYhlb(yhlb);
            yh.setYhlx(yhlx);
            if (zjInfoDTO != null) {
                String lxdh = zjInfoDTO.getYhMsg().getLxdh();
                String yhtx = zjInfoDTO.getYhMsg().getYhtx();
                yh.setLxdh(lxdh);
                yh.setYhtx(yhtx);
            } else {
                yh.setLxdh(zj.getDh());
            }
            yh.setJgId(zj.getJgId());
            yh.setGxr(getUserId());
            yhService.updateYh(yh,token);
        }
        zj.setGxr(getUserId());
        return zjService.updateById(zj);
    }

    /**
     * 删除专家信息
     * @param zj
     * @return
     */
    @Override
    public boolean delete(Zj zj) {
        log.info("【人才培养 - 更新专家:{}信息】",zj);
        zj.setGxr(getUserId());
        zj.setSfsc(true);
        if (zjService.updateById(zj)) {
            ResponseUtil responseUtil = userFeignService.delete(zj.getYhId());
            return responseUtil.getErrMsg().equals("success");
        }
        return false;
    }

    /**
     * 根据姓名/电话/用户id查询专家信息
     * @param xm
     * @param dh
     * @param yhId
     * @return
     */
    @Override
    public Zj getByXmDh(String xm, String dh, Long yhId) {
        log.info("【人才培养 - 根据姓名:{},电话:{},用户Id查询专家信息】",xm,dh,yhId);
        QueryWrapper<Zj> zjQueryWrapper = new QueryWrapper<>();
        zjQueryWrapper.eq("sfsc",false)
                .eq(StringUtils.isNotBlank(xm),"xm",xm)
                .eq(StringUtils.isNotBlank(dh),"dh",dh)
                .eq(yhId != null,"yh_id",yhId);
        return zjMapper.selectOne(zjQueryWrapper);
    }

    /**
     * 新增专家(外部调用)
     * @param zj
     * @return
     */
    @Override
    public boolean addZj(Zj zj) {
        log.info("【人才培养 - 新增专家(外部调用):{}】",zj);
        zj.setGxr(getUserId());
        zj.setCjr(getUserId());
        return zjService.save(zj);
    }

    /**
     * 更新专家(外部调用)
     * @param zj
     * @return
     */
    @Override
    public boolean updateZj(Zj zj) {
        log.info("【人才培养 - 更新专家(外部调用):{}】",zj);
        zj.setGxr(getUserId());
        return zjService.updateById(zj);
    }

    /**
     * 查询专家综合信息
     * @return
     */
    @Override
    public ZjInfoDTO getByYhId() {
        Long userId = getUserId();
        log.info("【人才培养 - 查询专家综合信息,用户id:{}】",userId);
        ZjInfoDTO zjInfoDTO = new ZjInfoDTO();
        Zj zjByYhId = zjMapper.getZjByYhId(userId);
        if (zjByYhId == null) {
            throw new ServiceException(PROFESSOR_MSG_NOT_EXISTS_ERROR);
        }
        Long zjId = zjByYhId.getId();
        BeanUtils.copyProperties(zjByYhId,zjInfoDTO);
        String userCategory = getUserCategory();
        ResponseUtil response = userFeignService.get();
        if(response.getErrCode() != 0 ){
            throw new ServiceException(USER_NOT_FIND_ERROR);
        }
        GetYhVo vo = response.conversionData(new TypeReference<GetYhVo>() {
        });
        switch (userCategory) {
            case "professor":
                zjInfoDTO.setKstz(0L);
                zjInfoDTO.setCjtz(0L);
                zjInfoDTO.setSjtz(0L);
                //TODO: 暂时假数据
                zjInfoDTO.setXftz(0L);
                zjInfoDTO.setQttz(0L);
                zjInfoDTO.setYhMsg(vo);
                break;
            default:
                break;
        }
        return zjInfoDTO;
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
