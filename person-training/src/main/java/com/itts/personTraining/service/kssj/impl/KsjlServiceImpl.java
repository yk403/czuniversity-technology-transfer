package com.itts.personTraining.service.kssj.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itts.common.bean.LoginUser;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.XsMsgDTO;
import com.itts.personTraining.enums.EduTypeEnum;
import com.itts.personTraining.enums.KsjlXxbhEnum;
import com.itts.personTraining.enums.TkzyTypeEnum;
import com.itts.personTraining.mapper.kssj.KsjlMapper;
import com.itts.personTraining.mapper.kssj.KsjlxxMapper;
import com.itts.personTraining.mapper.kssj.KssjMapper;
import com.itts.personTraining.mapper.pc.PcMapper;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.mapper.tkzy.TkzyMapper;
import com.itts.personTraining.mapper.tkzy.TmxxMapper;
import com.itts.personTraining.mapper.tzXs.TzXsMapper;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.mapper.xsCj.XsCjMapper;
import com.itts.personTraining.model.kssj.Ksjl;
import com.itts.personTraining.model.kssj.Ksjlxx;
import com.itts.personTraining.model.kssj.Kssj;
import com.itts.personTraining.model.tkzy.Tkzy;
import com.itts.personTraining.model.tkzy.Tmxx;
import com.itts.personTraining.model.tz.Tz;
import com.itts.personTraining.model.tzXs.TzXs;
import com.itts.personTraining.model.xsCj.XsCj;
import com.itts.personTraining.request.kssj.CommitKsjlRequest;
import com.itts.personTraining.request.kssj.CommitKsjlXxRequest;
import com.itts.personTraining.service.kssj.KsjlService;
import com.itts.personTraining.service.sjpz.SjpzService;
import com.itts.personTraining.vo.kssj.GetKsjlTmVO;
import com.itts.personTraining.vo.kssj.GetKsjlTmXxVO;
import com.itts.personTraining.vo.kssj.GetKsjlVO;
import com.itts.personTraining.vo.sjpz.SjpzVO;
import com.itts.personTraining.vo.sjpz.SjtxpzVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * ???????????? ???????????????
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-27
 */
@Service
public class KsjlServiceImpl extends ServiceImpl<KsjlMapper, Ksjl> implements KsjlService {

    @Autowired
    private KsjlMapper ksjlMapper;

    @Autowired
    private TkzyMapper tkzyMapper;

    @Autowired
    private TmxxMapper tmxxMapper;

    @Autowired
    private KsjlxxMapper ksjlxxMapper;

    @Autowired
    private PcXsMapper pcXsMapper;

    @Autowired
    private KssjMapper kssjMapper;

    @Autowired
    private PcMapper pcMapper;

    @Autowired
    private XsCjMapper xsCjMapper;

    @Autowired
    private XsMapper xsMapper;

    @Autowired
    private TzXsMapper tzXsMapper;
    @Resource
    private SjpzService sjpzService;

    /**
     * ????????????
     */
    @Override
    public GetKsjlVO get(Long id) {

        Ksjl ksjl = ksjlMapper.selectById(id);
        if (ksjl == null) {
            return null;
        }

        GetKsjlVO vo = new GetKsjlVO();
        BeanUtils.copyProperties(ksjl, vo);

        //??????????????????????????????????????????
        List<Tkzy> tkzys = tkzyMapper.findBySjId(ksjl.getSjId());
        if (CollectionUtils.isEmpty(tkzys)) {
            return vo;
        }

        List<Long> tkzyIds = tkzys.stream().map(Tkzy::getId).collect(Collectors.toList());

        //??????????????????????????????
        List<Tmxx> tmxxs = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(tkzyIds)) {

            tmxxs = tmxxMapper.selectList(new QueryWrapper<Tmxx>().in("tm_id", tkzyIds));
        }

