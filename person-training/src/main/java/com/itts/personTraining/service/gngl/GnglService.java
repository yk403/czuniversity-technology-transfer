package com.itts.personTraining.service.gngl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itts.personTraining.model.gngl.Gngl;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fuli
 * @since 2021-07-21
 */
public interface GnglService extends IService<Gngl> {

    List<Gngl> find();
    Gngl get(Long id);
    boolean use(Long id);
}
