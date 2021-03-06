package com.itts.userservice.service.yh.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.RedisConstant;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.DateUtils;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.dto.JsDTO;
import com.itts.userservice.dto.MenuDTO;
import com.itts.userservice.dto.YhDTO;
import com.itts.userservice.enmus.TypeEnum;
import com.itts.userservice.enmus.UserCategoryEnum;
import com.itts.userservice.enmus.UserTypeEnum;
import com.itts.userservice.feign.persontraining.pc.PcRpcService;
import com.itts.userservice.feign.persontraining.sz.SzRpcService;
import com.itts.userservice.feign.persontraining.xs.XsRpcService;
import com.itts.userservice.feign.persontraining.zj.ZjRpcService;
import com.itts.userservice.feign.persontraining.jdxy.JdxyRpcService;
import com.itts.userservice.feign.persontraining.szgl.SzglRpcService;
import com.itts.userservice.mapper.cd.CdMapper;
import com.itts.userservice.mapper.jggl.JgglMapper;
import com.itts.userservice.mapper.js.JsMapper;
import com.itts.userservice.mapper.sjzd.SjzdMapper;
import com.itts.userservice.mapper.yh.YhJsGlMapper;
import com.itts.userservice.mapper.yh.YhMapper;
import com.itts.userservice.model.cd.Cd;
import com.itts.userservice.model.jggl.Jggl;
import com.itts.userservice.model.js.Js;
import com.itts.userservice.model.pc.Pc;
import com.itts.userservice.model.sjzd.Sjzd;
import com.itts.userservice.model.sz.Sz;
import com.itts.userservice.model.xs.Xs;
import com.itts.userservice.model.yh.Yh;
import com.itts.userservice.model.yh.YhJsGl;
import com.itts.userservice.model.zj.Zj;
import com.itts.userservice.request.jsxy.AddJdxyRequest;
import com.itts.userservice.request.szgl.AddSzglRequest;
import com.itts.userservice.request.yh.AddYhRequest;
import com.itts.userservice.request.yh.RpcAddYhRequest;
import com.itts.userservice.service.yh.YhService;
import com.itts.userservice.vo.yh.GetSystemsVO;
import com.itts.userservice.vo.yh.GetYhVO;
import com.itts.userservice.vo.yh.YhListVO;
import com.itts.userservice.vo.yh.YhVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * ????????? ???????????????
 * </p>
 *
 * @author fl
 * @since 2021-03-18
 */
@Service
@Primary
public class YhServiceImpl extends ServiceImpl<YhMapper, Yh> implements YhService {

    @Resource
    private YhMapper yhMapper;

    @Resource
    private JgglMapper jgglMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private JsMapper jsMapper;

    @Resource
    private CdMapper cdMapper;

    @Resource
    private YhJsGlMapper yhJsGlMapper;

    @Autowired
    private JdxyRpcService jdxyRpcService;

    @Autowired
    private SzglRpcService szglRpcService;

    @Resource
    private SjzdMapper sjzdMapper;
    @Resource
    private ZjRpcService zjRpcService;
    @Resource
    private SzRpcService szRpcService;
    @Resource
    private XsRpcService xsRpcService;
    @Resource
    private PcRpcService pcRpcService;

    /**
     * ???????????? - ??????
     */
    @Override
    public PageInfo<YhListVO> findByPage(Integer pageNum, Integer pageSize, String type, Jggl group, String condition) {

        List<Long> groupIds = null;

        if (group != null) {
            //??????????????????????????????????????????????????????ID
            groupIds = jgglMapper.findThisAndChildByLevel(group.getCj()).stream().map(Jggl::getId).collect(Collectors.toList());
        }

        PageHelper.startPage(pageNum, pageSize);

        List<Yh> list = yhMapper.findByTypeAndGroupId(type, groupIds, condition);
        List<YhListVO> yhListVOs = this.yh2YhVO(list);

        PageInfo<YhListVO> page = new PageInfo(list);
        page.setList(yhListVOs);
        return page;
    }

