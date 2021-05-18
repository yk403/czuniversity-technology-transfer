package com.itts.personTraining.service.xxjs;

import com.github.pagehelper.PageInfo;
import com.itts.personTraining.model.xxjs.Xxjs;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 学校教室表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-04-22
 */
public interface XxjsService extends IService<Xxjs> {

    /**
     * 查询学校教室列表
     * @param pageNum
     * @param pageSize
     * @param jxlmc
     * @return
     */
    PageInfo<Xxjs> findByPage(Integer pageNum, Integer pageSize, String jxlmc);

    /**
     * 根据id查询学校教室详情
     * @param id
     * @return
     */
    Xxjs get(Long id);

    /**
     * 新增学校教室
     * @param xxjs
     * @return
     */
    boolean add(Xxjs xxjs);

    /**
     * 更新学校教室
     * @param xxjs
     * @return
     */
    boolean update(Xxjs xxjs);

    /**
     * 删除学校教室
     * @param xxjs
     * @return
     */
    boolean delete(Xxjs xxjs);

    /**
     * 查询学校教室是否存在
     * @return
     */
    boolean selectExists(Xxjs xxjs);

    /**
     * 根据教学楼名称或教室编号查询学校教室信息
     * @param jxlmc
     * @param jsbh
     * @return
     */
    List<Xxjs> getByMcOrBh(String jxlmc,String jsbh);

    /**
     * 查询所有学校教室
     * @return
     */
    List<Xxjs> getAll();
}
