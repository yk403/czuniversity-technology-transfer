package com.itts.userservice.service.cd.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.ServiceException;
import com.itts.userservice.dto.GetCdAndCzDTO;
import com.itts.userservice.dto.GetCdCzGlDTO;
import com.itts.userservice.enmus.CdEnum;
import com.itts.userservice.enmus.JgTpyeEnum;
import com.itts.userservice.mapper.cd.CdCzGlMapper;
import com.itts.userservice.mapper.cd.CdMapper;
import com.itts.userservice.mapper.cz.CzMapper;
import com.itts.userservice.mapper.js.JsCdCzGlMapper;
import com.itts.userservice.mapper.js.JsCdGlMapper;
import com.itts.userservice.model.cd.Cd;
import com.itts.userservice.model.cd.CdCzGl;
import com.itts.userservice.model.cz.Cz;
import com.itts.userservice.model.js.Js;
import com.itts.userservice.model.js.JsCdCzGl;
import com.itts.userservice.model.js.JsCdGl;
import com.itts.userservice.request.AddCdRequest;
import com.itts.userservice.service.cd.CdService;
import com.itts.userservice.vo.CdTreeVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author fl
 * @since 2021-03-24
 */
@Service
public class CdServiceImpl implements CdService {

    @Resource
    private CdMapper cdMapper;

    @Autowired
    private CdCzGlMapper cdCzGlMapper;

    @Autowired
    private CzMapper czMapper;

    @Autowired
    private JsCdCzGlMapper jsCdCzGlMapper;

    @Autowired
    private JsCdGlMapper jsCdGlMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取列表 - 分页
     */
    @Override
    public PageInfo<GetCdAndCzDTO> findByPage(Integer pageNum, Integer pageSize, String name, String systemType, String modelType) {

        PageHelper.startPage(pageNum, pageSize);

        QueryWrapper<Cd> query = new QueryWrapper<>();

        query.eq("sfsc", false);
        if (StringUtils.isNotBlank(name)) {
            query.like("cdmc", name);
        }
        if (StringUtils.isNotBlank(systemType)) {
            query.eq("xtlx", systemType);
        }
        if (StringUtils.isNotBlank(modelType)) {
            query.eq("mklx", modelType);
        }

        query.orderByDesc("cjsj");

        List<Cd> cds = cdMapper.selectList(query);
        PageInfo<GetCdAndCzDTO> pageInfo = new PageInfo(cds);

        //查询菜单拥有的操作
        List<GetCdAndCzDTO> dtos = Lists.newArrayList();
        cds.forEach(cd -> {

            GetCdAndCzDTO dto = new GetCdAndCzDTO();
            BeanUtils.copyProperties(cd, dto);

            List<GetCdCzGlDTO> czs = cdCzGlMapper.getCdCzGlByCdId(cd.getId());
            dto.setCzs(czs);

            dtos.add(dto);
        });

        pageInfo.setList(dtos);
        return pageInfo;
    }

    /**
     * 通过ID获取当前菜单及其子菜单（树形）
     */
    @Override
    public List<CdTreeVO> findByTree(List<Cd> cds,String jglx) {

        List<CdTreeVO> vos = Lists.newArrayList();

        //循环遍历菜单获取其子级，并组装树状
        for (Cd cd : cds) {

            //设置父级菜单信息
            CdTreeVO vo = new CdTreeVO();
            BeanUtils.copyProperties(cd, vo);
            vos.add(vo);

            //获取当前菜单拥有的操作
            List<Cz> czs = czMapper.findByCdId(cd.getId());
            if (!CollectionUtils.isEmpty(czs)) {
                vo.setCzs(czs);
            }

            //获取当前菜单及所有子菜单
            List<Cd> children = findThisAndAllChildrenByCode(cd.getCdbm());

            if(Objects.equals(jglx, JgTpyeEnum.BRANCH.getKey()) && Objects.equals(cd.getCdmc(),CdEnum.ZDMHWH.getMsg())){
                Iterator<Cd> iterator = children.iterator();
                while (iterator.hasNext()){
                    Cd next = iterator.next();
                    if(Objects.equals(next.getCdmc(), CdEnum.SJBWH.getMsg())){
                        iterator.remove();
                    }
                }
            }

            //去除当前菜单
            children.remove(cd);

            //递归组装树状
            combineMenusToTree(vo, children);
        }

        return vos;
    }

