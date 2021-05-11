package com.itts.userservice.service.sjzd.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.itts.common.bean.LoginUser;
import com.itts.userservice.mapper.sjzd.SjzdMapper;
import com.itts.userservice.model.sjzd.Sjzd;
import com.itts.userservice.request.sjzd.*;
import com.itts.userservice.service.sjzd.SjzdService;
import com.itts.userservice.vo.SjzdModelVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fl
 * @since 2021-03-31
 */
@Service
public class SjzdServiceImpl implements SjzdService {

    @Resource
    private SjzdMapper sjzdMapper;

    /**
     * 获取数据字典模块列表
     */
    @Override
    public PageInfo<SjzdModelVO> findDictionaryModel(Integer pageNum, Integer pageSize, String model, String systemType, String condition) {

        PageHelper.startPage(pageNum, pageSize);

        List<SjzdModelVO> sjzdModels = sjzdMapper.findDictionaryModel(model, systemType, condition);

        PageInfo<SjzdModelVO> pageInfo = new PageInfo<>(sjzdModels);

        return pageInfo;
    }

    /**
     * 通过所属模块获取数据
     */
    @Override
    public List<Sjzd> findBySsmk(String xtlb, String mklx, String ssmk) {

        List<Sjzd> sjzds = sjzdMapper.findBySsmk(xtlb, mklx, ssmk);
        return sjzds;
    }

    /**
     * 获取列表
     */
    @Override
    public PageInfo<Sjzd> findByPage(Integer pageNum, Integer pageSize, String model, String systemType, String dictionary, String zdbm, Long parentId) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Sjzd> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("sfsc", false);

        if (StringUtils.isNotBlank(model)) {
            objectQueryWrapper.eq("mklx", model);
        }

        if (StringUtils.isNotBlank(systemType)) {
            objectQueryWrapper.eq("xtlb", systemType);
        }

        if (StringUtils.isNotBlank(dictionary)) {
            objectQueryWrapper.eq("ssmk", dictionary);
        }

        if (StringUtils.isNotBlank(zdbm)) {
            objectQueryWrapper.eq("zdbm", zdbm);
        }

        if (parentId != null) {
            objectQueryWrapper.eq("fj_id", parentId);
        }

        objectQueryWrapper.orderByDesc("cjsj");

