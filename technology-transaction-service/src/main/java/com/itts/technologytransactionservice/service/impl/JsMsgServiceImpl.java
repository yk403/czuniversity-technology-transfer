package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.technologytransactionservice.mapper.JsMsgMapper;
import com.itts.technologytransactionservice.model.JsMsg;
import com.itts.technologytransactionservice.model.JsMsgDTO;
import com.itts.technologytransactionservice.service.JsMsgService;
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
public class JsMsgServiceImpl extends ServiceImpl<JsMsgMapper, JsMsg> implements JsMsgService {

    @Resource
    private JsMsgMapper jsMsgMapper;


    @Override
    public PageInfo<JsMsgDTO> findPage(Integer pageNum, Integer pageSize,String yhm) {
        PageHelper.startPage(pageNum,pageSize);
        Long userId = getUserId();
        List<JsMsgDTO> page = jsMsgMapper.findPage(userId,yhm);
        PageInfo<JsMsgDTO> jsMsgDTOPageInfo = new PageInfo<>(page);
        return jsMsgDTOPageInfo;
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
}
