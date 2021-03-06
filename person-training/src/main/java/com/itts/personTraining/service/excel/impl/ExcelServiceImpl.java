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
import com.itts.personTraining.model.xsCj.YzyCjListener;
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
 * @Description: excel????????????
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
     * ????????????Excel
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
     * ????????????Excel
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
     * ????????????????????????Excel
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
        //??????excel???????????????
        for (int i = 0; i < data.size(); i++) {
            XlXwCjDTO xlXwCjDTO = data.get(i);
            XsCj xsCj = new XsCj();
            XsKcCj xsKcCj = new XsKcCj();
            //?????????????????????
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
            //???????????????????????????
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

        result1.append("???????????????????????????");
        result1.append(count1);
        result1.append("???????????????????????????");
        return ResponseUtil.success(result1.toString());
    }
    /**
     * ?????????????????????Excel
     * @Author: fuli
     * @param file
     * @param headRowNumber
     * @return
     */
    @Override
    public ResponseUtil importYzyCj(MultipartFile file, Integer headRowNumber, Long pcId, String token) {
        YzyCjListener yzyCjListener = new YzyCjListener();
        yzyCjListener.setHeadRowNumber(headRowNumber);
        try {
            EasyExcel.read(file.getInputStream(), YzyCjDTO.class, yzyCjListener).extraRead(CellExtraTypeEnum.MERGE).sheet().headRowNumber(headRowNumber).doRead();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        List<CellExtra> extraMergeInfoList = yzyCjListener.getExtraMergeInfoList();
        /*if (CollectionUtils.isEmpty(extraMergeInfoList)) {
            return ResponseUtil.error(SYSTEM_ERROR);
        }*/
        List<YzyCjDTO> data = explainMerge(yzyCjListener.getData(), extraMergeInfoList, headRowNumber);
        StringBuilder result=new StringBuilder();
        Integer count=0;
        for (int i = 0; i < data.size(); i++) {
            YzyCjDTO yzyCjDTO = data.get(i);
            XsKcCj xsKcCj = new XsKcCj();
            //?????????????????????
            Xs xs;
            xs=xsMapper.getByXhAndXm(yzyCjDTO.getXh(), yzyCjDTO.getXm());
            if(xs==null){
                continue;
            }
            xsKcCj.setXsId(xs.getId());

            xsKcCj.setKclx(1);

            if(yzyCjDTO.getKcdm()!=null){
                xsKcCj.setKcdm(yzyCjDTO.getKcdm());
            }else {
                continue;
            }
            if(yzyCjDTO.getKcmc()!=null){
                xsKcCj.setKcmc(yzyCjDTO.getKcmc());
            }
            if(yzyCjDTO.getSfbx()!=null){
                xsKcCj.setSfbx(yzyCjDTO.getSfbx());
            }

            if(yzyCjDTO.getXwk()!=null){
                xsKcCj.setXwk(yzyCjDTO.getXwk());
            }
            if(yzyCjDTO.getYzyxf()!=null){
                xsKcCj.setYzyxf(yzyCjDTO.getYzyxf());
            }
            if(yzyCjDTO.getDqxf()!=null){
                xsKcCj.setDqxf(yzyCjDTO.getDqxf());
            }
            if(!StringUtils.isBlank(yzyCjDTO.getCj())){
                xsKcCj.setCj(yzyCjDTO.getCj());
            }
            if(yzyCjDTO.getXxxq()!=null){
                xsKcCj.setXxxq(yzyCjDTO.getXxxq());
            }
            if(!StringUtils.isBlank(yzyCjDTO.getCjsx())){
                xsKcCj.setCjsx(yzyCjDTO.getCjsx());
            }
            if(!StringUtils.isBlank(yzyCjDTO.getBkcj())){
                xsKcCj.setBkcj(yzyCjDTO.getBkcj());
            }
            Long userId = getUserId();
            xsKcCj.setCjr(userId);
            xsKcCj.setGxr(userId);
            xsKcCj.setCjsj(new Date());
            xsKcCj.setGxsj(new Date());
            QueryWrapper<XsKcCj> xsKcCjQueryWrapper = new QueryWrapper<>();
            xsKcCjQueryWrapper.eq("xs_id",xs.getId())
                    .eq("kcdm",yzyCjDTO.getKcdm())
                    .eq("sfsc",false);
            XsKcCj one = xsKcCjService.getOne(xsKcCjQueryWrapper);
            if(one == null){
                xsKcCjService.save(xsKcCj);
                count++;
            }else {
                BeanUtils.copyProperties(xsKcCj,one,"cjr","cjsj","id");
                xsKcCjService.updateById(xsKcCj);
                count++;
            }

        }

        result.append("???????????????????????????");
        result.append(count);
        result.append("???????????????????????????");
        return ResponseUtil.success(result.toString());
    }


    /**
     * ????????????????????????Excel
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
     * ????????????Excel
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
     * ????????????Excel
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
     * ?????????????????????
     *
     * @param data               ????????????
     * @param extraMergeInfoList ?????????????????????
     * @param headRowNumber      ?????????
     * @return ????????????????????????
     */
    public static List<XlXwCjDTO> explainMergeData(List<XlXwCjDTO> data, List<CellExtra> extraMergeInfoList, Integer headRowNumber) {
//        ?????????????????????????????????
        extraMergeInfoList.forEach(cellExtra -> {
            int firstRowIndex = cellExtra.getFirstRowIndex() - headRowNumber;
            int lastRowIndex = cellExtra.getLastRowIndex() - headRowNumber;
            int firstColumnIndex = cellExtra.getFirstColumnIndex();
            int lastColumnIndex = cellExtra.getLastColumnIndex();
//            ???????????????
            Object initValue = getInitValueFromList(firstRowIndex, firstColumnIndex, data);
//            ?????????
            for (int i = firstRowIndex; i <= lastRowIndex; i++) {
                for (int j = firstColumnIndex; j <= lastColumnIndex; j++) {
                    setInitValueToList(initValue, i, j, data);
                }
            }
        });
        return data;
    }
    /**
     * ?????????????????????
     *
     * @param data               ????????????
     * @param extraMergeInfoList ?????????????????????
     * @param headRowNumber      ?????????
     * @return ????????????????????????
     */
    public static List<YzyCjDTO> explainMerge(List<YzyCjDTO> data, List<CellExtra> extraMergeInfoList, Integer headRowNumber) {
//        ?????????????????????????????????
        extraMergeInfoList.forEach(cellExtra -> {
            int firstRowIndex = cellExtra.getFirstRowIndex() - headRowNumber;
            int lastRowIndex = cellExtra.getLastRowIndex() - headRowNumber;
            int firstColumnIndex = cellExtra.getFirstColumnIndex();
            int lastColumnIndex = cellExtra.getLastColumnIndex();
//            ???????????????
            Object initValue = getInitValue(firstRowIndex, firstColumnIndex, data);
//            ?????????
            for (int i = firstRowIndex; i <= lastRowIndex; i++) {
                for (int j = firstColumnIndex; j <= lastColumnIndex; j++) {
                    setInitValue(initValue, i, j, data);
                }
            }
        });
        return data;
    }

    /**
     * ???????????????????????????
     *
     * @param filedValue  ???
     * @param rowIndex    ???
     * @param columnIndex ???
     * @param data        ????????????
     */
    public static void setInitValueToList(Object filedValue, Integer rowIndex, Integer columnIndex, List<XlXwCjDTO> data) {
        XlXwCjDTO object = data.get(rowIndex);

        for (Field field : object.getClass().getDeclaredFields()) {
            //???????????????????????????????????????
            field.setAccessible(true);
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (annotation != null) {
                if (annotation.index() == columnIndex) {
                    try {
                        field.set(object, filedValue);
                        break;
                    } catch (IllegalAccessException e) {
                        throw new WebException("???????????????????????????!");
                    }
                }
            }
        }
    }
    /**
     * ???????????????????????????
     *
     * @param filedValue  ???
     * @param rowIndex    ???
     * @param columnIndex ???
     * @param data        ????????????
     */
    public static void setInitValue(Object filedValue, Integer rowIndex, Integer columnIndex, List<YzyCjDTO> data) {
        YzyCjDTO object = data.get(rowIndex);

        for (Field field : object.getClass().getDeclaredFields()) {
            //???????????????????????????????????????
            field.setAccessible(true);
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (annotation != null) {
                if (annotation.index() == columnIndex) {
                    try {
                        field.set(object, filedValue);
                        break;
                    } catch (IllegalAccessException e) {
                        throw new WebException("???????????????????????????!");
                    }
                }
            }
        }
    }

    /**
     * ?????????????????????????????????
     * rowIndex??????list?????????
     * columnIndex????????????????????????
     *
     * @param firstRowIndex    ?????????
     * @param firstColumnIndex ?????????
     * @param data             ?????????
     * @return ?????????
     */
    private static Object getInitValueFromList(Integer firstRowIndex, Integer firstColumnIndex, List<XlXwCjDTO> data) {
        Object filedValue = null;
        XlXwCjDTO object = data.get(firstRowIndex);
        for (Field field : object.getClass().getDeclaredFields()) {
            //???????????????????????????????????????
            field.setAccessible(true);
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (annotation != null) {
                if (annotation.index() == firstColumnIndex) {
                    try {
                        filedValue = field.get(object);
                        break;
                    } catch (IllegalAccessException e) {
                        throw new WebException("???????????????????????????!");
                    }
                }
            }
        }
        return filedValue;
    }
    /**
     * ?????????????????????????????????
     * rowIndex??????list?????????
     * columnIndex????????????????????????
     *
     * @param firstRowIndex    ?????????
     * @param firstColumnIndex ?????????
     * @param data             ?????????
     * @return ?????????
     */
    private static Object getInitValue(Integer firstRowIndex, Integer firstColumnIndex, List<YzyCjDTO> data) {
        Object filedValue = null;
        YzyCjDTO object = data.get(firstRowIndex);
        for (Field field : object.getClass().getDeclaredFields()) {
            //???????????????????????????????????????
            field.setAccessible(true);
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (annotation != null) {
                if (annotation.index() == firstColumnIndex) {
                    try {
                        filedValue = field.get(object);
                        break;
                    } catch (IllegalAccessException e) {
                        throw new WebException("???????????????????????????!");
                    }
                }
            }
        }
        return filedValue;
    }
    /**
     * ??????????????????id
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
