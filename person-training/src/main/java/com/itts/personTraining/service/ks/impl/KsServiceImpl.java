package com.itts.personTraining.service.ks.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.ServiceException;
import com.itts.common.exception.WebException;
import com.itts.common.utils.DateUtils;
import com.itts.personTraining.dto.KsDTO;
import com.itts.personTraining.dto.KsExpDTO;
import com.itts.personTraining.dto.XsMsgDTO;
import com.itts.personTraining.mapper.ks.KsMapper;
import com.itts.personTraining.mapper.ksExp.KsExpMapper;
import com.itts.personTraining.mapper.ksXs.KsXsMapper;
import com.itts.personTraining.mapper.pc.PcMapper;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.mapper.szKs.SzKsMapper;
import com.itts.personTraining.mapper.szKsExp.SzKsExpMapper;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.model.ks.Ks;
import com.itts.personTraining.model.ksExp.KsExp;
import com.itts.personTraining.model.ksXs.KsXs;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.szKs.SzKs;
import com.itts.personTraining.model.szKsExp.SzKsExp;
import com.itts.personTraining.model.tz.Tz;
import com.itts.personTraining.model.tzSz.TzSz;
import com.itts.personTraining.model.tzXs.TzXs;
import com.itts.personTraining.service.ks.KsService;
import com.itts.personTraining.service.ksExp.KsExpService;
import com.itts.personTraining.service.ksXs.KsXsService;
import com.itts.personTraining.service.szKs.SzKsService;
import com.itts.personTraining.service.szKsExp.SzKsExpService;
import com.itts.personTraining.service.tz.TzService;
import com.itts.personTraining.service.tzSz.TzSzService;
import com.itts.personTraining.service.tzXs.TzXsService;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.personTraining.enums.BmfsEnum.OFF_LINE;
import static com.itts.personTraining.enums.BmfsEnum.ON_LINE;

