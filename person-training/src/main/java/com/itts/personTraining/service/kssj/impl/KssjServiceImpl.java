package com.itts.personTraining.service.kssj.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.enums.KsjlXxbhEnum;
import com.itts.personTraining.enums.SjtxndpzEnum;
import com.itts.personTraining.enums.TkzyTypeEnum;
import com.itts.personTraining.mapper.kc.KcMapper;
import com.itts.personTraining.mapper.kssj.KssjMapper;
import com.itts.personTraining.mapper.kssj.SjTmGlMapper;
import com.itts.personTraining.mapper.tkzy.TkzyMapper;
import com.itts.personTraining.mapper.tkzy.TmxxMapper;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.kssj.Kssj;
import com.itts.personTraining.model.kssj.SjTmGl;
import com.itts.personTraining.model.sjtxndpz.Sjtxndpz;
import com.itts.personTraining.model.tkzy.Tkzy;
import com.itts.personTraining.model.tkzy.Tmxx;
import com.itts.personTraining.request.kssj.AddKssjRequest;
import com.itts.personTraining.request.kssj.RandomKssjRequest;
import com.itts.personTraining.request.kssj.UpdateKssjRequest;
import com.itts.personTraining.service.kssj.KssjService;
import com.itts.personTraining.service.sjpz.SjpzService;
import com.itts.personTraining.vo.kssj.GetKssjVO;
import com.itts.personTraining.vo.kssj.GetRandomKssjVO;
import com.itts.personTraining.vo.sjpz.SjpzVO;
import com.itts.personTraining.vo.sjpz.SjtxpzVO;
import com.itts.personTraining.vo.tkzy.GetTkzyVO;
import com.itts.personTraining.vo.tkzy.GetTmxxVO;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 * ???????????? ???????????????
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-14
 */
@Service
public class KssjServiceImpl extends ServiceImpl<KssjMapper, Kssj> implements KssjService {

    @Autowired
    private KssjMapper kssjMapper;

    @Autowired
    private SjTmGlMapper sjTmGlMapper;

    @Autowired
    private TkzyMapper tkzyMapper;

    @Autowired
    private TmxxMapper tmxxMapper;

    @Autowired
    private KcMapper kcMapper;

    @Resource
    private SjpzService sjpzService;

    /**
     * ??????????????????
     */
    @Override
    public GetKssjVO get(Long id) {

        //??????????????????
        QueryWrapper kssjQuery = new QueryWrapper();
        kssjQuery.eq("id", id);
        kssjQuery.eq("sfsc", false);

        Kssj kssj = kssjMapper.selectOne(kssjQuery);

        if (kssj == null) {
            return null;
        }

        GetKssjVO vo = new GetKssjVO();
        BeanUtils.copyProperties(kssj, vo);

        //???????????????????????????????????????
        QueryWrapper sjTmGlQuery = new QueryWrapper();
        sjTmGlQuery.eq("kssj_id", id);
        List<SjTmGl> sjTmgls = sjTmGlMapper.selectList(sjTmGlQuery);

        if (CollectionUtils.isEmpty(sjTmgls)) {
            return vo;
        }

        //????????????????????????ID
        List<Long> sjTmIds = sjTmgls.stream().map(SjTmGl::getTmId).collect(Collectors.toList());

        //????????????????????????
        QueryWrapper tmQuery = new QueryWrapper();
        tmQuery.in("id", sjTmIds);

        List<Tkzy> tms = tkzyMapper.selectList(tmQuery);

        if (CollectionUtils.isEmpty(tms)) {
            return vo;
        }

        List<GetTkzyVO> tkzyVOs = Lists.newArrayList();

        //????????????????????????
        for (Tkzy tm : tms) {

            GetTkzyVO tkzyVo = new GetTkzyVO();
            BeanUtils.copyProperties(tm, tkzyVo);

            tkzyVOs.add(tkzyVo);

            List<Tmxx> tmxx = tmxxMapper.findByTmId(tm.getId());

            if (CollectionUtils.isEmpty(tmxx)) {
                continue;
            }

            List<GetTmxxVO> tmxxVOs = tmxx.stream().map(obj -> {
                GetTmxxVO tmxxVo = new GetTmxxVO();
                BeanUtils.copyProperties(obj, tmxxVo);
                return tmxxVo;
            }).collect(Collectors.toList());

            for (int i = 0; i < tmxxVOs.size(); i++) {
                GetTmxxVO getTmxxVO = tmxxVOs.get(i);
                int num = i + 1;
                getTmxxVO.setXxnr(KsjlXxbhEnum.getByKey(num).getValue()+"???"+getTmxxVO.getXxnr());

            }

            tkzyVo.setTmxxs(tmxxVOs);
        }

        Map<String, List<GetTkzyVO>> tmMap = Maps.newHashMap();

        //???????????????????????????????????????????????????
        List<GetTkzyVO> judgmentList = tkzyVOs.stream().filter(obj -> Objects.equals(obj.getTmlx(), TkzyTypeEnum.JUDGMENT.getKey())).collect(Collectors.toList());
        tmMap.put(TkzyTypeEnum.JUDGMENT.getKey(), judgmentList);

        List<GetTkzyVO> singleChoiceList = tkzyVOs.stream().filter(obj -> Objects.equals(obj.getTmlx(), TkzyTypeEnum.SINGLE_CHOICE.getKey())).collect(Collectors.toList());
        tmMap.put(TkzyTypeEnum.SINGLE_CHOICE.getKey(), singleChoiceList);

        List<GetTkzyVO> multipleChoiceList = tkzyVOs.stream().filter(obj -> Objects.equals(obj.getTmlx(), TkzyTypeEnum.MULTIPLE_CHOICE.getKey())).collect(Collectors.toList());
        tmMap.put(TkzyTypeEnum.MULTIPLE_CHOICE.getKey(), multipleChoiceList);

        vo.setTms(tmMap);

        return vo;
    }

