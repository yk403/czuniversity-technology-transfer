package com.itts.personTraining.service.sjpz.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.enums.SjtxndpzEnum;
import com.itts.personTraining.enums.TkzyTypeEnum;
import com.itts.personTraining.mapper.sjpz.SjpzMapper;
import com.itts.personTraining.mapper.sjtxndpz.SjtxndpzMapper;
import com.itts.personTraining.mapper.sjtxpz.SjtxpzMapper;
import com.itts.personTraining.model.sjpz.Sjpz;
import com.itts.personTraining.model.sjtxndpz.Sjtxndpz;
import com.itts.personTraining.model.sjtxpz.Sjtxpz;
import com.itts.personTraining.service.sjpz.SjpzService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.service.sjtxndpz.SjtxndpzService;
import com.itts.personTraining.service.sjtxpz.SjtxpzService;
import com.itts.personTraining.vo.sjpz.SjpzVO;
import com.itts.personTraining.vo.sjpz.SjtxpzVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fuli
 * @since 2021-09-07
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SjpzServiceImpl extends ServiceImpl<SjpzMapper, Sjpz> implements SjpzService {

    @Resource
    private SjtxpzMapper sjtxpzMapper;
    @Resource
    private SjtxpzService sjtxpzService;
    @Resource
    private SjtxndpzMapper sjtxndpzMapper;
    @Resource
    private SjtxndpzService sjtxndpzService;

    @Resource
    private SjpzMapper sjpzMapper;

    @Override
    public PageInfo<Sjpz> getList(Integer pageNum, Integer pageSize, Long fjjgId, String nd, String mc) {
        PageHelper.startPage(pageNum,pageSize);
        if(fjjgId == null){
            fjjgId = getFjjgId();
        }
        List<Sjpz> sjpzs = sjpzMapper.selectList(new QueryWrapper<Sjpz>().eq("fjjg_id", fjjgId)
                .eq("sfsc", false)
                .eq(StringUtils.isNotBlank(nd),"nd", nd)
                .eq(StringUtils.isNotBlank(mc),"mc", mc));
        return new PageInfo<>(sjpzs);
    }

    @Override
    public SjpzVO get(Long id) {
        SjpzVO sjpzVO = new SjpzVO();
        //试卷配置
        Sjpz sjpz = sjpzMapper.selectOne(new QueryWrapper<Sjpz>().eq("id", id)
                .eq("sfsc", false));
        BeanUtils.copyProperties(sjpz,sjpzVO);
        //试卷题型配置
        //判断
        Sjtxpz sjtxpz = sjtxpzService.get(id, TkzyTypeEnum.JUDGMENT.getMsg());
        Long sjtxpzid1 = sjtxpz.getId();
        SjtxpzVO judgment = new SjtxpzVO();
        BeanUtils.copyProperties(sjtxpz,judgment);

        Sjtxndpz easy = sjtxndpzService.get(sjtxpzid1, SjtxndpzEnum.EASY.getValue());
        judgment.setEasy(easy);

        Sjtxndpz commonly = sjtxndpzService.get(sjtxpzid1, SjtxndpzEnum.COMMONLY.getValue());
        judgment.setCommonly(commonly);

        Sjtxndpz difficulty = sjtxndpzService.get(sjtxpzid1, SjtxndpzEnum.DIFFCULTY.getValue());
        judgment.setDifficulty(difficulty);
        sjpzVO.setJudge(judgment);
        //单选
        Sjtxpz sjtxpz1 = sjtxpzService.get(id, TkzyTypeEnum.SINGLE_CHOICE.getMsg());
        Long id1 = sjtxpz1.getId();
        SjtxpzVO single = new SjtxpzVO();
        BeanUtils.copyProperties(sjtxpz1,single);

        Sjtxndpz easy1 = sjtxndpzService.get(id1, SjtxndpzEnum.EASY.getValue());
        single.setEasy(easy1);

        Sjtxndpz commonly1 = sjtxndpzService.get(id1, SjtxndpzEnum.COMMONLY.getValue());
        single.setCommonly(commonly1);

        Sjtxndpz difficulty1 = sjtxndpzService.get(id1, SjtxndpzEnum.DIFFCULTY.getValue());
        single.setDifficulty(difficulty1);
        sjpzVO.setSingle(single);
        //多选
        Sjtxpz sjtxpz2 = sjtxpzService.get(id, TkzyTypeEnum.MULTIPLE_CHOICE.getMsg());
        Long id2 = sjtxpz2.getId();
        SjtxpzVO multiple = new SjtxpzVO();
        BeanUtils.copyProperties(sjtxpz2,multiple);

        Sjtxndpz easy2 = sjtxndpzService.get(id2, SjtxndpzEnum.EASY.getValue());
        multiple.setEasy(easy2);

        Sjtxndpz commonly2 = sjtxndpzService.get(id2, SjtxndpzEnum.COMMONLY.getValue());
        multiple.setCommonly(commonly2);

        Sjtxndpz difficulty2 = sjtxndpzService.get(id2, SjtxndpzEnum.DIFFCULTY.getValue());
        multiple.setDifficulty(difficulty2);
        sjpzVO.setMultiple(multiple);

        return sjpzVO;
    }

    @Override
    public SjpzVO add(SjpzVO sjpzVO) {
        Sjpz byMc = getByMc(sjpzVO.getMc());
        if(byMc != null){
            throw new ServiceException(ErrorCodeEnum.SYSTEM_FIND_ERROR);
        }
        Long userId = getUserId();
        Date date = new Date();
        //新增试卷配置
        Sjpz sjpz = new Sjpz();
        Long fjjgId = getFjjgId();
        sjpzVO.setFjjgId(fjjgId);
        sjpzVO.setCjr(userId);
        sjpzVO.setCjsj(date);
        sjpzVO.setGxr(userId);
        sjpzVO.setGxsj(date);
        BeanUtils.copyProperties(sjpzVO,sjpz);
        sjpzMapper.insert(sjpz);
        Long id = getByMc(sjpz.getMc()).getId();
        //新增试卷题型配置
        //判断
        SjtxpzVO judge = sjpzVO.getJudge();
        judge.setSjpzId(id);
        Sjtxpz sjtxpz = new Sjtxpz();
        BeanUtils.copyProperties(judge,sjtxpz);
        sjtxpzMapper.insert(sjtxpz);
        Long judgeid1 = sjtxpzService.get(id, TkzyTypeEnum.JUDGMENT.getMsg()).getId();
        //判断简单
        Sjtxndpz easy = judge.getEasy();
        easy.setSjtxpzId(judgeid1);
        sjtxndpzMapper.insert(easy);

        Sjtxndpz commonly = judge.getCommonly();
        commonly.setSjtxpzId(judgeid1);
        sjtxndpzMapper.insert(commonly);

        Sjtxndpz difficulty= judge.getDifficulty();
        difficulty.setSjtxpzId(judgeid1);
        sjtxndpzMapper.insert(difficulty);

        //单选
        SjtxpzVO single = sjpzVO.getSingle();
        single.setSjpzId(id);
        Sjtxpz sjtxpz1 = new Sjtxpz();
        BeanUtils.copyProperties(single,sjtxpz1);
        sjtxpzMapper.insert(sjtxpz1);
        Long singleid1 = sjtxpzService.get(id, TkzyTypeEnum.SINGLE_CHOICE.getMsg()).getId();
        //判断简单
        Sjtxndpz easy1 = single.getEasy();
        easy1.setSjtxpzId(singleid1);
        sjtxndpzMapper.insert(easy1);

        Sjtxndpz commonly1 = single.getCommonly();
        commonly1.setSjtxpzId(singleid1);
        sjtxndpzMapper.insert(commonly1);

        Sjtxndpz difficulty1= single.getDifficulty();
        difficulty1.setSjtxpzId(singleid1);
        sjtxndpzMapper.insert(difficulty1);

        //多选
        SjtxpzVO multiple = sjpzVO.getMultiple();
        multiple.setSjpzId(id);
        Sjtxpz sjtxpz2 = new Sjtxpz();
        BeanUtils.copyProperties(multiple,sjtxpz2);
        sjtxpzMapper.insert(sjtxpz2);
        Long multipleid1 = sjtxpzService.get(id, TkzyTypeEnum.MULTIPLE_CHOICE.getMsg()).getId();
        //判断简单
        Sjtxndpz easy2 = multiple.getEasy();
        easy2.setSjtxpzId(multipleid1);
        sjtxndpzMapper.insert(easy2);

        Sjtxndpz commonly2 = multiple.getCommonly();
        commonly2.setSjtxpzId(multipleid1);
        sjtxndpzMapper.insert(commonly2);

        Sjtxndpz difficulty2= multiple.getDifficulty();
        difficulty2.setSjtxpzId(multipleid1);
        sjtxndpzMapper.insert(difficulty2);
        return sjpzVO;
    }

    @Override
    public Sjpz getByMc(String mc) {
        Sjpz sjpz = sjpzMapper.selectOne(new QueryWrapper<Sjpz>().eq("mc", mc).eq("sfsc", false));
        return sjpz;
    }

    @Override
    public SjpzVO update(SjpzVO sjpzVO) {
        Sjpz byMc = getByMc(sjpzVO.getMc());
        if(byMc != null){
            throw new ServiceException(ErrorCodeEnum.SYSTEM_FIND_ERROR);
        }
        byMc.setSfsc(true);
        sjpzMapper.updateById(byMc);
        sjpzVO.setId(null);
        add(sjpzVO);
        return sjpzVO;
    }

    @Override
    public Boolean delete(Long id) {
        Sjpz sjpz = sjpzMapper.selectById(id);
        sjpz.setSfsc(true);
        sjpzMapper.updateById(sjpz);
        return true;
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
    private Long getFjjgId(){
        LoginUser loginUser = threadLocal.get();
        Long fjjgId = null;
        if (loginUser != null) {
            fjjgId = loginUser.getFjjgId();
        }
        return fjjgId;
    }
}
