package com.itts.personTraining.mapper.kcsj;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.personTraining.dto.KcXsXfDTO;
import com.itts.personTraining.model.kcsj.Kcsj;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 课程时间表 Mapper 接口
 * </p>
 *
 * @author liuyingming
 * @since 2021-04-28
 */
@Repository
public interface KcsjMapper extends BaseMapper<Kcsj> {
}
