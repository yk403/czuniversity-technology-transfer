package com.itts.personTraining.service.tz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.dto.TzDTO;
import com.itts.personTraining.dto.XsMsgDTO;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.model.tz.Tz;
import com.itts.personTraining.mapper.tz.TzMapper;
import com.itts.personTraining.service.tz.TzService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

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
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class TzServiceImpl extends ServiceImpl<TzMapper, Tz> implements TzService {

    @Resource
    private TzMapper tzMapper;
    @Resource
    private XsMapper xsMapper;

    /**
     * 根据用户类别查询通知信息(前)
     * @return
     */
    @Override
    public PageInfo<TzDTO> findByCategory(Integer pageNum, Integer pageSize, String tzlx) {
        log.info("【人才培养 - 分页条件查询学员列表,通知类型:{}】",tzlx);
        PageHelper.startPage(pageNum, pageSize);
        Long userId = getUserId();
        XsMsgDTO xsId = xsMapper.getByYhId(userId);
        String userCategory = getUserCategory();
        List<TzDTO> tzDTOList = null;
        switch (userCategory) {
            case "postgraduate":
            case "corporate_mentor":
                tzDTOList = tzMapper.findTzDTOByXsIdAndTzlx(xsId,tzlx);
                break;
            default:
                break;
        }
        return new PageInfo<>(tzDTOList);
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
