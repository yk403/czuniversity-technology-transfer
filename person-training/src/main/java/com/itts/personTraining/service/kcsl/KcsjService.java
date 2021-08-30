package com.itts.personTraining.service.kcsl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.personTraining.dto.KcXsXfDTO;
import com.itts.personTraining.model.kcsj.Kcsj;

import java.util.List;

/**
 * <p>
 * 课程时间表 服务类
 * </p>
 *
 * @author liuyingming
 * @since 2021-04-28
 */
public interface KcsjService extends IService<Kcsj> {

    /**
     * 列表 - 分页
     */
    PageInfo<Kcsj> fingByPage(Integer pageNum, Integer pageSize, Long fjjgId);

    /**
     * 新增
     */
    Kcsj add(Kcsj kcsj);

    /**
     * 更新
     */
    Kcsj update(Kcsj kcsj);

    /**
     * 删除
     */
    void delete(Kcsj kcsj);

}