    /**
     * 通过角色获取菜单操作
     */
    @Override
    public List<Cz> getOptionsByRole(List<Js> js, Long menuId) {

        //获取角色ID
        List<Long> jsIds = js.stream().map(Js::getId).collect(Collectors.toList());

        List<JsCdCzGl> jsCdczGls = jsCdCzGlMapper.selectList(new QueryWrapper<JsCdCzGl>()
                .eq("cd_id", menuId).in("js_id", jsIds));

        if (CollectionUtils.isEmpty(jsCdczGls)) {
            return null;
        }

        List<Long> czIds = Lists.newArrayList(jsCdczGls.stream().map(JsCdCzGl::getCzId).collect(Collectors.toSet()));
        if (CollectionUtils.isEmpty(czIds)) {
            return null;
        }

        List<Cz> czs = czMapper.selectList(new QueryWrapper<Cz>().in("id", czIds));
        return czs;
    }

    /**
     * 通过角色获取菜单
     */
    @Override
    public List<CdTreeVO> getMenuByRole(List<Js> js,String xtlx) {

        List<Long> jsIds = js.stream().map(Js::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(jsIds)) {
            return null;
        }

        List<JsCdGl> jsCdGls = jsCdGlMapper.selectList(new QueryWrapper<JsCdGl>().in("js_id", jsIds));
        if (CollectionUtils.isEmpty(jsCdGls)) {
            return null;
        }

        List<Long> cdIds = jsCdGls.stream().map(JsCdGl::getCdId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cdIds)) {
            return null;
        }


