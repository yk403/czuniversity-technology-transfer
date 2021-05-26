package com.itts.personTraining.service.excel.impl;

import com.alibaba.excel.EasyExcel;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.SzDTO;
import com.itts.personTraining.dto.XsDTO;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.sz.SzListener;
import com.itts.personTraining.model.xs.XsListener;
import com.itts.personTraining.service.excel.ExcelService;
import com.itts.personTraining.service.sz.SzService;
import com.itts.personTraining.service.xy.XyService;
import com.itts.personTraining.service.yh.YhService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

import static com.itts.common.enums.ErrorCodeEnum.SYSTEM_UPLOAD_ERROR;

/**
 * @Author: Austin
 * @Data: 2021/4/21
 * @Version: 1.0.0
 * @Description: excel业务逻辑
 */
@Service
@Primary
@Slf4j
public class ExcelServiceImpl implements ExcelService {

    @Resource
    private XsMapper xsMapper;
    @Resource
    private SzMapper szMapper;
    @Resource
    private SzService szService;
    @Resource
    private XyService xyService;
    @Resource
    private YhService yhService;
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 导入学员Excel
     * @param file
     * @param headRowNumber
     * @return
     */
    @Override
    public ResponseUtil importXs(MultipartFile file, Integer headRowNumber, Long jgId, Pc pc, String token) {
        XsListener xsListener = new XsListener();
        xsListener.setXsMapper(xsMapper);
        xsListener.setXyService(xyService);
        xsListener.setYhService(yhService);
        xsListener.setRedisTemplate(redisTemplate);
        xsListener.setPc(pc);
        xsListener.setToken(token);
        xsListener.setJgId(jgId);
        try{
            EasyExcel.read(file.getInputStream(), XsDTO.class,xsListener).headRowNumber(headRowNumber).sheet().doRead();
            return ResponseUtil.success(xsListener.getResult());
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseUtil.error(SYSTEM_UPLOAD_ERROR);
        }
    }

    /**
     * 导入师资Excel
     * @param file
     * @param headRowNumber
     * @return
     */
    @Override
    public ResponseUtil importSz(MultipartFile file, Integer headRowNumber, Long jgId, String token) {
        SzListener szListener = new SzListener();
        szListener.setSzMapper(szMapper);
        szListener.setSzService(szService);
        szListener.setXyService(xyService);
        szListener.setYhService(yhService);
        szListener.setToken(token);
        szListener.setJgId(jgId);
        try{
            EasyExcel.read(file.getInputStream(), SzDTO.class,szListener).headRowNumber(headRowNumber).sheet().doRead();
            return ResponseUtil.success(szListener.getResult());
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseUtil.error(SYSTEM_UPLOAD_ERROR);
        }
    }
}
