package com.itts.userservice.service.js.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.userservice.enmus.JgTpyeEnum;
import com.itts.userservice.enmus.UserTypeEnum;
import com.itts.userservice.mapper.cz.CzMapper;
import com.itts.userservice.mapper.jggl.JgglMapper;
import com.itts.userservice.mapper.js.JsCdCzGlMapper;
import com.itts.userservice.mapper.js.JsCdGlMapper;
import com.itts.userservice.mapper.js.JsMapper;
import com.itts.userservice.model.cz.Cz;
import com.itts.userservice.model.jggl.Jggl;
import com.itts.userservice.model.js.Js;
import com.itts.userservice.model.js.JsCdCzGl;
import com.itts.userservice.model.js.JsCdGl;
import com.itts.userservice.request.AddJsCdRequest;
import com.itts.userservice.request.AddJsRequest;
import com.itts.userservice.service.js.JsService;
import com.itts.userservice.vo.GetJsCdCzGlVO;
import com.itts.userservice.vo.GetJsCdGlVO;
import com.itts.userservice.vo.GetJsVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author fl
 * @since 2021-03-19
 */
@Service
@Primary
@Slf4j
public class JsServiceImpl implements JsService {

    @Resource
    private JsMapper jsMapper;

    @Resource
    private JsCdGlMapper jsCdGlMapper;

    @Resource
    private JsCdCzGlMapper jsCdCzGlMapper;

    @Resource
    private CzMapper czMapper;
    @Resource
    private JgglMapper jgglMapper;

    /**
     * 获取列表 - 分页
     */
    @Override
    public PageInfo<Js> findByPage(Integer pageNum, Integer pageSize, String name, String systemType,Long jgId) {
        log.info("分页是否起效,pageNum:{}",pageNum);

        Jggl jggl = jgglMapper.selectOne(new QueryWrapper<Jggl>().eq("id", jgId).eq("sfsc", false));
        Jggl jggl1 = jgglMapper.selectOne(new QueryWrapper<Jggl>().eq("jgbm", StringUtils.substring(jggl.getCj(), 0, 3))
                .eq("sfsc", false));
        jgId = jggl1.getId();
        QueryWrapper<Js> query = new QueryWrapper<>();
        query.eq("sfsc", false);
        if (StringUtils.isNotBlank(name)) {
            query.like("jsmc", name);
        }
        if (StringUtils.isNotBlank(systemType)) {
            query.eq("yhjslx", systemType);
        }
        //查询条件jgId
        query.eq(jgId!=null,"jg_id",jgId);

        query.orderByDesc("cjsj");

        if(pageNum != -1){
            PageHelper.startPage(pageNum, pageSize);
        }

        List<Js> js = jsMapper.selectList(query);
        PageInfo<Js> tJsPageInfo = new PageInfo<>(js);

        return tJsPageInfo;
    }

    /**
     * 通过系统类型是否为默认角色获取角色信息
     *
     * @param userType    用户类型
     * @param defaultFlag 是否为默认角色
     * @return
     * @author liuyingming
     */
    @Override
    public List<Js> findByUserTypeAndDefault(String userType,Long jgId, Boolean defaultFlag) {

        QueryWrapper query = new QueryWrapper();
        query.eq("yhjslx", userType);
        query.eq("sfmr", defaultFlag);
        query.eq("sfsc", false);
        query.eq("jg_id",jgId);


        List jsList = jsMapper.selectList(query);

        return jsList;
    }

    /**
     * 通过用户ID获取
     */
    @Override
    public List<Js> findByUserId(Long userId) {

        List<Js> jsList = jsMapper.findByYhId(userId);
        return jsList;
    }

    /**
     * 获取详情
     */
    @Override
    public Js get(Long id) {
        Js Js = jsMapper.selectById(id);
        return Js;
    }

    /**
     * 通过ID获取角色菜单操作关联信息
     */
    @Override
    public GetJsVO getJsCdCzGl(Long id) {

        Js js = get(id);
        if (js == null) {
            return null;
        }

        //设置角色相关信息
        GetJsVO vo = new GetJsVO();
        BeanUtils.copyProperties(js, vo);

        //获取当前角色拥有的菜单信息
        List<GetJsCdGlVO> cds = jsCdGlMapper.getJsCdGlByJsId(id);
        if (CollectionUtils.isEmpty(cds)) {
            return vo;
        }

        vo.setJsCdGls(cds);

        QueryWrapper query = new QueryWrapper();
        query.eq("sfsc", false);
        //获取所有操作
        List<Cz> allCz = czMapper.selectList(query);
        Map<Long, Cz> czMap = allCz.stream().collect(Collectors.toMap(Cz::getId, cz -> cz));

        for (GetJsCdGlVO cd : cds) {

            //获取当前角色当前菜单下的角色菜单操作关联
            List<JsCdCzGl> jsCdCzGls = jsCdCzGlMapper.getByJsIdAndCdId(id, cd.getId());

            if (CollectionUtils.isEmpty(jsCdCzGls)) {
                continue;
            }

            List<GetJsCdCzGlVO> jsCdCzGlVOs = Lists.newArrayList();
            //循环遍历，组装数据
            for (JsCdCzGl jsCdCzGl : jsCdCzGls) {

                Cz cz = czMap.get(jsCdCzGl.getCzId());
                if (cz == null) {
                    continue;
                }

                GetJsCdCzGlVO jsCdCzGlVO = new GetJsCdCzGlVO();
                BeanUtils.copyProperties(cz, jsCdCzGlVO);
                jsCdCzGlVOs.add(jsCdCzGlVO);
            }

            cd.setJsCdCzGls(jsCdCzGlVOs);
        }

        return vo;
    }

