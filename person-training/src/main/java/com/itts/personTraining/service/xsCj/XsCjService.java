package com.itts.personTraining.service.xsCj;

import com.github.pagehelper.PageInfo;
import com.itts.personTraining.dto.XsCjDTO;
import com.itts.personTraining.model.xsCj.XsCj;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 学生成绩表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-06-01
 */
public interface XsCjService extends IService<XsCj> {

    /**
     * 根据批次id查询所有学生成绩(学历学位)
     * @param pcId
     * @return
     */
    List<XsCjDTO> getByPcId(Long pcId);

    /**
     * 新增学生成绩
     * @param xsCjDTO
     * @return
     */
    boolean add(XsCjDTO xsCjDTO);

    /**
     * 删除学生成绩
     * @param xsCj
     * @return
     */
    boolean delete(XsCj xsCj);

    /**
     * 根据id查询XsCj对象
     * @param id
     * @return
     */
    XsCj get(Long id);

    /**
     * 根据piId查询XsCj对象
     * @param pcId
     * @return
     */
    List<XsCj> getXsCjByPcId(Long pcId);

    /**
     * 分页条件查询学生成绩
     * @param pageNum
     * @param pageSize
     * @param pcId
     * @param xh
     * @param xm
     * @param xymc
     * @return
     */
    PageInfo<XsCjDTO> findByPage(Integer pageNum, Integer pageSize, Long pcId, String xh, String xm, String xymc);

    /**
     * 更新学生成绩
     * @param xsCjDTO
     * @return
     */
    boolean update(XsCjDTO xsCjDTO);

    /**
     * 学生成绩批量下发
     * @param ids
     * @return
     */
    boolean issueBatch(List<Long> ids);
}
