package com.itts.personTraining.controller.xsKcCj.admin;


import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.XsCjDTO;
import com.itts.personTraining.dto.XsKcCjDTO;
import com.itts.personTraining.mapper.xsKcCj.XsKcCjMapper;
import com.itts.personTraining.model.xsCj.XsCj;
import com.itts.personTraining.service.xsKcCj.XsKcCjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.personTraining.enums.CourseTypeEnum.ORIGINAL_PROFESSION_COURSE;
import static com.itts.personTraining.enums.CourseTypeEnum.TECHNOLOGY_TRANSFER_COURSE;

/**
 * <p>
 * 学生课程成绩表 前端控制器
 * </p>
 *
 * @author Austin
 * @since 2021-06-01
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/xsKcCj")
@Api(value = "XsKcCjAdminController", tags = "学生课程成绩后台管理")
public class XsKcCjAdminController {
    @Autowired
    private XsKcCjService xsKcCjService;
    @Resource
    private XsKcCjMapper xsKcCjMapper;

    /**
     * 根据学生成绩id和课程类型查询学生课程成绩集合
     *
     * @param xsCjId
     * @return
     */
    @GetMapping("/getByXsCjId")
    @ApiOperation(value = "根据学生成绩id和课程类型查询学生课程成绩集合")
    public ResponseUtil getByXsCjId(@RequestParam(value = "xsCjId",required = false) Long xsCjId,
                                    @RequestParam(value = "kclx") Integer kclx,
                                    @RequestParam(value = "xsId",required = false) Long xsId) {
        checkRequest(kclx);
        return ResponseUtil.success(xsKcCjService.getByXsCjId(xsCjId,kclx,xsId));
    }
    /**
     * Excel继续教育
     * @author fuli
     * @param
     * @return
     */
    @GetMapping("/getJxjy")
    @ApiOperation(value = "根据pcid和教育类型查询继续教育课程成绩集合")
    public ResponseUtil getByXsCjId(@RequestParam(value = "pcId",required = false) Long pcId,
                                    @RequestParam(value = "jylx") String jylx) {

        return ResponseUtil.success(xsKcCjService.getByPcId(pcId,jylx));
    }
    /**
     * Excel学历学位教育
     * @author fuli
     * @param
     * @return
     */
    @GetMapping("/getXlxw")
    @ApiOperation(value = "根据pcid查询Excel学历学位教育成绩集合")
    public ResponseUtil getByXsCjId(@RequestParam(value = "pcId",required = false) Long pcId) {

        return ResponseUtil.success(xsKcCjService.findByXixw(pcId));
    }


    /**
     * 更新学生课程成绩(原专业不可修改)
     *
     * @param xsKcCjDTOs
     * @return
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新学生课程成绩")
    public ResponseUtil update(@RequestBody List<XsKcCjDTO> xsKcCjDTOs) {
        return ResponseUtil.success(xsKcCjService.update(xsKcCjDTOs));
    }

    /**
     * 校验参数
     */
    private void checkRequest(Integer kclx) throws WebException {
        if (!TECHNOLOGY_TRANSFER_COURSE.getKey().equals(kclx.toString()) && !ORIGINAL_PROFESSION_COURSE.getKey().equals(kclx.toString())) {
            throw new WebException(COURSE_TYPE_ERROR);
        }
    }
}

