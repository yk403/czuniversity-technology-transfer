package com.itts.technologytransactionservice.controller.cd.admin;

import com.itts.common.utils.R;
import com.itts.technologytransactionservice.controller.BaseController;
import com.itts.technologytransactionservice.service.cd.ExcelAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;


@RestController
@RequestMapping(ADMIN_BASE_URL+"/v1/sys/excel")
//@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/excel")
@Slf4j
public class ExcelAdminController extends BaseController {


    @Autowired
    private ExcelAdminService excelAdminService;

/*    *//**
     * 企业导入
     * @param file
     * @param headRowNumber
     * @return
     *//*
    @PostMapping("/importCompany")
    public R importCompany(@RequestParam(value = "file", required = true) MultipartFile file, @RequestParam(value = "headRowNumber", required = true) Integer headRowNumber){
        String userId = getUserId();
        try {
            return  excelService.importCompany(userId,file,headRowNumber);
//            EasyExcel.read(file.getInputStream(), StaffDO.class, new UploadDataListener(uploadDAO)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return R.error();
        }
    }*/
    /**
     * 需求导入
     * @param file
     * @param headRowNumber
     * @return
     */
    @PostMapping("/importxq")
    public R importXq(@RequestParam(value = "file", required = true) MultipartFile file, @RequestParam(value = "headRowNumber", required = true) Integer headRowNumber){

        try {
            return  excelAdminService.importXq(file,headRowNumber);
//            EasyExcel.read(file.getInputStream(), StaffDO.class, new UploadDataListener(uploadDAO)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return R.error();
        }
    }
    /**
     * 成果导入
     * @param file
     * @param headRowNumber
     * @return
     */
    @PostMapping("/importcg")
    public R importCg(@RequestParam(value = "file", required = true) MultipartFile file, @RequestParam(value = "headRowNumber", required = true) Integer headRowNumber){

        try {
            return  excelAdminService.importCg(file,headRowNumber);
//            EasyExcel.read(file.getInputStream(), StaffDO.class, new UploadDataListener(uploadDAO)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return R.error();
        }
    }








}