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
import com.itts.personTraining.vo.kssj.GetKsjlTmVO;
import com.itts.personTraining.vo.kssj.GetKsjlTmXxVO;
import com.itts.personTraining.vo.kssj.GetKsjlVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 考试记录 服务实现类
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

    /**
     * 获取详情
     */
    @Override
    public GetKsjlVO get(Long id) {

        Ksjl ksjl = ksjlMapper.selectById(id);
        if (ksjl == null) {
            return null;
        }

        GetKsjlVO vo = new GetKsjlVO();
        BeanUtils.copyProperties(ksjl, vo);

        //获取当前考试记录所有题目信息
        List<Tkzy> tkzys = tkzyMapper.findBySjId(ksjl.getSjId());
        if (CollectionUtils.isEmpty(tkzys)) {
            return vo;
        }

        List<Long> tkzyIds = tkzys.stream().map(Tkzy::getId).collect(Collectors.toList());

        //获取试卷题目所有选项
        List<Tmxx> tmxxs = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(tkzyIds)) {

            tmxxs = tmxxMapper.selectList(new QueryWrapper<Tmxx>().in("tm_id", tkzyIds));
        }

        //题目选项根据题目ID分组
        Map<Long, List<Tmxx>> tmxxMap = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(tmxxs)) {
            tmxxMap = tmxxs.stream().collect(Collectors.groupingBy(Tmxx::getTmId));
        }

        //循环便利所有题目并转VO
        Map<Long, List<Tmxx>> finalTmxxMap = tmxxMap;
        List<GetKsjlTmVO> tmVOs = tkzys.stream().map(obj -> {

            GetKsjlTmVO tmVO = new GetKsjlTmVO();
            BeanUtils.copyProperties(obj, tmVO);

            //循环便利所有选项，通过题目ID将选项设置到对应题目中
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
     * 生成试卷
     */
    @Override
    @Transactional
    public GetKsjlVO add(Kssj kssj, Tz tz, LoginUser loginUser) {

        XsMsgDTO xs = xsMapper.getByYhId(loginUser.getUserId());
        if (xs == null) {
            return null;
        }

        //通过试卷ID和学生ID获取考试记录
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

        return getKsjlVO;
    }

    /**
     * 生成考试试卷VO
     */
    private GetKsjlVO getKsjlVO(Ksjl ksjl, Kssj kssj) {

        GetKsjlVO getKsjlVO = new GetKsjlVO();
        BeanUtils.copyProperties(ksjl, getKsjlVO);
        getKsjlVO.setCjsj(ksjl.getCjsj().getTime());

        //获取当前试卷的题目
        List<Tkzy> tms = tkzyMapper.findBySjId(kssj.getId());
        //获取题目ID
        List<Long> tmIds = tms.stream().map(Tkzy::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(tmIds)) {
            return null;
        }

        //查询所有题目选项
        List<Tmxx> allTmxxs = tmxxMapper.selectList(new QueryWrapper<Tmxx>().in("tm_id", tmIds));

        ksjlMapper.insert(ksjl);

        //根据题目ID分组成map
        Map<Long, List<Tmxx>> allTmxxMap = Maps.newHashMap();

        if (!CollectionUtils.isEmpty(allTmxxs)) {
            //根据题目ID分组成map
            allTmxxMap = allTmxxs.stream().collect(Collectors.groupingBy(Tmxx::getTmId));
        }

        List<GetKsjlTmVO> ksjlTms = Lists.newArrayList();

        //便利所有题目，生成试卷记录选项
        Iterator<Tkzy> tmIterator = tms.iterator();
        while (tmIterator.hasNext()) {

            Tkzy tm = tmIterator.next();

            GetKsjlTmVO getKsjlTmVO = new GetKsjlTmVO();
            BeanUtils.copyProperties(tm, getKsjlTmVO);

            if (tm != null) {

                //判断题目是否为判断题
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
     * 获取已生成考试记录信息
     */
    public GetKsjlVO getAlreadyKsjl(Ksjl ksjl) {

        GetKsjlVO getKsjlVO = new GetKsjlVO();
        BeanUtils.copyProperties(ksjl, getKsjlVO);
        getKsjlVO.setCjsj(ksjl.getCjsj().getTime());

        //获取当前考试记录所有考试选项
        List<Ksjlxx> ksjlxxs = ksjlxxMapper.selectList(new QueryWrapper<Ksjlxx>().eq("ksjl_id", ksjl.getId()));
        if (CollectionUtils.isEmpty(ksjlxxs)) {
            return getKsjlVO;
        }

        //通过题目ID进行分组
        Map<Long, List<Ksjlxx>> ksjlxxMap = ksjlxxs.stream().collect(Collectors.groupingBy(Ksjlxx::getTmId));

        Set<Long> tmIds = ksjlxxs.stream().map(Ksjlxx::getTmId).collect(Collectors.toSet());
        //获取当前考试记录所有题目
        List<Tkzy> tms = tkzyMapper.selectList(new QueryWrapper<Tkzy>().in("id", tmIds));
        if (CollectionUtils.isEmpty(tms)) {
            return getKsjlVO;
        }

        List<GetKsjlTmVO> tmVOs = tms.stream().map(obj -> {

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
     * 提交试卷
     */
    @Override
    public ResponseUtil commit(CommitKsjlRequest commitKsjlRequest, LoginUser loginUser) {

        //查询当前试卷记录信息
        Ksjl ksjl = ksjlMapper.selectById(commitKsjlRequest.getId());
        if (ksjl == null) {
            return ResponseUtil.error(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        //获取当前试卷所有题目
        List<Tkzy> tms = tkzyMapper.findBySjId(ksjl.getSjId());
        if (CollectionUtils.isEmpty(tms)) {
            return ResponseUtil.error(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        Map<Long, Tkzy> tmMap = tms.stream().collect(Collectors.toMap(Tkzy::getId, Function.identity()));

        ksjl.setJsdtsj(new Date());

        //获取当前考试记录所有考试记录选项
        List<Ksjlxx> ksjlxxs = ksjlxxMapper.selectList(new QueryWrapper<Ksjlxx>().eq("ksjl_id", ksjl.getId()));
        if (CollectionUtils.isEmpty(ksjlxxs)) {
            return ResponseUtil.error(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        Map<Long, List<Ksjlxx>> ksjlxxMap = ksjlxxs.stream().collect(Collectors.groupingBy(Ksjlxx::getTmId));

        //获取用户提交的考试选项记录
        List<CommitKsjlXxRequest> commitKsjlxxs = commitKsjlRequest.getKsjlxxs();
        Map<Long, List<CommitKsjlXxRequest>> commitKsjlxxMap = commitKsjlxxs.stream().collect(Collectors.groupingBy(CommitKsjlXxRequest::getTmId));

        AtomicInteger totalScore = new AtomicInteger();
        AtomicInteger pdScore = new AtomicInteger();
        AtomicInteger duoxScore = new AtomicInteger();
        AtomicInteger danxScore = new AtomicInteger();

        ksjlxxMap.forEach((k, v) -> {

            //通过ID分组当前题目选项
            Map<Long, Ksjlxx> vMap = v.stream().collect(Collectors.toMap(Ksjlxx::getId, Function.identity()));

            //获取当前选项正确答案有几个
            List<Ksjlxx> zqList = v.stream().filter(Ksjlxx::getSfzqda).collect(Collectors.toList());

            //获取当前用户选项选中哪些答案
            List<CommitKsjlXxRequest> xzList = commitKsjlxxMap.get(k).stream().filter(CommitKsjlXxRequest::getSfxz).collect(Collectors.toList());

            //设置选中的选项
            xzList.forEach(xz -> {

                Ksjlxx ksjlxx = vMap.get(xz.getId());
                if (ksjlxx != null) {

                    ksjlxx.setSfxz(true);
                    ksjlxxMapper.updateById(ksjlxx);
                }
            });

            //选中数量大于正确数量，直接判断错误
            if (zqList.size() < xzList.size()) {
                return;
            }

            //如果选中数量一致， 则判断是否选对选项
            List<Long> zqIdList = zqList.stream().map(Ksjlxx::getId).collect(Collectors.toList());
            List<Long> xzIdList = xzList.stream().map(CommitKsjlXxRequest::getId).collect(Collectors.toList());

            //如果选中数量等于正确答案数量，循环便利，判断是否全部正确
            if (zqIdList.size() == xzIdList.size()) {

                xzIdList.stream().forEach(xzId -> {

                    if (zqIdList.contains(xzId)) {
                        zqIdList.remove(xzId);
                    }
                });

                if (CollectionUtils.isEmpty(zqIdList)) {

                    //设置总分最终得分
                    totalScore.addAndGet(tmMap.get(k).getFz());

                    //设置判断最终得分
                    if (Objects.equals(tmMap.get(k).getTmlx(), TkzyTypeEnum.JUDGMENT.getKey())) {

                        pdScore.addAndGet(tmMap.get(k).getFz());
                    }

                    //设置单选最终得分
                    if (Objects.equals(tmMap.get(k).getTmlx(), TkzyTypeEnum.SINGLE_CHOICE.getKey())) {

                        danxScore.addAndGet(tmMap.get(k).getFz());
                    }

                    //设置多选最终得分
                    if (Objects.equals(tmMap.get(k).getTmlx(), TkzyTypeEnum.MULTIPLE_CHOICE.getKey())) {

                        duoxScore.addAndGet(tmMap.get(k).getFz());
                    }
                }
            }

            //如果选中的数量小于正确答案数量，漏选得一半分，多选或者有选错选项不得分
            if (zqIdList.size() > xzIdList.size()) {

                zqIdList.stream().forEach(zqId -> {

                    if (xzIdList.contains(zqId)) {
                        xzIdList.remove(zqId);
                    }
                });

                if (CollectionUtils.isEmpty(xzIdList)) {

                    totalScore.addAndGet(tmMap.get(k).getFz() / 2);

                    //设置判断最终得分
                    if (Objects.equals(tmMap.get(k).getTmlx(), TkzyTypeEnum.JUDGMENT.getKey())) {

                        pdScore.addAndGet(tmMap.get(k).getFz());
                    }

                    //设置单选最终得分
                    if (Objects.equals(tmMap.get(k).getTmlx(), TkzyTypeEnum.SINGLE_CHOICE.getKey())) {

                        danxScore.addAndGet(tmMap.get(k).getFz());
                    }

                    //设置多选最终得分
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

        //获取批次ID
        Long pcId = pcXsMapper.findByXsIdAndXslx(xs.getId(), kssj.getXylx());

        XsCj xsCj = new XsCj();
        xsCj.setPcId(pcId);
        xsCj.setXsId(loginUser.getUserId());
        xsCj.setType(Objects.equals(kssj.getJylx(), EduTypeEnum.ACADEMIC_DEGREE_EDUCATION.getKey()) ? 1 : 2);
        xsCj.setZhcj(totalScore.get() + "");

        xsCj.setCjr(loginUser.getUserId());
        xsCj.setGxr(loginUser.getUserId());

        Date now = new Date();
        xsCj.setCjsj(now);
        xsCj.setGxsj(now);

        //插入学生成绩
        xsCjMapper.insert(xsCj);

        return ResponseUtil.success(totalScore.get());
    }

    /**
     * 设置判断题选项
     */
    private void setJudgmentOptions(Tkzy tm, Ksjl ksjl, GetKsjlTmVO getKsjlTmVO) {

        List<GetKsjlTmXxVO> ksjlTmXxs = Lists.newArrayList();

        GetKsjlTmXxVO zqVo = new GetKsjlTmXxVO();
        GetKsjlTmXxVO cwVo = new GetKsjlTmXxVO();

        //正确
        Ksjlxx zqxx = new Ksjlxx();
        zqxx.setTmId(tm.getId());
        zqxx.setKsjlId(ksjl.getId());
        zqxx.setXxnr("正确");

        //错误
        Ksjlxx cwxx = new Ksjlxx();
        cwxx.setTmId(tm.getId());
        cwxx.setKsjlId(ksjl.getId());
        cwxx.setXxnr("错误");

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
     * 设置选择题选项
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
