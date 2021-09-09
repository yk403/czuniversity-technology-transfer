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
 * 考试试卷 服务实现类
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
     * 获取试卷详情
     */
    @Override
    public GetKssjVO get(Long id) {

        //获取试卷详情
        QueryWrapper kssjQuery = new QueryWrapper();
        kssjQuery.eq("id", id);
        kssjQuery.eq("sfsc", false);

        Kssj kssj = kssjMapper.selectOne(kssjQuery);

        if (kssj == null) {
            return null;
        }

        GetKssjVO vo = new GetKssjVO();
        BeanUtils.copyProperties(kssj, vo);

        //获取当前考试试卷的所有题目
        QueryWrapper sjTmGlQuery = new QueryWrapper();
        sjTmGlQuery.eq("kssj_id", id);
        List<SjTmGl> sjTmgls = sjTmGlMapper.selectList(sjTmGlQuery);

        if (CollectionUtils.isEmpty(sjTmgls)) {
            return vo;
        }

        //获取试卷所有题目ID
        List<Long> sjTmIds = sjTmgls.stream().map(SjTmGl::getTmId).collect(Collectors.toList());

        //获取所有题目信息
        QueryWrapper tmQuery = new QueryWrapper();
        tmQuery.in("id", sjTmIds);

        List<Tkzy> tms = tkzyMapper.selectList(tmQuery);

        if (CollectionUtils.isEmpty(tms)) {
            return vo;
        }

        List<GetTkzyVO> tkzyVOs = Lists.newArrayList();

        //循环获取题目选项
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
                getTmxxVO.setXxnr(KsjlXxbhEnum.getByKey(num).getValue()+"."+getTmxxVO.getXxnr());

            }

            tkzyVo.setTmxxs(tmxxVOs);
        }

        Map<String, List<GetTkzyVO>> tmMap = Maps.newHashMap();

        //将所有题目按照单选、多选、判断分组
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
     * 随机组卷添加
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
        String sjlb = randomKssjRequest.getSjlb();
        String sjlx = randomKssjRequest.getSjlx();
        String xylx = randomKssjRequest.getXylx();
        Long sjpzId = randomKssjRequest.getSjpzId();
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
        //新增考试试卷
        Kssj kssj = new Kssj();
        kssj.setFjjgId(fjjgId);
        kssj.setSjmc(sjmc);
        kssj.setTmzs(sjpzVO.getSjts());
        kssj.setSjzf(sjpzVO.getSjzf());
        kssj.setPdzf(judge.getTxzf());
        kssj.setDanzf(single.getTxzf());
        kssj.setDuozf(multiple.getTxzf());
        kssj.setJylx(jylx);
        kssj.setSjlb(sjlb);
        kssj.setXylx(xylx);
        kssj.setSjlx(sjlx);
        kssj.setLx("random");
        kssj.setSjpzId(sjpzVO.getId());
        kssj.setCjr(userId);
        kssj.setGxr(userId);
        kssj.setCjsj(now);
        kssj.setGxsj(now);
        kssjMapper.insert(kssj);
        Long id = getBySjmc(sjmc).getId();

        //新增试卷题目关联
        List<Long> kcIdList = null;
        if(Objects.equals(sjlx,"single_subject")){
            kcIdList = randomKssjRequest.getKcIdList();
        }else if(Objects.equals(sjlx,"comprehensive")){
            List<Kc> kcs = kcMapper.selectList(new QueryWrapper<Kc>().eq("fjjg_id", fjjgId)
                    .eq("xylx", xylx)
                    .eq("sfsc", false));
            kcIdList = kcs.stream().map(Kc::getId).collect(Collectors.toList());
        }
        //判断题

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
        GetRandomKssjVO vo = new GetRandomKssjVO();
        BeanUtils.copyProperties(kssj, vo);
        //获取试卷配置
        SjpzVO sjpzVO = sjpzService.get(kssj.getSjpzId());

        Integer dtfz = sjpzVO.getJudge().getDtfz();
        Integer px = sjpzVO.getJudge().getPx();
        vo.setPddt(dtfz);

        Integer dtfz1 = sjpzVO.getSingle().getDtfz();
        Integer px1 = sjpzVO.getSingle().getPx();
        vo.setDandt(dtfz1);

        Integer dtfz2 = sjpzVO.getMultiple().getDtfz();
        Integer px2 = sjpzVO.getMultiple().getPx();
        vo.setDuodt(dtfz2);

        //获取当前考试试卷的所有题目
        List<SjTmGl> sjTmgls = sjTmGlMapper.selectList(new QueryWrapper<SjTmGl>().eq("kssj_id",id));
        if (CollectionUtils.isEmpty(sjTmgls)) {
            return vo;
        }
        List<GetTkzyVO> tkzyVOs = Lists.newArrayList();
        //获取试卷所有题目ID
        List<Long> sjTmIds = sjTmgls.stream().map(SjTmGl::getTmId).collect(Collectors.toList());
        //获取所有题目信息
        List<Tkzy> tms = tkzyMapper.selectList(new QueryWrapper<Tkzy>().in("id",sjTmIds));
        //循环获取题目选项
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
                getTmxxVO.setXxnr(KsjlXxbhEnum.getByKey(num).getValue()+"."+getTmxxVO.getXxnr());

            }
            tkzyVo.setTmxxs(tmxxVOs);
        }
        Map<String, List<GetTkzyVO>> judgmentMap = Maps.newHashMap();
        Map<String, List<GetTkzyVO>> singleChoiceMap = Maps.newHashMap();
        Map<String, List<GetTkzyVO>> multipleChoiceMap = Maps.newHashMap();
        Map<Integer,Map<String, List<GetTkzyVO>>> pxtmMap = Maps.newHashMap();
        //将所有题目按照单选、多选、判断分组
        List<GetTkzyVO> judgmentList = tkzyVOs.stream().filter(obj -> Objects.equals(obj.getTmlx(), TkzyTypeEnum.JUDGMENT.getKey())).collect(Collectors.toList());
        judgmentMap.put(TkzyTypeEnum.JUDGMENT.getKey(), judgmentList);
        pxtmMap.put(px,judgmentMap);

        List<GetTkzyVO> singleChoiceList = tkzyVOs.stream().filter(obj -> Objects.equals(obj.getTmlx(), TkzyTypeEnum.SINGLE_CHOICE.getKey())).collect(Collectors.toList());
        singleChoiceMap.put(TkzyTypeEnum.SINGLE_CHOICE.getKey(), singleChoiceList);
        pxtmMap.put(px1,singleChoiceMap);

        List<GetTkzyVO> multipleChoiceList = tkzyVOs.stream().filter(obj -> Objects.equals(obj.getTmlx(), TkzyTypeEnum.MULTIPLE_CHOICE.getKey())).collect(Collectors.toList());
        multipleChoiceMap.put(TkzyTypeEnum.MULTIPLE_CHOICE.getKey(), multipleChoiceList);
        pxtmMap.put(px2,multipleChoiceMap);

        vo.setTms(pxtmMap);
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
        //随机获取题目
        Collections.shuffle(by);
        List<Tkzy> tkzies = by.subList(0, sjtxndpz.getTs());
        if(tkzies.size() < sjtxndpz.getTs()){
            Kssj kssj = kssjMapper.selectOne(new QueryWrapper<Kssj>().eq("id", id).eq("sfsc", false));
            kssj.setSfsc(true);
            kssjMapper.updateById(kssj);
            throw new ServiceException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        //建立试卷题目关联
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
                .in("kc_id", kcIdList));
        return tkzies;
    }
    private Kssj getBySjmc(String sjmc){
        Kssj kssj = kssjMapper.selectOne(new QueryWrapper<Kssj>().eq("sjmc", sjmc).eq("sfsc", false));
        return kssj;
    }

    /**
     * 新增
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

        //获取课程信息
        Kc kc = kcMapper.selectById(addKssjRequest.getKcId());
        if (kc != null) {
            kssj.setKcMc(kc.getKcmc());
        }

        kssj.setTmzs(addKssjRequest.getTmIds().size());

        //获取试卷所有题目信息
        QueryWrapper query = new QueryWrapper();
        query.in("id", addKssjRequest.getTmIds());

        //获取当前用户父级机构ID
        kssj.setFjjgId(getFjjgId());
        kssjMapper.insert(kssj);

        //添加试卷题目关联
        for (Long tmId : addKssjRequest.getTmIds()) {

            SjTmGl sjTmGl = new SjTmGl();
            sjTmGl.setKssjId(kssj.getId());
            sjTmGl.setTmId(tmId);

            sjTmGlMapper.insert(sjTmGl);
        }

        return kssj;
    }

    /**
     * 更新
     */
    @Override
    public Kssj update(UpdateKssjRequest updateKssjRequest, Kssj kssj, Long userId) {
        Kssj bySjmc = getBySjmc(updateKssjRequest.getSjmc());
        if(bySjmc != null){
            throw new ServiceException(ErrorCodeEnum.NAME_EXIST_ERROR);
        }
        BeanUtils.copyProperties(updateKssjRequest, kssj);

        kssj.setTmzs(updateKssjRequest.getTmIds().size());
        kssj.setGxr(userId);
        kssj.setGxsj(new Date());

        kssjMapper.updateById(kssj);

        //获取试卷当前所有题目
        QueryWrapper oldTmQuery = new QueryWrapper();
        oldTmQuery.eq("kssj_id", updateKssjRequest.getId());
        List<SjTmGl> oldSjTmGls = sjTmGlMapper.selectList(oldTmQuery);

        List<Long> oldTmIds = oldSjTmGls.stream().map(SjTmGl::getTmId).collect(Collectors.toList());

        //更新试卷题目
        List<Long> tmIds = updateKssjRequest.getTmIds();
        List<Long> addTmIds = Lists.newArrayList();

        for (Long tmId : tmIds) {

            if (oldTmIds.contains(tmId)) {
                oldTmIds.remove(tmId);
            } else {
                addTmIds.add(tmId);
            }
        }

        //删除题目
        oldTmIds.forEach(oldTmId -> {

            QueryWrapper deleteQuery = new QueryWrapper();
            deleteQuery.eq("tm_id", oldTmId);
            deleteQuery.eq("kssj_id", updateKssjRequest.getId());

            sjTmGlMapper.delete(deleteQuery);
        });

        //添加题目
        addTmIds.forEach(addTmId -> {

            SjTmGl sjTmGl = new SjTmGl();
            sjTmGl.setKssjId(updateKssjRequest.getId());
            sjTmGl.setTmId(addTmId);

            sjTmGlMapper.insert(sjTmGl);
        });

        return kssj;
    }

    /**
     * 获取父级机构ID
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
     * 随机生成考试试卷
     */
    public Kssj randomTest(int zf) {

        return null;
    }
}
