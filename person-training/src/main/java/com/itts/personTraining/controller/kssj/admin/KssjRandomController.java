package com.itts.personTraining.controller.kssj.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.kssj.Kssj;
import com.itts.personTraining.model.sjpz.Sjpz;
import com.itts.personTraining.request.kssj.RandomKssjRequest;
import com.itts.personTraining.service.kssj.KssjService;
import com.itts.personTraining.vo.kssj.GetKssjVO;
import com.itts.personTraining.vo.kssj.GetRandomKssjVO;
import com.itts.personTraining.vo.sjpz.SjpzVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.itts.common.enums.ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR;

@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/v1/kssjRandom")
@Api(tags = "随机组卷后台管理")
public class KssjRandomController {

    @Autowired
    private KssjService kssjService;


    @ApiOperation(value = "列表")
    @GetMapping("/list/")
    public ResponseUtil list(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam(value = "教育类型") @RequestParam(value = "educationType", required = false) String educationType,
                             @ApiParam(value = "学员类型") @RequestParam(value = "studentType", required = false) String studentType,
                             @ApiParam(value = "试卷类型") @RequestParam(value = "paperType", required = false) String paperType,
                             @ApiParam(value = "查询条件") @RequestParam(value = "condition", required = false) String condition,
                             @ApiParam(value = "父级机构ID") @RequestParam(value = "fjjgId", required = false) Long fjjgId,
                             @RequestParam(value = "lx")String lx) {

        PageHelper.startPage(pageNum, pageSize);

        List kssjs = kssjService.list(new QueryWrapper<Kssj>()
                .eq("sfsc", false)
                .eq(StringUtils.isNotBlank(educationType), "jylx", educationType)
                .eq(StringUtils.isNotBlank(studentType), "xylx", studentType)
                .eq(StringUtils.isNotBlank(paperType), "sjlx", paperType)
                .eq(fjjgId != null, "fjjg_id", fjjgId)
                .eq(lx !=null,"lx",lx)
                .like(StringUtils.isNotBlank(condition), "sjmc", condition)
                .orderByDesc("cjsj"));

        PageInfo<Kssj> pageInfo = new PageInfo<>(kssjs);

        return ResponseUtil.success(pageInfo);
    }

    @ApiOperation(value = "详情")
    @GetMapping("/get/{id}")
    public ResponseUtil get(@PathVariable("id") Long id) {

        GetRandomKssjVO kssj = kssjService.getRandom(id);
        if (kssj == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        return ResponseUtil.success(kssj);
    }

    @ApiOperation(value = "随机组卷")
    @PostMapping("/random/add/")
    public ResponseUtil randomAdd(@RequestBody RandomKssjRequest randomKssjRequest) {
        Boolean aBoolean = kssjService.randomAdd(randomKssjRequest);
        return ResponseUtil.success(aBoolean);
    }
    @ApiOperation(value = "更新")
    @PutMapping("/update/")
    public ResponseUtil update(@RequestBody RandomKssjRequest randomKssjRequest)throws WebException{
        Kssj old = kssjService.getById(randomKssjRequest.getId());
        if(old == null){
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        RandomKssjRequest randomKssj = kssjService.randomUpdate(old, randomKssjRequest);
        return ResponseUtil.success(randomKssj);
    }


    @ApiOperation(value = "更新状态")
    @PutMapping("/update/status/{id}")
    public ResponseUtil updateStatus(@ApiParam(value = "主键ID") @PathVariable("id") Long id,
                                     @ApiParam(value = "是否上架") @RequestParam("sfsj") Boolean sfsj) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }
        Kssj old = kssjService.getOne(new QueryWrapper<Kssj>().eq("sfsc", false).eq("id", id));
        if (old == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        Date now = new Date();
        old.setSfsj(sfsj);
        old.setSjsj(now);
        old.setGxr(loginUser.getUserId());
        old.setGxsj(now);
        kssjService.updateById(old);
        return ResponseUtil.success(old);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable(value = "id") Long id) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }
        Kssj old = kssjService.getOne(new QueryWrapper<Kssj>().eq("sfsc", false).eq("id", id));
        if (old == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        if (!Objects.equals(old.getCjr(), loginUser.getUserId())) {
            throw new WebException(ErrorCodeEnum.NOT_PERMISSION_ERROR);
        }
        Date now = new Date();
        old.setSfsc(true);
        old.setGxr(loginUser.getUserId());
        old.setGxsj(now);
        kssjService.updateById(old);

        return ResponseUtil.success(old);
    }

}
