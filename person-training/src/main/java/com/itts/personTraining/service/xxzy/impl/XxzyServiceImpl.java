package com.itts.personTraining.service.xxzy.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.enums.SystemTypeEnum;
import com.itts.common.exception.ServiceException;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.CommonUtils;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.enums.UserTypeEnum;
import com.itts.personTraining.feign.paymentservice.OrderFeignService;
import com.itts.personTraining.feign.userservice.GroupFeignService;
import com.itts.personTraining.mapper.fjzy.FjzyMapper;
import com.itts.personTraining.mapper.kc.KcMapper;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.mapper.xxzy.XxzyMapper;
import com.itts.personTraining.mapper.xxzy.XxzyscMapper;
import com.itts.personTraining.model.fjzy.Fjzy;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.model.xxzy.Xxzy;
import com.itts.personTraining.model.xxzy.Xxzysc;
import com.itts.personTraining.request.ddxfjl.AddDdxfjlRequest;
import com.itts.personTraining.request.fjzy.AddFjzyRequest;
import com.itts.personTraining.request.xxzy.AddXxzyRequest;
import com.itts.personTraining.request.xxzy.BuyXxzyRequest;
import com.itts.personTraining.request.xxzy.UpdateXxzyRequest;
import com.itts.personTraining.service.xxzy.XxzyService;
import com.itts.personTraining.vo.ddxfjl.GetDdxfjlVO;
import com.itts.personTraining.vo.jggl.JgglVO;
import com.itts.personTraining.vo.xxzy.GetXxzyVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 * 学习资源 服务实现类
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-12
 */
@Service
public class XxzyServiceImpl extends ServiceImpl<XxzyMapper, Xxzy> implements XxzyService {

  @Autowired
  private FjzyMapper fjzyMapper;
  @Autowired
  private GroupFeignService groupFeignService;
  @Autowired
  private KcMapper kcMapper;
  @Autowired
  private OrderFeignService orderFeignService;
  @Autowired
  private SzMapper szMapper;
  @Autowired
  private XxzyMapper xxzyMapper;
  @Autowired
  private XxzyscMapper xxzyscMapper;

  /**
   * 新增
   */
  @Override
  public Xxzy add(AddXxzyRequest addXxzyRequest) {

    LoginUser loginUser = SystemConstant.threadLocal.get();

    Long userId = null;
    if (loginUser != null) {
      userId = loginUser.getUserId();
    }

    Xxzy xxzy = new Xxzy();
    BeanUtils.copyProperties(addXxzyRequest, xxzy);

    Date now = new Date();

    xxzy.setCjr(userId);
    xxzy.setGxr(userId);
    xxzy.setCjsj(now);
    xxzy.setGxsj(now);
    xxzy.setJg(addXxzyRequest.getJg());
    xxzy.setZz(loginUser.getRealName());

    if (!CollectionUtils.isEmpty(addXxzyRequest.getFjzys())) {

      String fjzyId = CommonUtils.generateUUID();
      xxzy.setFjzyId(fjzyId);

      List<AddFjzyRequest> fjzys = addXxzyRequest.getFjzys();

      for (AddFjzyRequest addFjzy : fjzys) {

        Fjzy fjzy = new Fjzy();
        BeanUtils.copyProperties(addFjzy, fjzy);

        fjzy.setFjzyId(fjzyId);
        fjzy.setCjr(userId);
        fjzy.setCjsj(now);

        fjzyMapper.insert(fjzy);
      }
    }
    xxzyMapper.insert(xxzy);

    return xxzy;
  }

  /**
   * 购买学习资源
   */
  @Override
  public ResponseUtil buy(BuyXxzyRequest buyXxzyRequest) {

    AddDdxfjlRequest request = new AddDdxfjlRequest();
    BeanUtils.copyProperties(buyXxzyRequest, request);

    request.setXtlx(SystemTypeEnum.TALENT_TRAINING.getKey());
    request.setDdmc("购买" + buyXxzyRequest.getSpmc() + "学习资源");
    request.setXflx("购买学习资源");
    request.setXfsm("购买" + buyXxzyRequest.getSpmc() + "学习资源");

    ResponseUtil result = orderFeignService.createOrder(request);

    return result;
  }

