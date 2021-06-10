package com.itts.personTraining.service.xxzy;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.xxzy.Xxzy;
import com.itts.personTraining.request.xxzy.AddXxzyRequest;
import com.itts.personTraining.request.xxzy.BuyXxzyRequest;
import com.itts.personTraining.request.xxzy.UpdateXxzyRequest;
import com.itts.personTraining.vo.xxzy.GetXxzyVO;

/**
 * <p>
 * 学习资源 服务类
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-12
 */
public interface XxzyService extends IService<Xxzy> {

    /**
     * 获取列表 - 分页
     */
    PageInfo<Xxzy> list(Integer pageNum, Integer pageSize, String type, String firstCategory,
                        String secondCategory, Long courseId, String condition);

    /**
     * 获取列表 - 分页
     */
    PageInfo<GetXxzyVO> listVO(Integer pageNum, Integer pageSize, String type, String firstCategory,
                               String secondCategory, Long courseId, String condition, String token);

    /**
     * 获取详情
     */
    GetXxzyVO get(Long id);

    /**
     * 新增
     */
    Xxzy add(AddXxzyRequest addXxzyRequest);

    /**
     * 更新
     */
    Xxzy update(UpdateXxzyRequest updateXxzyRequest, Xxzy xxzy, Long userId);

    /**
     * 删除附件资源
     */
    void deleteFjzy(Long fjzyId);

    /**
     * 购买学习资源
     */
    ResponseUtil buy(BuyXxzyRequest buyXxzyRequest, String token);
}
