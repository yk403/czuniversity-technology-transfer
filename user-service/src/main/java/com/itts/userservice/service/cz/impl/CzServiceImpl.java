package com.itts.userservice.service.cz.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.RedisConstant;
import com.itts.common.constant.SystemConstant;
import com.itts.userservice.dto.CzDTO;
import com.itts.userservice.mapper.yh.YhJsGlMapper;
import com.itts.userservice.model.cz.Cz;
import com.itts.userservice.model.yh.YhJsGl;
import com.itts.userservice.service.cz.CzService;
import com.itts.userservice.mapper.cz.CzMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 操作表 服务实现类
 * </p>
 *
 * @author fl
 * @since 2021-03-19
 */
@Service
@Primary
public class CzServiceImpl implements CzService {


    @Resource
    private CzMapper czMapper;

    @Resource
    private YhJsGlMapper yhJsGlMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取列表 - 分页
     */
    @Override
    public PageInfo<Cz> findByPage(Integer pageNum, Integer pageSize, String name, String systemType, String modelType) {

        PageHelper.startPage(pageNum,pageSize);

        QueryWrapper<Cz> query = new QueryWrapper<>();
        //过滤
        query.eq("sfsc",false);
        if(StringUtils.isNotBlank(name)){
            query.like("czmc", name);
        }
        if(StringUtils.isNotBlank(systemType)){
            query.eq("xtlx", systemType);
        }
        if(StringUtils.isNotBlank(modelType)){
            query.eq("mklx", modelType);
        }

        query.orderByDesc("cjsj");

        List<Cz> Czs = czMapper.selectList(query);

        PageInfo<Cz> tCzPageInfo = new PageInfo<>(Czs);
        return tCzPageInfo;
    }

    /**
     * 获取详情
     */
    @Override
    public Cz get(Long id) {
        return czMapper.selectById(id);
    }

    /**
     * 新增
     */
    @Override
    public Cz add(Cz cz) {

        //获取当前登录用户信息
        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser != null) {
            cz.setCjr(loginUser.getUserId());
            cz.setGxr(loginUser.getUserId());
        }

        //设置创建时间、更新时间
        Date now = new Date();
        cz.setCjsj(now);
        cz.setGxsj(now);

        czMapper.insert(cz);
        return cz;
    }

    /**
     * 更新
     */
    @Override
    public Cz update(Cz cz) {

        cz.setGxsj(new Date());

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser != null) {
            cz.setGxr(loginUser.getUserId());
        }

        czMapper.updateById(cz);
        return cz;
    }



    /**
     * 查询当前菜单的操作
     * @param id
     * @param cdid
     * @return
     */
    @Override
    public List<CzDTO> findCz(Long id, Long cdid){
        //获取缓存中的操作
        Object json = redisTemplate.opsForValue().get(RedisConstant.USERSERVICE_MENUS_OPERTION + id);
        JSONArray jsonArry = JSONUtil.parseArray(json);
        List<CzDTO> czDTOList = JSONUtil.toList(jsonArry, CzDTO.class);
        if(czDTOList==null){
            //查询角色id
            YhJsGl yhJsGl = yhJsGlMapper.selectById(id);
            Long jsid = yhJsGl.getJsId();
            //根据角色id和菜单id查出操作
            List<CzDTO> findcdcz = czMapper.findCdCzGlByJsIdAndCdId(jsid, cdid);
            czDTOList=findcdcz;
            //缓存中没有数据则存入操作
            redisTemplate.opsForValue().set(RedisConstant.USERSERVICE_MENUS_OPERTION+id,czDTOList);
        }
        return czDTOList;
    }
}