  /**
   * 删除附件资源
   */
  @Override
  public void deleteFjzy(Long fjzyId) {

    fjzyMapper.deleteById(fjzyId);

  }

  /**
   * 通过机构ID获取数据
   */
  @Override
  public PageInfo<GetXxzyVO> findByJgId(Integer pageNum, Integer pageSize, String type, String firstCategory,
                                        String secondCategory, String category, String direction, Long courseId, String condition,
                                        String jgCode) {

    if(StringUtils.isBlank(jgCode)){
      throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
    }

    ResponseUtil response = groupFeignService.getByCode(jgCode);
    if(response == null || response.getErrCode().intValue() != 0){
      throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
    }

    JgglVO jggl = response.conversionData(new TypeReference<JgglVO>() {});
    PageHelper.startPage(pageNum, pageSize);

    List<Xxzy> result = xxzyMapper.selectList(new QueryWrapper<Xxzy>().eq("zylb", type).eq("sfsc", false)
        .eq("sfsj", true).eq(StringUtils.isNotBlank(firstCategory), "zyyjfl", firstCategory)
        .eq(StringUtils.isNotBlank(secondCategory), "zyejfl", secondCategory).eq(StringUtils.isNotBlank(category), "zylx", category)
        .eq(StringUtils.isNotBlank(direction), "zyfx", direction).eq(courseId != null, "kc_id", courseId)
        .like(StringUtils.isNotBlank(condition), "mc", condition).eq( jggl.getId() != null,"fjjg_id",jggl.getId()));

    PageInfo pageInfo = new PageInfo(result);

    PageInfo pageResult = new PageInfo();
    BeanUtils.copyProperties(pageInfo, pageResult);

    List<GetXxzyVO> voList = result.stream().map(obj -> {

      GetXxzyVO vo = new GetXxzyVO();
      BeanUtils.copyProperties(obj, vo);

      return vo;
    }).collect(Collectors.toList());

    pageResult.setList(voList);

    return pageResult;
  }

  /**
   * 获取详情
   */
  @Override
  public GetXxzyVO get(Long id) {


    Xxzy xxzy = xxzyMapper.selectOne(new QueryWrapper<Xxzy>().eq("id", id).eq("sfsc", false));

    if (xxzy == null) {
      return null;
    }

    GetXxzyVO vo = new GetXxzyVO();
    BeanUtils.copyProperties(xxzy, vo);

    if (StringUtils.isNotBlank(xxzy.getFjzyId())) {

      List<AddFjzyRequest> fjzys = fjzyMapper.selectList(new QueryWrapper<Fjzy>().eq("fjzy_id", xxzy.getFjzyId()))
          .stream().map(obj -> {

            AddFjzyRequest request = new AddFjzyRequest();
            BeanUtils.copyProperties(obj, request);
            return request;

          }).collect(Collectors.toList());

      vo.setFjzys(fjzys);
    }

    return vo;
  }

  /**
   * 获取云课堂课程列表
   */
  @Override
  public List<Kc> getCloudClassroomCourse(String userType, String educationType, String studentType, Long groupId) {

    List<Kc> kcs = Lists.newArrayList();

    //判断用户是否是授课教师
    if (Objects.equals(userType, UserTypeEnum.TEACHER.getKey())) {

      LoginUser loginUser = SystemConstant.threadLocal.get();

      Sz sz = szMapper.getSzByYhId(loginUser.getUserId());

      kcs = kcMapper.findBySzId(sz.getId(), educationType, studentType, groupId);

    } else {

      List<String> studentTypes = Lists.newArrayList(studentType.split(","));
      if (CollectionUtils.isEmpty(studentTypes)) {
        return kcs;
      }

      kcs = kcMapper.findByType(educationType, studentTypes, groupId);
    }

    return kcs;
  }