        List<Cd> cds =new ArrayList<>();
        if(StringUtils.isNotBlank(xtlx)){
            List<Cd> collect = cdMapper.selectList(new QueryWrapper<Cd>().in("id", cdIds).eq("sfsc", false));
            cds = collect.stream().filter(cd -> Objects.equals(cd.getXtlx(), xtlx)).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(cds)) {
                return null;
            }
        }else {
            cds = cdMapper.selectList(new QueryWrapper<Cd>().in("id", cdIds).eq("sfsc", false));
            if (CollectionUtils.isEmpty(cds)) {
                return null;
            }

        }
        //获取菜单的一级菜单
        List<Cd> parentCds = getParentMenu(cds);

        //菜单层级组装
        List<Cd> finalCds = cds;
        List<CdTreeVO> vos = parentCds.stream().map(obj -> {

            CdTreeVO vo = assemblyLevel(finalCds, obj);
            return vo;
        }).collect(Collectors.toList());

        return vos;
    }

    /**
     * 获取一级菜单列表
     */
    private List<Cd> getParentMenu(List<Cd> cds) {

        Iterator<Cd> iterator = cds.iterator();
        List<Cd> parentMenus = Lists.newArrayList();

        while (iterator.hasNext()) {

            Cd cd = iterator.next();
            if (cd != null) {

                String[] cjs = cd.getCj().split("-");
                if(cjs.length == 1){
                    parentMenus.add(cd);
                    iterator.remove();
                }
            }
        }

        return parentMenus;
    }

    /**
     * 通过父级ID获取菜单列表
     */
    private CdTreeVO assemblyLevel(List<Cd> cds, Cd parentCd) {
        
        if(parentCd == null){
            return null;
        }

        CdTreeVO parentVo = new CdTreeVO();
        BeanUtils.copyProperties(parentCd, parentVo);

        Iterator<Cd> iterator = cds.iterator();

        List<CdTreeVO> childCds = Lists.newArrayList();

        while (iterator.hasNext()) {

            Cd cd = iterator.next();
            if(cd != null){

                if(cd.getFjcdId().longValue() == parentVo.getId().longValue()){

                    iterator.remove();

                    //组装当前菜单及子菜单
                    CdTreeVO childVO = assemblyLevel(cds, cd);
                    childCds.add(childVO);
                }
            }
        }

        if(CollectionUtils.isEmpty(childCds)){
            parentVo.setChildCds(null);
        }else{
            parentVo.setChildCds(childCds);
        }

        return parentVo;
    }

    /**
     * 通过菜单编码获取当前菜单和所有子菜单
     */
    @Override
    public List<Cd> findThisAndAllChildrenByCode(String code) {

        List<Cd> cds = cdMapper.findThisAndAllChildrenByCode(code);
        return cds;
    }

    /**
     * 通过名称和编码获取列表
     */
    @Override
    public PageInfo<GetCdAndCzDTO> findByNameOrCodePage(Integer pageNum, Integer pageSize, String parameter, String systemType, String modelType) {


        PageHelper.startPage(pageNum, pageSize);

        List<Cd> cds = cdMapper.selectByParameterList(parameter, systemType, modelType);
        if (cds == null) {
            return null;
        }
        PageInfo<GetCdAndCzDTO> pageInfo = new PageInfo(cds);

        //查询菜单拥有的操作
        List<GetCdAndCzDTO> dtos = Lists.newArrayList();
        cds.forEach(cd -> {

            GetCdAndCzDTO dto = new GetCdAndCzDTO();
            BeanUtils.copyProperties(cd, dto);

            List<GetCdCzGlDTO> czs = cdCzGlMapper.getCdCzGlByCdId(cd.getId());
            dto.setCzs(czs);

            dtos.add(dto);
        });

        pageInfo.setList(dtos);
        return pageInfo;
    }

    /**
     * 通过父级菜单ID获取其子级信息
     */
    @Override
    public List<Cd> findByParentId(Long parentId, String systemType) {

        List<Cd> cds = cdMapper.findByParentId(parentId, systemType);
        return cds;
    }

    /**
     * 通过父级菜单ID获取其子级数量
     */
    @Override
    public Long countByParentId(Long parentId) {

        Long count = cdMapper.countByParentId(parentId);
        return count;
    }

    /**
     * 通过ID获取详情
     */
    @Override
    public Cd getById(Long id) {
        Cd cd = cdMapper.selectById(id);
        return cd;
    }

    /**
     * 通过ID获取菜单、菜单关联操作
     */
    @Override
    public GetCdAndCzDTO getCdAndCzById(Long id) {

        Cd cd = cdMapper.selectById(id);
        if (cd == null) {
            return null;
        }

        List<GetCdCzGlDTO> czs = cdCzGlMapper.getCdCzGlByCdId(id);

        GetCdAndCzDTO dto = new GetCdAndCzDTO();
        BeanUtils.copyProperties(cd, dto);

        dto.setCzs(czs);
        return dto;
    }

    /**
     * 新增
     */
    @Override
    public Cd add(AddCdRequest cd) {
        Cd cdbm = cdMapper.selectOne(new QueryWrapper<Cd>().eq("cdbm", cd.getCdbm()));
        if(cdbm != null){
            throw new ServiceException(ErrorCodeEnum.USER_NUMBER_EXISTS_ERROR);
        }
        LoginUser loginUser = SystemConstant.threadLocal.get();

        Long userId = null;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        Date now = new Date();
        cd.setCjr(userId);
        cd.setGxr(userId);
        cd.setCjsj(now);
        cd.setGxsj(now);

        //设置层级
        if (cd.getFjcdId() == 0L) {
            cd.setCj(cd.getCdbm());
        } else {

            Cd fjcd = cdMapper.selectById(cd.getFjcdId());
            cd.setCj(fjcd.getCj() + "-" + cd.getCdbm());
        }

        cdMapper.insert(cd);

        //甚至菜单与操作关联信息
        addCdCzGl(cd.getId(), cd.getCzIds(), userId, now);

        return cd;
    }

    /**
     * 更新
     */
    @Override
    public Cd update(AddCdRequest cd, Cd old) {

        //浅拷贝，更新的数据覆盖已存数据,并过滤指定字段
        BeanUtils.copyProperties(cd, old, "id", "cjsj", "cjr");

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser != null) {
            old.setGxr(loginUser.getUserId());
        }

        Date now = new Date();
        old.setGxsj(now);

        //设置层级
        if (old.getFjcdId() == 0L) {
            old.setCj(old.getCdbm());
        } else {

            Cd fjcd = cdMapper.selectById(old.getFjcdId());
            old.setCj(fjcd.getCj() + "-" + old.getCdbm());
        }

        cdMapper.updateById(old);

        //更新菜单的操作关联
        List<GetCdCzGlDTO> oldCzs = cdCzGlMapper.getCdCzGlByCdId(old.getId());

        Long userId = null;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        if (CollectionUtils.isEmpty(oldCzs)) {

            addCdCzGl(old.getId(), cd.getCzIds(), userId, now);
        } else {

            //要被删除的操作ID
            List<Long> oldCzIds = oldCzs.stream().map(GetCdCzGlDTO::getId).collect(Collectors.toList());
            //要被新增的操作ID
            List<Long> addCzIds = Lists.newArrayList();

            cd.getCzIds().forEach(czId -> {
                if (oldCzIds.contains(czId)) {

                    oldCzIds.remove(czId);
                } else {

                    addCzIds.add(czId);
                }
            });

            //删除菜单操作关联
            deleteCdCzGl(cd.getId(), oldCzIds);

            //添加菜单操作关联
            addCdCzGl(cd.getId(), addCzIds, userId, now);
        }
        return old;
    }

    /**
     *
     */
    @Override
    public void delete(Cd cd) {

        //设置删除状态
        cd.setSfsc(true);
        cd.setGxsj(new Date());

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser != null) {
            cd.setGxr(loginUser.getUserId());
        }

        cdMapper.updateById(cd);
    }

    /**
     * 添加菜单操作关联
     */
    private void addCdCzGl(Long cdId, List<Long> czIds, Long userId, Date now) {

        if (CollectionUtils.isEmpty(czIds)) {
            return;
        }

        czIds.forEach(czId -> {

            CdCzGl cdCzGl = new CdCzGl();

            cdCzGl.setCjr(userId);
            cdCzGl.setGxr(userId);
            cdCzGl.setCjsj(now);
            cdCzGl.setGxsj(now);
            cdCzGl.setCdId(cdId);
            cdCzGl.setCzId(czId);

            cdCzGlMapper.insert(cdCzGl);
        });
    }

    /**
     * 删除菜单操作关联
     */
    private void deleteCdCzGl(Long cdId, List<Long> czIds) {

        if (CollectionUtils.isEmpty(czIds)) {
            return;
        }

        QueryWrapper query = new QueryWrapper();
        query.eq("cd_id", cdId);
        query.in("cz_id", czIds);

        cdCzGlMapper.delete(query);
    }

    /**
     * 组装菜单树形数据
     */
    private CdTreeVO combineMenusToTree(CdTreeVO cd, List<Cd> children) {

        if (CollectionUtils.isEmpty(children)) {
            return cd;
        }

        List<CdTreeVO> childrenVOs = Lists.newArrayList();

        /*children.forEach(child ->{
            if (Objects.equals(cd.getId(), child.getFjcdId())) {

                CdTreeVO childVO = new CdTreeVO();
                BeanUtils.copyProperties(child, childVO);
                childrenVOs.add(childVO);

                combineMenusToTree(childVO, children);
            }
        });*/

        Iterator<Cd> cdIterator = children.iterator();

        while (cdIterator.hasNext()) {

            Cd child = cdIterator.next();

            if (Objects.equals(cd.getId(), child.getFjcdId())) {

                CdTreeVO childVO = new CdTreeVO();
                BeanUtils.copyProperties(child, childVO);

                //获取当前菜单拥有的操作
                List<Cz> czs = czMapper.findByCdId(child.getId());
                if (!CollectionUtils.isEmpty(czs)) {
                    childVO.setCzs(czs);
                }

                childrenVOs.add(childVO);

                //将当前子级菜单从列表中删除
                cdIterator.remove();

                combineMenusToTree(childVO, children);
            }
        }

        cd.setChildCds(childrenVOs);
        return cd;
    }
}
