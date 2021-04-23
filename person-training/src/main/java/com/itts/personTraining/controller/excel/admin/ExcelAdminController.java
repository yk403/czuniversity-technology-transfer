package com.itts.personTraining.controller.excel.admin;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.service.excel.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.SYSTEM_UPLOAD_ERROR;

/**
 * @Author: Austin
 * @Data: 2021/4/21
 * @Version: 1.0.0
 * @Description: excel导入
 */
@Api(tags = "Excel导入")
@RestController
@RequestMapping(ADMIN_BASE_URL + "/personTrainingService/excel")
@Slf4j
public class ExcelAdminController {
    @Autowired
    private ExcelService excelService;
    /**
     * 学员导入
     */
    @PostMapping("/importXs")
    @ApiOperation(value = "学员导入")
    public ResponseUtil importXs(@RequestParam(value = "file", required = true) MultipartFile file, @RequestParam(value = "headRowNumber", required = true)Integer headRowNumber){
        try{
            return excelService.importXs(file, headRowNumber);
        }catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseUtil.error(SYSTEM_UPLOAD_ERROR);
        }
    }

    /**
     * 师资导入
     */
    @PostMapping("/importSz")
    @ApiOperation(value = "师资导入")
    public ResponseUtil importSz(@RequestParam(value = "file", required = true) MultipartFile file, @RequestParam(value = "headRowNumber", required = true)Integer headRowNumber){
        try{
            return excelService.importSz(file, headRowNumber);
        }catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseUtil.error(SYSTEM_UPLOAD_ERROR);
        }
    }
}
