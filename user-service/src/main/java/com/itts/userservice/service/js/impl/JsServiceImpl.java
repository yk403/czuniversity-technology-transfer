package com.itts.userservice.service.js.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.userservice.mapper.cz.CzMapper;
import com.itts.userservice.mapper.js.JsCdCzGlMapper;
import com.itts.userservice.mapper.js.JsCdGlMapper;
import com.itts.userservice.mapper.js.JsMapper;
import com.itts.userservice.model.cz.Cz;
import com.itts.userservice.model.js.Js;
import com.itts.userservice.model.js.JsCdCzGl;
import com.itts.userservice.model.js.JsCdGl;
import com.itts.userservice.request.AddJsCdRequest;
import com.itts.userservice.request.AddJsRequest;
import com.itts.userservice.service.js.JsService;
import com.itts.userservice.vo.GetJsCdCzGlVO;
import com.itts.userservice.vo.GetJsCdGlVO;
import com.itts.userservice.vo.GetJsVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
public class JsServiceImpl implements JsService {


    @Resource
    private JsMapper jsMapper;

    @Resource
    private JsCdGlMapper jsCdGlMapper;

    @Resource
    private JsCdCzGlMapper jsCdCzGlMapper;

    @Resource
    private CzMapper czMapper;

    /**
     * 获取列表 - 分页
     */
    @Override
    public PageInfo<Js> findByPage(Integer pageNum, Integer pageSize, String name, String systemType) {

        PageHelper.startPage(pageNum, pageSize);

        QueryWrapper<Js> query = new QueryWrapper<>();
        query.eq("sfsc", false);
        if (StringUtils.isNotBlank(name)) {
            query.like("jsmc", name);
        }
        if (StringUtils.isNotBlank(systemType)) {
            query.eq("xtlx", systemType);
        }

        List<Js> js = jsMapper.selectList(query);
        PageInfo<Js> tJsPageInfo = new PageInfo<>(js);

        return tJsPageInfo;
    }

    /**
     * 通过系统类型是否为默认角色获取角色信息
     *
     * @param systemType  系统类型
     * @param defaultFlag 是否为默认角色
     * @return
     * @author liuyingming
     */
    @Override
    public List<Js> findBySystemTypeAndDefault(String systemType, Boolean defaultFlag) {

        QueryWrapper query = new QueryWrapper();
        query.eq("xtlx", systemType);
        query.eq("sfmr", defaultFlag);
        query.eq("sfsc", false);

        List jsList = jsMapper.selectList(query);

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

        //获取所有操作
        List<Cz> allCz = czMapper.selectList(null);
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

                Cz cz = czMap.get(jsCdCzGl.getId());
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

        jsMapper.insert(js);

        if (!CollectionUtils.isEmpty(request.getMenuAndOptionIds())) {

            List<AddJsCdRequest> menuAndOptions = request.getMenuAndOptionIds();

            for (AddJsCdRequest menuAndOption : menuAndOptions) {

                //设置角色菜单关联
                JsCdGl jsCdGl = new JsCdGl();

                jsCdGl.setCdId(menuAndOption.getMenuId());
                jsCdGl.setJsId(js.getId());

                jsCdGl.setCjr(userId);
                jsCdGl.setGxr(userId);
                jsCdGl.setGxsj(now);
                jsCdGl.setCjsj(now);

                jsCdGlMapper.insert(jsCdGl);

                //设置角色菜单操作关联
                if (CollectionUtils.isEmpty(menuAndOption.getOptionIds())) {
                    continue;
                }

                List<Long> optionIds = menuAndOption.getOptionIds();
                for (Long optionId : optionIds) {

                    JsCdCzGl jsCdCzGl = new JsCdCzGl();

                    jsCdCzGl.setCdId(menuAndOption.getMenuId());
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
        jsMapper.updateById(Js);
        return Js;
    }
}
