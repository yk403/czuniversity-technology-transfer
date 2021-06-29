package com.itts.personTraining.service.xxzy;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.personTraining.model.xxzy.Xxzy;
import com.itts.personTraining.model.xxzy.Xxzysc;

/**
 * <p>
 * 学习资源收藏 服务类
 * </p>
 *
 * @author liuyingming
 * @since 2021-06-09
 */
public interface XxzyscService extends IService<Xxzysc> {

    /**
     * 获取学习资源收藏列表 - 分页
     */
    PageInfo<Xxzy> findScByPage(int pageNum, int pageSize, Long userId, String firstCategory,
                                String secondCategory, String category, String direction);

    /**
     * 新增
     */
    Xxzysc add(Xxzy xxzy, Long userId);

    /**
     * 取消收藏
     */
    void delete(Long id);
}
