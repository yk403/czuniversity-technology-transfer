package com.itts.personTraining.service.sjpz.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.enums.SjtxndpzEnum;
import com.itts.personTraining.enums.TkzyTypeEnum;
import com.itts.personTraining.mapper.kssj.KssjMapper;
import com.itts.personTraining.mapper.sjpz.SjpzMapper;
import com.itts.personTraining.mapper.sjtxndpz.SjtxndpzMapper;
import com.itts.personTraining.mapper.sjtxpz.SjtxpzMapper;
import com.itts.personTraining.model.kssj.Kssj;
import com.itts.personTraining.model.sjpz.Sjpz;
import com.itts.personTraining.model.sjtxndpz.Sjtxndpz;
import com.itts.personTraining.model.sjtxpz.Sjtxpz;
import com.itts.personTraining.service.kssj.KssjService;
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
import java.util.Objects;

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
    @Resource
    private KssjMapper kssjMapper;

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
        sjpzVO = getBy(id,sjpzVO,TkzyTypeEnum.JUDGMENT.getMsg());
        //单选
        sjpzVO = getBy(id,sjpzVO,TkzyTypeEnum.SINGLE_CHOICE.getMsg());
        //多选
        sjpzVO = getBy(id,sjpzVO,TkzyTypeEnum.MULTIPLE_CHOICE.getMsg());

        return sjpzVO;
    }
    private SjpzVO getBy(Long id,SjpzVO sjpzVO,String tkzyTypeEnum){
        Sjtxpz sjtxpz = sjtxpzService.get(id, tkzyTypeEnum);
        Long sjtxpzid1 = sjtxpz.getId();
        SjtxpzVO sjtxpzVO = new SjtxpzVO();
        BeanUtils.copyProperties(sjtxpz,sjtxpzVO);

        Sjtxndpz easy = sjtxndpzService.get(sjtxpzid1, SjtxndpzEnum.EASY.getValue());
        sjtxpzVO.setEasy(easy);

        Sjtxndpz commonly = sjtxndpzService.get(sjtxpzid1, SjtxndpzEnum.COMMONLY.getValue());
        sjtxpzVO.setCommonly(commonly);

        Sjtxndpz difficulty = sjtxndpzService.get(sjtxpzid1, SjtxndpzEnum.DIFFCULTY.getValue());
        sjtxpzVO.setDifficulty(difficulty);

        if(Objects.equals(tkzyTypeEnum,TkzyTypeEnum.JUDGMENT.getMsg())){
            sjpzVO.setJudge(sjtxpzVO);
        }else if(Objects.equals(tkzyTypeEnum,TkzyTypeEnum.SINGLE_CHOICE.getMsg())){
            sjpzVO.setSingle(sjtxpzVO);
        }else if(Objects.equals(tkzyTypeEnum,TkzyTypeEnum.MULTIPLE_CHOICE.getMsg())){
            sjpzVO.setMultiple(sjtxpzVO);
        }

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
        insertSjpz(id,sjpzVO.getJudge(),TkzyTypeEnum.JUDGMENT.getMsg());

        //单选
        insertSjpz(id,sjpzVO.getSingle(),TkzyTypeEnum.SINGLE_CHOICE.getMsg());

        //多选
        insertSjpz(id, sjpzVO.getMultiple(),TkzyTypeEnum.MULTIPLE_CHOICE.getMsg());
        return sjpzVO;
    }
    private void insertSjpz(Long id,SjtxpzVO sjtxpzVO,String tkzyTypeEnum){

        sjtxpzVO.setSjpzId(id);
        Sjtxpz sjtxpz = new Sjtxpz();
        BeanUtils.copyProperties(sjtxpzVO,sjtxpz);
        sjtxpzMapper.insert(sjtxpz);
        Long id1 = sjtxpzService.get(id, tkzyTypeEnum).getId();
        //判断简单
        Sjtxndpz easy = sjtxpzVO.getEasy();
        easy.setSjtxpzId(id1);
        sjtxndpzMapper.insert(easy);

        Sjtxndpz commonly = sjtxpzVO.getCommonly();
        commonly.setSjtxpzId(id1);
        sjtxndpzMapper.insert(commonly);

        Sjtxndpz difficulty= sjtxpzVO.getDifficulty();
        difficulty.setSjtxpzId(id1);
        sjtxndpzMapper.insert(difficulty);
    }

    @Override
    public Sjpz getByMc(String mc) {
        Sjpz sjpz = sjpzMapper.selectOne(new QueryWrapper<Sjpz>().eq("mc", mc).eq("sfsc", false));
        return sjpz;
    }

    @Override
    public SjpzVO update(Sjpz old,SjpzVO sjpzVO) {

        Sjpz byMc = getByMc(sjpzVO.getMc());
        //重名不通过
        if(byMc != null && byMc.getId().intValue() != sjpzVO.getId().intValue()){
            throw new ServiceException(ErrorCodeEnum.NAME_EXIST_ERROR);
        }
        old.setSfsc(true);
        sjpzMapper.updateById(old);
        sjpzVO.setId(null);
        add(sjpzVO);
        return sjpzVO;
    }

    @Override
    public Boolean delete(Long id) {
        List<Kssj> kssjs = kssjMapper.selectList(new QueryWrapper<Kssj>().eq("sjpz_id", id).eq("sfsc", false));
        if(kssjs.size() > 0 || kssjs != null){
            throw new ServiceException(ErrorCodeEnum.EXISTENCE_CONFIGURED);
        }
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
