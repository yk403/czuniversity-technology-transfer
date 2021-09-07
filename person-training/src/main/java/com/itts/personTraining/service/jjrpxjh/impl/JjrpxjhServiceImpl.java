package com.itts.personTraining.service.jjrpxjh.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.DateUtils;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.JjrbmInfo;
import com.itts.personTraining.dto.JjrpxjhDTO;
import com.itts.personTraining.dto.KcXsXfDTO;
import com.itts.personTraining.dto.StuDTO;
import com.itts.personTraining.enums.UserTypeEnum;
import com.itts.personTraining.mapper.jjrpxjh.JjrpxjhMapper;
import com.itts.personTraining.mapper.kc.KcMapper;
import com.itts.personTraining.mapper.pk.PkMapper;
import com.itts.personTraining.mapper.pkKc.PkKcMapper;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.mapper.xy.XyMapper;
import com.itts.personTraining.model.jjrpxjh.Jjrpxjh;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.pcXs.PcXs;
import com.itts.personTraining.model.pk.Pk;
import com.itts.personTraining.model.pkKc.PkKc;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.model.xs.Xs;
import com.itts.personTraining.model.xy.Xy;
import com.itts.personTraining.model.yh.Yh;
import com.itts.personTraining.request.jjrpxjh.AddJjrpxjhRequest;
import com.itts.personTraining.request.jjrpxjh.UpdateJjrpxjhRequest;
import com.itts.personTraining.service.jjrpxjh.JjrpxjhService;
import com.itts.personTraining.service.pc.PcService;
import com.itts.personTraining.service.pcXs.PcXsService;
import com.itts.personTraining.service.xs.XsService;
import com.itts.personTraining.service.yh.YhService;
import com.itts.personTraining.vo.jjrpxjh.GetJjrpxhKcVO;
import com.itts.personTraining.vo.jjrpxjh.GetJjrpxjhSzVO;
import com.itts.personTraining.vo.jjrpxjh.GetJjrpxjhVO;
import com.itts.personTraining.vo.yh.RpcAddYhRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.itts.common.enums.ErrorCodeEnum.USER_EXISTS_ERROR;
import static com.itts.common.enums.ErrorCodeEnum.USER_INSERT_ERROR;
import static com.itts.personTraining.enums.BmfsEnum.ON_LINE;
import static com.itts.personTraining.enums.UserTypeEnum.*;

/**
 * <p>
 * 经纪人培训计划表 服务实现类
 * </p>
 *
 * @author liuyingming
 * @since 2021-06-01
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class JjrpxjhServiceImpl extends ServiceImpl<JjrpxjhMapper, Jjrpxjh> implements JjrpxjhService {

    @Autowired
    private PcService pcService;
    @Autowired
    private YhService yhService;
    @Autowired
    private XsService xsService;
    @Autowired
    private PcXsService pcXsService;
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
    @Autowired
    private RedisTemplate redisTemplate;

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
                    szVO.setXyMc(obj.getYx());
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

    /**
     * 获取经纪人培训计划
     * @return
     */
    @Override
    public JjrpxjhDTO getJjrpxjh() {
        log.info("【人才培养 - 获取经纪人培训计划】");
        //查询最新经纪人培训计划
        JjrpxjhDTO jjrpxjhDTO = jjrpxjhMapper.getJirpxjh();
        if (jjrpxjhDTO != null) {
            //根据学员类型查询课程
            List<KcXsXfDTO> kcDTOList = kcMapper.findByXylx(jjrpxjhDTO.getXylx(),null);
            jjrpxjhDTO.setKcDTOList(kcDTOList);
        }
        //查询授课教师
        List<Sz> szList = szMapper.findByDslb(TEACHER.getKey());
        jjrpxjhDTO.setSzList(szList);
        return jjrpxjhDTO;
    }

    /**
     * 培训报名
     * @param jjrbmInfo
     * @return
     */
    @Override
    public boolean saveJjrInfo(JjrbmInfo jjrbmInfo) {
        log.info("【人才培养 - 培训报名，经纪人报名基础信息:{}】",jjrbmInfo);
        //生成经纪人学号
        Pc pc = pcService.get(jjrbmInfo.getPcId());
        String bh = redisTemplate.opsForValue().increment(pc.getPch()).toString();
        String xh = pc.getJylx() + StringUtils.replace(DateUtils.toString(pc.getRxrq()),"/","") + String.format("%03d", Long.parseLong(bh));
        String yhlx = IN.getKey();
        String yhlb = BROKER.getKey();
        String lxdh = jjrbmInfo.getLxdh();
        //用户表新增,学生表新增
        RpcAddYhRequest yh = new RpcAddYhRequest();
        yh.setYhbh(xh);
        yh.setYhm(xh);
        yh.setMm(xh);
        yh.setZsxm(jjrbmInfo.getXm());
        yh.setLxdh(lxdh);
        yh.setYhlx(yhlx);
        yh.setYhlb(yhlb);
        yh.setJgId(jjrbmInfo.getJgId());
        ResponseUtil responseUtil = yhService.rpcAdd(yh);
        Object data1 = responseUtil.getData();
        if (data1 == null) {
            throw new ServiceException(USER_INSERT_ERROR);
        }
        Yh yh1 = JSONObject.parseObject(JSON.toJSON(data1).toString(), Yh.class);
        Long yh1Id = yh1.getId();
        StuDTO dto = xsService.selectByCondition(null, lxdh, null);
        if (dto != null) {
            //存在,提示用户已存在
            throw new ServiceException(USER_EXISTS_ERROR);
        } else {
            //不存在.则新增
            Xs xs = new Xs();
            BeanUtils.copyProperties(jjrbmInfo,xs);
            xs.setYhId(yh1Id);
            xs.setCjr(yh1Id);
            xs.setGxr(yh1Id);
            xs.setXh(xh);
            xs.setBmfs(ON_LINE.getMsg());
            xs.setXslbmc(BROKER.getKey());
            if (xsService.save(xs)) {
                Long pcId = jjrbmInfo.getPcId();
                PcXs pcXs = new PcXs();
                pcXs.setXsId(xs.getId());
                pcXs.setPcId(pcId);
                boolean save = pcXsService.save(pcXs);
                return save;
            }
            return false;
        }
    }

}