    /**
     * ??????????????????
     * @param randomKssjRequest
     * @return
     */
    @Override
    public Boolean randomAdd(RandomKssjRequest randomKssjRequest) {
        Kssj bySjmc = getBySjmc(randomKssjRequest.getSjmc());
        if(bySjmc != null){
            throw new ServiceException(ErrorCodeEnum.NAME_EXIST_ERROR);
        }
        String sjmc = randomKssjRequest.getSjmc();
        String sjlx = randomKssjRequest.getSjlx();
        String xylx = randomKssjRequest.getXylx();
        Long sjpzId = randomKssjRequest.getSjpzId();
        String sjlb = randomKssjRequest.getSjlb();
        SjpzVO sjpzVO = sjpzService.get(sjpzId);

        String jylx = randomKssjRequest.getJylx();
        SjtxpzVO judge = sjpzVO.getJudge();
        SjtxpzVO single = sjpzVO.getSingle();
        SjtxpzVO multiple = sjpzVO.getMultiple();

        LoginUser loginUser = SystemConstant.threadLocal.get();
        Long userId = null;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }
        Date now = new Date();
        Long fjjgId = getFjjgId();
        //??????????????????
        Kssj kssj = new Kssj();
        kssj.setFjjgId(fjjgId);
        if(Objects.equals(sjlx,"single_subject")){
            Kc kc = kcMapper.selectOne(new QueryWrapper<Kc>().eq("id", randomKssjRequest.getKcIdList().get(0))
                    .eq("sfsc", false));
            if(kc == null ){
                throw new ServiceException(ErrorCodeEnum.COURSE_ISEMPTY_ERROR);
            }
            kssj.setKcId(kc.getId());
            kssj.setKcMc(kc.getKcmc());
        }
        kssj.setSjmc(sjmc);
        kssj.setTmzs(sjpzVO.getSjts());
        kssj.setSjzf(sjpzVO.getSjzf());
        kssj.setPdzf(judge.getTxzf());
        kssj.setDanzf(single.getTxzf());
        kssj.setDuozf(multiple.getTxzf());
        kssj.setJylx(jylx);
        kssj.setXylx(xylx);
        kssj.setSjlx(sjlx);
        kssj.setLx("random");
        kssj.setSjpzId(sjpzVO.getId());
        kssj.setCjr(userId);
        kssj.setGxr(userId);
        kssj.setCjsj(now);
        kssj.setGxsj(now);
        kssj.setSjlb(sjlb);
        kssjMapper.insert(kssj);
        Long id = getBySjmc(sjmc).getId();

