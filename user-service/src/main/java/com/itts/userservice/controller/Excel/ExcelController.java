package com.itts.userservice.controller.Excel;


import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.service.Excel.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@Api(tags = "Excel导入")
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/userService/excel")
@Slf4j
public class ExcelController {
    @Autowired
    private ExcelService excelService;
    /**
     * 机构导入
     */
    @PostMapping("/importJggl")
    @ApiOperation(value = "机构导入")
    public ResponseUtil importJggl(@RequestParam(value = "file", required = true) MultipartFile file, @RequestParam(value = "headRowNumber", required = true)Integer headRowNumber){
        try{
            return excelService.importJggl(file, headRowNumber);
        }catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseUtil.error(ErrorCodeEnum.SYSTEM_UPLOAD_ERROR);
        }
    }
    /**
     * 字典导入
     */
    @PostMapping("/importShzd")
    @ApiOperation(value = "字典导入")
    public ResponseUtil importShzd(@RequestParam(value = "file", required = true) MultipartFile file, @RequestParam(value = "headRowNumber", required = true)Integer headRowNumber){
        try{
            return excelService.importShzd(file, headRowNumber);
        }catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseUtil.error(ErrorCodeEnum.SYSTEM_UPLOAD_ERROR);
        }
    }
}
