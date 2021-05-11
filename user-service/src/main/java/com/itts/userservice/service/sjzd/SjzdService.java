package com.itts.userservice.service.sjzd;

import com.github.pagehelper.PageInfo;
import com.itts.userservice.model.sjzd.Sjzd;
import com.itts.userservice.request.sjzd.AddSjzdRequest;
import com.itts.userservice.request.sjzd.GetSjzdRequest;
import com.itts.userservice.request.sjzd.UpdateSjzdRequest;
import com.itts.userservice.vo.SjzdModelVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author fl
 * @since 2021-03-31
 */
public interface SjzdService {

    /**
     * 获取数据字典模块列表
     */
    PageInfo<SjzdModelVO> findDictionaryModel(Integer pageNum, Integer pageSize, String model, String systemType, String condition);

    /**
     * 通过所属模块获取列表
     */
    List<Sjzd> findBySsmk(String xtlb, String mklx, String ssmk);

    /**
     * 获取列表 - 分页
     */
    PageInfo<Sjzd> findByPage(Integer pageNum, Integer pageSize, String model, String systemType, String dictionary, String zdbm);

    /**
     * 获取通过id
     */
    Sjzd get(Long id);

    /**
     * 通过所属模块获取数据
     */
    GetSjzdRequest get(String xtlb, String mklx, String ssmk);

    /**
     * 查询，通过名称或编码
     */
    PageInfo<Sjzd> selectByString(Integer pageNum, Integer pageSize, String string, String ssmk);

    /**
     * 新增
     */
    AddSjzdRequest add(AddSjzdRequest sjzd);

    /**
     * 更新
     */
    UpdateSjzdRequest update(UpdateSjzdRequest sjzd);

    /**
     * 删除
     */
    void delete(Long id);
}
