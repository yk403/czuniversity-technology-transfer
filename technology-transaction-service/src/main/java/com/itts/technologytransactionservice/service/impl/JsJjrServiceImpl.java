package com.itts.technologytransactionservice.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.technologytransactionservice.mapper.JsJjrMapper;
import com.itts.technologytransactionservice.model.JsJjr;
import com.itts.technologytransactionservice.model.JsJjrDTO;
import com.itts.technologytransactionservice.service.JsJjrService;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fuli
 * @since 2021-09-17
 */
@Service
public class JsJjrServiceImpl extends ServiceImpl<JsJjrMapper, JsJjr> implements JsJjrService {

    @Resource
    private JsJjrMapper jsJjrMapper;

    @Override
    public PageInfo<JsJjrDTO> findPage(Integer pageNum, Integer pageSize, String xslbmcArr, Long jgId, String zsxm) {
        if(jgId == null){
            jgId = getFjjgId();
        }
        String[] split = xslbmcArr.split(",");
        PageHelper.startPage(pageNum,pageSize);
        List<JsJjrDTO> page = jsJjrMapper.findPage(split, jgId, zsxm);
        PageInfo<JsJjrDTO> jsJjrDTOPageInfo = new PageInfo<>(page);
        return jsJjrDTOPageInfo;
    }

    @Override
    public JsJjrDTO getByJjrId(Long id) {
        JsJjrDTO byJjrId = jsJjrMapper.getByJjrId(id);
        return byJjrId;
    }
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

    private Long getFjjgId(){
        LoginUser loginUser = threadLocal.get();
        Long fjjgId = null;
        if (loginUser != null) {
            fjjgId = loginUser.getFjjgId();
        }
        return fjjgId;
    }
}
