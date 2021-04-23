package com.itts.personTraining.mapper.pc;

import com.itts.personTraining.model.pc.Pc;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 批次表 Mapper 接口
 * </p>
 *
 * @author FL
 * @since 2021-04-20
 */
public interface PcMapper extends BaseMapper<Pc> {


    List<Pc> selectPcList(List<Long> ids);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Boolean updatePcList(List<Long> ids);
}
