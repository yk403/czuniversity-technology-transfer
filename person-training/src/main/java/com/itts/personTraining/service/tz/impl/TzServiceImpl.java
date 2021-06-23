package com.itts.personTraining.service.tz.impl;

import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.model.tz.Tz;
import com.itts.personTraining.mapper.tz.TzMapper;
import com.itts.personTraining.service.tz.TzService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 * 通知表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-06-21
 */
@Service
public class TzServiceImpl extends ServiceImpl<TzMapper, Tz> implements TzService {

    @Resource
    private TzMapper tzMapper;

    /**
     * 根据用户类别查询通知信息(前)
     * @return
     */
    @Override
    public Tz findByCategory() {
        Long userId = getUserId();
        String userCategory = getUserCategory();
        switch (userCategory) {
            case "postgraduate":
                ;
                break;
            case "corporate_mentor":
                ;
                break;
            default:
                break;
        }
        return null;
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

    /**
     * 获取当前用户id所属类别
     * @return
     */
    private String getUserCategory() {
        LoginUser loginUser = threadLocal.get();
        String userCategory;
        if (loginUser != null) {
            userCategory = loginUser.getUserCategory();
        } else {
            throw new ServiceException(GET_THREADLOCAL_ERROR);
        }
        return userCategory;
    }
}
