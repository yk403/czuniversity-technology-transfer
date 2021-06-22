package com.itts.personTraining.controller.kssj.user;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.kssj.Ksjl;
import com.itts.personTraining.model.kssj.Kssj;
import com.itts.personTraining.request.kssj.CommitKsjlRequest;
import com.itts.personTraining.service.kssj.KsjlService;
import com.itts.personTraining.service.kssj.KssjService;
import com.itts.personTraining.vo.kssj.GetKsjlScoreVO;
import com.itts.personTraining.vo.kssj.GetKsjlVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 考试记录 前端控制器
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-27
 */
@RestController
@RequestMapping(SystemConstant.BASE_URL + "/v1/ksjl")
@Api(tags = "考试记录门户")
public class KsjlController {

    @Autowired
    private KsjlService ksjlService;

    @Autowired
    private KssjService kssjService;

    @ApiOperation(value = "生成试卷")
    @PostMapping("/add/")
    public ResponseUtil add(@ApiParam("试卷ID") @RequestParam("sjId") Long sjId) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        Kssj kssj = kssjService.getById(sjId);
        if (kssj == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        GetKsjlVO ksjl = ksjlService.add(kssj, loginUser);

        return ResponseUtil.success(ksjl);
    }

    @ApiOperation(value = "提交试卷")
    @PostMapping("/commit/")
    public ResponseUtil commit(@RequestBody CommitKsjlRequest commitKsjlRequest) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        checkCommitRequest(commitKsjlRequest);
        ResponseUtil result = ksjlService.commit(commitKsjlRequest, loginUser);

        return result;
    }

    @ApiOperation(value = "通过学生ID获取分数")
    @GetMapping("/get/score/by/student/")
    public ResponseUtil getScoreByUserId(@RequestParam("xsId") Long xsId, @RequestParam("sjId") Long sjId) {

        Ksjl ksjl = ksjlService.getOne(new QueryWrapper<Ksjl>().eq("xs_id", xsId).eq("sj_id", sjId));

        GetKsjlScoreVO vo = new GetKsjlScoreVO();

        BeanUtils.copyProperties(ksjl, vo);

        return ResponseUtil.success(vo);
    }

    /**
     * 校验提交试卷参数
     */
    private void checkCommitRequest(CommitKsjlRequest commitKsjlRequest) {

        if (commitKsjlRequest == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        if (commitKsjlRequest.getId() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        if (CollectionUtils.isEmpty(commitKsjlRequest.getKsjlxxs())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
    }
}

