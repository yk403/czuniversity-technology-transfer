package com.itts.personTraining.service.sjtxndpz;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itts.personTraining.model.sjtxndpz.Sjtxndpz;
import com.itts.personTraining.vo.sjpz.SjpzVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fuli
 * @since 2021-09-07
 */
public interface SjtxndpzService extends IService<Sjtxndpz> {

    Sjtxndpz get(Long id,String mc);


}