        //????????????????????????
        List<Long> kcIdList = null;
        if(Objects.equals(sjlx,"single_subject")){
            kcIdList = randomKssjRequest.getKcIdList();
        }else if(Objects.equals(sjlx,"comprehensive")){
            List<Kc> kcs = kcMapper.selectList(new QueryWrapper<Kc>().eq("fjjg_id", fjjgId)
                    .eq("xylx", xylx)
                    .eq("sfsc", false));
            kcIdList = kcs.stream().map(Kc::getId).collect(Collectors.toList());
        }
        //?????????

        random(id,judge.getEasy(),SjtxndpzEnum.EASY.getValue(),TkzyTypeEnum.JUDGMENT.getKey(),kcIdList);
        random(id,judge.getCommonly(),SjtxndpzEnum.COMMONLY.getValue(),TkzyTypeEnum.JUDGMENT.getKey(), kcIdList);
        random(id,judge.getDifficulty(),SjtxndpzEnum.DIFFCULTY.getValue(),TkzyTypeEnum.JUDGMENT.getKey(), kcIdList);


        random(id,single.getEasy(),SjtxndpzEnum.EASY.getValue(),TkzyTypeEnum.SINGLE_CHOICE.getKey(),kcIdList);
        random(id,single.getCommonly(),SjtxndpzEnum.COMMONLY.getValue(),TkzyTypeEnum.SINGLE_CHOICE.getKey(), kcIdList);
        random(id,single.getDifficulty(),SjtxndpzEnum.DIFFCULTY.getValue(),TkzyTypeEnum.SINGLE_CHOICE.getKey(), kcIdList);