    /**
     * ??????????????????????????????
     */
    @Override
    public List<GetSystemsVO> findSystems(Long userId) {

        List<Js> jsList = jsMapper.findByYhId(userId);
        if (CollectionUtils.isEmpty(jsList)) {
            return null;
        }

        List<Long> jsIds = jsList.stream().map(Js::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(jsIds)) {
            return null;
        }

        //??????????????????????????????????????????
        List<Cd> cds = cdMapper.findByJsId(jsIds);
        if (CollectionUtils.isEmpty(cds)) {
            return null;
        }

        Set<String> cdSystems = cds.stream().map(Cd::getXtlx).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(cdSystems)) {
            return null;
        }

        List<Sjzd> sjzds = sjzdMapper.findBySsmk(null, null, "system_type");
        if(CollectionUtils.isEmpty(sjzds)){
            return null;
        }
        Map<String, Sjzd> sjzdMap = sjzds.stream().collect(Collectors.toMap(Sjzd::getZdbm, Function.identity()));

        //????????????????????????
        List<GetSystemsVO> result = cdSystems.stream().map(obj -> {

            GetSystemsVO vo = new GetSystemsVO();

            Sjzd thisSjzd = sjzdMap.get(obj);
            if (thisSjzd != null) {


                vo.setBm(thisSjzd.getZdbm());
                vo.setMc(thisSjzd.getZdmc());
            }

            return vo;

        }).collect(Collectors.toList());

