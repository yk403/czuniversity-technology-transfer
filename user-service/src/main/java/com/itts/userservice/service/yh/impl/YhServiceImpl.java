package com.itts.userservice.service.yh.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.itts.common.constant.RedisConstant;
import com.itts.userservice.dto.JsDTO;
import com.itts.userservice.dto.MenuDTO;
import com.itts.userservice.dto.YhDTO;
import com.itts.userservice.mapper.jggl.JgglMapper;
import com.itts.userservice.mapper.js.JsMapper;
import com.itts.userservice.mapper.yh.YhJsGlMapper;
import com.itts.userservice.mapper.yh.YhMapper;
import com.itts.userservice.model.jggl.Jggl;
import com.itts.userservice.model.js.Js;
import com.itts.userservice.model.yh.Yh;
import com.itts.userservice.model.yh.YhJsGl;
import com.itts.userservice.service.yh.YhService;
import com.itts.userservice.vo.YhListVO;
import com.itts.userservice.vo.YhVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author fl
 * @since 2021-03-18
 */
@Service
@Primary
public class YhServiceImpl implements YhService {

    @Resource
    private YhMapper yhMapper;

    @Resource
    private JgglMapper jgglMapper;

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private JsMapper jsMapper;

    @Resource
    private YhJsGlMapper yhJsGlMapper;

    /**
     * 获取列表 - 分页
     */
    @Override
    public PageInfo<YhListVO> findByPage(Integer pageNum, Integer pageSize, String type, Jggl group, String condition) {

        List<Long> groupIds = null;

        if (group != null) {
            //通过编号获取该机构及机构下所有机构的ID
            groupIds = jgglMapper.findThisAndChildByCode(group.getJgbm()).stream().map(Jggl::getId).collect(Collectors.toList());
        }

        PageHelper.startPage(pageNum, pageSize);

        List<Yh> list = yhMapper.findByTypeAndGroupId(type, groupIds, condition);
        List<YhListVO> yhListVOs = this.yh2YhVO(list);

        PageInfo<YhListVO> page = new PageInfo(list);
        page.setList(yhListVOs);
        return page;
    }

    @Override
    public PageInfo<YhDTO> selectByString(Integer pageNum, Integer pageSize, String type, String string) {
        PageHelper.startPage(pageNum,pageSize);
        List<YhDTO> byString = yhMapper.findByString(type, string);
        PageInfo<YhDTO> yhDTOPageInfo = new PageInfo<>(byString);
        return yhDTOPageInfo;
    }

    /**
     * 获取详情
     */
    @Override
    public Yh get(Long id) {
        Yh Yh = yhMapper.selectById(id);
        return Yh;
    }

    /**
     * 通过用户名获取用户信息
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
     * 级联新增
     */
    @Override
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

        //新增用户角色关联表
        Long yhid = Yh.getId();
        YhJsGl yhJsGl = new YhJsGl();
        yhJsGl.setYhId(yhid);
        yhJsGl.setJsId(jsid);
        yhJsGl.setGxsj(new Date());
        yhJsGl.setCjsj(new Date());
        yhJsGlMapper.insert(yhJsGl);
        return true;
    }

    /**
     * 更新
     */
    @Override
    public Yh update(Yh yh) {

        yhMapper.updateById(yh);
        yhJsGlMapper.deleteByUserId(yh.getId());

        return yh;
    }

    /**
     * 级联更新
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Yh updateByYhAndJsmc(Yh Yh, Long jsid) {
        yhMapper.updateById(Yh);
        //新增用户角色关联表
        Long yhid = Yh.getId();
        YhJsGl yhJsGl = new YhJsGl();
        yhJsGl.setYhId(yhid);
        yhJsGl.setJsId(jsid);
        yhJsGlMapper.insert(yhJsGl);
        return Yh;
    }

    /**
     * 查询角色菜单目录
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
            //角色及其菜单的全部列表
            List<JsDTO> jsDTOList = yhMapper.findByUserId(userId, systemType);

            jsDTOList.forEach(jsDTO -> {
                List<MenuDTO> rootMenu = jsDTO.getMenuDTOList();
                //获取菜单树
                List<MenuDTO> menuDTOList = buildMenuTree(rootMenu, 0L);

                jsDTO.setMenuDTOList(menuDTOList);
            });
            //查询用户信息
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

    //生成菜单树
    private List<MenuDTO> buildMenuTree(List<MenuDTO> rootMenu, Long parentId) {
        //菜单树
        List<MenuDTO> treeList = new ArrayList<>();

        rootMenu.forEach(menuDTO -> {
            if (parentId.longValue() == menuDTO.getParentId().longValue()) {
                //通过递归，循环遍历子级菜单
                menuDTO.setChildMenus(buildMenuTree(rootMenu, menuDTO.getId()));
                treeList.add(menuDTO);
            }
        });
        return treeList;
    }

    /**
     * 列表 - 将用户dto转vo
     */
    private List<YhListVO> yh2YhVO(List<Yh> yhs) {

        List<YhListVO> yhListVOs = Lists.newArrayList();

        yhs.forEach(yhDTO -> {

            YhListVO yhListVO = new YhListVO();
            BeanUtils.copyProperties(yhDTO, yhListVO);

            List<Js> jsList = jsMapper.findByYhId(yhDTO.getId());

            StringBuilder builder = new StringBuilder();

            if(!CollectionUtils.isEmpty(jsList)){

                jsList.forEach(js->{
                    builder.append(js.getJsmc()).append(",");
                });

                yhListVO.setYhjsmc(builder.substring(0, builder.length() - 1));
            }

            yhListVOs.add(yhListVO);
        });

        return yhListVOs;
    }
}
