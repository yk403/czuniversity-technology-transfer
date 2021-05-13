package com.itts.personTraining.service.pyjh;

import com.github.pagehelper.PageInfo;
import com.itts.personTraining.dto.PyJhDTO;
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
     * @param pch
     * @param jylx
     * @param jhmc
     * @return
     */
    PageInfo<PyJhDTO> findByPage(Integer pageNum, Integer pageSize, String pch, String jylx, String jhmc);

    /**
     * 根据id查询培养计划
     * @param id
     * @return
     */
    PyJhDTO get(Long id);

    /**
     * 新增培养计划
     * @param pyJhDTO
     * @return
     */
    boolean add(PyJhDTO pyJhDTO);

    /**
     * 更新培养计划
     * @param pyJhDTO
     * @return
     */
    boolean update(PyJhDTO pyJhDTO);

    /**
     * 删除培养计划
     * @param pyJhDTO
     * @return
     */
    boolean delete(PyJhDTO pyJhDTO);

    /**
     * 培养计划批量下发
     * @param ids
     * @return
     */
    boolean issueBatch(List<Long> ids);
}
