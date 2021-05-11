package com.itts.personTraining.service.pyjh.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.model.pyjh.PyJh;
import com.itts.personTraining.mapper.pyjh.PyJhMapper;
import com.itts.personTraining.service.pyjh.PyJhService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 * 培养计划表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Service
@Slf4j
@Transactional
public class PyJhServiceImpl extends ServiceImpl<PyJhMapper, PyJh> implements PyJhService {

    @Resource
    private PyJhMapper pyJhMapper;
    @Autowired
    private PyJhService pyJhService;

    /**
     * 查询培养计划列表
     * @param pageNum
     * @param pageSize
     * @param xslbmc
     * @param name
     * @param pcId
     * @return
     */
    @Override
    public PageInfo<PyJh> findByPage(Integer pageNum, Integer pageSize, String xslbmc, String name, Long pcId) {
        log.info("【人才培养 - 分页条件查询培养计划列表,学生类别名称:{},培养方案/培养计划/教学大纲:{},批次id:{}】",xslbmc,name,pcId);
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<PyJh> pyJhQueryWrapper = new QueryWrapper<>();
        pyJhQueryWrapper.eq("sfsc",false)
                .eq(pcId != null,"pc_id",pcId)
                .eq(StringUtils.isNotBlank(xslbmc),"xslbmc",xslbmc)
                .like(StringUtils.isNotBlank(name), "pyfa", name)
                .or().like(StringUtils.isNotBlank(name), "pyjh", name)
                .or().like(StringUtils.isNotBlank(name), "jxdg", name);
        return new PageInfo<>(pyJhMapper.selectList(pyJhQueryWrapper));
    }

    /**
     * 根据id查询培养计划
     * @param id
     * @return
     */
    @Override
    public PyJh get(Long id) {
        log.info("【人才培养 - 根据id:{}查询培养计划】",id);
        QueryWrapper<PyJh> pyJhQueryWrapper = new QueryWrapper<>();
        pyJhQueryWrapper.eq("sfsc",false)
                .eq("id",id);
        return pyJhMapper.selectOne(pyJhQueryWrapper);
    }

    /**
     * 新增培养计划
     * @param pyJh
     * @return
     */
    @Override
    public boolean add(PyJh pyJh) {
        log.info("【人才培养 - 新增培养计划:{}】",pyJh);
        Long userId = getUserId();
        pyJh.setCjr(userId);
        pyJh.setGxr(userId);
        return pyJhService.save(pyJh);
    }

    /**
     * 更新培养计划
     * @param pyJh
     * @return
     */
    @Override
    public boolean update(PyJh pyJh) {
        log.info("【人才培养 - 更新培养计划:{}】",pyJh);
        Long userId = getUserId();
        pyJh.setGxr(userId);
        return pyJhService.updateById(pyJh);
    }

    /**
     * 删除培养计划
     * @param pyJh
     * @return
     */
    @Override
    public boolean delete(PyJh pyJh) {
        log.info("【人才培养 - 删除培养计划:{}】",pyJh);
        //设置删除状态
        pyJh.setSfsc(true);
        pyJh.setGxr(getUserId());
        return pyJhService.updateById(pyJh);
    }

    /**
     * 培养计划批量下发
     * @param ids
     * @return
     */
    @Override
    public boolean issueBatch(List<Long> ids) {
        log.info("【人才培养 - 培养计划批量下发,ids:{}】",ids);
        List<PyJh> pyJhs = pyJhMapper.selectBatchIds(ids);
        Long userId = getUserId();
        for (PyJh pyJh : pyJhs) {
            pyJh.setSfxf(true);
            pyJh.setGxr(userId);
        }
        return pyJhService.updateBatchById(pyJhs);
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
