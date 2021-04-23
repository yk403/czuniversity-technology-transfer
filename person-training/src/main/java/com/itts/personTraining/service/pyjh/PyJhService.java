package com.itts.personTraining.service.pyjh;

import com.github.pagehelper.PageInfo;
import com.itts.personTraining.model.pyjh.PyJh;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 培养计划表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
public interface PyJhService extends IService<PyJh> {

    /**
     * 查询培养计划列表
     * @param pageNum
     * @param pageSize
     * @param xslbmc
     * @param name
     * @param pcId
     * @return
     */
    PageInfo<PyJh> findByPage(Integer pageNum, Integer pageSize, String xslbmc, String name, Long pcId);

    /**
     * 根据id查询培养计划
     * @param id
     * @return
     */
    PyJh get(Long id);

    /**
     * 新增培养计划
     * @param pyJh
     * @return
     */
    boolean add(PyJh pyJh);

    /**
     * 更新培养计划
     * @return
     */
    boolean update(PyJh pyJh);

    /**
     * 删除培养计划
     * @param pyJh
     * @return
     */
    boolean delete(PyJh pyJh);

    /**
     * 培养计划批量下发
     * @param ids
     * @return
     */
    boolean issueBatch(List<Long> ids);
}