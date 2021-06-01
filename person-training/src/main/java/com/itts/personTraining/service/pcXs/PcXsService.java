package com.itts.personTraining.service.pcXs;

import com.itts.personTraining.dto.StuDTO;
import com.itts.personTraining.model.pcXs.PcXs;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 批次学生关系表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-05-18
 */
public interface PcXsService extends IService<PcXs> {

    /**
     * 根据pcId查询学生信息
     * @param pcId
     * @return
     */
    List<StuDTO> getByPcId(Long pcId);

}