  /**
   * 获取列表 - 分页
   */
  @Override
  public PageInfo<Xxzy> list(Integer pageNum, Integer pageSize, String type,
                             String firstCategory, String secondCategory, String category,
                             Long courseId, String condition, Long fjjgId) {

    PageHelper.startPage(pageNum, pageSize);

//    QueryWrapper query = new QueryWrapper();
//
//    query.eq("zylb", type);
//    query.eq("sfsc", false);
//
//    if (StringUtils.isNotBlank(firstCategory)) {
//      query.eq("zyyjfl", firstCategory);
//    }
//
//    if (StringUtils.isNotBlank(secondCategory)) {
//      query.eq("zyejfl", secondCategory);
//    }
//
//    if (courseId != null) {
//      query.eq("kc_id", courseId);
//    }
//
//    if (StringUtils.isNotBlank(condition)) {
//      query.like("mc", condition.trim());
//    }
//
//    if (StringUtils.isNotBlank(category)) {
//      query.eq("zylx", category);
//    }
//
//    if (groupId != null) {
//      query.eq("jg_id", groupId);
//    }
//
//    query.orderByDesc("cjsj");
//
//    List xxzys = xxzyMapper.selectList(query);

    List<GetXxzyVO> xxzys = null;
    if (UserTypeEnum.IN.getKey().equals(type)) {
      xxzys = xxzyMapper.findByPage( type, firstCategory,  secondCategory,  category, courseId,  condition,  fjjgId);
    } else if (UserTypeEnum.OUT.getKey().equals(type)){
      xxzys = xxzyMapper.findOutPage(type, firstCategory,  secondCategory,  category, courseId,  condition,   fjjgId);
    }

    PageInfo pageInfo = new PageInfo(xxzys);

    return pageInfo;
  }

  /**
   * 获取列表 - 分页
   */
  @Override
  public PageInfo<GetXxzyVO> listVO(Integer pageNum, Integer pageSize, String type, String firstCategory,
                                    String secondCategory, String category, String direction, Long courseId,
                                    String condition, Long groupId, String groupCode) {

    PageHelper.startPage(pageNum, pageSize);

    QueryWrapper<Xxzy> query = new QueryWrapper();

    query.eq("zylb", type);
    query.eq("sfsc", false);
    query.eq("sfsj", true);

    if (StringUtils.isNotBlank(firstCategory)) {
      query.eq("zyyjfl", firstCategory);
    }

    if (StringUtils.isNotBlank(secondCategory)) {
      query.eq("zyejfl", secondCategory);
    }

    if (StringUtils.isNotBlank(category)) {
      query.eq("zylx", category);
    }

    if (StringUtils.isNotBlank(direction)) {
      query.eq("zyfx", direction);
    }

    if (courseId != null) {
      query.eq("kc_id", courseId);
    }

    if (StringUtils.isNotBlank(condition)) {
      query.like("mc", condition.trim());
    }
    Long fjjgId = getFjjgId();

    if (fjjgId != null && StringUtils.isBlank(groupCode)) {
      query.eq("fjjg_id", fjjgId);
    }

    if (StringUtils.isNotBlank(groupCode)) {
      ResponseUtil response = groupFeignService.getByCode(groupCode);
      if (response.getErrCode().intValue() == 0) {

        JgglVO jg = response.conversionData(new TypeReference<JgglVO>() {});

        if (jg != null) {
          query.eq("fjjg_id", jg.getId());
        }
      }
    }

    query.orderByDesc("cjsj");

    List<Xxzy> list = xxzyMapper.selectList(query);
    PageInfo<Xxzy> page = new PageInfo<>(list);

    PageInfo<GetXxzyVO> pageInfo = new PageInfo<>();

    BeanUtils.copyProperties(page, pageInfo);

    if (CollectionUtils.isEmpty(list)) {

      return pageInfo;
    }

    List<GetXxzyVO> voList = list.stream().map(obj -> {

      GetXxzyVO vo = new GetXxzyVO();
      BeanUtils.copyProperties(obj, vo);

      vo.setSfgm(false);

      return vo;
    }).collect(Collectors.toList());

    pageInfo.setList(voList);

    LoginUser loginUser = SystemConstant.threadLocal.get();
    if (loginUser == null) {

      return pageInfo;
    }

    ResponseUtil response = orderFeignService.getByUserId();
    if (response.getErrCode().intValue() != 0) {

      return pageInfo;
    }

    if (response.getData() == null) {
      return pageInfo;
    }

    List<GetDdxfjlVO> ddxfjls = response.conversionData(new TypeReference<List<GetDdxfjlVO>>() {
    });

    //获取订单中商品ID
    List<Long> spIds = ddxfjls.stream().map(GetDdxfjlVO::getSpId).collect(Collectors.toList());

    //获取当前查询列表的商品是否为已支付订单
    Map<Long, GetXxzyVO> xxzyMap = voList.stream().collect(Collectors.toMap(GetXxzyVO::getId, Function.identity()));

    spIds.forEach(spId -> {

      GetXxzyVO xxzy = xxzyMap.get(spId);
      if (xxzy != null) {

        xxzy.setSfgm(true);
      }
    });

    //获取我收藏的学习资源
    List<Xxzysc> xxzyscs = xxzyscMapper.selectList(new QueryWrapper<Xxzysc>()
        .eq("yh_id", loginUser.getUserId()));

    List<Long> xxzyscIds = xxzyscs.stream().map(Xxzysc::getXxzyId).collect(Collectors.toList());

    xxzyscIds.forEach(xxzyscId -> {

      GetXxzyVO xxzy = xxzyMap.get(xxzyscId);

      if (xxzy != null) {
        xxzy.setSfshouc(true);
      }
    });

    return pageInfo;
  }

