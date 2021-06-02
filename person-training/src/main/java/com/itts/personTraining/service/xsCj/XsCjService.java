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
     * 查询所有学生成绩
     * @return
     */
    List<XsCjDTO> getAll();

    /**
     * 新增学生成绩
     * @param xsCjDTO
     * @return
     */
    boolean add(XsCjDTO xsCjDTO);
}
