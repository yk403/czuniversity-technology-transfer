package com.itts.personTraining.service.kssj.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.personTraining.enums.TkzyTypeEnum;
import com.itts.personTraining.mapper.kc.KcMapper;
import com.itts.personTraining.mapper.kssj.KssjMapper;
import com.itts.personTraining.mapper.kssj.SjTmGlMapper;
import com.itts.personTraining.mapper.tkzy.TkzyMapper;
import com.itts.personTraining.mapper.tkzy.TmxxMapper;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.kssj.Kssj;
import com.itts.personTraining.model.kssj.SjTmGl;
import com.itts.personTraining.model.tkzy.Tkzy;
import com.itts.personTraining.model.tkzy.Tmxx;
import com.itts.personTraining.request.kssj.AddKssjRequest;
import com.itts.personTraining.request.kssj.UpdateKssjRequest;
import com.itts.personTraining.service.kssj.KssjService;
import com.itts.personTraining.vo.kssj.GetKssjVO;
import com.itts.personTraining.vo.tkzy.GetTkzyVO;
import com.itts.personTraining.vo.tkzy.GetTmxxVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    /**
     * 获取试卷详情
     */
    @Override
    public GetKssjVO get(Long id) {

        //获取试卷详情
        QueryWrapper kssjQuery = new QueryWrapper();
        kssjQuery.eq("id", id);
        kssjQuery.eq("sfsc", false);

        Kssj kssj = kssjMapper.selectOne(query());

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
     * 新增
     */
    @Override
    public Kssj add(AddKssjRequest addKssjRequest) {

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

        List<Tkzy> tms = tkzyMapper.selectList(query);
        if (!CollectionUtils.isEmpty(tms)) {

            long totalCount = tms.stream().map(Tkzy::getFz).count();
            kssj.setSjzf(Integer.valueOf((int) totalCount));
        }

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

        BeanUtils.copyProperties(updateKssjRequest, kssj);

        kssj.setTmzs(updateKssjRequest.getTmIds().size());
        kssj.setGxr(userId);
        kssj.setGxsj(new Date());

        //获取当前试卷题目总分
        QueryWrapper query = new QueryWrapper();
        query.in(updateKssjRequest.getTmIds());

        List<Tkzy> tms = tkzyMapper.selectList(query);
        long totalCount = tms.stream().map(Tkzy::getFz).count();
        kssj.setSjzf(Integer.valueOf((int) totalCount));

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
}