  /**
   * 支付金额
   */
  @Override
  public ResponseUtil pay(String orderNo, String payType) {

    ResponseUtil response = orderFeignService.getByCode(orderNo);
    if (response.getErrCode().intValue() != 0) {

      return response;
    }

    if (response.getData() == null) {
      return ResponseUtil.error(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR.getCode(), "订单不存在");
    }

    GetDdxfjlVO ddxfjl = response.conversionData(new TypeReference<GetDdxfjlVO>() {
    });

    ddxfjl.setZffs(payType);

    //如果是微信支付、支付宝支付调用三方支付接口
    ResponseUtil payResponse = orderFeignService.pay(ddxfjl);

    return payResponse;
  }

  /**
   * 更新
   */
  @Override
  public Xxzy update(UpdateXxzyRequest updateXxzyRequest, Xxzy xxzy, Long userId) {

    Date now = new Date();

    BeanUtils.copyProperties(updateXxzyRequest, xxzy, "cjr", "cjsj", "fjzy_id", "lll", "zz", "sfsc", "sfsj", "sjsj");
    xxzy.setGxsj(now);
    xxzy.setGxr(userId);

    xxzyMapper.update(xxzy);

    if (!CollectionUtils.isEmpty(updateXxzyRequest.getFjzys())) {

      List<AddFjzyRequest> fjzys = updateXxzyRequest.getFjzys();

      String xxzyFjzyId = xxzy.getFjzyId();

      //判断当前学习资源是否有附件资源
      if (StringUtils.isNotBlank(xxzyFjzyId)) {

        //删除当前所有的附件资源，增加新的附件资源
        fjzyMapper.delete(new QueryWrapper<Fjzy>().eq("fjzy_id", xxzy.getFjzyId()));

      } else {

        xxzyFjzyId = CommonUtils.generateUUID();
      }

      for (AddFjzyRequest addFjzy : fjzys) {

        Fjzy fjzy = new Fjzy();
        BeanUtils.copyProperties(addFjzy, fjzy);

        fjzy.setFjzyId(xxzyFjzyId);
        fjzy.setCjr(userId);
        fjzy.setCjsj(now);

        fjzyMapper.insert(fjzy);
      }
    }

    return xxzy;
  }

  private Long getUserId() {
    LoginUser loginUser = threadLocal.get();
    Long userId;
    if (loginUser != null) {
      userId = loginUser.getUserId();
    } else {
      throw new ServiceException(GET_THREADLOCAL_ERROR);
    }
    return userId;
  }
  /**
   * 获取父级机构ID
   */
  public Long getFjjgId() {
    LoginUser loginUser = threadLocal.get();
    Long fjjgId = null;
    if (loginUser != null) {
      fjjgId = loginUser.getFjjgId();
    }
    return fjjgId;
  }
}