/**
 * <p>
 * 考试表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-05-06
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class KsServiceImpl extends ServiceImpl<KsMapper, Ks> implements KsService {

    @Autowired
    private KsService ksService;
    @Resource
    private KsMapper ksMapper;
    @Autowired
    private SzKsExpService szKsExpService;
    @Resource
    private SzKsExpMapper szKsExpMapper;
    @Autowired
    private KsExpService ksExpService;
    @Resource
    private KsExpMapper ksExpMapper;
    @Autowired
    private SzKsService szKsService;
    @Autowired
    private KsXsService ksXsService;
    @Autowired
    private TzService tzService;
    @Autowired
    private TzXsService tzXsService;
    @Autowired
    private TzSzService tzSzService;
    @Resource
    private KsXsMapper ksXsMapper;
    @Resource
    private SzKsMapper szKsMapper;
    @Resource
    private PcXsMapper pcXsMapper;
    @Resource
    private XsMapper xsMapper;
    @Resource
    private PcMapper pcMapper;

    /**
     * 查询考试列表
     * @param pageNum
     * @param pageSize
     * @param pcId
     * @param ksmc
     * @param kslx
     * @return
     */
    @Override
    public PageInfo<KsDTO> findByPage(Integer pageNum, Integer pageSize, Long pcId, String ksmc, String kslx, Long fjjgId) {
        log.info("【人才培养 - 分页条件查询考试列表,,批次id:{},考试名称:{},考试类型:{},父级机构ID:{}】",pcId,ksmc,kslx,fjjgId);
        PageHelper.startPage(pageNum, pageSize);
        List<KsDTO> ksDTOs = ksMapper.findByPage(pcId, ksmc, kslx, fjjgId);
        for (KsDTO ksDTO : ksDTOs) {
            List<Long> szIds = szKsMapper.getByKsId(ksDTO.getId());
            ksDTO.setSzIds(szIds);
        }
        return new PageInfo<>(ksDTOs);
    }

    /**
     * 根据id查询考试详情
     * @param id
     * @return
     */
    @Override
    public KsDTO get(Long id) {
        log.info("【人才培养 - 根据id:{}查询考试详情】",id);
        KsDTO ksDTO = ksMapper.findById(id);
        if (ksDTO != null) {
            List<Long> szIds = szKsMapper.getByKsId(ksDTO.getId());
            ksDTO.setSzIds(szIds);
            List<KsExpDTO> ksExpDTOs = ksExpMapper.findByCondition(null,ksDTO.getId());
            for (KsExpDTO ksExpDTO : ksExpDTOs) {
                List<Long> expsSzIds = szKsExpMapper.selectByKsExpId(ksExpDTO.getId());
                ksExpDTO.setSzIds(expsSzIds);
            }
            ksDTO.setKsExps(ksExpDTOs);
        }
        return ksDTO;
    }



    /**
     * 新增考试
     * @param ksDTO
     * @return
     */
    @Override
    public boolean add(KsDTO ksDTO) {
        log.info("【人才培养 - 新增考试:{}】",ksDTO);
        QueryWrapper<Ks> ksQueryWrapper = new QueryWrapper<>();
        ksQueryWrapper.eq("pc_id",ksDTO.getPcId())
                .eq("kslx","exam_type_01")
                .eq("sfsc",false);
        Ks ks1 = ksMapper.selectOne(ksQueryWrapper);
        if(ks1 != null){
            throw new WebException(SYSTEM_FIND_ERROR);
        }
        Long userId = getUserId();
        Ks ks = new Ks();
        ksDTO.setCjr(userId);
        ksDTO.setGxr(userId);
        BeanUtils.copyProperties(ksDTO,ks);
        if (ksService.save(ks)) {
            Long ksId = ks.getId();
            List<Long> szIds = ksDTO.getSzIds();
            if (szIds != null && szIds.size() > 0) {
                saveSzKs(ks, szIds);
            }
            List<KsExpDTO> ksExpDTOs = ksDTO.getKsExps();
            for (KsExpDTO ksExpDTO : ksExpDTOs) {
                KsExp ksExp = new KsExp();
                ksExpDTO.setKsId(ksId);
                ksExpDTO.setCjr(userId);
                ksExpDTO.setGxr(userId);
                BeanUtils.copyProperties(ksExpDTO,ksExp);
                if (ksExpService.save(ksExp)) {
                    List<Long> expszIds = ksExpDTO.getSzIds();
                    if (expszIds != null && expszIds.size() > 0) {
                        saveSzKs(expszIds,ksExp);
                        continue;
                    }
                    continue;
                }
                return false;
            }
            return true;
        }
        return false;
    }



    /**
     * 更新考试
     * @param ksDTO
     * @return
     */
    @Override
    public boolean update(KsDTO ksDTO) {
        log.info("【人才培养 - 更新考试:{}】",ksDTO);
        Ks ks = new Ks();
        ksDTO.setGxr(getUserId());
        BeanUtils.copyProperties(ksDTO,ks);
        if (ksService.updateById(ks)) {
            List<Long> szIds = ksDTO.getSzIds();
            if (szIds != null && szIds.size() > 0) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("ks_id",ksDTO.getId());
                if (szKsService.removeByMap(map)) {
                    return saveSzKs(ks, szIds);
                }
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 删除考试
     * @param ks
     * @return
     */
    @Override
    public boolean delete(Ks ks) {
        log.info("【人才培养 - 删除考试:{}】",ks);
        Long userId = getUserId();
        //设置删除状态
        ks.setSfsc(true);
        ks.setGxr(userId);
        List<Long> szIds = szKsMapper.getByKsId(ks.getId());
        if (ksService.updateById(ks)) {
            if (ON_LINE.getMsg().equals(ks.getKslb())) {
                return true;
            }
            if (CollectionUtils.isEmpty(szIds)) {
                return true;
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("ks_id",ks.getId());
            if (szIds != null && szIds.size() > 0) {
                //继续教育
                return szKsService.removeByMap(map);
            } else {
                //学历学位
                List<KsExp> ksExps = ksExpMapper.selectByMap(map);
                List<KsExp> ksExpList = getKsExps(userId, ksExps);
                if (ksExpService.updateBatchById(ksExps)) {
                    for (KsExp ksExp : ksExpList) {
                        HashMap<String, Object> map1 = new HashMap<>();
                        map1.put("ks_exp_id",ksExp.getId());
                        szKsExpService.removeByMap(map1);
                    }
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /**
     * 考试批量下发
     * @param ids
     * @return
     */
    @Override
    public boolean issueBatch(List<Long> ids) {
        log.info("【人才培养 - 考试批量下发,ids:{}】",ids);
        List<Ks> ksList = ksMapper.selectBatchIds(ids);
        Long userId = getUserId();
        for (Ks ks : ksList) {
            Pc pc = pcMapper.getPcById(ks.getPcId());
            ks.setGxr(userId);
            ks.setSfxf(true);
            ks.setXfsj(new Date());
            Tz tz = new Tz();
            tz.setTzlx("考试通知");
            tz.setTzmc(pc.getPch() + "考试通知" + DateUtils.getDateFormat(new Date()));
            Tz tz1 = new Tz();
            tz1.setTzlx("考试通知");
            tz1.setTzmc(pc.getPch() + "考试通知" + DateUtils.getDateFormat(new Date()));
            Tz tz2 = new Tz();
            tz2.setTzlx("考试通知");
            tz2.setTzmc(pc.getPch() + "考试通知" + DateUtils.getDateFormat(new Date()));
            if (ks.getType() == 1) {
                //学历学位教育,通过批次id查询学员ids(研究生)
                List<Long> xsIds = xsMapper.findXsIdsByPcId(pc.getId());
                //通过考试id查询考试扩展集合
                List<KsExpDTO> ksExpDTOList = ksExpMapper.findByCondition(null, ks.getId());
                List<KsExpDTO> ksExpDTOS = ksExpDTOList.stream().filter(obj->
                        obj.getKsrq() != null
                ).collect(Collectors.toList());
                String nr = "您好，您此批次："+pc.getPch()+ks.getKsmc()+"的";
                for (KsExpDTO ksExpDTO : ksExpDTOS) {
                    nr += ksExpDTO.getKcmc()+"课程将于"+DateUtils.getDateFormat(ksExpDTO.getKsrq())+"，"+ksExpDTO.getKskssj()+"—"+ksExpDTO.getKsjssj()+"在"+ksExpDTO.getJxlmc()+ksExpDTO.getJsbh()+"进行考试，";
                    tz1.setNr("您好，您将于"+DateUtils.getDateFormat(ksExpDTO.getKsrq())+"，"+ksExpDTO.getKskssj()+"—"+ksExpDTO.getKsjssj()+"在"+ksExpDTO.getJxlmc()+ksExpDTO.getJsbh()+"进行"+ksExpDTO.getKcmc()+"课程的监考，请悉知！");
                    tz1.setCjr(userId);
                    tz1.setGxr(userId);
                    if (tzService.save(tz1)) {
                        List<Long> szIds = szKsExpMapper.findSzIdsByKsExpId(ksExpDTO.getId());
                        saveTzSz(tz1, szIds);
                    } else {
                        throw new ServiceException(INSERT_FAIL);
                    }
                }
                nr += "请悉知！";
                tz.setNr(nr);
                tz.setCjr(userId);
                tz.setGxr(userId);
                saveTzAndTzXs(tz, xsIds,null);
            } else if (ks.getType() == 2) {
                if (ks.getKslb().equals(ON_LINE.getMsg())) {
                    //继续教育,通过批次id和报名方式(线上)查询学员ids(经纪人)
                    List<Long> xsIdList = xsMapper.findXsIdsByPcIdAndBmfs(pc.getId(),ON_LINE.getMsg());
                    if (CollectionUtils.isNotEmpty(xsIdList)) {
                        tz2.setNr("您好，您此批次："+pc.getPch()+"的"+ks.getKsmc()+"将于"+DateUtils.getDateFormat(ks.getKsksnyr())+"至"+DateUtils.getDateFormat(ks.getKsjsnyr())+"进行线上考试，请悉知！");
                        tz2.setCjr(userId);
                        tz2.setGxr(userId);
                        tz2.setKsksnyr(ks.getKsksnyr());
                        tz2.setKsjsnyr(ks.getKsjsnyr());
                        tz2.setKssjId(ks.getKssjId());
                        saveTzAndTzXs(tz2, xsIdList, ks.getKssc());
                    }
                } else {
                    //继续教育,通过批次id和报名方式(线下)查询学员ids(经纪人)
                    List<Long> xsIds = xsMapper.findXsIdsByPcIdAndBmfs(pc.getId(),OFF_LINE.getMsg());
                    if (CollectionUtils.isNotEmpty(xsIds)) {
                        tz.setNr("您好，您此批次："+pc.getPch()+"的"+ks.getKsmc()+"将于"+DateUtils.getDateFormat(ks.getKsrq())+"，"+ks.getKskssj()+"--"+ks.getKsjssj()+"在"+ks.getKsdd()+"进行考试，请悉知！");
                        tz.setCjr(userId);
                        tz.setGxr(userId);
                        saveTzAndTzXs(tz, xsIds, null);
                        tz1.setNr("您好，您将于"+DateUtils.getDateFormat(ks.getKsrq())+"，"+ks.getKskssj()+"--"+ks.getKsjssj()+"在"+ks.getKsdd()+"进行监考，请悉知！");
                        tz1.setCjr(userId);
                        tz1.setGxr(userId);
                        if (tzService.save(tz1)) {
                            List<Long> szIds = szKsMapper.getByKsId(ks.getId());
                            saveTzSz(tz1, szIds);
                        } else {
                            throw new ServiceException(INSERT_FAIL);
                        }
                    }
                }
            }
        }
        if (ksService.updateBatchById(ksList)) {
            for (Long id : ids) {
                //根据考试id查询批次id
                Ks ks = ksService.getById(id);
                Long pcId = ks.getPcId();
                List<Long> xsIds = pcXsMapper.selectByPcId(pcId);
                List<KsXs> ksXsList = new ArrayList<>();
                for (Long xsId : xsIds) {
                    KsXs ksXs = new KsXs();
                    ksXs.setXsId(xsId);
                    ksXs.setKsId(id);
                    ksXsList.add(ksXs);
                }
                return ksXsService.saveBatch(ksXsList);
            }
            return true;
        }
        return false;
    }

    /**
     * 根据用户id查询考试详情(前)
     * @return
     */
    @Override
    public List<KsDTO> getByYhId() {
        Long userId = getUserId();
        log.info("【人才培养 - 根据用户id:{}查询考试详情】",userId);
        XsMsgDTO xsMsgDTO = xsMapper.getByYhId(userId);
        List<Ks> ksList = ksXsMapper.getKsIdAndTypeByYhId(userId);
        if (Collections.isEmpty(ksList)) {
            return java.util.Collections.EMPTY_LIST;
        }
        List<Long> ksIds = new ArrayList<>();
        List<KsDTO> ksDTOs = new ArrayList<>();
        for (Ks ks : ksList) {
            KsDTO ksDTO = get(ks.getId());
            if (ks.getType() == 2) {
                //继续教育考试
                List<KsExpDTO> ksExpDTOs = ksExpMapper.getByCondition(null, ks.getId());
                ksDTO.setKsExps(ksExpDTOs);
            }
            ksDTOs.add(ksDTO);
            ksIds.add(ks.getId());
        }
        //考试学生表是否查看更新成是,即已查看
        if (ksXsMapper.updateSfckByXsId(xsMsgDTO.getId()) == 0) {
           throw new ServiceException(UPDATE_FAIL);
        }
        return ksDTOs;
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
     * 新增师资考试
     * @param szIds
     * @param ksExp
     * @return
     */
    private boolean saveSzKs(List<Long> szIds, KsExp ksExp) {
        List<SzKsExp> szKsExpList = new ArrayList<>();
        for (Long szId : szIds) {
            SzKsExp szKsExp = new SzKsExp();
            szKsExp.setSzId(szId);
            szKsExp.setKsExpId(ksExp.getId());
            szKsExpList.add(szKsExp);
        }
        return szKsExpService.saveBatch(szKsExpList);
    }

    /**
     * 获取考试扩展集合信息
     * @param userId
     * @param ksExps
     * @return
     */
    private List<KsExp> getKsExps(Long userId, List<KsExp> ksExps) {
        for (KsExp ksExp : ksExps) {
            ksExp.setGxr(userId);
            ksExp.setSfsc(true);
        }
        return ksExps;
    }

    /**
     * 新增师资考试(监考老师)
     * @param ks
     * @param szIds
     * @return
     */
    private boolean saveSzKs(Ks ks, List<Long> szIds) {
        List<SzKs> szKsList = new ArrayList<>();
        for (Long szId : szIds) {
            SzKs szKs = new SzKs();
            szKs.setSzId(szId);
            szKs.setKsId(ks.getId());
            szKsList.add(szKs);
        }
        return szKsService.saveBatch(szKsList);
    }

    /**
     * 保存通知和通知学生信息
     * @param tz
     * @param xsIds
     */
    private void saveTzAndTzXs(Tz tz, List<Long> xsIds, String kssc) {
        if (tzService.save(tz)) {
            List<TzXs> tzXsList = new ArrayList<>();
            for (Long xsId : xsIds) {
                TzXs tzXs = new TzXs();
                tzXs.setTzId(tz.getId());
                tzXs.setXsId(xsId);
                tzXs.setKssc(kssc);
                tzXsList.add(tzXs);
            }
            if (!tzXsService.saveBatch(tzXsList)) {
                throw new ServiceException(INSERT_FAIL);
            }
        } else {
            throw new ServiceException(INSERT_FAIL);
        }
    }

    /**
     * 新增通知师资关系
     * @param tz1
     * @param szIds
     */
    private void saveTzSz(Tz tz1, List<Long> szIds) {
        List<TzSz> tzSzs = new ArrayList<>();
        for (Long szId : szIds) {
            TzSz tzSz = new TzSz();
            tzSz.setSzId(szId);
            tzSz.setTzId(tz1.getId());
            tzSzs.add(tzSz);
        }
        if (!tzSzService.saveBatch(tzSzs)) {
            throw new ServiceException(INSERT_FAIL);
        }
    }

}
