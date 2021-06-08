package com.itts.personTraining.service.xxzy.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.CommonUtils;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.feign.paymentservice.OrderFeignService;
import com.itts.personTraining.mapper.fjzy.FjzyMapper;
import com.itts.personTraining.mapper.xxzy.XxzyMapper;
import com.itts.personTraining.model.fjzy.Fjzy;
import com.itts.personTraining.model.xxzy.Xxzy;
import com.itts.personTraining.request.fjzy.AddFjzyRequest;
import com.itts.personTraining.request.xxzy.AddXxzyRequest;
import com.itts.personTraining.request.xxzy.UpdateXxzyRequest;
import com.itts.personTraining.service.xxzy.XxzyService;
import com.itts.personTraining.vo.xxzy.GetXxzyVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
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
    private OrderFeignService orderFeignService;

    /**
     * 获取列表 - 分页
     */
    @Override
    public PageInfo<Xxzy> list(Integer pageNum, Integer pageSize, String type,
                               String firstCategory, String secondCategory, Long courseId, String condition) {

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
            query.like("mc", condition);
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
    public PageInfo<GetXxzyVO> listVO(Integer pageNum, Integer pageSize, String type, String firstCategory, String secondCategory, Long courseId, String condition) {

        PageInfo<Xxzy> page = this.list(pageNum, pageSize, type, firstCategory, secondCategory, courseId, condition);

        PageInfo<GetXxzyVO> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(page, pageInfo);

        if (CollectionUtils.isEmpty(page.getList())) {

            return pageInfo;
        }

        List<GetXxzyVO> voList = page.getList().stream().map(obj -> {

            GetXxzyVO vo = new GetXxzyVO();
            BeanUtils.copyProperties(obj, vo);

            vo.setBuyFlag(false);

            return vo;
        }).collect(Collectors.toList());

        pageInfo.setList(voList);

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {

            return pageInfo;
        }

        ResponseUtil response = orderFeignService.getByUserId(loginUser.getUserId());
        if (response.getErrCode().intValue() != 0) {

            return pageInfo;
        }

        Object data = response.getData();

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

        xxzyMapper.updateById(xxzy);

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
}
