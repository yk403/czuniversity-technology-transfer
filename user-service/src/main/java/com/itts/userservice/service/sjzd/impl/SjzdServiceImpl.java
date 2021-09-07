package com.itts.userservice.service.sjzd.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.itts.common.bean.LoginUser;
import com.itts.userservice.feign.technologytransactionservice.jslb.JslbService;
import com.itts.userservice.feign.technologytransactionservice.jsly.JslyService;
import com.itts.userservice.mapper.sjzd.SjzdMapper;
import com.itts.userservice.model.jslb.TJsLb;
import com.itts.userservice.model.jsly.TJsLy;
import com.itts.userservice.model.sjzd.Sjzd;
import com.itts.userservice.request.sjzd.*;
import com.itts.userservice.service.sjzd.SjzdService;
import com.itts.userservice.vo.SjzdModelVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
 * @since 2021-03-31
 */
@Service
public class SjzdServiceImpl implements SjzdService {

    @Resource
    private SjzdMapper sjzdMapper;
    @Resource
    private JslbService jslbService;
    @Resource
    private JslyService jslyService;

    /**
     * 获取数据字典模块列表
     */
    @Override
    public PageInfo<SjzdModelVO> findDictionaryModel(Integer pageNum, Integer pageSize, String model, String systemType, String condition) {

        PageHelper.startPage(pageNum, pageSize);

        List<SjzdModelVO> sjzdModels=sjzdMapper.findDictionaryModel(model, systemType, condition);

        PageInfo<SjzdModelVO> pageInfo=new PageInfo<>(sjzdModels);

        return pageInfo;
    }

    /**
     * 通过所属模块获取数据
     */
    @Override
    public List<Sjzd> findBySsmk(String xtlb, String mklx, String ssmk) {

        List<Sjzd> sjzds=sjzdMapper.findBySsmk(xtlb, mklx, ssmk);
        return sjzds;
    }

    /**
     * 获取列表
     */
    @Override
    public PageInfo<Sjzd> findByPage(Integer pageNum, Integer pageSize, String model, String systemType, String dictionary, String zdbm, Long parentId, String parentCode) {

        if (pageSize != -1) {
            PageHelper.startPage(pageNum, pageSize);
        }

        QueryWrapper<Sjzd> objectQueryWrapper=new QueryWrapper<>();
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
        if (StringUtils.isNotBlank(parentCode)) {
            objectQueryWrapper.eq("fj_bm", parentCode);
        }

        objectQueryWrapper.orderByAsc("px").orderByDesc("cjsj");

        List<Sjzd> sjzds=sjzdMapper.selectList(objectQueryWrapper);
        PageInfo<Sjzd> shzdPageInfo=new PageInfo<>(sjzds);
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
        Sjzd sjzd=sjzdMapper.selectById(id);
        return sjzd;
    }

