package com.itts.personTraining.service.spzb;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.personTraining.model.spzb.Spzb;

/**
 * <p>
 * 视频直播 服务类
 * </p>
 *
 * @author liuyingming
 * @since 2021-04-27
 */
public interface SpzbService extends IService<Spzb> {

    /**
     * 获取列表 - 分页
     *
     * @param
     * @return
     * @author liuyingming
     */
    PageInfo findByPage(Integer pageNum, Integer pageSize, String name, String videoType);

    /**
     * 新增
     *
     * @param
     * @return
     * @author liuyingming
     */
    Spzb add(Spzb spzb);

    /**
     * 更新
     *
     * @param
     * @return
     * @author liuyingming
     */
    Spzb update(Spzb spzb);

    /**
     * 删除
     *
     * @param
     * @return
     * @author liuyingming
     */
    void delete(Spzb spzb);

}
