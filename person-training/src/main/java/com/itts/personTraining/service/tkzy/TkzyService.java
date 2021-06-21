package com.itts.personTraining.service.tkzy;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.personTraining.model.tkzy.Tkzy;
import com.itts.personTraining.request.tkzy.AddTkzyRequest;
import com.itts.personTraining.request.tkzy.UpdateTkzyRequest;
import com.itts.personTraining.vo.tkzy.GetTkzyVO;

/**
 * <p>
 * 题库资源 服务类
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-13
 */
public interface TkzyService extends IService<Tkzy> {

    /**
     * 列表 - 有题目选项
     */
    PageInfo listByDetail(Integer pageNum, Integer pageSize, String firstCategory, String secondCategory, Long courseId, Integer score, String type, Boolean putOnShelf);

    /**
     * 详情
     */
    GetTkzyVO get(Long id);

    /**
     * 新增
     */
    Tkzy add(AddTkzyRequest addTkzyRequest);

    /**
     * 更新
     */
    Tkzy update(UpdateTkzyRequest updateTkzyRequest, Tkzy tkzy, Long userId);
}
