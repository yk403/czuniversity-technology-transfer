package com.itts.personTraining.service.xsKcCj;

import com.itts.personTraining.dto.XsCjDTO;
import com.itts.personTraining.dto.XsKcCjDTO;
import com.itts.personTraining.model.xsCj.XixwExcel;
import com.itts.personTraining.model.xsCj.XsCjExcel;
import com.itts.personTraining.model.xsCj.YzyCjExcel;
import com.itts.personTraining.model.xsKcCj.XsKcCj;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
     * @param xsId
     * @return
     */
    XsCjDTO getByXsCjId(Long xsCjId, Integer kclx, Long xsId);

    /**
     * 更新学生课程成绩
     * @param xsKcCjDTOs
     * @return
     */
    boolean update(List<XsKcCjDTO> xsKcCjDTOs);

    List<XsCjExcel> getByPcId(Long pcId,String jylx);
    List<XixwExcel> findByXixw(Long pcId);
    List<YzyCjExcel> findByYzy(Long pcId);
}
