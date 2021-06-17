package com.itts.technologytransactionservice.service.cd.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.common.utils.common.CommonUtils;
import com.itts.technologytransactionservice.mapper.FjzyMapper;
import com.itts.technologytransactionservice.mapper.LyHzMapper;
import com.itts.technologytransactionservice.mapper.TZwHzMapper;
import com.itts.technologytransactionservice.model.*;
import com.itts.technologytransactionservice.service.LyHzService;
import com.itts.technologytransactionservice.service.cd.LyHzAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yukai
 * @since 2021-05-18
 */
@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class LyHzAdminServiceImpl extends ServiceImpl<LyHzMapper, LyHz> implements LyHzAdminService {
    @Autowired
    private LyHzMapper lyHzMapper;
    @Autowired
    private FjzyMapper fjzyMapper;
    @Autowired
    private TZwHzMapper tZwHzMapper;
    @Override
    public PageInfo findLyHzBack(Map<String, Object> params) {
        log.info("【技术交易 - 分页条件查询(前台)】");
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<LyHzDto> list = lyHzMapper.findLyHzBack(query);
        return new PageInfo<>(list);
    }

    @Override
    public Boolean saveHz(LyHzDto lyHzDto) {
        LyHz lyHz = new LyHz();
        BeanUtils.copyProperties(lyHzDto,lyHz);
        //保存轮播图附件
        if (!CollectionUtils.isEmpty(lyHzDto.getFjzyList())) {
            String fjzyId = CommonUtils.generateUUID();
            lyHz.setHzlbt(fjzyId);
            List<Fjzy>fjzyList=lyHzDto.getFjzyList();
            for (Fjzy fjzyDto: fjzyList) {
                Fjzy fjzy = new Fjzy();
                BeanUtils.copyProperties(fjzyDto, fjzy);

                fjzy.setFjzyId(fjzyId);
                fjzyMapper.insert(fjzy);

            }
        }
        //保存广告附件
        if (!CollectionUtils.isEmpty(lyHzDto.getGgList())) {
            String fjzyId = CommonUtils.generateUUID();
            lyHz.setHzgg(fjzyId);
            List<Fjzy>ggList=lyHzDto.getGgList();
            for (Fjzy fjzyDto: ggList) {
                Fjzy fjzy = new Fjzy();
                BeanUtils.copyProperties(fjzyDto, fjzy);

                fjzy.setFjzyId(fjzyId);
                fjzyMapper.insert(fjzy);

            }
        }
        if(save(lyHz)){
        }else{
            return false;
        }
        //保存会展展位关联关系
        if (!CollectionUtils.isEmpty(lyHzDto.getZwHzList())){
            List<TZwHz> zwHzList=lyHzDto.getZwHzList();
            for (TZwHz tZwHz:zwHzList) {
                tZwHz.setHzId(lyHz.getId());
                tZwHzMapper.insert(tZwHz);
            }
        }
        return true;
    }
//    *
//     * 获取当前用户id
//     * @return

    public Long getUserId() {
        LoginUser loginUser = threadLocal.get();
        Long userId;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        } else {
            throw new ServiceException(GET_THREADLOCAL_ERROR);
        }
        return userId;
    }

    @Override
    public boolean updateHz(LyHzDto lyHzDto) {
        LyHz lyHz = new LyHz();
        BeanUtils.copyProperties(lyHzDto,lyHz);
        //保存轮播图附件
        if (!CollectionUtils.isEmpty(lyHzDto.getFjzyList())) {
            //前端传过来的附件列表
            //后端查询出附件列表
            Map<String,Object> fjzymap=new HashMap<>();
            fjzymap.put("fjzyId",lyHzDto.getHzlbt());
            List<Fjzy> dblist = fjzyMapper.list(fjzymap);
            //判空，删除掉旧的附件
            if(dblist.size()>0) {
                dblist.forEach(item->{
                    fjzyMapper.deleteById(item);
                });
            }
            String fjzyId = CommonUtils.generateUUID();
            lyHz.setHzlbt(fjzyId);
            List<Fjzy>fjzyList=lyHzDto.getFjzyList();
            for (Fjzy fjzyDto: fjzyList) {
                Fjzy fjzy = new Fjzy();
                BeanUtils.copyProperties(fjzyDto, fjzy);

                fjzy.setFjzyId(fjzyId);
                fjzyMapper.insert(fjzy);

            }

        };
        //保存广告附件
        if (!CollectionUtils.isEmpty(lyHzDto.getGgList())) {
            //前端传过来的附件列表
            //后端查询出附件列表
            Map<String,Object> fjzymap=new HashMap<>();
            fjzymap.put("fjzyId",lyHzDto.getHzgg());
            List<Fjzy> dblist = fjzyMapper.list(fjzymap);
            //判空，删除掉旧的附件
            if(dblist.size()>0) {
                dblist.forEach(item->{
                    fjzyMapper.deleteById(item);
                });
            }
            String fjzyId = CommonUtils.generateUUID();
            lyHz.setHzgg(fjzyId);
            List<Fjzy> ggList=lyHzDto.getGgList();
            for (Fjzy fjzyDto: ggList) {
                Fjzy fjzy = new Fjzy();
                BeanUtils.copyProperties(fjzyDto, fjzy);

                fjzy.setFjzyId(fjzyId);
                fjzyMapper.insert(fjzy);

            }

        };


        if(updateById(lyHz)){
        }else{
            return false;
        }
        //保存会展展位关联关系(先删除原有关联)
        Map<String,Object>map= new HashMap<String,Object>();
        map.put("hz_id",lyHz.getId());
        tZwHzMapper.deleteByMap(map);
        if (!CollectionUtils.isEmpty(lyHzDto.getZwHzList())){
            List<TZwHz> zwHzList=lyHzDto.getZwHzList();
            for (TZwHz tZwHz:zwHzList) {
                tZwHz.setHzId(lyHz.getId());
                tZwHzMapper.insert(tZwHz);
            }
        }
        return true;
    }
}
