package com.itts.personTraining.service.zj;

import com.github.pagehelper.PageInfo;
import com.itts.personTraining.dto.ZjInfoDTO;
import com.itts.personTraining.model.zj.Zj;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 专家表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-05-25
 */
public interface ZjService extends IService<Zj> {

    /**
     * 分页查询专家
     * @param pageNum
     * @param pageSize
     * @param yjly
     * @param name
     * @param lx
     * @return
     */
    PageInfo<Zj> findByPage(Integer pageNum, Integer pageSize, String yjly, String name, String lx);

    List<Zj> findExport(String yjly, String name);
    /**
     * 查询所有专家
     * @return
     */
    List<Zj> getAll();

    /**
     * 新增专家
     * @param zj
     * @param token
     * @return
     */
    boolean add(Zj zj,String token);

    /**
     * 根据id查询专家信息
     * @param id
     * @return
     */
    Zj get(Long id);

    /**
     * 更新专家
     * @param zj
     * @param zjInfoDTO
     * @param token
     * @return
     */
    boolean update(Zj zj,ZjInfoDTO zjInfoDTO,String token);

    /**
     * 删除专家信息
     * @param zj
     * @return
     */
    boolean delete(Zj zj);

    /**
     * 根据姓名电话用户id查询专家信息
     * @param xm
     * @param dh
     * @param yhId
     * @return
     */
    Zj getByXmDh(String xm, String dh, Long yhId);

    /**
     * 新增专家(外部调用)
     * @param zj
     * @return
     */
    boolean addZj(Zj zj);

    /**
     * 更新专家(外部调用)
     * @param zj
     * @return
     */
    boolean updateZj(Zj zj);

    /**
     * 查询专家综合信息
     * @return
     */
    ZjInfoDTO getByYhId();
}
