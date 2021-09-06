package com.itts.personTraining.service.xxzy;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.xxzy.Xxzy;
import com.itts.personTraining.request.xxzy.AddXxzyRequest;
import com.itts.personTraining.request.xxzy.BuyXxzyRequest;
import com.itts.personTraining.request.xxzy.UpdateXxzyRequest;
import com.itts.personTraining.vo.xxzy.GetXxzyVO;

import java.util.List;

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
   * 新增
   */
  Xxzy add(AddXxzyRequest addXxzyRequest);

  /**
   * 购买学习资源
   */
  ResponseUtil buy(BuyXxzyRequest buyXxzyRequest);

  /**
   * 删除附件资源
   */
  void deleteFjzy(Long fjzyId);

  /**
   * 通过机构ID获取数据
   */
  PageInfo<GetXxzyVO> findByJgId(Integer pageNum, Integer pageSize, String type, String firstCategory,
                                 String secondCategory, String category, String direction, Long courseId,
                                 String condition);

  /**
   * 获取详情
   */
  GetXxzyVO get(Long id);

  /**
   * 获取云课堂课程列表
   */
  List<Kc> getCloudClassroomCourse(String userType, String educationType, String studentType, Long groupId);

  /**
   * 获取列表 - 分页
   */
  PageInfo<Xxzy> list(Integer pageNum, Integer pageSize, String type, String firstCategory,
                      String secondCategory, String category, Long courseId, String condition, Long groupId,Long fjjgId);

  /**
   * 获取列表 - 分页
   */
  PageInfo<GetXxzyVO> listVO(Integer pageNum, Integer pageSize, String type, String firstCategory,
                             String secondCategory, String category, String direction, Long courseId,
                             String condition, Long groupId, String groupCode);

  /**
   * 支付金额
   */
  ResponseUtil pay(String orderNo, String payType);

  /**
   * 更新
   */
  Xxzy update(UpdateXxzyRequest updateXxzyRequest, Xxzy xxzy, Long userId);
}