        //????????????????????????ID??????
        Map<Long, List<Tmxx>> tmxxMap = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(tmxxs)) {
            tmxxMap = tmxxs.stream().collect(Collectors.groupingBy(Tmxx::getTmId));
        }

        //??????????????????????????????VO
        Map<Long, List<Tmxx>> finalTmxxMap = tmxxMap;
        List<GetKsjlTmVO> tmVOs = tkzys.stream().map(obj -> {

            GetKsjlTmVO tmVO = new GetKsjlTmVO();
            BeanUtils.copyProperties(obj, tmVO);

            //???????????????????????????????????????ID?????????????????????????????????
            if (!CollectionUtils.isEmpty(finalTmxxMap)) {

                List<Tmxx> thisTmxxs = finalTmxxMap.get(obj.getId());
                if (!CollectionUtils.isEmpty(thisTmxxs)) {

                    List<GetKsjlTmXxVO> thisTmxxVOs = thisTmxxs.stream().map(thisTmxx -> {

                        GetKsjlTmXxVO tmxxVO = new GetKsjlTmXxVO();
                        BeanUtils.copyProperties(thisTmxx, tmxxVO);
                        return tmxxVO;
                    }).collect(Collectors.toList());

                    tmVO.setKsjlTmXxs(thisTmxxVOs);
                }
            }

            return tmVO;
        }).collect(Collectors.toList());

        vo.setKsjlTms(tmVOs);

        return vo;
    }

    /**
     * ????????????
     */
    @Override
    @Transactional
    public GetKsjlVO add(Kssj kssj, Tz tz, LoginUser loginUser) {

        XsMsgDTO xs = xsMapper.getByYhId(loginUser.getUserId());
        if (xs == null) {
            return null;
        }

        //????????????ID?????????ID??????????????????
        Ksjl checkKsjl = ksjlMapper.selectOne(new QueryWrapper<Ksjl>()
                .eq("sj_id", kssj.getId())
                .eq("xs_id", xs.getId()));
        if (checkKsjl != null) {

            return getAlreadyKsjl(checkKsjl);
        }

        TzXs tzxs = tzXsMapper.selectOne(new QueryWrapper<TzXs>()
                .eq("tz_id", tz.getId())
                .eq("xs_id", xs.getId())
                .eq("clzt", 0));

        if (tzxs == null) {
            return null;
        }

        tzxs.setClzt(true);
        tzXsMapper.updateById(tzxs);

        Ksjl ksjl = new Ksjl();

        ksjl.setSjlx(kssj.getSjlx());
        ksjl.setTmzs(kssj.getTmzs());
        ksjl.setSjzf(kssj.getSjzf());
        ksjl.setPdzf(kssj.getPdzf());
        ksjl.setDanxzf(kssj.getDanzf());
        ksjl.setDuoxzf(kssj.getDuozf());

        ksjl.setXsId(xs.getId());
        ksjl.setXsmc(loginUser.getRealName());
        ksjl.setXsbm("");

        ksjl.setSjId(kssj.getId());
        ksjl.setSjmc(kssj.getSjmc());

        Date now = new Date();

        ksjl.setKsdtsj(now);
        ksjl.setCjsj(now);

        GetKsjlVO getKsjlVO = getKsjlVO(ksjl, kssj);

        getKsjlVO.setZt(false);
        return getKsjlVO;
    }

    /**
     * ??????????????????VO
     */
    private GetKsjlVO getKsjlVO(Ksjl ksjl, Kssj kssj) {

        GetKsjlVO getKsjlVO = new GetKsjlVO();
        BeanUtils.copyProperties(ksjl, getKsjlVO);
        getKsjlVO.setCjsj(ksjl.getCjsj().getTime());

        Long sjpzId = kssj.getSjpzId();


        //???????????????????????????
        List<Tkzy> tms = tkzyMapper.findBySjId(kssj.getId());
        //????????????ID
        List<Long> tmIds = tms.stream().map(Tkzy::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(tmIds)) {
            return null;
        }

        //????????????????????????
        List<Tmxx> allTmxxs = tmxxMapper.selectList(new QueryWrapper<Tmxx>().in("tm_id", tmIds));

        ksjlMapper.insert(ksjl);

        //????????????ID?????????map
        Map<Long, List<Tmxx>> allTmxxMap = Maps.newHashMap();

        if (!CollectionUtils.isEmpty(allTmxxs)) {
            //????????????ID?????????map
            allTmxxMap = allTmxxs.stream().collect(Collectors.groupingBy(Tmxx::getTmId));
        }

        List<GetKsjlTmVO> ksjlTms = Lists.newArrayList();

        SjtxpzVO judge = null;
        SjtxpzVO single = null;
        SjtxpzVO multiple = null;
        if(sjpzId !=null){
            SjpzVO sjpzVO = sjpzService.get(sjpzId);
            judge = sjpzVO.getJudge();
            single = sjpzVO.getSingle();
            multiple = sjpzVO.getMultiple();
            getKsjlVO.setJudgepx(judge.getPx());
            getKsjlVO.setSinglepx(single.getPx());
            getKsjlVO.setMultiplepx(multiple.getPx());
        }

        //?????????????????????????????????????????????
        Iterator<Tkzy> tmIterator = tms.iterator();
        while (tmIterator.hasNext()) {

            Tkzy tm = tmIterator.next();
            //?????????????????????????????????????????????
            if(sjpzId != null){

                if(Objects.equals(tm.getTmlx(),TkzyTypeEnum.JUDGMENT.getKey())){
                    tm.setFz(judge.getDtfz());
                }else if(Objects.equals(tm.getTmlx(),TkzyTypeEnum.SINGLE_CHOICE.getKey())){
                    tm.setFz(single.getDtfz());
                }else if(Objects.equals(tm.getTmlx(),TkzyTypeEnum.MULTIPLE_CHOICE.getKey())){
                    tm.setFz(multiple.getDtfz());
                }
            }
            GetKsjlTmVO getKsjlTmVO = new GetKsjlTmVO();
            BeanUtils.copyProperties(tm, getKsjlTmVO);

            if (tm != null) {

                //??????????????????????????????
                if (Objects.equals(tm.getTmlx(), TkzyTypeEnum.JUDGMENT.getKey())) {

                    setJudgmentOptions(tm, ksjl, getKsjlTmVO);
                } else {

                    if (!CollectionUtils.isEmpty(allTmxxMap)) {
                        setSelectOptions(tm, allTmxxMap, ksjl, getKsjlTmVO);
                    }
                }

                tmIterator.remove();
            }

            ksjlTms.add(getKsjlTmVO);
        }

        getKsjlVO.setKsjlTms(ksjlTms);
        getKsjlVO.setId(ksjl.getId());

        return getKsjlVO;
    }

    /**
     * ?????????????????????????????????
     */
    public GetKsjlVO getAlreadyKsjl(Ksjl ksjl) {

        GetKsjlVO getKsjlVO = new GetKsjlVO();
        BeanUtils.copyProperties(ksjl, getKsjlVO);
        getKsjlVO.setCjsj(ksjl.getCjsj().getTime());

        getKsjlVO.setZt(true);
        Kssj kssj = kssjMapper.selectOne(new QueryWrapper<Kssj>().eq("id", ksjl.getSjId()).eq("sfsc", false));
        Long sjpzId = kssj.getSjpzId();
        //??????????????????????????????????????????
        List<Ksjlxx> ksjlxxs = ksjlxxMapper.selectList(new QueryWrapper<Ksjlxx>().eq("ksjl_id", ksjl.getId()));
        if (CollectionUtils.isEmpty(ksjlxxs)) {
            return getKsjlVO;
        }

        //????????????ID????????????
        Map<Long, List<Ksjlxx>> ksjlxxMap = ksjlxxs.stream().collect(Collectors.groupingBy(Ksjlxx::getTmId));

        Set<Long> tmIds = ksjlxxs.stream().map(Ksjlxx::getTmId).collect(Collectors.toSet());
        //????????????????????????????????????
        List<Tkzy> tms = tkzyMapper.selectList(new QueryWrapper<Tkzy>().in("id", tmIds));
        if (CollectionUtils.isEmpty(tms)) {
            return getKsjlVO;
        }


        List<GetKsjlTmVO> tmVOs = tms.stream().map(obj -> {

            //?????????????????????????????????????????????
            if(sjpzId != null){
                SjpzVO sjpzVO = sjpzService.get(sjpzId);
                SjtxpzVO judge = sjpzVO.getJudge();
                SjtxpzVO single = sjpzVO.getSingle();
                SjtxpzVO multiple = sjpzVO.getMultiple();
                if(Objects.equals(obj.getTmlx(),TkzyTypeEnum.JUDGMENT.getKey())){
                    obj.setFz(judge.getDtfz());
                }else if(Objects.equals(obj.getTmlx(),TkzyTypeEnum.SINGLE_CHOICE.getKey())){
                    obj.setFz(single.getDtfz());
                }else if(Objects.equals(obj.getTmlx(),TkzyTypeEnum.MULTIPLE_CHOICE.getKey())){
                    obj.setFz(multiple.getDtfz());
                }
            }

            GetKsjlTmVO getKsjlTmVO = new GetKsjlTmVO();
            BeanUtils.copyProperties(obj, getKsjlTmVO);

            List<Ksjlxx> thisKsjlxxs = ksjlxxMap.get(obj.getId());
            if (!CollectionUtils.isEmpty(thisKsjlxxs)) {

                List<GetKsjlTmXxVO> ksjlxxVOs = thisKsjlxxs.stream().map(xxObj -> {

                    GetKsjlTmXxVO getKsjlTmXxVO = new GetKsjlTmXxVO();
                    BeanUtils.copyProperties(xxObj, getKsjlTmXxVO);
                    return getKsjlTmXxVO;
                }).collect(Collectors.toList());

                getKsjlTmVO.setKsjlTmXxs(ksjlxxVOs);
            }

            return getKsjlTmVO;
        }).collect(Collectors.toList());

        getKsjlVO.setKsjlTms(tmVOs);

        return getKsjlVO;
    }


    /**
     * ????????????
     */
    @Override
    public ResponseUtil commit(CommitKsjlRequest commitKsjlRequest, LoginUser loginUser) {

        //??????????????????????????????
        Ksjl ksjl = ksjlMapper.selectById(commitKsjlRequest.getId());
        if (ksjl == null) {
            return ResponseUtil.error(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        Kssj kssj2 = kssjMapper.selectOne(new QueryWrapper<Kssj>().eq("id", ksjl.getSjId()).eq("sfsc", false));
        Long sjpzId = kssj2.getSjpzId();
        //??????????????????????????????
        List<Tkzy> tms = tkzyMapper.findBySjId(ksjl.getSjId());
        if (CollectionUtils.isEmpty(tms)) {
            return ResponseUtil.error(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        if (sjpzId != null) {
            SjpzVO sjpzVO = sjpzService.get(sjpzId);
            SjtxpzVO judge = sjpzVO.getJudge();
            SjtxpzVO single = sjpzVO.getSingle();
            SjtxpzVO multiple = sjpzVO.getMultiple();
            for (Tkzy tm : tms) {
                //?????????????????????????????????????????????
                if(Objects.equals(tm.getTmlx(),TkzyTypeEnum.JUDGMENT.getKey())){
                    tm.setFz(judge.getDtfz());
                }else if(Objects.equals(tm.getTmlx(),TkzyTypeEnum.SINGLE_CHOICE.getKey())){
                    tm.setFz(single.getDtfz());
                }else if(Objects.equals(tm.getTmlx(),TkzyTypeEnum.MULTIPLE_CHOICE.getKey())){
                    tm.setFz(multiple.getDtfz());
                }
            }
        }

        Map<Long, Tkzy> tmMap = tms.stream().collect(Collectors.toMap(Tkzy::getId, Function.identity()));

        ksjl.setJsdtsj(new Date());

        //????????????????????????????????????????????????
        List<Ksjlxx> ksjlxxs = ksjlxxMapper.selectList(new QueryWrapper<Ksjlxx>().eq("ksjl_id", ksjl.getId()));
        if (CollectionUtils.isEmpty(ksjlxxs)) {
            return ResponseUtil.error(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        Map<Long, List<Ksjlxx>> ksjlxxMap = ksjlxxs.stream().collect(Collectors.groupingBy(Ksjlxx::getTmId));

        //???????????????????????????????????????
        List<CommitKsjlXxRequest> commitKsjlxxs = commitKsjlRequest.getKsjlxxs();
        Map<Long, List<CommitKsjlXxRequest>> commitKsjlxxMap = commitKsjlxxs.stream().collect(Collectors.groupingBy(CommitKsjlXxRequest::getTmId));

        AtomicInteger totalScore = new AtomicInteger();
        AtomicInteger pdScore = new AtomicInteger();
        AtomicInteger duoxScore = new AtomicInteger();
        AtomicInteger danxScore = new AtomicInteger();

        ksjlxxMap.forEach((k, v) -> {

            //??????ID????????????????????????
            Map<Long, Ksjlxx> vMap = v.stream().collect(Collectors.toMap(Ksjlxx::getId, Function.identity()));

            //???????????????????????????????????????
            List<Ksjlxx> zqList = v.stream().filter(Ksjlxx::getSfzqda).collect(Collectors.toList());

            //??????????????????????????????????????????
            List<CommitKsjlXxRequest> xzList = commitKsjlxxMap.get(k).stream().filter(CommitKsjlXxRequest::getSfxz).collect(Collectors.toList());

            //?????????????????????
            xzList.forEach(xz -> {

                Ksjlxx ksjlxx = vMap.get(xz.getId());
                if (ksjlxx != null) {

                    ksjlxx.setSfxz(true);
                    ksjlxxMapper.updateById(ksjlxx);
                }
            });

            //???????????????????????????????????????????????????
            if (zqList.size() < xzList.size()) {
                return;
            }

            //??????????????????????????? ???????????????????????????
            List<Long> zqIdList = zqList.stream().map(Ksjlxx::getId).collect(Collectors.toList());
            List<Long> xzIdList = xzList.stream().map(CommitKsjlXxRequest::getId).collect(Collectors.toList());

            //????????????????????????????????????????????????????????????????????????????????????
            if (zqIdList.size() == xzIdList.size()) {

                xzIdList.stream().forEach(xzId -> {

                    if (zqIdList.contains(xzId)) {
                        zqIdList.remove(xzId);
                    }
                });

                if (CollectionUtils.isEmpty(zqIdList)) {

                    //????????????????????????
                    totalScore.addAndGet(tmMap.get(k).getFz());

                    //????????????????????????
                    if (Objects.equals(tmMap.get(k).getTmlx(), TkzyTypeEnum.JUDGMENT.getKey())) {

                        pdScore.addAndGet(tmMap.get(k).getFz());
                    }

                    //????????????????????????
                    if (Objects.equals(tmMap.get(k).getTmlx(), TkzyTypeEnum.SINGLE_CHOICE.getKey())) {

                        danxScore.addAndGet(tmMap.get(k).getFz());
                    }

                    //????????????????????????
                    if (Objects.equals(tmMap.get(k).getTmlx(), TkzyTypeEnum.MULTIPLE_CHOICE.getKey())) {

                        duoxScore.addAndGet(tmMap.get(k).getFz());
                    }
                }
            }

            //?????????????????????????????????????????????????????????????????????????????????????????????????????????
            if (zqIdList.size() > xzIdList.size()) {

                zqIdList.stream().forEach(zqId -> {

                    if (xzIdList.contains(zqId)) {
                        xzIdList.remove(zqId);
                    }
                });

                if (CollectionUtils.isEmpty(xzIdList)) {

                    totalScore.addAndGet(tmMap.get(k).getFz() / 2);

                    //????????????????????????
                    if (Objects.equals(tmMap.get(k).getTmlx(), TkzyTypeEnum.JUDGMENT.getKey())) {

                        pdScore.addAndGet(tmMap.get(k).getFz());
                    }

                    //????????????????????????
                    if (Objects.equals(tmMap.get(k).getTmlx(), TkzyTypeEnum.SINGLE_CHOICE.getKey())) {

                        danxScore.addAndGet(tmMap.get(k).getFz());
                    }

                    //????????????????????????
                    if (Objects.equals(tmMap.get(k).getTmlx(), TkzyTypeEnum.MULTIPLE_CHOICE.getKey())) {

                        duoxScore.addAndGet(tmMap.get(k).getFz());
                    }
                }
            }
        });

        ksjl.setZzcj(totalScore.get());
        ksjl.setPddf(pdScore.get());
        ksjl.setDanxdf(danxScore.get());
        ksjl.setDuoxdf(duoxScore.get());

        ksjlMapper.updateById(ksjl);

        Kssj kssj = kssjMapper.selectById(ksjl.getSjId());
        if (kssj == null) {
            return ResponseUtil.success(totalScore.get());
        }

        XsMsgDTO xs = xsMapper.getByYhId(loginUser.getUserId());
        if (xs == null) {
            return ResponseUtil.error(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        //????????????ID
        Long pcId = pcXsMapper.findByXsIdAndXslx(xs.getId(), kssj.getXylx());

        XsCj xsCj = new XsCj();
        xsCj.setPcId(pcId);
        xsCj.setXsId(xs.getId());
        xsCj.setType(Objects.equals(kssj.getJylx(), EduTypeEnum.ACADEMIC_DEGREE_EDUCATION.getKey()) ? 1 : 2);
        xsCj.setZhcj(totalScore.get() + "");

        xsCj.setCjr(loginUser.getUserId());
        xsCj.setGxr(loginUser.getUserId());

        Date now = new Date();
        xsCj.setCjsj(now);
        xsCj.setGxsj(now);

        //??????????????????
        xsCjMapper.insert(xsCj);

        return ResponseUtil.success(totalScore.get());
    }

    /**
     * ?????????????????????
     */
    private void setJudgmentOptions(Tkzy tm, Ksjl ksjl, GetKsjlTmVO getKsjlTmVO) {

        List<GetKsjlTmXxVO> ksjlTmXxs = Lists.newArrayList();

        GetKsjlTmXxVO zqVo = new GetKsjlTmXxVO();
        GetKsjlTmXxVO cwVo = new GetKsjlTmXxVO();

        //??????
        Ksjlxx zqxx = new Ksjlxx();
        zqxx.setTmId(tm.getId());
        zqxx.setKsjlId(ksjl.getId());
        zqxx.setXxnr("??????");

        //??????
        Ksjlxx cwxx = new Ksjlxx();
        cwxx.setTmId(tm.getId());
        cwxx.setKsjlId(ksjl.getId());
        cwxx.setXxnr("??????");

        if (tm.getSfzqda()) {
            zqxx.setSfzqda(true);
            cwxx.setSfzqda(false);
        } else {
            zqxx.setSfzqda(false);
            cwxx.setSfzqda(true);
        }

        ksjlxxMapper.insert(zqxx);
        ksjlxxMapper.insert(cwxx);

        BeanUtils.copyProperties(zqxx, zqVo);
        BeanUtils.copyProperties(cwxx, cwVo);
        ksjlTmXxs.add(zqVo);
        ksjlTmXxs.add(cwVo);

        getKsjlTmVO.setKsjlTmXxs(ksjlTmXxs);
    }

    /**
     * ?????????????????????
     */
    private void setSelectOptions(Tkzy tm, Map<Long, List<Tmxx>> allTmxxMap, Ksjl ksjl, GetKsjlTmVO getKsjlTmVO) {

        List<Tmxx> tmxxs = allTmxxMap.get(tm.getId());
        List<GetKsjlTmXxVO> ksjlTmXxs = Lists.newArrayList();

        for (int i = 0; i < tmxxs.size(); i++) {

            Ksjlxx ksjlxx = new Ksjlxx();
            ksjlxx.setKsjlId(ksjl.getId());
            ksjlxx.setTmId(tm.getId());
            int num = i + 1;
            ksjlxx.setXxbh(KsjlXxbhEnum.getByKey(num).getValue());
            ksjlxx.setXxnr(tmxxs.get(i).getXxnr());
            ksjlxx.setSfzqda(tmxxs.get(i).getSfzqda());

            ksjlxxMapper.insert(ksjlxx);

            GetKsjlTmXxVO getKsjlTmXxVO = new GetKsjlTmXxVO();
            BeanUtils.copyProperties(ksjlxx, getKsjlTmXxVO);

            ksjlTmXxs.add(getKsjlTmXxVO);
        }

        getKsjlTmVO.setKsjlTmXxs(ksjlTmXxs);
    }
}
