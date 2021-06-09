package com.itts.technologytransactionservice.service.cd.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.common.utils.common.CommonUtils;
import com.itts.technologytransactionservice.mapper.FjzyMapper;
import com.itts.technologytransactionservice.mapper.LyHdMapper;
import com.itts.technologytransactionservice.model.Fjzy;
import com.itts.technologytransactionservice.model.LyHd;
import com.itts.technologytransactionservice.model.LyHdDto;
import com.itts.technologytransactionservice.service.LyHdService;
import com.itts.technologytransactionservice.service.cd.LyHdAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
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
public class LyHdAdminServiceImpl extends ServiceImpl<LyHdMapper, LyHd> implements LyHdAdminService {
    @Autowired
    private LyHdMapper lyHdMapper;
    @Autowired
    private FjzyMapper fjzyMapper;
    @Override
    public PageInfo findLyHdBack(Map<String, Object> params) {
        log.info("【技术交易 - 分页条件查询(前台)】");
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<LyHd> list = lyHdMapper.findLyHdBack(query);
        return new PageInfo<>(list);
    }

    @Override
    public Boolean saveHd(LyHdDto lyHdDto) {
        LyHd lyHd = new LyHd();
        BeanUtils.copyProperties(lyHdDto,lyHd);
        //保存附件
        if (!CollectionUtils.isEmpty(lyHdDto.getFjzyList())) {
            String fjzyId = CommonUtils.generateUUID();
            lyHd.setHdlbt(fjzyId);
            List<Fjzy>fjzyList=lyHdDto.getFjzyList();
            for (Fjzy fjzyDto: fjzyList) {
                Fjzy fjzy = new Fjzy();
                BeanUtils.copyProperties(fjzyDto, fjzy);

                fjzy.setFjzyId(fjzyId);
                fjzyMapper.insert(fjzy);
                
            }
        }
        if(save(lyHd)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 更新活动
     * @param
     * @return
     */
    @Override
    public boolean updateHd(LyHdDto lyHdDto) {
        LyHd lyHd = new LyHd();
        BeanUtils.copyProperties(lyHdDto,lyHd);
        //保存附件
        if (!CollectionUtils.isEmpty(lyHdDto.getFjzyList())) {
            //前端传过来的附件列表
            //后端查询出附件列表
            Map<String,Object> fjzymap=new HashMap<>();
            fjzymap.put("fjzyId",lyHdDto.getHdlbt());
            List<Fjzy> dblist = fjzyMapper.list(fjzymap);
            //判空，删除掉旧的附件
            if(dblist.size()>0) {
                dblist.forEach(item->{
                    fjzyMapper.deleteById(item);
                });
            }
            String fjzyId = CommonUtils.generateUUID();
            lyHd.setHdlbt(fjzyId);
            List<Fjzy>fjzyList=lyHdDto.getFjzyList();
            for (Fjzy fjzyDto: fjzyList) {
                Fjzy fjzy = new Fjzy();
                BeanUtils.copyProperties(fjzyDto, fjzy);

                fjzy.setFjzyId(fjzyId);
                fjzyMapper.insert(fjzy);

            }

            };


        if(updateById(lyHd)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean issueBatch(List<Long> ids) {
        log.info("【人才培养 - 培养计划批量下发,ids:{}】",ids);
        List<LyHd> lyHds = lyHdMapper.selectBatchIds(ids);
        for (LyHd lyHd : lyHds) {
            lyHd.setHdfbzt(1);
        }
        return updateBatchById(lyHds);
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

}
