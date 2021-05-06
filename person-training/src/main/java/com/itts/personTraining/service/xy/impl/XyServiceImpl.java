package com.itts.personTraining.service.xy.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.model.pk.Pk;
import com.itts.personTraining.model.xy.Xy;
import com.itts.personTraining.mapper.xy.XyMapper;
import com.itts.personTraining.model.xyKc.XyKc;
import com.itts.personTraining.service.xy.XyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.service.xyKc.XyKcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 * 学院表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-04-23
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class XyServiceImpl extends ServiceImpl<XyMapper, Xy> implements XyService {

    @Resource
    private XyMapper xyMapper;
    @Autowired
    private XyService xyService;
    @Autowired
    private XyKcService xyKcService;
    /**
     * 查询所有学院
     * @return
     */
    @Override
    public List<Xy> getAll() {
        QueryWrapper<Xy> xyQueryWrapper = new QueryWrapper<>();
        xyQueryWrapper.eq("sfsc",false);
        List<Xy> xyList = xyMapper.selectList(xyQueryWrapper);
        return xyList;
    }

    /**
     * 新增学院
     * @param xy
     * @return
     */
    @Override
    public boolean add(Xy xy) {
        log.info("【人才培养 - 新增学院:{}】",xy);
        Long userId = getUserId();
        xy.setCjr(userId);
        xy.setGxr(userId);
        return xyService.save(xy);
    }

    /**
     * 根据id查询学院
     * @param id
     * @return
     */
    @Override
    public Xy get(Long id) {
        log.info("【人才培养 - 根据id:{}查询学院信息】",id);
        QueryWrapper<Xy> xyQueryWrapper = new QueryWrapper<>();
        xyQueryWrapper.eq("sfsc",false)
                .eq("id",id);
        return xyMapper.selectOne(xyQueryWrapper);
    }

    /**
     * 更新学院
     * @param xy
     * @return
     */
    @Override
    public boolean update(Xy xy) {
        log.info("【人才培养 - 更新学院:{}】",xy);
        xy.setGxr(getUserId());
        return xyService.updateById(xy);
    }

    /**
     * 删除学院
     * @param xy
     * @return
     */
    @Override
    public boolean delete(Xy xy) {
        log.info("【人才培养 - 删除学院:{}】",xy);
        //设置删除状态
        xy.setSfsc(true);
        xy.setGxr(getUserId());
        if (xyService.updateById(xy)) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("xy_id",xy.getId());
            return xyKcService.removeByMap(map);
        }
        return false;
    }

    /**
     * 获取当前用户id
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
}
