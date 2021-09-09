package com.itts.personTraining.service.sjpz;

import com.github.pagehelper.PageInfo;
import com.itts.personTraining.model.sjpz.Sjpz;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itts.personTraining.vo.sjpz.SjpzVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fuli
 * @since 2021-09-07
 */
public interface SjpzService extends IService<Sjpz> {

    PageInfo<Sjpz> getList(Integer pageNum, Integer pageSize, Long fjjgId, String nd, String mc);
    SjpzVO get(Long id);
    SjpzVO add(SjpzVO sjpzVO);
    Sjpz getByMc(String mc);
    SjpzVO update(Sjpz old,SjpzVO sjpzVO);
    Boolean delete(Long id);
}
