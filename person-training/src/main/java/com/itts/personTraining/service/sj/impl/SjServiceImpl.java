package com.itts.personTraining.service.sj.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.DateUtils;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.SjDTO;
import com.itts.personTraining.dto.XsMsgDTO;
import com.itts.personTraining.feign.userservice.UserFeignService;
import com.itts.personTraining.mapper.pc.PcMapper;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.mapper.pk.PkMapper;
import com.itts.personTraining.mapper.pyjh.PyJhMapper;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.sj.Sj;
import com.itts.personTraining.mapper.sj.SjMapper;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.model.tz.Tz;
import com.itts.personTraining.model.tzSz.TzSz;
import com.itts.personTraining.model.tzXs.TzXs;
import com.itts.personTraining.model.xs.Xs;
import com.itts.personTraining.model.xsCj.XsCj;
import com.itts.personTraining.model.yh.Yh;
import com.itts.personTraining.service.sj.SjService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.service.tz.TzService;
import com.itts.personTraining.service.tzSz.TzSzService;
import com.itts.personTraining.service.tzXs.TzXsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.common.enums.ErrorCodeEnum.TEACHER_MSG_NOT_EXISTS_ERROR;

/**
 * <p>
 * ????????? ???????????????
 * </p>
 *
 * @author Austin
 * @since 2021-05-28
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SjServiceImpl extends ServiceImpl<SjMapper, Sj> implements SjService {

    @Autowired
    private  SjService sjService;
    @Autowired
    private TzXsService tzXsService;
    @Autowired
    private TzService tzService;
    @Autowired
    private TzSzService tzSzService;
    @Resource
    private SjMapper sjMapper;
    @Resource
    private PcMapper pcMapper;
    @Resource
    private XsMapper xsMapper;

    @Resource
    private PcXsMapper pcXsMapper;
    @Resource
    private SzMapper szMapper;
    @Resource
    private PkMapper pkMapper;
    @Resource
    private PyJhMapper pyJhMapper;

    @Resource
    private UserFeignService userFeignService;

    /**
     * ??????????????????
     * @param pageNum
     * @param pageSize
     * @param pcId
     * @param sjlx
     * @param export
     * @return
     */
    @Override
    public PageInfo<SjDTO> findByPage(Integer pageNum, Integer pageSize, Long pcId, String sjlx, String name, Integer export, Long fjjgId) {
        log.info("??????????????? - ??????pcId:{},????????????:{},??????/??????:{}??????????????????,????????????:{},????????????ID:{}???",pcId,sjlx,name,export,fjjgId);
        PageHelper.startPage(pageNum, pageSize);
        List<SjDTO> sjDTOs = sjMapper.getByCondition(pcId, sjlx, name, export, fjjgId);
        return new PageInfo<>(sjDTOs);
    }

    /**
     * ????????????
     * @param sjDTO
     * @return
     */
    @Override
    public boolean delete(SjDTO sjDTO) {
        log.info("??????????????? - ?????????????????????");
        sjDTO.setGxr(getUserId());
        sjDTO.setSfsc(true);
        Sj sj = new Sj();
        BeanUtils.copyProperties(sjDTO,sj);
        return sjService.updateById(sj);
    }

    /**
     * ??????????????????
     * @param ids
     * @return
     */
    @Override
    public boolean issueBatch(List<Long> ids) {
        log.info("??????????????? - ??????????????????,ids:{}???",ids);
        List<Sj> sjList = sjMapper.selectBatchIds(ids);
        Long userId = getUserId();
        for (Sj sj : sjList) {
            sj.setGxr(userId);
            sj.setSfxf(true);
            sj.setSfck(true);
            Pc pc = pcMapper.getPcById(sj.getPcId());
            List<Tz> tzList = new ArrayList<>();
            Tz tz = new Tz();
            tz.setCjr(userId);
            tz.setGxr(userId);
            tz.setTzlx("????????????");
            tz.setTzmc(pc.getPch() + "????????????" + DateUtils.getDateFormat(new Date()));
            List<Long> xsIds = xsMapper.findXsIdsByPcId(pc.getId());
            tz.setNr("????????????????????????"+pc.getPch()+"?????????????????????"+sj.getSjdw()+"?????????????????????"+DateUtils.getDateFormat(sj.getKsrq())+"???"+DateUtils.getDateFormat(sj.getJsrq())+"???????????????");
            tzList.add(tz);
            Xs xs = xsMapper.selectById(sj.getXsId());
            Tz tz1 = new Tz();;
            if (xs.getYzydsId() != null || xs.getQydsId() != null) {
                tz1.setCjr(userId);
                tz1.setGxr(userId);
                tz1.setTzlx("????????????");
                tz1.setTzmc(pc.getPch() + "????????????" + DateUtils.getDateFormat(new Date()));
                tz1.setNr("?????????????????????"+pc.getPch()+"????????????"+xs.getXm()+"?????????????????????"+sj.getSjdw()+"?????????????????????"+DateUtils.getDateFormat(sj.getKsrq())+"???"+DateUtils.getDateFormat(sj.getJsrq())+"???????????????");
                tzList.add(tz1);
            }
            if (tzService.saveBatch(tzList)) {
                TzXs tzXs = new TzXs();
                tzXs.setXsId(sj.getXsId());
                tzXs.setTzId(tz.getId());
                if (!tzXsService.save(tzXs)) {
                    throw new ServiceException(INSERT_FAIL);
                }
                if (xs.getYzydsId() != null || xs.getQydsId() != null) {
                    List<TzSz> tzSzList =  new ArrayList<>();
                    if (xs.getYzydsId() != null) {
                        TzSz tzSz = new TzSz();
                        tzSz.setTzId(tz1.getId());
                        tzSz.setSzId(xs.getYzydsId());
                        tzSzList.add(tzSz);
                    }
                    if (xs.getQydsId() != null) {
                        TzSz tzSz = new TzSz();
                        tzSz.setTzId(tz1.getId());
                        tzSz.setSzId(xs.getQydsId());
                        tzSzList.add(tzSz);
                    }
                    if (!tzSzService.saveBatch(tzSzList)) {
                        throw new ServiceException(INSERT_FAIL);
                    }
                }
            } else {
                throw new ServiceException(INSERT_FAIL);
            }
        }
        return sjService.updateBatchById(sjList);
    }

    /**
     * ????????????id????????????????????????
     * @return
     */
    @Override
    public List<SjDTO> findByYhId() {
        Long userId = getUserId();
        log.info("??????????????? - ????????????id:{}???????????????????????????",userId);
        List<SjDTO> sjDTOs = sjMapper.findByCondition(userId,null);
        return sjDTOs;
    }

    /**
     * ????????????(???)
     * @param sjDTO
     * @return
     */
    @Override
    public boolean userAdd(SjDTO sjDTO) {
        log.info("??????????????? - ????????????(???):{}???",sjDTO);
        Long userId = getUserId();
        String userCategory = getUserCategory();
        boolean flag = false;
        switch (userCategory) {
            case "postgraduate":
            case "corporate_mentor":
                sjDTO.setCjr(userId);
                sjDTO.setGxr(userId);
                Sj sj = new Sj();
                BeanUtils.copyProperties(sjDTO, sj);
                flag = sjService.save(sj);
                break;
            default:
                break;
        }
        return flag;
    }

    /**
     * ????????????(???)
     * @param sjDTO
     * @return
     */
    @Override
    public boolean userUpdate(SjDTO sjDTO) {
        log.info("??????????????? - ????????????:{}???",sjDTO);
        sjDTO.setGxr(getUserId());
        Sj sj = new Sj();
        BeanUtils.copyProperties(sjDTO,sj);
        sj.setSfxf(false);
        return sjService.updateById(sj);
    }

    /**
     * ????????????????????????????????????(???)
     * @return
     */
    @Override
    public List<SjDTO> findByCategory(Long pcId) {
        Long userId = getUserId();
        String userCategory = getUserCategory();
        log.info("??????????????? - ???????????????:{}??????????????????(???),??????:{}???",userId,userCategory);
        List<SjDTO> sjDTOs = new ArrayList<SjDTO>();
        List<Pc>  pcList = null;
        switch (userCategory) {
            case "postgraduate":
                if(pcId != null){
                    XsMsgDTO xsMsg = xsMapper.getByYhId(userId);
                    if (xsMsg == null) {
                        throw new ServiceException(STUDENT_MSG_NOT_EXISTS_ERROR);
                    }
                    QueryWrapper<Sj> sjQueryWrapper = new QueryWrapper<>();
                    sjQueryWrapper.eq("pc_id",pcId)
                            .eq("xs_id",xsMsg.getId())
                            .eq("sfsc",false)
                            .eq("sfck",true);
                    List<Sj> sjs = sjMapper.selectList(sjQueryWrapper);
                    for (int i = 0; i < sjs.size(); i++) {
                        Sj sj = sjs.get(i);
                        SjDTO sjDTO = new SjDTO();
                        BeanUtils.copyProperties(sj,sjDTO);
                        sjDTO.setXh(xsMsg.getXh());
                        sjDTO.setXm(xsMsg.getXm());
                        sjDTO.setYx(xsMsg.getYx());
                        sjDTOs.add(sjDTO);
                    }
                }else {
                    XsMsgDTO xsMsg = xsMapper.getByYhId(userId);
                    if (xsMsg == null) {
                        throw new ServiceException(STUDENT_MSG_NOT_EXISTS_ERROR);
                    }
                    pcList = pcXsMapper.findPcByXsId(xsMsg.getId());
                    if(pcList == null){
                        return sjDTOs;
                    }
                    QueryWrapper<Sj> sjQueryWrapper = new QueryWrapper<>();
                    sjQueryWrapper.eq("pc_id",pcList.get(0).getId())
                            .eq("xs_id",xsMsg.getId())
                            .eq("sfsc",false)
                            .eq("sfck",true);
                    List<Sj> sjs = sjMapper.selectList(sjQueryWrapper);
                    for (int i = 0; i < sjs.size(); i++) {
                        Sj sj = sjs.get(i);
                        SjDTO sjDTO = new SjDTO();
                        BeanUtils.copyProperties(sj,sjDTO);
                        sjDTO.setXh(xsMsg.getXh());
                        sjDTO.setXm(xsMsg.getXm());
                        sjDTO.setYx(xsMsg.getYx());
                        sjDTOs.add(sjDTO);
                    }
                }
                break;
            case "tutor":
                if(pcId != null){
                    sjDTOs = sjMapper.getCondition(pcId);
                }else {
                    Sz yzyds = szMapper.getSzByYhId(userId);
                    if (yzyds == null) {
                        throw new ServiceException(TEACHER_MSG_NOT_EXISTS_ERROR);
                    }
                    pcList = pcXsMapper.findByYzydsIdOrQydsId(yzyds.getId(),null);
                    if(pcList == null){
                        return sjDTOs;
                    }
                    sjDTOs = sjMapper.getCondition(pcList.get(0).getId());
                }

                break;
            //????????????
            case "corporate_mentor":
                if(pcId != null){
                    sjDTOs = sjMapper.getCondition(pcId);
                }else {
                    Sz qyds = szMapper.getSzByYhId(userId);
                    if (qyds == null) {
                        throw new ServiceException(TEACHER_MSG_NOT_EXISTS_ERROR);
                    }
                    pcList = pcXsMapper.findByYzydsIdOrQydsId(null,qyds.getId());
                    if(pcList == null){
                        return sjDTOs;
                    }
                    sjDTOs = sjMapper.getCondition(pcList.get(0).getId());
                }

                break;
            case "teacher":
                if(pcId != null){
                    sjDTOs = sjMapper.getCondition(pcId);
                }else {
                    Sz skjs = szMapper.getSzByYhId(userId);
                    if (skjs == null) {
                        throw new ServiceException(TEACHER_MSG_NOT_EXISTS_ERROR);
                    }
                    pcList = pkMapper.findPcsBySzId(skjs.getId());
                    if(pcList == null){
                        return sjDTOs;
                    }
                    sjDTOs = sjMapper.getCondition(pcList.get(0).getId());
                }

                break;
            case "school_leader":
            case "administrator":
            case "professor":
            case "out_professor":
                if(pcId != null){
                    sjDTOs = sjMapper.getCondition(pcId);
                }else {
                    pcList = pyJhMapper.findAllPc(getFjjgId());
                    if(pcList == null){
                        return sjDTOs;
                    }
                    sjDTOs = sjMapper.getCondition(pcList.get(0).getId());
                }

                break;
            default:
                break;
        }
        if(!CollectionUtils.isEmpty(sjDTOs)){
            for (SjDTO sjDTO : sjDTOs) {
                Xs xs = xsMapper.selectById(sjDTO.getXsId());
                if(xs != null){
                    ResponseUtil byId = userFeignService.getById(xs.getYhId());
                    Yh yh = null;
                    if(byId != null){
                        if(byId.getErrCode().intValue() == 0){
                            yh = byId.conversionData(new TypeReference<Yh>() {});
                        }
                    }
                    if(yh != null){
                        sjDTO.setYhtx(yh.getYhtx());
                    }
                }
            }
        }
        return sjDTOs;
    }

    /**
     * ??????????????????
     * @return
     */
    @Override
    public List<SjDTO> getAll(Long fjjgId) {
        log.info("??????????????? - ?????????????????????");
        List<SjDTO> sjDTOs = sjMapper.getByCondition(null,null,null,null,fjjgId);
        return sjDTOs;
    }

    /**
     * ????????????
     * @param sjDTO
     * @return
     */
    @Override
    public boolean add(SjDTO sjDTO) {
        log.info("??????????????? - ????????????:{}???",sjDTO);
        Long userId = getUserId();
        sjDTO.setCjr(userId);
        sjDTO.setGxr(userId);
        Sj sj = new Sj();
        BeanUtils.copyProperties(sjDTO,sj);
        return sjService.save(sj);
    }

    /**
     * ??????id??????????????????
     * @param id
     * @return
     */
    @Override
    public SjDTO get(Long id) {
        log.info("??????????????? - ??????id:{}?????????????????????",id);
        SjDTO sjDTO = sjMapper.getById(id);
        return sjDTO;
    }

    /**
     * ????????????
     * @param sjDTO
     * @return
     */
    @Override
    public boolean update(SjDTO sjDTO) {
        log.info("??????????????? - ????????????:{}???",sjDTO);
        sjDTO.setGxr(getUserId());
        Sj sj = new Sj();
        BeanUtils.copyProperties(sjDTO,sj);
        return sjService.updateById(sj);
    }

    /**
     * ??????????????????id
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
     * ??????????????????id????????????
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

    /**
     * ??????????????????ID
     */
    public Long getFjjgId() {
        LoginUser loginUser = threadLocal.get();
        Long fjjgId;
        if (loginUser != null) {
            fjjgId = loginUser.getFjjgId();
        } else {
            throw new ServiceException(GET_THREADLOCAL_ERROR);
        }
        return fjjgId;
    }

}
