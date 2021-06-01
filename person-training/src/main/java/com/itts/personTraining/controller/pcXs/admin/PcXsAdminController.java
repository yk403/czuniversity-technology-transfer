package com.itts.personTraining.controller.pcXs.admin;


import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.StuDTO;
import com.itts.personTraining.service.pcXs.PcXsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

/**
 * <p>
 * 批次学生关系表 前端控制器
 * </p>
 *
 * @author Austin
 * @since 2021-05-18
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/pcXs")
public class PcXsAdminController {

    @Autowired
    private PcXsService pcXsService;

    /**
     * 根据pcId查询学生信息
     * @param pcId
     * @return
     */
    @GetMapping("/getByPcId/{pcId}")
    @ApiOperation(value = "根据pcId查询学生信息")
    public ResponseUtil getByPcId(@PathVariable("pcId")Long pcId){
        List<StuDTO> stuDTOs = pcXsService.getByPcId(pcId);
        return ResponseUtil.success(stuDTOs);
    }

}

