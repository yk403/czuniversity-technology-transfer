package com.itts.personTraining.service.xsKcCj;

import com.itts.personTraining.dto.XsKcCjDTO;
import com.itts.personTraining.model.xsKcCj.XsKcCj;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 学生课程成绩表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-06-01
 */
public interface XsKcCjService extends IService<XsKcCj> {

    /**
     * 根据学生成绩id查询学生课程成绩集合
     * @param xsCjId
     * @param kclx
     * @return
     */
    List<XsKcCjDTO> getByXsCjId(Long xsCjId,Integer kclx);

    /**
     * 更新学生课程成绩
     * @param xsKcCjDTOs
     * @return
     */
    boolean update(List<XsKcCjDTO> xsKcCjDTOs);
}