        return result;
    }

    @Override
    public PageInfo<YhDTO> selectByString(Integer pageNum, Integer pageSize, String type, String string) {
        PageHelper.startPage(pageNum, pageSize);
        List<YhDTO> byString = yhMapper.findByString(type, string);
        PageInfo<YhDTO> yhDTOPageInfo = new PageInfo<>(byString);
        return yhDTOPageInfo;
    }

    /**
     * ????????????
     */
    @Override
    public Yh get(Long id) {
        Yh Yh = yhMapper.selectById(id);
        return Yh;
    }

    /**
     * ????????????????????????????????????
     */
    @Override
    public GetYhVO getByCode(String code) {

        Yh yh = yhMapper.selectOne(new QueryWrapper<Yh>().eq("yhbh", code)
                .eq("sfsc", false));
        GetYhVO vo = new GetYhVO();
        if(yh == null){
            vo =null;
            return vo;
        }

        BeanUtils.copyProperties(yh, vo);

        return vo;
    }

    /**
     * ?????????????????????????????????
     */
    @Override
    public GetYhVO getByphone(String phone) {

        Yh yh = yhMapper.selectOne(new QueryWrapper<Yh>().eq("lxdh", phone)
                .eq("sfsc", false));
        GetYhVO vo = new GetYhVO();
        if(yh == null){
            return null;
        }

        BeanUtils.copyProperties(yh, vo);

        return vo;
    }

    /**
     * ?????????????????????????????????
     *
     * @param
     * @return
     * @author liuyingming
     */
    @Override
    public Yh getByUserName(String userName) {

        QueryWrapper query = new QueryWrapper();
        query.eq("yhm", userName);
        query.eq("sfsc", false);

        Yh yh = yhMapper.selectOne(query);
        return yh;
    }

    /**
     * ????????????
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GetYhVO add(AddYhRequest addYhRequest, String token) {
        Yh old = yhMapper.selectOne(new QueryWrapper<Yh>().eq("lxdh", addYhRequest.getLxdh())
                .eq("sfsc", false));
        if(old != null){
            throw new WebException(ErrorCodeEnum.PHONE_NUMBER_EXISTS_ERROR);
        }
        Yh old2 = yhMapper.selectOne(new QueryWrapper<Yh>().eq("yhbh", addYhRequest.getYhbh())
                .eq("sfsc", false));
        if(old2 != null){
            throw new WebException(ErrorCodeEnum.USER_NUMBER_EXISTS_ERROR);
        }
        Yh old3 = yhMapper.selectOne(new QueryWrapper<Yh>().eq("yhm", addYhRequest.getYhm())
                .eq("sfsc", false));
        if(old3 != null){
            throw new WebException(ErrorCodeEnum.USER_NAME_EXISTS_ERROR);
        }
        LoginUser loginUser = SystemConstant.threadLocal.get();
        Long userId = null;

        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        Date now = new Date();

        Yh yh = new Yh();

        BeanUtils.copyProperties(addYhRequest, yh);
        if(yh.getYhlb().equals("broker") && addYhRequest.getPcId()!= null){
            ResponseUtil response = pcRpcService.getByOne(addYhRequest.getPcId());
            if(response == null){
                throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
            }
            if(response.getErrCode().intValue() != 0){
                throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
            }
            Pc pc = response.conversionData(new TypeReference<Pc>(){});
            if(pc == null){
                throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
            }
            String bh = redisTemplate.opsForValue().increment(pc.getPch()).toString();
            String xh = pc.getJylx() + StringUtils.replace(DateUtils.toString(pc.getRxrq()),"/","") + String.format("%03d", Long.parseLong(bh));
            yh.setYhbh(xh);
        }

        yh.setMm(new BCryptPasswordEncoder().encode(addYhRequest.getMm()));

        yh.setGxsj(now);
        yh.setCjsj(now);
        yh.setCjr(userId);
        yh.setGxr(userId);

        yhMapper.insert(yh);

        //????????????????????????????????????????????????
        if (CollectionUtils.isEmpty(addYhRequest.getJsidlist())) {

            Js defaultJs = jsMapper.selectOne(new QueryWrapper<Js>().eq("jg_id", addYhRequest.getJgId())
                    .eq("yhjslx", addYhRequest.getYhlx())
                    .eq("sfmr", true)
                    .eq("jslb", addYhRequest.getYhlb())
                    .eq("sfsc", false));

            if (defaultJs != null) {

                YhJsGl yhJsGl = new YhJsGl();

                yhJsGl.setJsId(defaultJs.getId());
                yhJsGl.setYhId(yh.getId());
                yhJsGl.setGxsj(now);
                yhJsGl.setCjsj(now);
                yhJsGl.setCjr(userId);
                yhJsGl.setGxr(userId);

                yhJsGlMapper.insert(yhJsGl);
            }

        } else {

            for (Long jsId : addYhRequest.getJsidlist()) {

                YhJsGl yhJsGl = new YhJsGl();

                yhJsGl.setJsId(jsId);
                yhJsGl.setYhId(yh.getId());
                yhJsGl.setGxsj(now);
                yhJsGl.setCjsj(now);
                yhJsGl.setCjr(userId);
                yhJsGl.setGxr(userId);

                yhJsGlMapper.insert(yhJsGl);
            }
        }

        GetYhVO vo = new GetYhVO();
        BeanUtils.copyProperties(yh, vo);

        //?????????????????????????????????????????????????????????????????????
        if (StringUtils.equals(UserTypeEnum.IN_USER.getCode(), addYhRequest.getYhlx())) {

            addSzglOrJdxy(yh, token);
        }

        return vo;
    }

    /**
     * ?????? - rpc
     */
    @Override
    public GetYhVO rpcAdd(RpcAddYhRequest addYhRequest) {
        Yh old = yhMapper.selectOne(new QueryWrapper<Yh>().eq("lxdh", addYhRequest.getLxdh())
                .eq("sfsc", false));
        if(old != null){
            throw new WebException(ErrorCodeEnum.PHONE_NUMBER_EXISTS_ERROR);
        }
        Yh old2 = yhMapper.selectOne(new QueryWrapper<Yh>().eq("yhbh", addYhRequest.getYhbh())
                .eq("sfsc", false));
        if(old2 != null){
            throw new WebException(ErrorCodeEnum.USER_NUMBER_EXISTS_ERROR);
        }
        LoginUser loginUser = SystemConstant.threadLocal.get();

        Long userId = null;

        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        Date now = new Date();

        Yh yh = new Yh();

        BeanUtils.copyProperties(addYhRequest, yh);

        yh.setMm(new BCryptPasswordEncoder().encode(addYhRequest.getMm()));

        yh.setGxsj(now);
        yh.setCjsj(now);
        yh.setCjr(userId);
        yh.setGxr(userId);

        yhMapper.insert(yh);

        //??????????????????
        Js defaultJs = jsMapper.selectOne(new QueryWrapper<Js>().eq("jg_id", addYhRequest.getJgId())
                .eq("yhjslx", addYhRequest.getYhlx())
                .eq("sfmr", true)
                .eq("jslb", addYhRequest.getYhlb())
                .eq("sfsc", false));

        if (defaultJs != null) {

            YhJsGl yhJsGl = new YhJsGl();

            yhJsGl.setJsId(defaultJs.getId());
            yhJsGl.setYhId(yh.getId());
            yhJsGl.setGxsj(now);
            yhJsGl.setCjsj(now);
            yhJsGl.setCjr(userId);
            yhJsGl.setGxr(userId);

            yhJsGlMapper.insert(yhJsGl);
        }

        GetYhVO vo = new GetYhVO();
        BeanUtils.copyProperties(yh, vo);

        return vo;
    }

    /**
     * ????????????
     */
    /*@Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addYhAndJsmc(Yh Yh, Long jsid) {
        Long id1 = Yh.getId();
        Yh yh = yhMapper.selectById(id1);
        if (yh == null) {
            Yh.setMm(new BCryptPasswordEncoder().encode(Yh.getMm()));
            Yh.setCjsj(new Date());
            Yh.setGxsj(new Date());
            yhMapper.insert(Yh);
        }

        //???????????????????????????
        Long yhid = Yh.getId();
        YhJsGl yhJsGl = new YhJsGl();
        yhJsGl.setYhId(yhid);
        yhJsGl.setJsId(jsid);
        yhJsGl.setGxsj(new Date());
        yhJsGl.setCjsj(new Date());
        yhJsGlMapper.insert(yhJsGl);
        return true;
    }*/

    /**
     * ??????
     */
    @Override
    public GetYhVO update(AddYhRequest request, Yh old) {
        if(!old.getLxdh().equals(request.getLxdh())){
            Yh two = yhMapper.selectOne(new QueryWrapper<Yh>().eq("lxdh", request.getLxdh())
                    .eq("sfsc", false));
            if(two != null){
                throw new WebException(ErrorCodeEnum.PHONE_NUMBER_EXISTS_ERROR);
            }
        }

        LoginUser loginUser = SystemConstant.threadLocal.get();
        Long userId = null;

        if (loginUser != null) {
            userId = loginUser.getUserId();
        }
        Long id = old.getId();
        if (StringUtils.equals(UserTypeEnum.IN_USER.getCode(), request.getYhlx())) {
            if(!request.getJgId().equals(old.getJgId()) || !request.getZsxm().equals(old.getZsxm()) || !request.getLxdh().equals(old.getLxdh())){
                if(request.getYhlb().equals("professor") || request.getYhlb().equals("out_professor")){
                    ResponseUtil response = zjRpcService.get(null,null,id);
                    if(response == null){
                        throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
                    }
                    if(response.getErrCode().intValue() != 0){
                        throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
                    }
                    Zj zj = response.conversionData(new TypeReference<Zj>(){});
                    if(zj == null){
                        throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
                    }
                    zj.setXm(request.getZsxm());
                    zj.setDh(request.getLxdh());
                    zj.setJgId(request.getJgId());
                    zjRpcService.update(zj);
                }else if(request.getYhlb().equals("postgraduate") || request.getYhlb().equals("broker")){
                    ResponseUtil response= xsRpcService.get(null,null,id);
                    if(response == null){
                        throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
                    }
                    if(response.getErrCode().intValue() != 0){
                        throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
                    }
                    Xs xs = response.conversionData(new TypeReference<Xs>(){});
                    if(xs == null){
                        throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
                    }
                    xs.setXm(request.getZsxm());
                    xs.setLxdh(request.getLxdh());
                    xs.setJgId(request.getJgId());
                    xsRpcService.update(xs);
                }else if(request.getYhlb().equals("tutor") || request.getYhlb().equals("corporate_mentor")|| request.getYhlb().equals("teacher")|| request.getYhlb().equals("school_leader")|| request.getYhlb().equals("cloud_admin")){
                    ResponseUtil response = szRpcService.get(null,null,id,null);
                    if(response == null){
                        throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
                    }
                    if(response.getErrCode().intValue() != 0){
                        throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
                    }
                    Sz sz = response.conversionData(new TypeReference<Sz>(){});
                    if(sz == null){
                        throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
                    }
                    sz.setDsxm(request.getZsxm());
                    sz.setDh(request.getLxdh());
                    sz.setSsjgId(request.getJgId());
                    szRpcService.update(sz);
                }

            }
        }
        if(!Objects.equals(request.getYhlb(),old.getYhlb())){
            yhJsGlMapper.deleteByUserId(old.getId());
            //??????????????????
            Js defaultJs = jsMapper.selectOne(new QueryWrapper<Js>().eq("jg_id", request.getJgId())
                    .eq("yhjslx", request.getYhlx())
                    .eq("sfmr", true)
                    .eq("jslb", request.getYhlb())
                    .eq("sfsc", false));

            if (defaultJs != null) {

                YhJsGl yhJsGl = new YhJsGl();

                yhJsGl.setJsId(defaultJs.getId());
                yhJsGl.setYhId(request.getId());
                yhJsGl.setGxsj(new Date());
                yhJsGl.setCjsj(new Date());
                yhJsGl.setCjr(userId);
                yhJsGl.setGxr(userId);

                yhJsGlMapper.insert(yhJsGl);
            }
        }

        Date now = new Date();

        BeanUtils.copyProperties(request, old, "cjsj", "cjr", "mm", "yhm");

        old.setGxsj(now);
        old.setGxr(userId);

        yhMapper.updateById(old);

        //??????????????????
        if (!CollectionUtils.isEmpty(request.getJsidlist())) {

            yhJsGlMapper.deleteByUserId(old.getId());

            for (Long jsId : request.getJsidlist()) {

                YhJsGl yhJsGl = new YhJsGl();

                yhJsGl.setJsId(jsId);
                yhJsGl.setYhId(old.getId());
                yhJsGl.setGxsj(now);
                yhJsGl.setCjsj(now);
                yhJsGl.setCjr(userId);
                yhJsGl.setGxr(userId);

                yhJsGlMapper.insert(yhJsGl);
            }
        }


        GetYhVO vo = new GetYhVO();
        BeanUtils.copyProperties(old, vo);

        return vo;
    }

    /**
     * ????????????
     */
    @Override
    public void resetPassword(Yh yh) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        Long userId = null;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        yh.setMm(new BCryptPasswordEncoder().encode(yh.getYhm()));
        yh.setGxr(userId);
        yh.setGxsj(new Date());

        yhMapper.updateById(yh);
    }

    /**
     * ????????????????????????
     *
     * @param
     * @return
     * @author fl
     */
    @Override
    public YhVO findMenusByUserID(Long userId, String systemType) {

        Object redisResult;

        if (StringUtils.isNotBlank(systemType)) {
            redisResult = redisTemplate.opsForValue().get(RedisConstant.USERSERVICE_MENUS + userId + ":" + systemType);
        } else {
            redisResult = redisTemplate.opsForValue().get(RedisConstant.USERSERVICE_MENUS + userId);
        }

        YhVO yhVO;

        if (redisResult != null) {

            yhVO = JSONUtil.toBean(redisResult.toString(), YhVO.class);
            return yhVO;

        } else {
            //?????????????????????????????????
            List<JsDTO> jsDTOList = yhMapper.findByUserId(userId, systemType);

            jsDTOList.forEach(jsDTO -> {
                List<MenuDTO> rootMenu = jsDTO.getMenuDTOList();
                //???????????????
                List<MenuDTO> menuDTOList = buildMenuTree(rootMenu, 0L);

                jsDTO.setMenuDTOList(menuDTOList);
            });
            //??????????????????
            Yh yh = yhMapper.selectById(userId);
            yhVO = new YhVO();
            BeanUtils.copyProperties(yh, yhVO);
            yhVO.setJsDTOList(jsDTOList);

            if (StringUtils.isNotBlank(systemType)) {

                redisTemplate.opsForValue().set(RedisConstant.USERSERVICE_MENUS + userId + ":" + systemType, JSONUtil.toJsonStr(yhVO), RedisConstant.USER_MENU_EXPIRE_DATE, TimeUnit.DAYS);
            } else {
                redisTemplate.opsForValue().set(RedisConstant.USERSERVICE_MENUS + userId, JSONUtil.toJsonStr(yhVO), RedisConstant.USER_MENU_EXPIRE_DATE, TimeUnit.DAYS);
            }
        }
        return yhVO;
    }

    /**
     * ????????????
     */
    @Override
    public void delete(Yh yh) {

        Long userId = null;

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        yh.setGxr(userId);
        yh.setGxsj(new Date());
        yh.setSfsc(true);

        yhMapper.updateById(yh);

    }

    //???????????????
    private List<MenuDTO> buildMenuTree(List<MenuDTO> rootMenu, Long parentId) {
        //?????????
        List<MenuDTO> treeList = new ArrayList<>();

        rootMenu.forEach(menuDTO -> {
            if (parentId.longValue() == menuDTO.getParentId().longValue()) {
                //???????????????????????????????????????
                menuDTO.setChildMenus(buildMenuTree(rootMenu, menuDTO.getId()));
                treeList.add(menuDTO);
            }
        });
        return treeList;
    }

    /**
     * ?????? - ?????????dto???vo
     */
    private List<YhListVO> yh2YhVO(List<Yh> yhs) {

        List<YhListVO> yhListVOs = Lists.newArrayList();

        yhs.forEach(yhDTO -> {

            YhListVO yhListVO = new YhListVO();
            BeanUtils.copyProperties(yhDTO, yhListVO);

            List<Js> jsList = jsMapper.findByYhId(yhDTO.getId());

            StringBuilder builder = new StringBuilder();

            if (!CollectionUtils.isEmpty(jsList)) {

                jsList.forEach(js -> {
                    builder.append(js.getJsmc()).append(",");
                });

                yhListVO.setYhjsmc(builder.substring(0, builder.length() - 1));
            }

            yhListVOs.add(yhListVO);
        });

        return yhListVOs;
    }

    /**
     * ????????????????????????
     */
    private void addJdxy(Yh yh, String token) {

        if (yh == null) {
            return;
        }

        LoginUser loginUser = SystemConstant.threadLocal.get();
        Long userId = null;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        AddJdxyRequest request = new AddJdxyRequest();

        request.setYhId(yh.getId());
        request.setJgId(yh.getJgId());
        request.setXm(yh.getZsxm());
        request.setXh(yh.getYhbh());
        request.setLxdh(yh.getLxdh());
        request.setXslbId(yh.getYhlb());
        request.setXslbmc(UserCategoryEnum.getMsgByKey(yh.getYhlb()));

        request.setCjr(userId);
        request.setGxr(userId);

        jdxyRpcService.addUsr(request, token);
    }

    /**
     * ??????????????????
     */
    private void addSzgl(Yh yh, String token) {

        if (yh == null) {
            return;
        }

        LoginUser loginUser = SystemConstant.threadLocal.get();
        Long userId = null;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        Date now = new Date();

        AddSzglRequest request = new AddSzglRequest();

        request.setYhId(yh.getId());
        request.setDsbh(yh.getYhbh());
        request.setDsxm(yh.getZsxm());
        request.setDslb(yh.getYhlb());
        request.setSsjgId(yh.getJgId());

        request.setCjr(userId);
        request.setGxr(userId);

        szglRpcService.addSzgl(request, token);
    }

    private void addZj(Yh yh) {

        if (yh == null) {
            return;
        }

        LoginUser loginUser = SystemConstant.threadLocal.get();
        Long userId = null;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        Date now = new Date();

        Zj request = new Zj();
        if(yh.getYhlb().equals("professor")){
            request.setLx(TypeEnum.IN.getMsg());
        }else {
            request.setLx(TypeEnum.OUT.getMsg());
        }

        request.setYhId(yh.getId());
        request.setBh(yh.getYhbh());
        request.setXm(yh.getZsxm());
        request.setJgId(yh.getJgId());

        request.setCjr(userId);
        request.setGxr(userId);

        zjRpcService.add(request);
    }

    /**
     * ?????????????????????????????????
     */
    private void addSzglOrJdxy(Yh yh, String token) {

        switch (yh.getYhlb()) {
            case "postgraduate":
            case "broker":
                addJdxy(yh, token);
                break;
            case "tutor":
            case "corporate_mentor":
            case "teacher":
                addSzgl(yh, token);
                break;
            case "professor":
            case "out_professor":
                addZj(yh);
                break;
        }
    }
}
