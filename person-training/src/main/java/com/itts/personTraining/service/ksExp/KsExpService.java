package com.itts.personTraining.service.ksExp;

import com.itts.personTraining.dto.KsExpDTO;
import com.itts.personTraining.model.ksExp.KsExp;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 考试扩展表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-05-13
 */
public interface KsExpService extends IService<KsExp> {

    /**
     * 根据考试id查询考试扩展信息
     * @param id
     * @return
     */
    List<KsExpDTO> get(Long id);
}
