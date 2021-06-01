package com.itts.personTraining.service.jjrpxjh.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.personTraining.mapper.jjrpxjh.JjrpxjhMapper;
import com.itts.personTraining.mapper.kc.KcMapper;
import com.itts.personTraining.mapper.pk.PkMapper;
import com.itts.personTraining.mapper.pkKc.PkKcMapper;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.mapper.xy.XyMapper;
import com.itts.personTraining.model.jjrpxjh.Jjrpxjh;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.pk.Pk;
import com.itts.personTraining.model.pkKc.PkKc;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.model.xy.Xy;
import com.itts.personTraining.request.jjrpxjh.AddJjrpxjhRequest;
import com.itts.personTraining.request.jjrpxjh.UpdateJjrpxjhRequest;
import com.itts.personTraining.service.jjrpxjh.JjrpxjhService;
import com.itts.personTraining.vo.jjrpxjh.GetJjrpxhKcVO;
import com.itts.personTraining.vo.jjrpxjh.GetJjrpxjhSzVO;
import com.itts.personTraining.vo.jjrpxjh.GetJjrpxjhVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 经纪人培训计划表 服务实现类
 * </p>
 *
 * @author liuyingming
 * @since 2021-06-01
 */
@Service
public class JjrpxjhServiceImpl extends ServiceImpl<JjrpxjhMapper, Jjrpxjh> implements JjrpxjhService {

    @Autowired
    private JjrpxjhMapper jjrpxjhMapper;

    @Autowired
    private PkMapper pkMapper;

    @Autowired
    private SzMapper szMapper;

    @Autowired
    private XyMapper xyMapper;

    @Autowired
    private PkKcMapper pkKcMapper;

    @Autowired
    private KcMapper kcMapper;

    /**
     * 获取详情
     */
    @Override
    public GetJjrpxjhVO get(Jjrpxjh old) {

        GetJjrpxjhVO vo = new GetJjrpxjhVO();
        BeanUtils.copyProperties(old, vo);

        //设置培训计划师资列表
        List<Pk> pks = pkMapper.selectList(new QueryWrapper<Pk>()
                .eq("sfsc", false)
                .eq("sfxf", true)
                .eq("pc_id", old.getPcId()));

        if (!CollectionUtils.isEmpty(pks)) {

            //获取当前批次的授课老师
            Set<Long> szIds = pks.stream().map(Pk::getSzId).collect(Collectors.toSet());

            //获取所有老师信息
            if (!CollectionUtils.isEmpty(szIds)) {

                List<GetJjrpxjhSzVO> szVOs = szMapper.selectList(new QueryWrapper<Sz>().in("id", szIds)).stream().map(obj -> {

                    GetJjrpxjhSzVO szVO = new GetJjrpxjhSzVO();
                    BeanUtils.copyProperties(obj, szVO);

                    Xy xy = xyMapper.selectById(obj.getXyId());

                    if (xy != null) {
                        szVO.setXyMc(xy.getXymc());
                    }

                    return szVO;
                }).collect(Collectors.toList());

                vo.setSzs(szVOs);
            }

            //设置培训计划课程列表
            List<Long> pkIds = pks.stream().map(Pk::getId).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(pkIds)){

                //获取所有课程ID
                List<Long> kcIds = pkKcMapper.selectList(new QueryWrapper<PkKc>().in("pk_id", pkIds)).stream().map(PkKc::getKcId).collect(Collectors.toList());

                if(!CollectionUtils.isEmpty(kcIds)){

                    List<GetJjrpxhKcVO> kcVOs = kcMapper.selectList(new QueryWrapper<Kc>().in("id", kcIds)).stream().map(obj -> {

                        GetJjrpxhKcVO kcVO = new GetJjrpxhKcVO();
                        BeanUtils.copyProperties(obj, kcVO);

                        return kcVO;
                    }).collect(Collectors.toList());

                    vo.setKcs(kcVOs);
                }
            }
        }

        return vo;
    }

    /**
     * 新增
     */
    @Override
    public Jjrpxjh add(AddJjrpxjhRequest addJjrpxjhRequest) {

        Jjrpxjh jjrpxjh = new Jjrpxjh();
        BeanUtils.copyProperties(addJjrpxjhRequest, jjrpxjh);

        Date now = new Date();

        LoginUser loginUser = SystemConstant.threadLocal.get();
        Long userId = null;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        jjrpxjh.setCjr(userId);
        jjrpxjh.setGxr(userId);
        jjrpxjh.setCjsj(now);
        jjrpxjh.setGxsj(now);

        jjrpxjhMapper.insert(jjrpxjh);
        return jjrpxjh;
    }

    /**
     * 更新
     */
    @Override
    public Jjrpxjh update(Jjrpxjh old, UpdateJjrpxjhRequest updateJjrpxjhRequest) {

        BeanUtils.copyProperties(updateJjrpxjhRequest, old, "id", "cjsj", "cjr", "sjsj", "sfsj", "sfsc", "bmrs");

        old.setGxsj(new Date());
        if (SystemConstant.threadLocal.get() != null) {
            old.setGxr(SystemConstant.threadLocal.get().getUserId());
        }

        jjrpxjhMapper.updateById(old);

        return old;
    }

    /**
     * 更新状态
     */
    @Override
    public Jjrpxjh updateStatus(Jjrpxjh old, Boolean sfsj) {

        Date now = new Date();

        old.setGxsj(now);
        old.setSjsj(now);
        old.setSfsj(sfsj);
        if (SystemConstant.threadLocal.get() != null) {
            old.setGxr(SystemConstant.threadLocal.get().getUserId());
        }

        jjrpxjhMapper.updateById(old);

        return old;
    }

    /**
     * 删除
     */
    @Override
    public void delete(Jjrpxjh old) {

        old.setGxsj(new Date());
        old.setSfsc(true);
        if (SystemConstant.threadLocal.get() != null) {
            old.setGxr(SystemConstant.threadLocal.get().getUserId());
        }

        jjrpxjhMapper.updateById(old);
    }
}