        List<Sjzd> sjzds = sjzdMapper.selectList(objectQueryWrapper);
        PageInfo<Sjzd> shzdPageInfo = new PageInfo<>(sjzds);
        return shzdPageInfo;
    }

    /**
     * 获取通过id
     *
     * @param id
     * @return
     */
    @Override
    public Sjzd get(Long id) {
        Sjzd sjzd = sjzdMapper.selectById(id);
        return sjzd;
    }

    /**
     * 通过所属模块获取数据
     */
    @Override
    public GetSjzdRequest get(String xtlb, String mklx, String ssmk) {

        List<Sjzd> sjzds = sjzdMapper.findBySsmk(xtlb, mklx, ssmk);

        GetSjzdRequest getSjzdRequest = new GetSjzdRequest();

        if (CollectionUtils.isEmpty(sjzds)) {
            return null;
        }

        getSjzdRequest.setXtlb(sjzds.get(0).getXtlb());
        getSjzdRequest.setMklx(sjzds.get(0).getMklx());
        getSjzdRequest.setSsmkmc(sjzds.get(0).getSsmkmc());
        getSjzdRequest.setSsmk(sjzds.get(0).getSsmk());
        getSjzdRequest.setFjId(sjzds.get(0).getFjId());
        getSjzdRequest.setFjmc(sjzds.get(0).getFjmc());

        List<GetSjzdItemRequest> getSjzdItemRequests = Lists.newArrayList();

        for (Sjzd sjzd : sjzds) {

            GetSjzdItemRequest getSjzdItemRequest = new GetSjzdItemRequest();

            BeanUtils.copyProperties(sjzd, getSjzdItemRequest);

            getSjzdItemRequests.add(getSjzdItemRequest);

        }
        getSjzdRequest.setSjzdItems(getSjzdItemRequests);

        return getSjzdRequest;
    }

    /**
     * 查询通过名称或编码
     *
     * @param pageNum
     * @param pageSize
     * @param string
     * @param ssmk
     * @return
     */
    @Override
    public PageInfo<Sjzd> selectByString(Integer pageNum, Integer pageSize, String string, String ssmk) {

        PageHelper.startPage(pageNum, pageSize);
        List<Sjzd> sjzdList = sjzdMapper.selectByNameOrCode(string, ssmk);
        PageInfo<Sjzd> shzdPageInfo = new PageInfo<>(sjzdList);
        return shzdPageInfo;
    }


    /**
     * 添加
     *
     * @param sjzd
     * @return
     */
    @Override
    public AddSjzdRequest add(AddSjzdRequest sjzd) {

        LoginUser loginUser = threadLocal.get();

        Long userId = null;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        Sjzd fjzd = sjzdMapper.selectById(sjzd.getFjId());

        Date now = new Date();

        //循环便利，
        for (AddSjzdItemRequest sjzdItem : sjzd.getSjzdItems()) {

            Sjzd addSjzd = new Sjzd();

            BeanUtils.copyProperties(sjzd, addSjzd);

            addSjzd.setZdmc(sjzdItem.getZdmc());
            addSjzd.setZdbm(sjzdItem.getZdbm());
            addSjzd.setFjId(sjzd.getFjId());

            if (fjzd != null) {

                addSjzd.setFjmc(fjzd.getZdmc());
                addSjzd.setZdbm(fjzd.getZdbm() + sjzdItem.getZdbm());
            } else {
                addSjzd.setZdbm(sjzdItem.getZdbm());
            }

            //新增时设置更新时间和更新人
            addSjzd.setCjsj(now);
            addSjzd.setCjr(userId);
            addSjzd.setGxsj(now);
            addSjzd.setGxr(userId);
            sjzdMapper.insert(addSjzd);
        }

        return sjzd;
    }

    /**
     * 更新
     *
     * @param sjzd
     * @return
     */
    @Override
    public UpdateSjzdRequest update(UpdateSjzdRequest sjzd) {

        LoginUser loginUser = threadLocal.get();

        Long userId = null;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        List<Sjzd> oldSjzds = sjzdMapper.findBySsmk(sjzd.getXtlb(), sjzd.getMklx(), sjzd.getSsmk());

        Date now = new Date();

        if (!CollectionUtils.isEmpty(oldSjzds)) {

            //删除所有旧的数据字典
            for (Sjzd oldSjzd : oldSjzds) {

                oldSjzd.setSfsc(true);
                oldSjzd.setGxr(userId);
                oldSjzd.setGxsj(now);
                sjzdMapper.updateById(oldSjzd);
            }
        }

        Sjzd fjzd = sjzdMapper.selectById(sjzd.getFjId());

        //增加新的数据字典
        for (UpdateSjzdItemRequest sjzdItem : sjzd.getSjzdItems()) {

            Sjzd addSjzd = new Sjzd();

            BeanUtils.copyProperties(sjzd, addSjzd);

            addSjzd.setZdmc(sjzdItem.getZdmc());
            addSjzd.setZdbm(sjzdItem.getZdbm());
            addSjzd.setFjId(sjzd.getFjId());

            if (fjzd != null) {

                addSjzd.setFjmc(fjzd.getZdmc());
                addSjzd.setZdbm(fjzd.getZdbm() + sjzdItem.getZdbm());
            } else {
                addSjzd.setZdbm(sjzdItem.getZdbm());
            }

            //新增时设置更新时间和更新人
            addSjzd.setCjsj(now);
            addSjzd.setCjr(userId);
            addSjzd.setGxsj(now);
            addSjzd.setGxr(userId);
            sjzdMapper.insert(addSjzd);
        }

        return sjzd;
    }

    /**
     * 删除
     */
    @Override
    public void delete(Long id) {

        sjzdMapper.deleteById(id);
    }
}