    /**
     * 新增
     */
    @Override
    public Js add(AddJsRequest request) {

        //如果当前角色为默认角色
        if (request.getSfmr()) {

            if (StringUtils.equals(request.getYhjslx(), UserTypeEnum.IN_USER.getCode())) {


                Js updateDefaultJs = jsMapper.selectOne(new QueryWrapper<Js>().eq("jslb", request.getJslb())
                        .eq("yhjslx", UserTypeEnum.IN_USER.getCode()).eq("sfmr", true).eq("jg_id",request.getJgId()));

                if (updateDefaultJs != null) {

                    updateDefaultJs.setSfmr(false);
                    jsMapper.updateById(updateDefaultJs);
                }

            } else {

                Js updateDefaultJs = jsMapper.selectOne(new QueryWrapper<Js>().eq("jslb", request.getJslb())
                        .eq("yhjslx", UserTypeEnum.OUT_USER.getCode()).eq("sfmr", true).eq("jg_id",request.getJgId()));

                if (updateDefaultJs != null) {
                    updateDefaultJs.setSfmr(false);
                    jsMapper.updateById(updateDefaultJs);
                }
            }
        }

        Integer count = jsMapper.selectCount(new QueryWrapper<Js>());
        String code = String.format(" %04d", count.intValue() + 1);

        Js js = new Js();
        BeanUtils.copyProperties(request, js);

        Date now = new Date();

        LoginUser loginUser = SystemConstant.threadLocal.get();

        Long userId = null;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        js.setCjr(userId);
        js.setGxr(userId);
        js.setCjsj(now);
        js.setGxsj(now);
        js.setJsbm(code);

        jsMapper.insert(js);

        if (!CollectionUtils.isEmpty(request.getCdCzIds())) {

            List<AddJsCdRequest> menuAndOptions = request.getCdCzIds();

            for (AddJsCdRequest menuAndOption : menuAndOptions) {

                //设置角色菜单关联
                JsCdGl jsCdGl = new JsCdGl();

                jsCdGl.setCdId(menuAndOption.getCdIds());
                jsCdGl.setJsId(js.getId());

                jsCdGl.setCjr(userId);
                jsCdGl.setGxr(userId);
                jsCdGl.setGxsj(now);
                jsCdGl.setCjsj(now);

                jsCdGlMapper.insert(jsCdGl);

                //设置角色菜单操作关联
                if (CollectionUtils.isEmpty(menuAndOption.getCzIds())) {
                    continue;
                }

                List<Long> optionIds = menuAndOption.getCzIds();
                for (Long optionId : optionIds) {

                    JsCdCzGl jsCdCzGl = new JsCdCzGl();

                    jsCdCzGl.setCdId(menuAndOption.getCdIds());
                    jsCdCzGl.setJsId(js.getId());
                    jsCdCzGl.setCzId(optionId);

                    jsCdCzGl.setCjr(userId);
                    jsCdCzGl.setGxr(userId);
                    jsCdCzGl.setGxsj(now);
                    jsCdCzGl.setCjsj(now);

                    jsCdCzGlMapper.insert(jsCdCzGl);
                }
            }
        }

        return js;
    }

    /**
     * 更新
     */
    @Override
    public Js update(Js Js) {

        //如果当前角色为默认角色
        if (Js.getSfmr()) {

            if (StringUtils.equals(Js.getYhjslx(), UserTypeEnum.IN_USER.getCode())) {


                Js updateDefaultJs = jsMapper.selectOne(new QueryWrapper<Js>().eq("jslb", Js.getJslb())
                        .eq("yhjslx", UserTypeEnum.IN_USER.getCode()).eq("sfmr", true));

                if (updateDefaultJs != null) {

                    updateDefaultJs.setSfmr(false);
                    jsMapper.updateById(updateDefaultJs);
                }
            } else {

                Js updateDefaultJs = jsMapper.selectOne(new QueryWrapper<Js>().eq("jslb", Js.getJslb())
                        .eq("yhjslx", UserTypeEnum.OUT_USER.getCode()).eq("sfmr", true));

                if (updateDefaultJs != null) {

                    updateDefaultJs.setSfmr(false);
                    jsMapper.updateById(updateDefaultJs);
                }
            }
        }

        jsMapper.updateById(Js);
        return Js;
    }

