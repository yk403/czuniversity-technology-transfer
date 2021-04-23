package com.itts.technologytransactionservice.service.cd.impl;

import com.alibaba.excel.EasyExcel;
import com.itts.common.utils.R;
import com.itts.technologytransactionservice.mapper.JsCgMapper;
import com.itts.technologytransactionservice.mapper.JsShMapper;
import com.itts.technologytransactionservice.mapper.JsXqMapper;
import com.itts.technologytransactionservice.model.CgListener;
import com.itts.technologytransactionservice.model.TJsCgDto;
import com.itts.technologytransactionservice.model.TJsXqDto;
import com.itts.technologytransactionservice.model.XqListener;
import com.itts.technologytransactionservice.service.cd.ExcelAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Primary
@Slf4j
public class ExcelAdminServiceImpl implements ExcelAdminService {

    @Autowired
    private JsXqMapper jsXqMapper;
    @Autowired
    private JsCgMapper jsCgMapper;
    @Autowired
    private JsShMapper jsShMapper;

    @Override
    public R importXq(MultipartFile file, Integer headRowNumber) {
        XqListener xqListener = new XqListener();
        xqListener.setJsXqMapper(jsXqMapper);
        xqListener.setJsShMapper(jsShMapper);
        try {
            EasyExcel.read(file.getInputStream(), TJsXqDto.class, xqListener).headRowNumber(headRowNumber).sheet().doRead();
            return R.ok(xqListener.getResult());
        } catch (IOException e) {
            log.error(e.getMessage());
            return R.error();
        }
    }

    @Override
    public R importCg(MultipartFile file, Integer headRowNumber) {
        CgListener cgListener = new CgListener();
        cgListener.setJsCgMapper(jsCgMapper);
        cgListener.setJsShMapper(jsShMapper);
        try {
            EasyExcel.read(file.getInputStream(), TJsCgDto.class, cgListener).headRowNumber(headRowNumber).sheet().doRead();
            return R.ok(cgListener.getResult());
        } catch (IOException e) {
            log.error(e.getMessage());
            return R.error();
        }
    }


}
