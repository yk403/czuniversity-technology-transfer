package com.itts.personTraining.service.yh.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.XsMsgDTO;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.mapper.zj.ZjMapper;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.model.xs.Xs;
import com.itts.personTraining.model.yh.GetYhVo;
import com.itts.personTraining.model.yh.Yh;
import com.itts.personTraining.model.zj.Zj;
import com.itts.personTraining.service.sz.SzService;
import com.itts.personTraining.service.xs.XsService;
import com.itts.personTraining.service.yh.YhService;
import com.itts.personTraining.service.yh.YhVOService;
import com.itts.personTraining.service.zj.ZjService;
import com.itts.personTraining.vo.yh.GetYhVO;
import com.itts.personTraining.vo.yh.YhVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Date;
import java.util.Objects;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;

@Service
@Slf4j
@Transactional
public class YhServiceImpl implements YhVOService {

    @Resource
    private YhService yhService;
    @Resource
    private XsService xsService;
    @Resource
    private SzService szService;
    @Resource
    private ZjService zjService;
    @Resource
    private XsMapper xsMapper;
    @Resource
    private SzMapper szMapper;
    @Resource
    private ZjMapper zjMapper;


    @Override
    public YhVO get() {
        LoginUser loginUser = threadLocal.get();

        Long userId = loginUser.getUserId();
        String realName = loginUser.getRealName();
        String userCategory = loginUser.getUserCategory();
        String theme = loginUser.getTheme();

        YhVO yhVO = new YhVO();
        switch (userCategory) {
            case "postgraduate":
                QueryWrapper<Xs> xsQueryWrapper = new QueryWrapper<>();
                xsQueryWrapper.eq("yh_id",userId)
                        .eq("sfsc",false);
                Xs postgraduate = xsMapper.selectOne(xsQueryWrapper);
                yhVO.setId(userId);
                yhVO.setYhbh(postgraduate.getXh());
                yhVO.setZsxm(realName);
                yhVO.setLxdh(postgraduate.getLxdh());
                yhVO.setYhlb(userCategory);
                yhVO.setYhtx(theme);
                yhVO.setYx(postgraduate.getYx());
                yhVO.setYzy(postgraduate.getYzy());
                break;
            case "broker":
                QueryWrapper<Xs> QueryWrapper = new QueryWrapper<>();
                QueryWrapper.eq("yh_id",userId)
                        .eq("sfsc",false);
                Xs broker = xsMapper.selectOne(QueryWrapper);
                yhVO.setId(userId);
                yhVO.setYhbh(broker.getXh());
                yhVO.setZsxm(realName);
                yhVO.setLxdh(broker.getLxdh());
                yhVO.setYhlb(userCategory);
                yhVO.setYhtx(theme);
                yhVO.setYx(broker.getYx());
                yhVO.setYbyyx(broker.getYbyyx());
                break;
            case "tutor":
            case "corporate_mentor":
            case "teacher":
            case "school_leader":
                QueryWrapper<Sz> szQueryWrapper = new QueryWrapper<>();
                szQueryWrapper.eq("yh_id",userId)
                        .eq("sfsc",false);
                Sz sz = szMapper.selectOne(szQueryWrapper);
                yhVO.setId(userId);
                yhVO.setYhbh(sz.getDsbh());
                yhVO.setZsxm(sz.getDsxm());
                yhVO.setLxdh(sz.getDh());
                yhVO.setYhlb(userCategory);
                yhVO.setYhtx(theme);
                yhVO.setYx(sz.getYx());
                yhVO.setJszc(sz.getJszc());
                break;
            case "professor":
                QueryWrapper<Zj> zjQueryWrapper = new QueryWrapper<>();
                zjQueryWrapper.eq("yh_id",userId)
                        .eq("sfsc",false);
                Zj zj = zjMapper.selectOne(zjQueryWrapper);
                yhVO.setId(userId);
                yhVO.setYhbh(zj.getBh());
                yhVO.setZsxm(zj.getXm());
                yhVO.setLxdh(zj.getDh());
                yhVO.setYhlb(userCategory);
                yhVO.setYhtx(theme);
                yhVO.setZyjszw(zj.getZyjszw());
                yhVO.setYjly(zj.getYjly());
                break;
            default:
                break;
        }
        return yhVO;
    }

    @Override
    public YhVO update(YhVO yhVO) {
        Long userId = getUserId();
        String userCategory = getUserCategory();

        switch (userCategory) {
            case "postgraduate":
            case "broker":
                getAndUpdate(userId,yhVO);
                QueryWrapper<Xs> xsQueryWrapper = new QueryWrapper<>();
                xsQueryWrapper.eq("yh_id",userId)
                        .eq("sfsc",false);
                Xs xs = xsMapper.selectOne(xsQueryWrapper);
                xs.setLxdh(yhVO.getLxdh());
                xs.setGxr(userId);
                xs.setGxsj(new Date());
                xsMapper.updateById(xs);
                break;
            case "tutor":
            case "corporate_mentor":
            case "teacher":
            case "school_leader":
                getAndUpdate(userId,yhVO);
                QueryWrapper<Sz> szQueryWrapper = new QueryWrapper<>();
                szQueryWrapper.eq("yh_id",userId)
                        .eq("sfsc",false);
                Sz sz = szMapper.selectOne(szQueryWrapper);
                sz.setDh(yhVO.getLxdh());
                sz.setGxsj(new Date());
                sz.setGxr(userId);
                szMapper.updateById(sz);
                break;
            case "professor":
                getAndUpdate(userId,yhVO);
                QueryWrapper<Zj> zjQueryWrapper = new QueryWrapper<>();
                zjQueryWrapper.eq("yh_id",userId)
                        .eq("sfsc",false);
                Zj zj = zjMapper.selectOne(zjQueryWrapper);
                zj.setDh(yhVO.getLxdh());
                zj.setGxr(userId);
                zj.setGxsj(new Date());
                zjMapper.updateById(zj);
                break;
            default:
                break;
        }
        return yhVO;
    }
    private Boolean getAndUpdate(Long userId,YhVO yhVO){


        ResponseUtil byId = yhService.getById(userId);
        if(byId.getErrCode() != 0 ){
            throw new ServiceException(USER_NOT_FIND_ERROR);
        }
        Yh yh = byId.conversionData(new TypeReference<Yh>() {});
        if(yh == null){
            throw new ServiceException(USER_NOT_FIND_ERROR);
        }
        if(!Objects.equals(yh.getLxdh(),yhVO.getLxdh()) || !Objects.equals(yh.getYhtx(),yhVO.getYhtx())){
            ResponseUtil byPhone = yhService.getByPhone(yhVO.getLxdh(), null);
            GetYhVO getYhVO = byPhone.conversionData(new TypeReference<GetYhVO>() {});
            if(yhVO.getId() != getYhVO.getId()){
                if(getYhVO != null){
                throw new ServiceException(PHONE_NUMBER_EXISTS_ERROR);
                }
            }
            yh.setYhtx(yhVO.getYhtx());
            yh.setLxdh(yhVO.getLxdh());
            yhService.updateYh(yh);
        }

        return true;
    }

    /**
     * 获取当前用户id
     *
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
     * 获取当前用户id所属类别
     * @return
     */
    private String getUserCategory() {
        LoginUser loginUser = threadLocal.get();
        String userCategory;
        if (loginUser != null) {
            userCategory = loginUser.getUserCategory();
        } else {
            throw new ServiceException(GET_THREADLOCAL_ERROR);
        }
        return userCategory;
    }


}
