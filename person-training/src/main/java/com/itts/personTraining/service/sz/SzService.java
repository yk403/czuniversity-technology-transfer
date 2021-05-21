package com.itts.personTraining.service.sz;

import com.github.pagehelper.PageInfo;
import com.itts.personTraining.model.sz.Sz;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 师资表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
public interface SzService extends IService<Sz> {

    /**
     * 获取师资列表
     * @param pageNum
     * @param pageSize
     * @param dsxm
     * @param dslb
     * @param hyly
     * @return
     */
    PageInfo<Sz> findByPage(Integer pageNum, Integer pageSize, String dsxm, String dslb, String hyly);

    /**
     * 根据id查询师资详情
     * @param id
     * @return
     */
    Sz get(Long id);

    /**
     * 新增师资
     * @param sz
     * @return
     */
    boolean add(Sz sz,String token);

    /**
     * 更新师资
     * @param sz
     * @return
     */
    boolean update(Sz sz);

    /**
     * 删除师资
     * @param sz
     * @return
     */
    boolean delete(Sz sz);

    /**
     * 根据导师编号查询导师信息
     * @param dsbh
     * @return
     */
    Sz selectByDsbh(String dsbh);

    /**
     * 新增师资(外部调用)
     * @param sz
     * @return
     */
    boolean addSz(Sz sz);

    /**
     * 根据条件查询师资信息
     * @param dsbh
     * @param yhId
     * @return
     */
    Sz selectByCondition(String dsbh, Long yhId);
}
