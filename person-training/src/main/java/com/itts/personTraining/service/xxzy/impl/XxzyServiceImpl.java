package com.itts.personTraining.service.xxzy.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.enums.SystemTypeEnum;
import com.itts.common.utils.common.CommonUtils;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.feign.paymentservice.OrderFeignService;
import com.itts.personTraining.feign.userservice.UserFeignService;
import com.itts.personTraining.mapper.fjzy.FjzyMapper;
import com.itts.personTraining.mapper.xxzy.XxzyMapper;
import com.itts.personTraining.mapper.xxzy.XxzyscMapper;
import com.itts.personTraining.model.fjzy.Fjzy;
import com.itts.personTraining.model.xxzy.Xxzy;
import com.itts.personTraining.model.xxzy.Xxzysc;
import com.itts.personTraining.request.ddxfjl.AddDdxfjlRequest;
import com.itts.personTraining.request.ddxfjl.PayDdxfjlRequest;
import com.itts.personTraining.request.fjzy.AddFjzyRequest;
import com.itts.personTraining.request.xxzy.AddXxzyRequest;
import com.itts.personTraining.request.xxzy.BuyXxzyRequest;
import com.itts.personTraining.request.xxzy.UpdateXxzyRequest;
import com.itts.personTraining.service.xxzy.XxzyService;
import com.itts.personTraining.vo.ddxfjl.GetDdxfjlVO;
import com.itts.personTraining.vo.xxzy.GetXxzyVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private XxzyMapper xxzyMapper;

    @Autowired
    private FjzyMapper fjzyMapper;

    @Autowired
    private XxzyscMapper xxzyscMapper;

    @Autowired
    private OrderFeignService orderFeignService;

    @Autowired
    private UserFeignService userFeignService;

    /**
     * 获取列表 - 分页
     */
    @Override
    public PageInfo<Xxzy> list(Integer pageNum, Integer pageSize, String type,
                               String firstCategory, String secondCategory, String category, Long courseId, String condition) {

        PageHelper.startPage(pageNum, pageSize);

        QueryWrapper query = new QueryWrapper();

        query.eq("zylb", type);
        query.eq("sfsc", false);

        if (StringUtils.isNotBlank(firstCategory)) {
            query.eq("zyyjfl", firstCategory);
        }

        if (StringUtils.isNotBlank(secondCategory)) {
            query.eq("zyejfl", secondCategory);
        }

        if (courseId != null) {
            query.eq("kc_id", courseId);
        }

        if (StringUtils.isNotBlank(condition)) {
            query.like("mc", condition.trim());
        }

        if (StringUtils.isNotBlank(category)) {
            query.eq("zylx", category);
        }

        query.orderByDesc("cjsj");

        List xxzys = xxzyMapper.selectList(query);

        PageInfo pageInfo = new PageInfo(xxzys);

        return pageInfo;
    }

    /**
     * 获取列表 - 分页
     */
    @Override
    public PageInfo<GetXxzyVO> listVO(Integer pageNum, Integer pageSize, String type, String firstCategory,
                                      String secondCategory, String category, String direction, Long courseId,
                                      String condition) {

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

    /**
     * 删除附件资源
     */
    @Override
    public void deleteFjzy(Long fjzyId) {

        fjzyMapper.deleteById(fjzyId);

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
     * 支付金额
     */
    @Override
    public ResponseUtil pay(PayDdxfjlRequest payDdxfjlRequest) {

        ResponseUtil response = orderFeignService.getByCode(payDdxfjlRequest.getBh());
        if (response.getErrCode().intValue() != 0) {

            return response;
        }

        if (response.getData() == null) {
            return ResponseUtil.error(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR.getCode(), "订单不存在");
        }

        //如果是微信支付、支付宝支付调用三方支付接口 TODO

        return ResponseUtil.success();
    }
}
