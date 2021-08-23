package com.itts.personTraining.service.excel.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.metadata.CellExtra;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.*;
import com.itts.personTraining.feign.userservice.SjzdFeignService;
import com.itts.personTraining.mapper.kc.KcMapper;
import com.itts.personTraining.mapper.pc.PcMapper;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.mapper.sj.SjMapper;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.mapper.xsCj.XsCjMapper;
import com.itts.personTraining.mapper.zj.ZjMapper;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.sj.SjListener;
import com.itts.personTraining.model.sz.SzListener;
import com.itts.personTraining.model.xs.Xs;
import com.itts.personTraining.model.xs.XsListener;
import com.itts.personTraining.model.xsCj.JxjyCjListener;
import com.itts.personTraining.model.xsCj.XlXwCjListener;
import com.itts.personTraining.model.xsCj.XsCj;
import com.itts.personTraining.model.xsKcCj.XsKcCj;
import com.itts.personTraining.model.zj.ZjListener;
import com.itts.personTraining.service.excel.ExcelService;
import com.itts.personTraining.service.kc.KcService;
import com.itts.personTraining.service.pcXs.PcXsService;
import com.itts.personTraining.service.sj.SjService;
import com.itts.personTraining.service.sz.SzService;
import com.itts.personTraining.service.xs.XsService;
import com.itts.personTraining.service.xsCj.XsCjService;
import com.itts.personTraining.service.xsKcCj.XsKcCjService;
import com.itts.personTraining.service.xy.XyService;
import com.itts.personTraining.service.yh.YhService;
import com.itts.personTraining.service.zj.ZjService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;

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
    private PcXsMapper pcXsMapper;
    @Resource
    private XsCjMapper xsCjMapper;
    @Resource
    private PcMapper pcMapper;
    @Resource
    private SjMapper sjMapper;
    @Resource
    private XsService xsService;
    @Resource
    private SzService szService;
    @Resource
    private XyService xyService;
    @Resource
    private YhService yhService;
    @Resource
    private ZjService zjService;
    @Resource
    private SjService sjService;
    @Resource
    private XsCjService xsCjService;
    @Resource
    private XsKcCjService xsKcCjService;
    @Resource
    private KcMapper kcMapper;
    @Resource
    private ZjMapper zjMapper;
    @Resource
    private PcXsService pcXsService;


    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private SjzdFeignService sjzdFeignService;
    /**
     * 导入学员Excel
     * @param file
     * @param headRowNumber
     * @return
     */
    @Override
    public ResponseUtil importXs(MultipartFile file, Integer headRowNumber, Long jgId, Long pcId, String jylx, String pch, Date rxrq, String token) {
        XsListener xsListener = new XsListener();
        xsListener.setXsMapper(xsMapper);
        xsListener.setXyService(xyService);
        xsListener.setYhService(yhService);
        xsListener.setPcXsMapper(pcXsMapper);
        xsListener.setRedisTemplate(redisTemplate);
        xsListener.setSzService(szService);
        xsListener.setXsService(xsService);
        xsListener.setPcXsService(pcXsService);
        xsListener.setSjService(sjService);
        xsListener.setXsCjService(xsCjService);
        xsListener.setSjzdFeignService(sjzdFeignService);
        xsListener.setPcId(pcId);
        xsListener.setJylx(jylx);
        xsListener.setPch(pch);
        xsListener.setRxrq(rxrq);
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

    /**
     * 导入学历学位成绩Excel
     * @Author: fuli
     * @param file
     * @param headRowNumber
     * @return
     */
    @Override
    public ResponseUtil importXlXwCj(MultipartFile file, Integer headRowNumber, Long pcId, String token) {
        XlXwCjListener xlXwCjListener = new XlXwCjListener();
        xlXwCjListener.setHeadRowNumber(headRowNumber);
        try {
            EasyExcel.read(file.getInputStream(), XlXwCjDTO.class, xlXwCjListener).extraRead(CellExtraTypeEnum.MERGE).sheet().headRowNumber(headRowNumber).doRead();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        List<CellExtra> extraMergeInfoList = xlXwCjListener.getExtraMergeInfoList();
        /*if (CollectionUtils.isEmpty(extraMergeInfoList)) {
            return ResponseUtil.error(SYSTEM_ERROR);
        }*/
        List<XlXwCjDTO> data = explainMergeData(xlXwCjListener.getData(), extraMergeInfoList, headRowNumber);
        StringBuilder result=new StringBuilder();
        Integer count=0;
        StringBuilder result1=new StringBuilder();
        Integer count1=0;
        //存储excel读取的数据
        for (int i = 0; i < data.size(); i++) {
            XlXwCjDTO xlXwCjDTO = data.get(i);
            XsCj xsCj = new XsCj();
            XsKcCj xsKcCj = new XsKcCj();
            //存入学生成绩表
            Xs xs = new Xs();
            xs=xsMapper.getByXhAndXm(xlXwCjDTO.getXh(), xlXwCjDTO.getXm());
            if(xs==null){
                continue;
            }


            xsCj.setPcId(pcId);

            if(!StringUtils.isBlank(xlXwCjDTO.getXh())){
                xsCj.setXsId(xs.getId());
            }
            xsCj.setType(1);

            Long userId = getUserId();
            xsCj.setCjr(userId);
            xsCj.setGxr(userId);
            xsCj.setCjsj(new Date());
            xsCj.setGxsj(new Date());
            QueryWrapper<XsCj> xsCjQueryWrapper = new QueryWrapper<>();
            xsCjQueryWrapper.eq("sfsc",false)
                    .eq("pc_id",pcId)
            .eq("xs_id",xs.getId());
            XsCj xsCj1 = xsCjMapper.selectOne(xsCjQueryWrapper);

            if(xsCj1 == null){
                xsCjService.save(xsCj);
                count++;

            }else {
                BeanUtils.copyProperties(xsCj,xsCj1,"cjr","cjsj","id");
                xsCjService.updateById(xsCj1);
                count++;

            }
            //存入学生课程成绩表
            QueryWrapper<Kc> kcQueryWrapper = new QueryWrapper<>();
            kcQueryWrapper.eq("sfsc",false)
                    .eq("kcdm",xlXwCjDTO.getKcdm());
            Kc kc;
            kc = kcMapper.selectOne(kcQueryWrapper);
            if(kc==null){
                continue;
            }

            XsCj xsCj2 = xsCjMapper.selectOne(xsCjQueryWrapper);

            xsKcCj.setXsCjId(xsCj2.getId());
            xsKcCj.setKcId(kc.getId());
            xsKcCj.setKclx(2);

            if(xlXwCjDTO.getSfbx()!=null){
                xsKcCj.setSfbx(xlXwCjDTO.getSfbx());
            }
            if(xlXwCjDTO.getDqxf()!=null){
                xsKcCj.setDqxf(xlXwCjDTO.getDqxf());
            }
            if(!StringUtils.isBlank(xlXwCjDTO.getCj())){
                xsKcCj.setCj(xlXwCjDTO.getCj());
            }

            if(!StringUtils.isBlank(xlXwCjDTO.getCjsx())){
                xsKcCj.setCjsx(xlXwCjDTO.getCjsx());
            }
            if(!StringUtils.isBlank(xlXwCjDTO.getBkcj())){
                xsKcCj.setBkcj(xlXwCjDTO.getBkcj());
            }
            xsKcCj.setCjr(userId);
            xsKcCj.setGxr(userId);
            xsKcCj.setCjsj(new Date());
            xsKcCj.setGxsj(new Date());
            QueryWrapper<XsKcCj> xsKcCjQueryWrapper = new QueryWrapper<>();
            xsKcCjQueryWrapper.eq("xs_cj_id",xsCj2.getId())
                    .eq("kc_id",kc.getId())
                    .eq("sfsc",false);
            XsKcCj one = xsKcCjService.getOne(xsKcCjQueryWrapper);
            if(one == null){
                xsKcCjService.save(xsKcCj);
                count1++;

            }else {
                BeanUtils.copyProperties(xsKcCj,one,"cjr","cjsj","id");
                xsKcCjService.updateById(xsKcCj);
                count1++;
            }

        }
        result.append("导入完成，成功导入");
        result.append(count);
        result.append("条学生成绩数据");
        result1.append("导入完成，成功导入");
        result1.append(count1);
        result1.append("条学生课程成绩数据");
        return ResponseUtil.success(result.toString()+"***"+result1.toString());
    }
    /**
     * 导入原专业成绩Excel
     * @Author: fuli
     * @param file
     * @param headRowNumber
     * @return
     */
    @Override
    public ResponseUtil importYzyCj(MultipartFile file, Integer headRowNumber, Long pcId, String token) {

        return null;
    }


    /**
     * 导入继续教育成绩Excel
     * @param file
     * @param headRowNumber
     * @return
     */
    @Override
    public ResponseUtil importJxjyCj(MultipartFile file, Integer headRowNumber, Long pcId, String token) {
        JxjyCjListener jxjyCjListener = new JxjyCjListener();
        jxjyCjListener.setXsService(xsService);
        jxjyCjListener.setPcXsMapper(pcXsMapper);
        jxjyCjListener.setXsCjMapper(xsCjMapper);
        jxjyCjListener.setToken(token);
        jxjyCjListener.setPcId(pcId);
        try{
            EasyExcel.read(file.getInputStream(), JxjyCjDTO.class, jxjyCjListener).headRowNumber(headRowNumber).sheet().doRead();
            return ResponseUtil.success(jxjyCjListener.getResult());
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseUtil.error(SYSTEM_UPLOAD_ERROR);
        }
    }

    /**
     * 导入专家Excel
     * @param file
     * @param headRowNumber
     * @param token
     * @return
     */
    @Override
    public ResponseUtil importZj(MultipartFile file, Integer headRowNumber, Long jgId, String token) {
        ZjListener zjListener = new ZjListener();
        zjListener.setZjService(zjService);
        zjListener.setYhService(yhService);
        zjListener.setXyService(xyService);
        zjListener.setSzMapper(szMapper);
        zjListener.setZjMapper(zjMapper);
        zjListener.setJgId(jgId);
        zjListener.setToken(token);
        try{
            EasyExcel.read(file.getInputStream(), ZjDTO.class, zjListener).headRowNumber(headRowNumber).sheet().doRead();
            return ResponseUtil.success(zjListener.getResult());
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseUtil.error(SYSTEM_UPLOAD_ERROR);
        }
    }

    /**
     * 导入实践Excel
     * @param file
     * @param headRowNumber
     * @param token
     * @return
     */
    @Override
    public ResponseUtil importSj(MultipartFile file, Integer headRowNumber, String token) {
        SjListener sjListener = new SjListener();
        sjListener.setSjService(sjService);
        sjListener.setSjMapper(sjMapper);
        sjListener.setXsMapper(xsMapper);
        sjListener.setPcMapper(pcMapper);
        sjListener.setToken(token);
        try{
            EasyExcel.read(file.getInputStream(), SjDrDTO.class, sjListener).headRowNumber(headRowNumber).sheet().doRead();
            return ResponseUtil.success(sjListener.getResult());
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseUtil.error(SYSTEM_UPLOAD_ERROR);
        }
    }
    /**
     * 处理合并单元格
     *
     * @param data               解析数据
     * @param extraMergeInfoList 合并单元格信息
     * @param headRowNumber      起始行
     * @return 填充好的解析数据
     */
    public static List<XlXwCjDTO> explainMergeData(List<XlXwCjDTO> data, List<CellExtra> extraMergeInfoList, Integer headRowNumber) {
//        循环所有合并单元格信息
        extraMergeInfoList.forEach(cellExtra -> {
            int firstRowIndex = cellExtra.getFirstRowIndex() - headRowNumber;
            int lastRowIndex = cellExtra.getLastRowIndex() - headRowNumber;
            int firstColumnIndex = cellExtra.getFirstColumnIndex();
            int lastColumnIndex = cellExtra.getLastColumnIndex();
//            获取初始值
            Object initValue = getInitValueFromList(firstRowIndex, firstColumnIndex, data);
//            设置值
            for (int i = firstRowIndex; i <= lastRowIndex; i++) {
                for (int j = firstColumnIndex; j <= lastColumnIndex; j++) {
                    setInitValueToList(initValue, i, j, data);
                }
            }
        });
        return data;
    }

    /**
     * 设置合并单元格的值
     *
     * @param filedValue  值
     * @param rowIndex    行
     * @param columnIndex 列
     * @param data        解析数据
     */
    public static void setInitValueToList(Object filedValue, Integer rowIndex, Integer columnIndex, List<XlXwCjDTO> data) {
        XlXwCjDTO object = data.get(rowIndex);

        for (Field field : object.getClass().getDeclaredFields()) {
            //提升反射性能，关闭安全检查
            field.setAccessible(true);
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (annotation != null) {
                if (annotation.index() == columnIndex) {
                    try {
                        field.set(object, filedValue);
                        break;
                    } catch (IllegalAccessException e) {
                        throw new WebException("解析数据时发生异常!");
                    }
                }
            }
        }
    }


    /**
     * 获取合并单元格的初始值
     * rowIndex对应list的索引
     * columnIndex对应实体内的字段
     *
     * @param firstRowIndex    起始行
     * @param firstColumnIndex 起始列
     * @param data             列数据
     * @return 初始值
     */
    private static Object getInitValueFromList(Integer firstRowIndex, Integer firstColumnIndex, List<XlXwCjDTO> data) {
        Object filedValue = null;
        XlXwCjDTO object = data.get(firstRowIndex);
        for (Field field : object.getClass().getDeclaredFields()) {
            //提升反射性能，关闭安全检查
            field.setAccessible(true);
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (annotation != null) {
                if (annotation.index() == firstColumnIndex) {
                    try {
                        filedValue = field.get(object);
                        break;
                    } catch (IllegalAccessException e) {
                        throw new WebException("解析数据时发生异常!");
                    }
                }
            }
        }
        return filedValue;
    }
    /**
     * 获取当前用户id
     * @return
     */
    private Long getUserId() {
        LoginUser loginUser = threadLocal.get();
        Long userId;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        } else {
            throw new ServiceException(GET_THREADLOCAL_ERROR);
        }
        return userId;
    }
}
