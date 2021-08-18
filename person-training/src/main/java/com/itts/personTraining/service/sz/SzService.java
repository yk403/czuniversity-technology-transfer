package com.itts.personTraining.service.sz;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.personTraining.dto.SzMsgDTO;
import com.itts.personTraining.model.sz.Sz;

import java.util.List;

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
     * @param name
     * @param dslb
     * @param hyly
     * @return
     */
    PageInfo<Sz> findByPage(Integer pageNum, Integer pageSize, String name, String dslb, String hyly, Long groupId);

    List<Sz>  findXsBySz(String dslb);
    List<Sz>  findExport(String dsxm, String dslb, String hyly);
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
    boolean update(Sz sz,String token);

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
     * @param xb
     * @param yhId
     * @param groupId
     * @return
     */
    Sz selectByCondition(String dsbh,String xb, Long yhId, Long groupId);

    /**
     * 根据用户id查询师资综合信息
     * @return
     */
    SzMsgDTO getByYhId();

    /**
     * 根据机构编号查询师资信息
     * @param code
     * @return
     */
    List<Sz> getByJgBh(String code);

    /**
     * 更新师资(外部调用)
     * @param sz
     * @return
     */
    boolean updateSz(Sz sz);
}
