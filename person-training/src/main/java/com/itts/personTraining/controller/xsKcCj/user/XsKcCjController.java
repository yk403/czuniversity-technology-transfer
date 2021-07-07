package com.itts.personTraining.controller.xsKcCj.user;

import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.mapper.xsKcCj.XsKcCjMapper;
import com.itts.personTraining.service.xsKcCj.XsKcCjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.itts.common.constant.SystemConstant.BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.COURSE_TYPE_ERROR;
import static com.itts.personTraining.enums.CourseTypeEnum.ORIGINAL_PROFESSION_COURSE;
import static com.itts.personTraining.enums.CourseTypeEnum.TECHNOLOGY_TRANSFER_COURSE;

/**
 * @Author: Austin
 * @Data: 2021/6/17
 * @Version: 1.0.0
 * @Description: 学生课程成绩门户
 */
@RestController
@RequestMapping(BASE_URL + "/v1/xsKcCj")
@Api(value = "XsKcCjController", tags = "学生课程成绩-门户")
public class XsKcCjController {
    @Autowired
    private XsKcCjService xsKcCjService;

    /**
     * 根据条件查询学生课程成绩集合
     *
     * @param xsCjId
     * @return
     */
    @GetMapping("/getByXsCjId")
    @ApiOperation(value = "根据条件查询学生课程成绩集合")
    public ResponseUtil getByXsCjId(@RequestParam(value = "xsCjId",required = false) Long xsCjId,
                                    @RequestParam(value = "kclx") Integer kclx,
                                    @RequestParam(value = "xsId",required = false) Long xsId) {
        checkRequest(kclx);
        return ResponseUtil.success(xsKcCjService.getByXsCjId(xsCjId,kclx,xsId));
    }

    /**
     * 校验参数
     */
    private void checkRequest(Integer kclx) throws WebException {
        if (Integer.parseInt(TECHNOLOGY_TRANSFER_COURSE.getKey()) != kclx && Integer.parseInt(ORIGINAL_PROFESSION_COURSE.getKey()) != kclx) {
            throw new WebException(COURSE_TYPE_ERROR);
        }
    }
}
