package com.itts.personTraining.mapper.xxjxl;

import com.itts.personTraining.model.xxjxl.Xxjxl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 学校教学楼表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-09-01
 */
public interface XxjxlMapper extends BaseMapper<Xxjxl> {
    /**
     * 查询所有教学楼
     * @return
     */
    List<Xxjxl> findAllJxlmc();
}
