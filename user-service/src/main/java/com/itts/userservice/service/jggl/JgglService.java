package com.itts.userservice.service.jggl;

import com.github.pagehelper.PageInfo;
import com.itts.userservice.model.jggl.Jggl;
import com.itts.userservice.vo.JgglVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fl
 * @since 2021-03-30
 */
public interface JgglService  {

    /**
     * 获取列表 - 分页
     */
    PageInfo<Jggl> findPage(Integer pageNum,Integer pageSize,String jgbm,String string,String jglb,String lx);
    PageInfo<Jggl> findByPage(Integer pageNum,Integer pageSize,String jgbm);
    PageInfo<Jggl> selectByString(Integer pageNum,Integer pageSize,String string);
    /**
     * 获取机构管理树
     * @return
     */
    List<JgglVO> findJgglVO(String jgbm);
    /**
     * 获取关键字机构管理树
     * @return
     */
    List<JgglVO> findStringJgglVO(String string);
    /**
     * 获取通过id
     */
    Jggl get(Long id);

    /**
     * 通过层级获取子机构
     * @param cj
     * @return
     */
    List<Jggl> getList(String cj);
    /**
     * 查询，通过机构名称
     */
    Jggl selectByJgmc(String jgmc);
    /**
     * 查询，通过机构代码
     */
    Jggl selectByJgbm(String jgbm);


    /**
     * 新增
     *
     */
    Jggl add(Jggl jggl);
    /**
     * 更新
     */
    Jggl update(Jggl jggl);

}
