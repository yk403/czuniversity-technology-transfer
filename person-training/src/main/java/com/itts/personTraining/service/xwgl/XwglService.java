package com.itts.personTraining.service.xwgl;

import com.github.pagehelper.PageInfo;
import com.itts.personTraining.dto.KcDTO;
import com.itts.personTraining.model.xwgl.Xwgl;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fuli
 * @since 2021-07-09
 */
public interface XwglService extends IService<Xwgl> {

    /**
     * 查询新闻列表
     * @param pageNum
     * @param pageSize
     * @param jgId
     * @param zt
     * @param lx
     * @return
     */
    PageInfo<Xwgl> findByPage(Integer pageNum, Integer pageSize,Long jgId,String zt,String lx);

    /**
     * 新增新闻
     * @param xwgl
     * @return
     */
    Xwgl add(Xwgl xwgl);
    /**
     * 更新
     * @param
     * @return
     */
    Xwgl update(Xwgl xwgl);
    /**
     * 根据id查询信息
     * @param id
     * @return
     */
    Xwgl get(Long id);
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
