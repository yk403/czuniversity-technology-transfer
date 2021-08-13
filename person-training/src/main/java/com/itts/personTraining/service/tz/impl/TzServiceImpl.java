package com.itts.personTraining.service.tz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.dto.TzDTO;
import com.itts.personTraining.dto.XsMsgDTO;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.mapper.tzSz.TzSzMapper;
import com.itts.personTraining.mapper.tzXs.TzXsMapper;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.model.tz.Tz;
import com.itts.personTraining.mapper.tz.TzMapper;
import com.itts.personTraining.service.tz.TzService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Collections;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;

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
    @Resource
    private TzXsMapper tzXsMapper;
    @Resource
    private SzMapper szMapper;
    @Resource
    private TzSzMapper tzSzMapper;

    /**
     * 根据用户类别分页条件查询通知信息(前)
     * @return
     */
    @Override
    public PageInfo<TzDTO> findByCategory(Integer pageNum, Integer pageSize, String tzlx) {
        log.info("【人才培养 - 根据用户类别分页条件查询通知信息(前),通知类型:{}】",tzlx);
        Long userId = getUserId();
        String userCategory = getUserCategory();
        List<TzDTO> tzDTOList = null;
        switch (userCategory) {
            case "postgraduate":
            case "broker":
                PageHelper.startPage(pageNum, pageSize);
                XsMsgDTO xsMsgDTO = xsMapper.getByYhId(userId);
                if (xsMsgDTO == null) {
                    throw new ServiceException(STUDENT_MSG_NOT_EXISTS_ERROR);
                }
                tzDTOList = tzMapper.findTzDTOByXsIdAndTzlx(xsMsgDTO.getId(),tzlx);
                break;
            case "tutor":
            case "corporate_mentor":
            case "teacher":
                PageHelper.startPage(pageNum, pageSize);
                Sz sz = szMapper.getSzByYhId(userId);
                if (sz == null) {
                    throw new ServiceException(TEACHER_MSG_NOT_EXISTS_ERROR);
                }
                tzDTOList = tzMapper.findTzDTOBySzIdAndTzlx(sz.getId(),tzlx);
                break;
            default:
                break;
        }
        if (tzDTOList == null) {
            return null;
        }
        return new PageInfo<>(tzDTOList);
    }

    /**
     * 根据通知id查询通知信息
     * @param id
     * @return
     */
    @Override
    public TzDTO getTzDTOById(Long id) {
        log.info("【人才培养 - 根据通知id:{}查询通知信息,通知类型:{}】",id);
        Long userId = getUserId();
        String userCategory = getUserCategory();
        TzDTO tzDTO = null;
        switch (userCategory) {
            case "postgraduate":
            case "broker":
                XsMsgDTO xsMsgDTO = xsMapper.getByYhId(userId);
                if (xsMsgDTO == null) {
                    throw new ServiceException(STUDENT_MSG_NOT_EXISTS_ERROR);
                }
                tzDTO = tzMapper.getTzDTOByIdAndXsId(id,xsMsgDTO.getId());
                tzXsMapper.updateByTzIdAndXsId(id, xsMsgDTO.getId());
                break;
            case "tutor":
            case "corporate_mentor":
            case "teacher":
                Sz sz = szMapper.getSzByYhId(userId);
                if (sz == null) {
                    throw new ServiceException(TEACHER_MSG_NOT_EXISTS_ERROR);
                }
                tzDTO = tzMapper.getTzDTOByIdAndSzId(id,sz.getId());
                tzSzMapper.updateByTzIdAndSzId(id,sz.getId());
                break;
            default:
                break;
        }

        return tzDTO;
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
