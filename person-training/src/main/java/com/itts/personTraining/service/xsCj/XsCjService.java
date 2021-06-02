package com.itts.personTraining.service.xsCj;

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
     * @param xsCjDTO
     * @return
     */
    boolean delete(XsCjDTO xsCjDTO);

    /**
     * 根据id查询学生成绩详情
     * @param id
     * @return
     */
    XsCjDTO get(Long id);

    /**
     * 根据piId查询XsCj对象
     * @param pcId
     * @return
     */
    List<XsCj> getXsCjByPcId(Long pcId);
}
