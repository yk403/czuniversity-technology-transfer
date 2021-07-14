package com.itts.personTraining.service.ggtz;

import com.github.pagehelper.PageInfo;
import com.itts.personTraining.model.ggtz.Ggtz;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fuli
 * @since 2021-07-12
 */
public interface GgtzService extends IService<Ggtz> {
    /**
     * 查询列表
     * @param pageNum
     * @param pageSize
     * @param jgId
     * @param zt
     * @param lx
     * @return
     */
    PageInfo<Ggtz> findByPage(Integer pageNum, Integer pageSize, Long jgId, String zt, String lx,String tzbt);

    /**
     * 新增
     * @param
     * @return
     */
    Ggtz add(Ggtz ggtz);
    /**
     * 更新
     * @param
     * @return
     */
    Ggtz update(Ggtz ggtz);
    /**
     * 根据id查询信息
     * @param id
     * @return
     */
    Ggtz get(Long id);
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
