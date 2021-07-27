package com.itts.userservice.service.jggl.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.userservice.common.UserServiceCommon;
import com.itts.userservice.enmus.GroupTypeEnum;
import com.itts.userservice.feign.persontraining.gngl.GnglFeignService;
import com.itts.userservice.feign.persontraining.rmdt.RmdtFeignService;
import com.itts.userservice.mapper.jggl.JgglMapper;
import com.itts.userservice.model.jggl.Jggl;
import com.itts.userservice.request.gngl.AddGnglRequest;
import com.itts.userservice.request.rmdt.AddRmdtRequest;
import com.itts.userservice.service.jggl.JgglService;
import com.itts.userservice.vo.JgglVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.itts.common.constant.SystemConstant.threadLocal;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fl
 * @since 2021-03-30
 */
@Slf4j
@Service
public class JgglServiceImpl implements JgglService {

    @Resource
    private JgglMapper jgglMapper;

    @Autowired
    private GnglFeignService gnglFeignService;
    @Resource
    private RmdtFeignService rmdtFeignService;

    /**
     * 轮播图功能简介
     */
    @Value(value = "${jdypt.gngl.lbt}")
    private String lbt;

    /**
     * 栏目菜单功能简介
     */
    @Value(value = "${jdypt.gngl.lmcd}")
    private String lmcd;

    /**
     * 视图功能简介
     */
    @Value(value = "${jdypt.gngl.stgn}")
    private String stgn;

    /**
     * 友情链接功能简介
     */
    @Value(value = "${jdypt.gngl.yqlj}")
    private String yqlj;

    /**
     * 热门动态功能简介
     */
    @Value(value = "${jdypt.gngl.rmdt}")
    private String rmdt;

    @Value(value = "${jdypt.rmdt.xw}")
    private String xw;

    @Value(value = "${jdypt.rmdt.gg}")
    private String gg;

