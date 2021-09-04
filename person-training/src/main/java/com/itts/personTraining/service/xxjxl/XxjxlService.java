package com.itts.personTraining.service.xxjxl;

import com.github.pagehelper.PageInfo;
import com.itts.personTraining.model.xxjxl.Xxjxl;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 学校教学楼表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-09-01
 */
public interface XxjxlService extends IService<Xxjxl> {

    /**
     * 查询学校教学楼列表
     * @param pageNum
     * @param pageSize
     * @param jxlmc
     * @return
     */
    PageInfo<Xxjxl> findByPage(Integer pageNum, Integer pageSize, String jxlmc,Long fjjgId);

    /**
     * 根据id查询学校教学楼详情
     * @param id
     * @return
     */
    Xxjxl get(Long id);

    /**
     * 新增学校教学楼
     * @param xxjxl
     * @return
     */
    boolean add(Xxjxl xxjxl);

    /**
     * 更新学校教学楼
     * @param xxjxl
     * @return
     */
    boolean update(Xxjxl xxjxl);

    /**
     * 删除学校教学楼
     * @param xxjxl
     * @return
     */
    boolean delete(Xxjxl xxjxl);

    /**
     * 查询所有学校教学楼
     * @return
     */
    List<Xxjxl> findAll();
}
