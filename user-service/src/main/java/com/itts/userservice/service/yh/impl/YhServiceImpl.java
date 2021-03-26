package com.itts.userservice.service.yh.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.constant.RedisConstant;
import com.itts.userservice.dto.JsDTO;
import com.itts.userservice.dto.MenuDTO;
import com.itts.userservice.model.yh.Yh;
import com.itts.userservice.mapper.yh.YhMapper;
import com.itts.userservice.model.yh.Yh;
import com.itts.userservice.service.yh.YhService;
import com.itts.userservice.vo.YhVO;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    private RedisTemplate redisTemplate;

    /**
     * 获取列表 - 分页
     */
    @Override
    public PageInfo<Yh> findByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Yh> query = new QueryWrapper<>();
        query.eq("sfsc", false);
        List<Yh> list = yhMapper.selectList(query);
        PageInfo<Yh> page = new PageInfo<>(list);
        return page;
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
     * 新增
     */
    @Override
    public Yh add(Yh Yh) {
        yhMapper.insert(Yh);
        return Yh;
    }

    /**
     * 更新
     */
    @Override
    public Yh update(Yh Yh) {
        yhMapper.updateById(Yh);
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
    public YhVO QueryMenuList(Long userId) {
        YhVO yhVO = JSONUtil.toBean((JSONObject) redisTemplate.opsForValue().get(RedisConstant.USERSERVICE_MENUS + userId),YhVO.class);
        if(yhVO==null){
            //角色及其菜单的全部列表
            List<JsDTO> jsDTOList = yhMapper.findByUserId(userId);

            jsDTOList.forEach(jsDTO -> {
                List<MenuDTO> rootMenu = jsDTO.getMenuDTOList();
                //获取菜单树
                List<MenuDTO> menuDTOList = buildMenuTree(rootMenu, 0L);

                jsDTO.setMenuDTOList(menuDTOList);
            });
            //查询用户信息
            Yh yh = yhMapper.selectById(userId);
            /*YhVO yhVO = new YhVO();*/
            BeanUtils.copyProperties(yh,yhVO);
            yhVO.setJsDTOList(jsDTOList);
        }
        redisTemplate.opsForValue().set(RedisConstant.USERSERVICE_MENUS + userId,yhVO);

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
}