    @Override
    public PageInfo<Jggl> findPage(Integer pageNum, Integer pageSize, String string, String jgbm, String jglb, String lx) {
        //传入机构编码
        if (!StringUtils.isBlank(jgbm)) {
            String cj = jgglMapper.selectByCode(jgbm).getCj();
            jgbm = cj;
            //传入string
        } else if (!StringUtils.isBlank(string)) {
            PageHelper.startPage(pageNum, pageSize);
            List<Jggl> jggls = jgglMapper.selectByString(string);
            PageInfo<Jggl> pageInfo = new PageInfo<>(jggls);
            return pageInfo;
            //传入类型
        } else if (!StringUtils.isBlank(lx)) {
            PageHelper.startPage(pageNum, pageSize);
            QueryWrapper<Jggl> jgglQueryWrapper = new QueryWrapper<>();
            jgglQueryWrapper.eq("sfsc", false)
                    .eq("lx", lx)
                    .orderByDesc("cjsj");
            List<Jggl> jgglList = jgglMapper.selectList(jgglQueryWrapper);
            PageInfo<Jggl> pageInfo = new PageInfo<>(jgglList);
            return pageInfo;
        } else if (!StringUtils.isBlank(jglb)) {
            PageHelper.startPage(pageNum, pageSize);
            QueryWrapper<Jggl> jgglQueryWrapper = new QueryWrapper<>();
            jgglQueryWrapper.eq("sfsc", false)
                    .eq("jglb", jglb)
                    .orderByDesc("cjsj");
            List<Jggl> jgglList = jgglMapper.selectList(jgglQueryWrapper);
            PageInfo<Jggl> pageInfo = new PageInfo<>(jgglList);
            return pageInfo;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Jggl> list = jgglMapper.findThisAndChildByCode(jgbm);
        PageInfo<Jggl> pageInfo = new PageInfo<>(list);
        return pageInfo;

    }

    /**
     * 获取指定分页
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Jggl> findByPage(Integer pageNum, Integer pageSize, String jgbm) {

        log.info("【机构管理 - 分页查询】");

        if (!StringUtils.isBlank(jgbm)) {
            String cj = jgglMapper.selectByCode(jgbm).getCj();
            jgbm = cj;
        }

        PageHelper.startPage(pageNum, pageSize);

        List<Jggl> list = jgglMapper.findThisAndChildByCode(jgbm);
        PageInfo<Jggl> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

    @Override
    public PageInfo<Jggl> selectByString(Integer pageNum, Integer pageSize, String string) {
        PageHelper.startPage(pageNum, pageSize);
        List<Jggl> jggls = jgglMapper.selectByString(string);
        PageInfo<Jggl> pageInfo = new PageInfo<>(jggls);
        return pageInfo;
    }

    /**
     * 获取基地列表
     */
    @Override
    public List<Jggl> findBaseList(Long jgId) {

        List<Jggl> jds = jgglMapper.findBaseList(jgId);
        return jds;
    }

    /**
     * 获取机构管理树
     *
     * @return
     */
    @Override
    public List<JgglVO> findJgglVO(String jgbm) {

        if (StringUtils.isBlank(jgbm)) {
            jgbm = UserServiceCommon.GROUP_SUPER_PARENT_CODE;
        }

        //获取所有机构
        List<JgglVO> jgglVOS = jgglMapper.selectJggl();

        //获取机构树
        List<JgglVO> jgglVOList = buildJgglVOTree(jgglVOS, jgbm);
        return jgglVOList;
    }

    /**
     * 获取关键字机构管理树
     *
     * @return
     */
    @Override
    public List<JgglVO> findStringJgglVO(String string) {
        List<JgglVO> jgglVOS = jgglMapper.selectStringJggl(string);
        List<JgglVO> jgglVOList = buildJgglVOTree(jgglVOS, "000");
        String[] sisoi = "100-199".split("-");
        return jgglVOList;
    }

    private List<JgglVO> buildJgglVOTree(List<JgglVO> jgglVOS, String fatherCode) {
        List<JgglVO> treeList = new ArrayList<>();
        jgglVOS.forEach(jgglVO -> {
            if (jgglVO.getFjbm().equals(fatherCode)) {
                jgglVO.setJgglVOList(buildJgglVOTree(jgglVOS, jgglVO.getJgbm()));
                treeList.add(jgglVO);
            }
        });
        return treeList;
    }

    @Override
    public Jggl get(Long id) {
        QueryWrapper<Jggl> jgglQueryWrapper = new QueryWrapper<>();
        jgglQueryWrapper.eq("sfsc", false)
                .eq("id", id);
        Jggl jggl = jgglMapper.selectOne(jgglQueryWrapper);
        return jggl;
    }

    @Override
    public List<Jggl> getList(String cj) {
        QueryWrapper<Jggl> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.likeRight("cj", cj)
                .eq("sfsc", false);
        List<Jggl> jggls = jgglMapper.selectList(objectQueryWrapper);
        return jggls;
    }

    /**
     * 查询，通过机构名称
     */
    @Override
    public Jggl selectByJgmc(String jgmc) {
        Jggl jggl = jgglMapper.selectByName(jgmc);
        return jggl;
    }

    /**
     * 查询，通过机构代码
     */
    @Override
    public Jggl selectByJgbm(String jgbm) {
        Jggl jggl = jgglMapper.selectByCode(jgbm);
        return jggl;
    }


    /**
     * 新增
     */
    @Override
    public Jggl add(Jggl jggl) {

        jgglMapper.insert(jggl);

        //如果是总基地或者分基地， 添加试图管理
        if (Objects.equals(jggl.getLx(), GroupTypeEnum.HEADQUARTERS.getKey())
                || Objects.equals(jggl.getLx(), GroupTypeEnum.BRANCH.getKey())) {

            Date now = new Date();
            for (int i = 0; i < 2; i++) {
                AddRmdtRequest addRmdtRequest = new AddRmdtRequest();
                addRmdtRequest.setJgId(jggl.getId());
                addRmdtRequest.setKssysj(now);
                switch (i){
                    case 1:
                        addRmdtRequest.setMc("新闻");
                        addRmdtRequest.setJj(xw);
                        addRmdtRequest.setPx("1");
                        break;
                    case 2:
                        addRmdtRequest.setMc("公告");
                        addRmdtRequest.setJj(gg);
                        addRmdtRequest.setPx("2");
                        break;
                }
                rmdtFeignService.add(addRmdtRequest);
            }

            for (int i = 1; i <= 5; i++) {

                AddGnglRequest request = new AddGnglRequest();

                request.setJgId(jggl.getId());
                request.setSysj(now);

                switch (i) {
                    case 1:
                        request.setGnmc("轮播图");
                        request.setGnjs(lbt);
                        break;
                    case 2:
                        request.setGnmc("栏目菜单");
                        request.setGnjs(lmcd);
                        break;
                    case 3:
                        request.setGnmc("视图功能");
                        request.setGnjs(stgn);
                        break;
                    case 4:
                        request.setGnmc("友情链接");
                        request.setGnjs(yqlj);
                        break;
                    case 5:
                        request.setGnmc("热门动态");
                        request.setGnjs(rmdt);
                        break;
                }

                gnglFeignService.add(request);
            }
        }

        return jggl;
    }

    /**
     * 更新
     */
    @Override
    public Jggl update(Jggl jggl) {
        LoginUser loginUser = threadLocal.get();
        if (loginUser.getUserId() != null) {
            jggl.setGxr(loginUser.getUserId());
        }
        jggl.setGxsj(new Date());
        jgglMapper.updateById(jggl);
        return jggl;
    }


}