        random(id,multiple.getEasy(),SjtxndpzEnum.EASY.getValue(),TkzyTypeEnum.MULTIPLE_CHOICE.getKey(),kcIdList);
        random(id,multiple.getCommonly(),SjtxndpzEnum.COMMONLY.getValue(),TkzyTypeEnum.MULTIPLE_CHOICE.getKey(), kcIdList);
        random(id,multiple.getDifficulty(),SjtxndpzEnum.DIFFCULTY.getValue(),TkzyTypeEnum.MULTIPLE_CHOICE.getKey(), kcIdList);
        return true;
    }

    @Override
    public GetRandomKssjVO getRandom(Long id) {
        Kssj kssj = kssjMapper.selectOne(new QueryWrapper<Kssj>().eq("id", id).eq("sfsc", false));
        if(kssj == null){
            throw new ServiceException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        GetRandomKssjVO vo = new GetRandomKssjVO();
        BeanUtils.copyProperties(kssj, vo);
        //??????????????????
        SjpzVO sjpzVO = sjpzService.get(kssj.getSjpzId());

        Integer dtfz = sjpzVO.getJudge().getDtfz();
        Integer px = sjpzVO.getJudge().getPx();
        vo.setPddt(dtfz);
        vo.setPdpx(px);

        Integer dtfz1 = sjpzVO.getSingle().getDtfz();
        Integer px1 = sjpzVO.getSingle().getPx();
        vo.setDandt(dtfz1);
        vo.setDanpx(px1);

        Integer dtfz2 = sjpzVO.getMultiple().getDtfz();
        Integer px2 = sjpzVO.getMultiple().getPx();
        vo.setDuodt(dtfz2);
        vo.setDuopx(px2);

        //???????????????????????????????????????
        List<SjTmGl> sjTmgls = sjTmGlMapper.selectList(new QueryWrapper<SjTmGl>().eq("kssj_id",id));
        if (CollectionUtils.isEmpty(sjTmgls)) {
            return vo;
        }
        List<GetTkzyVO> tkzyVOs = Lists.newArrayList();
        //????????????????????????ID
        List<Long> sjTmIds = sjTmgls.stream().map(SjTmGl::getTmId).collect(Collectors.toList());
        //????????????????????????
        List<Tkzy> tms = tkzyMapper.selectList(new QueryWrapper<Tkzy>().in("id",sjTmIds));
        //????????????????????????
        for (Tkzy tm : tms) {
            GetTkzyVO tkzyVo = new GetTkzyVO();
            BeanUtils.copyProperties(tm, tkzyVo);
            tkzyVOs.add(tkzyVo);
            List<Tmxx> tmxx = tmxxMapper.findByTmId(tm.getId());
            if (CollectionUtils.isEmpty(tmxx)) {
                continue;
            }
            List<GetTmxxVO> tmxxVOs = tmxx.stream().map(obj -> {
                GetTmxxVO tmxxVo = new GetTmxxVO();
                BeanUtils.copyProperties(obj, tmxxVo);
                return tmxxVo;
            }).collect(Collectors.toList());
            for (int i = 0; i < tmxxVOs.size(); i++) {
                GetTmxxVO getTmxxVO = tmxxVOs.get(i);
                int num = i + 1;
                getTmxxVO.setXxnr(KsjlXxbhEnum.getByKey(num).getValue()+"???"+getTmxxVO.getXxnr());

            }
            tkzyVo.setTmxxs(tmxxVOs);
        }
        Map<String, List<GetTkzyVO>> tmMap = Maps.newHashMap();

        //???????????????????????????????????????????????????
        List<GetTkzyVO> judgmentList = tkzyVOs.stream().filter(obj -> Objects.equals(obj.getTmlx(), TkzyTypeEnum.JUDGMENT.getKey())).collect(Collectors.toList());
        tmMap.put(TkzyTypeEnum.JUDGMENT.getKey(), judgmentList);

        List<GetTkzyVO> singleChoiceList = tkzyVOs.stream().filter(obj -> Objects.equals(obj.getTmlx(), TkzyTypeEnum.SINGLE_CHOICE.getKey())).collect(Collectors.toList());
        tmMap.put(TkzyTypeEnum.SINGLE_CHOICE.getKey(), singleChoiceList);

        List<GetTkzyVO> multipleChoiceList = tkzyVOs.stream().filter(obj -> Objects.equals(obj.getTmlx(), TkzyTypeEnum.MULTIPLE_CHOICE.getKey())).collect(Collectors.toList());
        tmMap.put(TkzyTypeEnum.MULTIPLE_CHOICE.getKey(), multipleChoiceList);

        vo.setTms(tmMap);

        return vo;
    }

    @Override
    public RandomKssjRequest randomUpdate(Kssj old, RandomKssjRequest randomKssjRequest) {
        Kssj bySjmc = getBySjmc(randomKssjRequest.getSjmc());
        if(bySjmc != null && bySjmc.getId().intValue() != randomKssjRequest.getId().intValue()){
            throw new ServiceException(ErrorCodeEnum.NAME_EXIST_ERROR);
        }
        old.setSfsc(true);
        kssjMapper.updateById(old);
        randomAdd(randomKssjRequest);
        return randomKssjRequest;
    }

    private void random(Long id,Sjtxndpz sjtxndpz,String sjtxnd,String tkzyType,List<Long> kcIdList){
        List<Tkzy> by = getBy(sjtxnd, tkzyType, kcIdList);
        int n =0;
        if(!CollectionUtils.isEmpty(by)){
            n = by.size();
        }
        //??????????????????
        if(n < sjtxndpz.getTs().intValue()){
            Kssj kssj = kssjMapper.selectOne(new QueryWrapper<Kssj>().eq("id", id).eq("sfsc", false));
            kssj.setSfsc(true);
            kssjMapper.updateById(kssj);
            throw new ServiceException(ErrorCodeEnum.QUSETION_BANK_EXISTS_ERROR);
        }
        Collections.shuffle(by);
        List<Tkzy> tkzies = by.subList(0, sjtxndpz.getTs());

        //????????????????????????
        List<Long> collect = tkzies.stream().map(Tkzy::getId).collect(Collectors.toList());
        for (Long aLong : collect) {
            SjTmGl sjTmGl = new SjTmGl();
            sjTmGl.setKssjId(id);
            sjTmGl.setTmId(aLong);
            sjTmGlMapper.insert(sjTmGl);
        }
    }
    private List<Tkzy> getBy(String tmnd,String tmlx,List<Long> kcIdList){
        Long fjjgId = getFjjgId();
        List<Tkzy> tkzies = tkzyMapper.selectList(new QueryWrapper<Tkzy>().eq("sfsc", false)
                .eq("tmnd", tmnd)
                .eq("tmlx",tmlx)
                .eq("fjjg_id",fjjgId)
                .eq("sfsj",true)
                .in("kc_id", kcIdList));
        return tkzies;
    }
    private Kssj getBySjmc(String sjmc){
        Kssj kssj = kssjMapper.selectOne(new QueryWrapper<Kssj>().eq("sjmc", sjmc).eq("sfsc", false));
        return kssj;
    }

    /**
     * ??????
     */
    @Override
    public Kssj add(AddKssjRequest addKssjRequest) {
        Kssj bySjmc = getBySjmc(addKssjRequest.getSjmc());
        if(bySjmc != null){
            throw new ServiceException(ErrorCodeEnum.NAME_EXIST_ERROR);
        }
        LoginUser loginUser = SystemConstant.threadLocal.get();
        Long userId = null;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        Kssj kssj = new Kssj();
        BeanUtils.copyProperties(addKssjRequest, kssj);

        Date now = new Date();

        kssj.setCjr(userId);
        kssj.setGxr(userId);
        kssj.setCjsj(now);
        kssj.setGxsj(now);

        //??????????????????
        Kc kc = kcMapper.selectById(addKssjRequest.getKcId());
        if (kc != null) {
            kssj.setKcMc(kc.getKcmc());
        }

        kssj.setTmzs(addKssjRequest.getTmIds().size());

        //??????????????????????????????
        QueryWrapper query = new QueryWrapper();
        query.in("id", addKssjRequest.getTmIds());

        //??????????????????????????????ID
        kssj.setFjjgId(getFjjgId());
        kssj.setLx("custom");
        kssjMapper.insert(kssj);

        //????????????????????????
        for (Long tmId : addKssjRequest.getTmIds()) {

            SjTmGl sjTmGl = new SjTmGl();
            sjTmGl.setKssjId(kssj.getId());
            sjTmGl.setTmId(tmId);

            sjTmGlMapper.insert(sjTmGl);
        }

        return kssj;
    }

    /**
     * ??????
     */
    @Override
    public Kssj update(UpdateKssjRequest updateKssjRequest, Kssj kssj, Long userId) {
        Kssj bySjmc = getBySjmc(updateKssjRequest.getSjmc());
        if(bySjmc != null && bySjmc.getId().intValue() != kssj.getId().intValue() ){
            throw new ServiceException(ErrorCodeEnum.NAME_EXIST_ERROR);
        }
        BeanUtils.copyProperties(updateKssjRequest, kssj);

        kssj.setTmzs(updateKssjRequest.getTmIds().size());
        kssj.setGxr(userId);
        kssj.setGxsj(new Date());

        kssjMapper.updateById(kssj);

        //??????????????????????????????
        QueryWrapper oldTmQuery = new QueryWrapper();
        oldTmQuery.eq("kssj_id", updateKssjRequest.getId());
        List<SjTmGl> oldSjTmGls = sjTmGlMapper.selectList(oldTmQuery);

        List<Long> oldTmIds = oldSjTmGls.stream().map(SjTmGl::getTmId).collect(Collectors.toList());

        //??????????????????
        List<Long> tmIds = updateKssjRequest.getTmIds();
        List<Long> addTmIds = Lists.newArrayList();

        for (Long tmId : tmIds) {

            if (oldTmIds.contains(tmId)) {
                oldTmIds.remove(tmId);
            } else {
                addTmIds.add(tmId);
            }
        }

        //????????????
        oldTmIds.forEach(oldTmId -> {

            QueryWrapper deleteQuery = new QueryWrapper();
            deleteQuery.eq("tm_id", oldTmId);
            deleteQuery.eq("kssj_id", updateKssjRequest.getId());

            sjTmGlMapper.delete(deleteQuery);
        });

        //????????????
        addTmIds.forEach(addTmId -> {

            SjTmGl sjTmGl = new SjTmGl();
            sjTmGl.setKssjId(updateKssjRequest.getId());
            sjTmGl.setTmId(addTmId);

            sjTmGlMapper.insert(sjTmGl);
        });

        return kssj;
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

    /**
     * ????????????????????????
     */
    public Kssj randomTest(int zf) {

        return null;
    }
}
