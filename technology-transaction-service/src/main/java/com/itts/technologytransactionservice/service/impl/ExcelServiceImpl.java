package com.itts.technologytransactionservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.itts.common.utils.R;
import com.itts.technologytransactionservice.mapper.TJsCgMapper;
import com.itts.technologytransactionservice.mapper.TJsShMapper;
import com.itts.technologytransactionservice.mapper.TJsXqMapper;
import com.itts.technologytransactionservice.model.CgListener;
import com.itts.technologytransactionservice.model.TJsCgDto;
import com.itts.technologytransactionservice.model.TJsXqDto;
import com.itts.technologytransactionservice.model.XqListener;
import com.itts.technologytransactionservice.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Primary
@Slf4j
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private TJsXqMapper tJsXqMapper;
    @Autowired
    private TJsCgMapper tJsCgMapper;
    @Autowired
    private TJsShMapper tJsShMapper;
    @Override
    public R importXq(MultipartFile file, Integer headRowNumber, Integer importType) {
        XqListener xqListener = new XqListener();
        xqListener.setTJsXqMapper(tJsXqMapper);
        xqListener.setTJsShMapper(tJsShMapper);
        try {
            EasyExcel.read(file.getInputStream(), TJsXqDto.class, xqListener).headRowNumber(headRowNumber).sheet().doRead();
            return R.ok(xqListener.getResult());
        } catch (IOException e) {
            log.error(e.getMessage());
            return R.error();
        }
    }
    @Override
    public R importCg(MultipartFile file, Integer headRowNumber, Integer importType) {
        CgListener cgListener = new CgListener();
        cgListener.setTJsCgMapper(tJsCgMapper);
        cgListener.setTJsShMapper(tJsShMapper);
        try {
            EasyExcel.read(file.getInputStream(), TJsCgDto.class, cgListener).headRowNumber(headRowNumber).sheet().doRead();
            return R.ok(cgListener.getResult());
        } catch (IOException e) {
            log.error(e.getMessage());
            return R.error();
        }
    }



}
