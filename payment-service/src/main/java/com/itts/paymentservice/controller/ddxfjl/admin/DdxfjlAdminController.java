package com.itts.paymentservice.controller.ddxfjl.admin;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.paymentservice.feign.userservice.YhService;
import com.itts.paymentservice.model.ddxfjl.Ddxfjl;
import com.itts.paymentservice.model.ddxfjl.DdxfjlVO;
import com.itts.paymentservice.model.yh.Yh;
import com.itts.paymentservice.service.DdxfjlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/2
 */
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/ddxfjl/v1")
@Api(tags = "订单消费记录后台管理")
public class DdxfjlAdminController {
    
    @Autowired
    private DdxfjlService ddxfjlService;
    @Resource
    private YhService yhService;

    @ApiOperation("获取列表")
    @GetMapping("/list/")
    public ResponseUtil list(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam(value = "订单编号") @RequestParam(value = "ddbh", required = false) String ddbh,
                             @ApiParam(value = "联系电话") @RequestParam(value = "lxdh", required = false) String lxdh,
                             @RequestParam(value = "fjjgId", required = false) Long fjjgId,
                             @ApiParam(value = "系统类型") @RequestParam(value = "xtlx", required = false) String xtlx,
                             @ApiParam(value = "支付方式") @RequestParam(value = "zffs", required = false) String zffs) {

        PageHelper.startPage(pageNum, pageSize);
        if(fjjgId == null){
            LoginUser loginUser = SystemConstant.threadLocal.get();
            if(loginUser != null){
                fjjgId = loginUser.getFjjgId();
            }
        }
        List<Ddxfjl> list = ddxfjlService.list(new QueryWrapper<Ddxfjl>()
                .eq(StringUtils.isNotBlank(ddbh), "bh", ddbh)
                .eq(StringUtils.isNotBlank(lxdh), "lxdh", lxdh)
                .eq(StringUtils.isNotBlank(xtlx),"xtlx",xtlx)
                .eq(StringUtils.isNotBlank(zffs),"zffs",zffs)
                .eq(fjjgId!=null,"fjjg_id",fjjgId));

        PageInfo<Ddxfjl> pageInfo = new PageInfo(list);

        List<DdxfjlVO> collect = list.stream().map(ddxfjl -> {
            DdxfjlVO ddxfjlVO = new DdxfjlVO();
            BeanUtils.copyProperties(ddxfjl, ddxfjlVO);
            ResponseUtil byId = yhService.getById(ddxfjl.getCjr());
            if (byId != null && byId.getErrCode().intValue() == 0) {
                Yh yh = byId.conversionData(new TypeReference<Yh>() {
                });
                ddxfjlVO.setYhm(yh.getYhm());
            }
            return ddxfjlVO;
        }).collect(Collectors.toList());
        PageInfo<DdxfjlVO> ddxfjlVOPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo,ddxfjlVOPageInfo);
        ddxfjlVOPageInfo.setList(collect);

        return ResponseUtil.success(ddxfjlVOPageInfo);
    }

    @ApiOperation("获取详情")
    @GetMapping("/get/{id}")
    public ResponseUtil get(@PathVariable("id") Long id) {

        Ddxfjl ddxfjl = ddxfjlService.getById(id);
        if(ddxfjl == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        return ResponseUtil.success(ddxfjl);
    }
}