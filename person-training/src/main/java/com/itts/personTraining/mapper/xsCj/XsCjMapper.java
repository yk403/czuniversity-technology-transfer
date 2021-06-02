package com.itts.personTraining.mapper.xsCj;

import com.itts.personTraining.dto.XsCjDTO;
import com.itts.personTraining.model.xsCj.XsCj;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 学生成绩表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-06-01
 */
public interface XsCjMapper extends BaseMapper<XsCj> {

    /**
     * 查询所有学生成绩(学历学位)
     * @return
     */
    List<XsCjDTO> findXsKcCj(@Param("pcId") Long pcId);

    /**
     * 查询所有学生成绩(继续教育)
     * @param pcId
     * @return
     */
    List<XsCjDTO> findXsCj(@Param("pcId") Long pcId);
}