    /**
     * 通过所属模块获取数据
     */
    @Override
    public GetSjzdRequest get(String xtlb, String mklx, String ssmk) {

        List<Sjzd> sjzds=sjzdMapper.findBySsmk(xtlb, mklx, ssmk);

        GetSjzdRequest getSjzdRequest=new GetSjzdRequest();

        if (CollectionUtils.isEmpty(sjzds)) {
            return null;
        }

        getSjzdRequest.setXtlb(sjzds.get(0).getXtlb());
        getSjzdRequest.setMklx(sjzds.get(0).getMklx());
        getSjzdRequest.setSsmkmc(sjzds.get(0).getSsmkmc());
        getSjzdRequest.setSsmk(sjzds.get(0).getSsmk());
        getSjzdRequest.setFjId(sjzds.get(0).getFjId());
        getSjzdRequest.setFjBm(sjzds.get(0).getFjBm());
        getSjzdRequest.setFjmc(sjzds.get(0).getFjmc());

        List<GetSjzdItemRequest> getSjzdItemRequests=Lists.newArrayList();

        for (Sjzd sjzd : sjzds) {

            GetSjzdItemRequest getSjzdItemRequest=new GetSjzdItemRequest();

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
        List<Sjzd> sjzdList=sjzdMapper.selectByNameOrCode(string, ssmk);
        PageInfo<Sjzd> shzdPageInfo=new PageInfo<>(sjzdList);
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

        LoginUser loginUser=threadLocal.get();

        Long userId=null;
        if (loginUser != null) {
            userId=loginUser.getUserId();
        }

        QueryWrapper query=new QueryWrapper();
        query.eq("zdbm", sjzd.getFjBm());

        Sjzd fjzd=sjzdMapper.selectOne(query);

        Date now=new Date();

        //循环便利，
        for (AddSjzdItemRequest sjzdItem : sjzd.getSjzdItems()) {

            Sjzd addSjzd=new Sjzd();

            BeanUtils.copyProperties(sjzd, addSjzd);

            addSjzd.setZdmc(sjzdItem.getZdmc());
            addSjzd.setPx(sjzdItem.getPx());

            if (fjzd != null) {

                addSjzd.setFjId(fjzd.getId());
                addSjzd.setFjBm(fjzd.getZdbm());
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
            //判断添加的数据字典是否是技术类别或者技术领域
            if (addSjzd.getSsmk() != null) {
                if (addSjzd.getSsmk().equals("technology_category")) {
                    TJsLb tJsLb=new TJsLb();
                    tJsLb.setId(addSjzd.getId());
                    tJsLb.setBh(addSjzd.getXtlb());
                    tJsLb.setMc(addSjzd.getZdmc());
                    tJsLb.setXq(addSjzd.getZdmc()+"详情");
                    jslbService.save(tJsLb);
                }
                if (addSjzd.getSsmk().equals("technical_field")) {
                    TJsLy tJsLy=new TJsLy();
                    tJsLy.setId(addSjzd.getId());
                    tJsLy.setBh(addSjzd.getXtlb());
                    tJsLy.setMc(addSjzd.getZdmc());
                    tJsLy.setXq(addSjzd.getZdmc()+"详情");
                    jslyService.save(tJsLy);
                }

            }
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

        //父级编码不可以为技术类别和邻域

            
        LoginUser loginUser=threadLocal.get();

        Long userId=null;
        if (loginUser != null) {
            userId=loginUser.getUserId();
        }
        List<Long> longs = new ArrayList<>();
        List<Sjzd> oldSjzds=sjzdMapper.findBySsmk(sjzd.getXtlb(), sjzd.getMklx(), sjzd.getSsmk());

        Date now=new Date();

        if (!CollectionUtils.isEmpty(oldSjzds)) {

            //删除所有旧的数据字典
            for (Sjzd oldSjzd : oldSjzds) {
                //子数据字典
                QueryWrapper<Sjzd> sjzdQueryWrapper = new QueryWrapper<>();
                sjzdQueryWrapper.eq("fj_id",oldSjzd.getId())
                        .eq("sfsc",false);
                List<Sjzd> sjzdList = sjzdMapper.selectList(sjzdQueryWrapper);
                //如果存在
                if(!sjzdList.isEmpty()){
                    List<UpdateSjzdItemRequest> sjzdItems = sjzd.getSjzdItems();
                    for (int i = 0; i < sjzdItems.size(); i++) {


                            if(Objects.equals(oldSjzd.getId(),sjzdItems.get(i).getId())){
                                BeanUtils.copyProperties(sjzdItems.get(i),oldSjzd);
                                sjzdMapper.updateById(oldSjzd);

                                for (Sjzd sjzd1 : sjzdList) {
                                    sjzd1.setFjBm(oldSjzd.getZdbm());
                                    sjzd1.setFjmc(oldSjzd.getZdmc());
                                    sjzd1.setGxsj(new Date());
                                    sjzd1.setGxr(userId);
                                    sjzdMapper.updateById(sjzd1);
                                }

                                longs.add(oldSjzd.getId());
                            }

                    }
                }else {
                    //判断删除的数据字典是否是技术类别或者技术领域
                    if (oldSjzd.getSsmk() != null) {
                        if (oldSjzd.getSsmk().equals("technology_category")) {

                            jslbService.remove(Long.toString(oldSjzd.getId()));
                        }
                        if (oldSjzd.getSsmk().equals("technical_field")) {
                            jslyService.remove(Long.toString(oldSjzd.getId()));
                        }

                    }
                    sjzdMapper.deleteById(oldSjzd.getId());
                }

            }
        }

        QueryWrapper query=new QueryWrapper();
        query.eq("zdbm", sjzd.getFjBm());

        Sjzd fjzd=sjzdMapper.selectOne(query);

        //增加新的数据字典
        for (UpdateSjzdItemRequest sjzdItem : sjzd.getSjzdItems()) {

            for (Long aLong : longs) {
                if(Objects.equals(aLong,sjzdItem.getId())){
                    continue;
                }else {
                    Sjzd addSjzd=new Sjzd();

                    BeanUtils.copyProperties(sjzd, addSjzd);

                    addSjzd.setZdmc(sjzdItem.getZdmc());

                    if (fjzd != null) {

                        addSjzd.setFjId(fjzd.getId());
                        addSjzd.setFjBm(fjzd.getZdbm());
                        addSjzd.setFjmc(fjzd.getZdmc());
                        addSjzd.setZdbm(sjzdItem.getZdbm());
                    } else {

                        addSjzd.setZdbm(sjzdItem.getZdbm());
                    }

                    addSjzd.setPx(sjzdItem.getPx());

                    //新增时设置更新时间和更新人
                    addSjzd.setCjsj(now);
                    addSjzd.setCjr(userId);
                    addSjzd.setGxsj(now);
                    addSjzd.setGxr(userId);
                    sjzdMapper.insert(addSjzd);
                    //判断修改的数据字典是否是技术类别或者技术领域
                    if (addSjzd.getSsmk() != null) {
                        if (addSjzd.getSsmk().equals("technology_category")) {
                            TJsLb tJsLb=new TJsLb();
                            tJsLb.setId(addSjzd.getId());
                            tJsLb.setBh(addSjzd.getXtlb());
                            tJsLb.setMc(addSjzd.getZdmc());
                            tJsLb.setXq(addSjzd.getZdmc()+"详情");
                            jslbService.save(tJsLb);
                        }
                        if (addSjzd.getSsmk().equals("technical_field")) {
                            TJsLy tJsLy=new TJsLy();
                            tJsLy.setId(addSjzd.getId());
                            tJsLy.setBh(addSjzd.getXtlb());
                            tJsLy.setMc(addSjzd.getZdmc());
                            tJsLy.setXq(addSjzd.getZdmc()+"详情");
                            jslyService.save(tJsLy);
                        }

                    }
                }
            }

        }

        return sjzd;
    }

    /**
     * 删除
     */
    @Override
    public void delete(Long id) {

        sjzdMapper.deleteById(id);
        Sjzd addSjzd=sjzdMapper.selectById(id);
        //判断删除的数据字典是否是技术类别或者技术领域
        if (addSjzd.getSsmk() != null) {
            if (addSjzd.getSsmk().equals("technology_category")) {

                jslbService.remove(Long.toString(id));
            }
            if (addSjzd.getSsmk().equals("technical_field")) {
                jslyService.remove(Long.toString(id));
            }

        }
    }
}
