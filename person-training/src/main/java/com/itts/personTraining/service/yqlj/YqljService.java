package com.itts.personTraining.service.yqlj;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.personTraining.model.xwgl.Xwgl;
import com.itts.personTraining.model.yqlj.Yqlj;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fuli
 * @since 2021-07-12
 */
public interface YqljService extends IService<Yqlj> {
    /**
     * 查询列表
     * @param pageNum
     * @param pageSize
     * @param jgId
     * @param zt
     * @param lx
     * @return
     */
    PageInfo<Yqlj> findByPage(Integer pageNum, Integer pageSize, Long jgId, String zt, String lx);

    /**
     * 新增
     * @param
     * @return
     */
    Yqlj add(Yqlj yqlj);
    /**
     * 更新
     * @param
     * @return
     */
    Yqlj update(Yqlj yqlj);
    /**
     * 根据id查询信息
     * @param id
     * @return
     */
    Yqlj get(Long id);
    /**
     * 发布
     * @param
     * @return
     */
    boolean release(Long id);
    /**
     * 停用
     * @param
     * @return
     */
    boolean out(Long id);
    /**
     * 批量下发
     * @param ids
     * @return
     */
    boolean issueBatch(List<Long> ids);

    /**
     * 删除
     * @param
     * @return
     */
    boolean delete(Long id);
}
