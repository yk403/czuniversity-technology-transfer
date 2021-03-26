package com.itts.technologytransactionservice.controller;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.R;
import com.itts.technologytransactionservice.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.itts.common.constant.SystemConstant.BASE_URL;


@RestController
@RequestMapping(BASE_URL+"/v1/sys/excel")
//@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/excel")
@Slf4j
public class ExcelController extends BaseController {


    @Autowired
    private ExcelService excelService;

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
    @PostMapping("/importXq")
    public R importXq(@RequestParam(value = "file", required = true) MultipartFile file, @RequestParam(value = "headRowNumber", required = true) Integer headRowNumber){

        try {
            return  excelService.importXq(file,headRowNumber);
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
    @PostMapping("/importCg")
    public R importCg(@RequestParam(value = "file", required = true) MultipartFile file, @RequestParam(value = "headRowNumber", required = true) Integer headRowNumber){

        try {
            return  excelService.importCg(file,headRowNumber);
//            EasyExcel.read(file.getInputStream(), StaffDO.class, new UploadDataListener(uploadDAO)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return R.error();
        }
    }








}