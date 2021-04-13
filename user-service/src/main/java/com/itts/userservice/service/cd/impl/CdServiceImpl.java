package com.itts.userservice.service.cd.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.userservice.dto.GetCdAndCzDTO;
import com.itts.userservice.dto.GetCdCzGlDTO;
import com.itts.userservice.mapper.cd.CdCzGlMapper;
import com.itts.userservice.mapper.cd.CdMapper;
import com.itts.userservice.model.cd.Cd;
import com.itts.userservice.model.cd.CdCzGl;
import com.itts.userservice.request.AddCdRequest;
import com.itts.userservice.service.cd.CdService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
     * 获取详情
     */
    @Override
    public GetCdAndCzDTO get(Long id) {

        Cd cd = cdMapper.selectById(id);
        if(cd == null){
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

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser != null) {
            cd.setCjr(loginUser.getUserId());
            cd.setGxr(loginUser.getUserId());
        }

        Date now = new Date();
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
        if (!CollectionUtils.isEmpty(cd.getCzIds())) {

            cd.getCzIds().forEach(czId -> {

                CdCzGl cdCzGl = new CdCzGl();

                if (loginUser != null) {
                    cdCzGl.setCjr(loginUser.getUserId());
                    cdCzGl.setGxr(loginUser.getUserId());
                }
                cdCzGl.setCjsj(now);
                cdCzGl.setGxsj(now);
                cdCzGl.setCdId(cd.getId());
                cdCzGl.setCzId(czId);

                cdCzGlMapper.insert(cdCzGl);
            });
        }

        return cd;
    }

    /**
     * 更新
     */
    @Override
    public Cd update(Cd cd) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser != null) {
            cd.setGxr(loginUser.getUserId());
        }
        cd.setGxsj(new Date());

        //设置层级
        if (cd.getFjcdId() == 0L) {
            cd.setCj(cd.getCdbm());
        } else {

            Cd fjcd = cdMapper.selectById(cd.getFjcdId());
            cd.setCj(fjcd.getCj() + "-" + cd.getCdbm());
        }

        cdMapper.updateById(cd);
        return cd;
    }
}
