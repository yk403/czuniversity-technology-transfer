package com.itts.personTraining.service.xsCj.impl;

import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.dto.XsCjDTO;
import com.itts.personTraining.dto.XsKcCjDTO;
import com.itts.personTraining.model.xsCj.XsCj;
import com.itts.personTraining.mapper.xsCj.XsCjMapper;
import com.itts.personTraining.model.xsKcCj.XsKcCj;
import com.itts.personTraining.service.xsCj.XsCjService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.service.xsKcCj.XsKcCjService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 * 学生成绩表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-06-01
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class XsCjServiceImpl extends ServiceImpl<XsCjMapper, XsCj> implements XsCjService {
    @Resource
    private XsCjMapper xsCjMapper;
    @Autowired
    private XsCjService xsCjService;
    @Autowired
    private XsKcCjService xsKcCjService;

    /**
     * 查询所有学生成绩
     * @return
     */
    @Override
    public List<XsCjDTO> getAll() {
        log.info("【人才培养 - 查询所有学生成绩】");
        List<XsCjDTO> xsCjDTOs = xsCjMapper.findXsCj();
        return xsCjDTOs;
    }

    /**
     * 新增学生成绩
     * @param xsCjDTO
     * @return
     */
    @Override
    public boolean add(XsCjDTO xsCjDTO) {
        log.info("【人才培养 - 新增学生成绩:{}】",xsCjDTO);
        Long userId = getUserId();
        xsCjDTO.setCjr(userId);
        xsCjDTO.setGxr(userId);
        XsCj xsCj = new XsCj();
        BeanUtils.copyProperties(xsCjDTO,xsCj);
        if (xsCjService.save(xsCj)) {
            List<XsKcCjDTO> xsKcCjDTOList = xsCjDTO.getXsKcCjDTOList();
            if (xsKcCjDTOList != null && xsKcCjDTOList.size() > 0) {
                Long id = xsCj.getId();
                List<XsKcCj> xsKcCjs = new ArrayList<>();
                int totalNum = 0;
                for (XsKcCjDTO xsKcCjDTO : xsKcCjDTOList) {
                    XsKcCj xsKcCj = new XsKcCj();
                    xsKcCjDTO.setXsCjId(id);
                    xsKcCjDTO.setKclx(true);
                    xsKcCjDTO.setCjr(userId);
                    xsKcCjDTO.setGxr(userId);
                    Integer dqxf = xsKcCjDTO.getDqxf();
                    totalNum += dqxf;
                    BeanUtils.copyProperties(xsKcCjDTO,xsKcCj);
                    xsKcCjs.add(xsKcCj);
                }
                if (xsKcCjService.saveBatch(xsKcCjs)) {
                    xsCj.setZxf(totalNum);
                    return xsCjService.updateById(xsCj);
                }
                return false;
            }
            return true;
        }
        return false;
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
}
