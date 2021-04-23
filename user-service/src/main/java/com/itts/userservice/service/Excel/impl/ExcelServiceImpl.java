package com.itts.userservice.service.Excel.impl;

import com.alibaba.excel.EasyExcel;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.dto.JgglDTO;
import com.itts.userservice.dto.SjzdDTO;
import com.itts.userservice.dto.YhExcelDTO;
import com.itts.userservice.mapper.jggl.JgglMapper;
import com.itts.userservice.mapper.sjzd.SjzdMapper;
import com.itts.userservice.mapper.yh.YhMapper;
import com.itts.userservice.model.jggl.JgglListener;
import com.itts.userservice.model.sjzd.SjzdListener;
import com.itts.userservice.model.yh.YhListener;
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
    private SjzdMapper sjzdMapper;
    @Resource
    private YhMapper yhMapper;
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
    public ResponseUtil importSjzd(MultipartFile file, Integer headRowNumber) {
        SjzdListener sjzdListener = new SjzdListener();
        sjzdListener.setSjzdMapper(sjzdMapper);
        try{
            EasyExcel.read(file.getInputStream(), SjzdDTO.class, sjzdListener).headRowNumber(headRowNumber).sheet().doRead();
            return ResponseUtil.success(sjzdListener.getResult());
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseUtil.error(ErrorCodeEnum.SYSTEM_UPLOAD_ERROR);
        }


    }

    /**
     * 用户导入
     * @param file
     * @param headRowNumber
     * @return
     */
    @Override
    public ResponseUtil importYh(MultipartFile file, Integer headRowNumber) {
        YhListener yhListener = new YhListener();
        yhListener.setYhMapper(yhMapper);
        try{
            EasyExcel.read(file.getInputStream(), YhExcelDTO.class, yhListener).headRowNumber(headRowNumber).sheet().doRead();
            return ResponseUtil.success(yhListener.getResult());
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseUtil.error(ErrorCodeEnum.SYSTEM_UPLOAD_ERROR);
        }
    }
}
