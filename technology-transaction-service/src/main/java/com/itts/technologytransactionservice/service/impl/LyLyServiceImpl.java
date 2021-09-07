package com.itts.technologytransactionservice.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.ServiceException;
import com.itts.common.exception.WebException;
import com.itts.common.utils.Query;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.feign.userservice.JgglFeignService;
import com.itts.technologytransactionservice.model.JgglVO;
import com.itts.technologytransactionservice.model.LyLy;
import com.itts.technologytransactionservice.mapper.LyLyMapper;
import com.itts.technologytransactionservice.model.LyLyDto;
import com.itts.technologytransactionservice.service.LyLyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
public class LyLyServiceImpl extends ServiceImpl<LyLyMapper, LyLy> implements LyLyService {
    @Autowired
    private LyLyMapper lyLyMapper;
    @Resource
    private JgglFeignService jgglFeignService;
    @Override
    public PageInfo findLyLyFront(Map<String, Object> params) {
        log.info("【技术交易 - 分页条件查询(前台)】");
        Long fjjgId = getFjjgId();
        if(params.get("jgCode") != null){
            ResponseUtil response = jgglFeignService.getByCode(params.get("jgCode").toString());
            if(response == null || response.getErrCode().intValue() != 0){
                throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
            }
            JgglVO jggl = response.conversionData(new TypeReference<JgglVO>() {});
            fjjgId = jggl.getId();
        }
        params.put("fjjgId",fjjgId);
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<LyLyDto> list = lyLyMapper.findLyLyFront(query);
        return new PageInfo<>(list);
    }

    @Override
    public Boolean saveLy(LyLy lyLy) {
        if(save(lyLy)){
            return true;
        }else{
            return false;
        }
    }
    private Long getFjjgId(){
        LoginUser loginUser = threadLocal.get();
        Long fjjgId = null;
        if (loginUser != null) {
            fjjgId = loginUser.getFjjgId();
        }
        return fjjgId;
    }

}