    /**
     * 删除
     */
    @Override
    public void delete(Js js) {

        LoginUser loginUser = SystemConstant.threadLocal.get();

        //设置删除状态，更新删除时间
        js.setSfsc(true);
        js.setCjsj(new Date());
        if (loginUser != null) {
            js.setGxr(loginUser.getUserId());
        }
        update(js);

        deleteJsCdCzGlByJsIdAndCdIdAndCzIds(js.getId(), null, null);
        deleteJsCdGlByJsIdAndCdIds(js.getId(), null);
    }

    /**
     * 更新 - 更新角色、角色菜单关联、角色菜单操作关联
     */
    @Override
    public Js updateJsCdCzGl(AddJsRequest request) {

        //如果当前角色为默认角色
        if (request.getSfmr()) {

            if (StringUtils.equals(request.getYhjslx(), UserTypeEnum.IN_USER.getCode())) {


                Js updateDefaultJs = jsMapper.selectOne(new QueryWrapper<Js>().eq("jslb", request.getJslb())
                        .eq("yhjslx", UserTypeEnum.IN_USER.getCode()).eq("sfmr", true));

                if (updateDefaultJs != null) {

                    updateDefaultJs.setSfmr(false);
                    jsMapper.updateById(updateDefaultJs);
                }
            } else {

                Js updateDefaultJs = jsMapper.selectOne(new QueryWrapper<Js>()
                        .eq("yhjslx", UserTypeEnum.OUT_USER.getCode()).eq("sfmr", true));

                if (updateDefaultJs != null) {
                    updateDefaultJs.setSfmr(false);
                    jsMapper.updateById(updateDefaultJs);
                }
            }
        }

        //设置角色更新后的信息
        Js js = new Js();
        BeanUtils.copyProperties(request, js, "cjsj", "jsbm", "cjr");

        Long userId = null;
        Date now = new Date();

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        js.setGxr(userId);
        js.setGxsj(now);

        jsMapper.updateById(js);

        if (CollectionUtils.isEmpty(request.getCdCzIds())) {

            //删除当前角色所有菜单、操作关联
            deleteJsCdCzGlByJsIdAndCdIdAndCzIds(request.getId(), null, null);
            deleteJsCdGlByJsIdAndCdIds(request.getId(), null);
            return js;
        }

        //获取之前的角色菜单关联信息
        List<GetJsCdGlVO> jsCdGls = jsCdGlMapper.getJsCdGlByJsId(js.getId());
        List<Long> oldCdIds = jsCdGls.stream().map(GetJsCdGlVO::getId).collect(Collectors.toList());

        //获取要更新的角色菜单关联信息
        List<Long> newCdIds = request.getCdCzIds().stream().map(AddJsCdRequest::getCdIds).collect(Collectors.toList());

        //需要新增的角色菜单关联
        List<Long> addCdIds = Lists.newArrayList();

        //更新角色菜单关联
        newCdIds.forEach(newCdId -> {

            //判断之前的角色菜单关联时候包含
            if (oldCdIds.contains(newCdId)) {
                oldCdIds.remove(newCdId);
            } else {
                addCdIds.add(newCdId);
            }
        });

        //删除角色菜单关联
        if (!CollectionUtils.isEmpty(oldCdIds)) {
            //删除角色菜单关联
            deleteJsCdGlByJsIdAndCdIds(js.getId(), oldCdIds);

            //删除角色菜单操作关联
            for (Long oldCdId : oldCdIds) {
                deleteJsCdCzGlByJsIdAndCdIdAndCzIds(js.getId(), oldCdId, null);
            }
        }

        Map<Long, AddJsCdRequest> addJsCdMap = request.getCdCzIds().stream().collect(Collectors.toMap(AddJsCdRequest::getCdIds, addJsCdRequest -> addJsCdRequest));

        //添加新的角色菜单关联
        for (Long addCdId : addCdIds) {

            JsCdGl jsCdGl = new JsCdGl();

            jsCdGl.setJsId(js.getId());
            jsCdGl.setCdId(addCdId);

            jsCdGl.setGxsj(now);
            jsCdGl.setCjsj(now);
            jsCdGl.setCjr(userId);
            jsCdGl.setGxr(userId);

            jsCdGlMapper.insert(jsCdGl);

            //获取新增的菜单要添加的操作列表
            AddJsCdRequest addJsCd = addJsCdMap.get(addCdId);
            if (!CollectionUtils.isEmpty(addJsCd.getCzIds())) {

                for (Long czId : addJsCd.getCzIds()) {

                    JsCdCzGl jsCdCzGl = new JsCdCzGl();

                    jsCdCzGl.setJsId(js.getId());
                    jsCdCzGl.setCdId(addCdId);
                    jsCdCzGl.setCzId(czId);

                    jsCdCzGl.setCjsj(now);
                    jsCdCzGl.setGxsj(now);
                    jsCdCzGl.setCjr(userId);
                    jsCdCzGl.setGxr(userId);

                    jsCdCzGlMapper.insert(jsCdCzGl);
                }
            }
        }

        //获取当前角色下关联的角色菜单操作关联
        List<JsCdCzGl> allJsCdCzGls = jsCdCzGlMapper.getByJsId(js.getId());

        //比较设置角色菜单操作关联
        for (GetJsCdGlVO jsCdGl : jsCdGls) {

            //获取当前角色、菜单下之前所有角色菜单操作关联
            List<JsCdCzGl> oldJsCdCzGls = Lists.newArrayList();

            Iterator<JsCdCzGl> allJsCdCzGlsIterator = allJsCdCzGls.iterator();

            while (allJsCdCzGlsIterator.hasNext()) {

                JsCdCzGl thisJsCdCzGl = allJsCdCzGlsIterator.next();

                if (Objects.equals(thisJsCdCzGl.getCdId(), jsCdGl.getId())) {

                    allJsCdCzGlsIterator.remove();
                    oldJsCdCzGls.add(thisJsCdCzGl);
                }
            }

            /*for (JsCdCzGl allJsCdCzGl : allJsCdCzGls) {

                if (Objects.equals(allJsCdCzGl.getCdId(), jsCdGl.getId())) {
                    oldJsCdCzGls.add(allJsCdCzGl);
                }
            }*/

            List<Long> oldCzIds = null;

            if (!CollectionUtils.isEmpty(oldJsCdCzGls)) {
                oldCzIds = oldJsCdCzGls.stream().map(JsCdCzGl::getCzId).collect(Collectors.toList());
            }

            //新的角色菜单操作关联Id
            List<Long> newCzIds = Lists.newArrayList();

            //要添加的角色菜单操作关联的操作ID
            List<Long> addCzIds = Lists.newArrayList();

            //判断新的角色菜单关联是否有更新
            for (AddJsCdRequest menuAndOptionId : request.getCdCzIds()) {

                if (Objects.equals(jsCdGl.getId(), menuAndOptionId.getCdIds())) {
                    newCzIds = menuAndOptionId.getCzIds();
                }
            }

            if (CollectionUtils.isEmpty(oldCzIds)) {

                addCzIds = newCzIds;
            } else {

                for (Long newCzId : newCzIds) {

                    if (oldCzIds.contains(newCzId)) {
                        oldCzIds.remove(newCzId);
                    } else {
                        addCzIds.add(newCzId);
                    }
                }
            }

            //删除角色菜单操作关联
            if (jsCdGl != null && !CollectionUtils.isEmpty(oldCzIds)) {
                deleteJsCdCzGlByJsIdAndCdIdAndCzIds(js.getId(), jsCdGl.getId(), oldCzIds);
            }

            //增加角色菜单操作关联
            for (Long addCzId : addCzIds) {

                JsCdCzGl jsCdCzGl = new JsCdCzGl();

                jsCdCzGl.setJsId(js.getId());
                jsCdCzGl.setCdId(jsCdGl.getId());
                jsCdCzGl.setCzId(addCzId);

                jsCdCzGl.setCjsj(now);
                jsCdCzGl.setGxsj(now);
                jsCdCzGl.setCjr(userId);
                jsCdCzGl.setGxr(userId);

                jsCdCzGlMapper.insert(jsCdCzGl);
            }
        }

        return js;
    }

    /**
     * 通过角色ID和菜单ID删除角色菜单关联
     */
    @Override
    public void deleteJsCdGlByJsIdAndCdIds(Long jsId, List<Long> cdIds) {

        QueryWrapper query = new QueryWrapper();
        query.eq("js_id", jsId);
        if (!CollectionUtils.isEmpty(cdIds)) {
            query.in("cd_id", cdIds);
        }

        jsCdGlMapper.delete(query);
    }

    /**
     * 通过角色ID和菜单ID和操作ID删除角色菜单操作关联
     */
    @Override
    public void deleteJsCdCzGlByJsIdAndCdIdAndCzIds(Long jsId, Long cdId, List<Long> czIds) {

        QueryWrapper query = new QueryWrapper();
        query.eq("js_id", jsId);
        if (cdId != null) {
            query.eq("cd_id", cdId);
        }
        if (!CollectionUtils.isEmpty(czIds)) {
            query.in("cz_id", czIds);
        }

        jsCdCzGlMapper.delete(query);
    }
}
