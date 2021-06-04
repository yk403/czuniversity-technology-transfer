package com.itts.personTraining.controller.excel.admin;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.service.excel.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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
@RequestMapping(ADMIN_BASE_URL + "/v1/personTrainingService/excel")
@Slf4j
public class ExcelAdminController {
    @Autowired
    private ExcelService excelService;
    /**
     * 学员导入
     */
    @RequestMapping("/importXs")
    @ApiOperation(value = "学员导入")
    public ResponseUtil importXs(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "headRowNumber")Integer headRowNumber, @RequestParam(value = "jgId")Long jgId, @RequestBody Pc pc, HttpServletRequest request){
        try{
            return excelService.importXs(file, headRowNumber, jgId, pc,request.getHeader("token"));
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
    public ResponseUtil importSz(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "headRowNumber")Integer headRowNumber, @RequestParam(value = "jgId")Long jgId, HttpServletRequest request){
        try{
            return excelService.importSz(file, headRowNumber, jgId, request.getHeader("token"));
        }catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseUtil.error(SYSTEM_UPLOAD_ERROR);
        }
    }

    /**
     * 学历学位成绩导入
     */
    @PostMapping("/importXlXwCj")
    @ApiOperation(value = "学历学位成绩导入")
    public ResponseUtil importXlXwCj(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "headRowNumber")Integer headRowNumber, @RequestParam(value = "pcId")Long pcId, @RequestParam(value = "jylx")String jylx, HttpServletRequest request){
        try{
            return excelService.importXlXwCj(file, headRowNumber, pcId, jylx, request.getHeader("token"));
        }catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseUtil.error(SYSTEM_UPLOAD_ERROR);
        }
    }

    /**
     * 继续教育成绩导入
     */
    @PostMapping("/importJxjyCj")
    @ApiOperation(value = "继续教育成绩导入")
    public ResponseUtil importJxjyCj(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "headRowNumber")Integer headRowNumber, @RequestParam(value = "pcId")Long pcId, @RequestParam(value = "jylx")String jylx, HttpServletRequest request){
        try{
            return excelService.importJxjyCj(file, headRowNumber, pcId, jylx, request.getHeader("token"));
        }catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseUtil.error(SYSTEM_UPLOAD_ERROR);
        }
    }

}
