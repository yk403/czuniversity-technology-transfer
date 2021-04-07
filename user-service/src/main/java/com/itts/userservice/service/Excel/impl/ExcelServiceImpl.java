package com.itts.userservice.service.Excel.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.WriteHandler;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.dto.JgglDTO;
import com.itts.userservice.dto.ShzdDTO;
import com.itts.userservice.mapper.jggl.JgglMapper;
import com.itts.userservice.mapper.shzd.ShzdMapper;
import com.itts.userservice.model.jggl.JgglListener;
import com.itts.userservice.model.shzd.ShzdListener;
import com.itts.userservice.service.Excel.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
@Service
@Primary
@Slf4j
public class ExcelServiceImpl implements ExcelService {

    @Resource
    private JgglMapper jgglMapper;

    @Resource
    private ShzdMapper shzdMapper;
    /**
     * 导入机构Excel
     * @param file
     * @param headRowNumber
     * @return
     */
    @Override
    public ResponseUtil importJggl(MultipartFile file, Integer headRowNumber) {
        JgglListener jgglListener = new JgglListener();
        jgglListener.setJgglMapper(jgglMapper);
        try{
            EasyExcel.read(file.getInputStream(), JgglDTO.class,jgglListener).headRowNumber(headRowNumber).sheet().doRead();
            return ResponseUtil.success(jgglListener.getResult());
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseUtil.error(ErrorCodeEnum.SYSTEM_UPLOAD_ERROR);
        }
    }

    /**
     * 导入字典Excel
     * @param file
     * @param headRowNumber
     * @return
     */
    @Override
    public ResponseUtil importShzd(MultipartFile file, Integer headRowNumber) {
        ShzdListener shzdListener = new ShzdListener();
        shzdListener.setShzdMapper(shzdMapper);
        try{
            EasyExcel.read(file.getInputStream(), ShzdDTO.class,shzdListener).headRowNumber(headRowNumber).sheet().doRead();
            return ResponseUtil.success(shzdListener.getResult());
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseUtil.error(ErrorCodeEnum.SYSTEM_UPLOAD_ERROR);
        }


    }
}
