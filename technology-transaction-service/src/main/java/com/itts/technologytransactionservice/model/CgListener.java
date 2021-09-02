package com.itts.technologytransactionservice.model;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.technologytransactionservice.mapper.JsCgMapper;
import com.itts.technologytransactionservice.mapper.JsShMapper;
import com.itts.technologytransactionservice.service.JsCgService;
import com.itts.technologytransactionservice.service.JsShService;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Slf4j
@Data
@Component
public class CgListener extends AnalysisEventListener<TJsCgDto> {

    private StringBuilder result = new StringBuilder();

    private Integer count = 0;
    @Autowired
    private JsShMapper jsShMapper;
    @Autowired
    private JsCgMapper jsCgMapper;
    @Autowired
    private JsShService tJsShService;
    @Autowired
    private JsCgService tJsCgService;

    public static CgListener cgListener;

    @PostConstruct
    public void init() {
        cgListener = this;
    }


    @SneakyThrows
    @Override
    public void invoke(TJsCgDto data, AnalysisContext analysisContext) {
        ReadRowHolder rrh = analysisContext.readRowHolder();
        int rowIndex = rrh.getRowIndex() + 1;
        log.info("解析第" + rowIndex + "行数据:{}", JSON.toJSONString(data));
//        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        TJsCg tJsCg = new TJsCg();
        //成果权属人
        if (!StringUtils.isBlank(data.getCgqsr())) {
            tJsCg.setCgqsr(data.getCgqsr());
        }
        //权属人联系电话
        if (!StringUtils.isBlank(data.getQsrlxdh())) {
            tJsCg.setQsrlxdh(data.getQsrlxdh());
        }
        //成果名称
        if (!StringUtils.isBlank(data.getCgmc())) {
            tJsCg.setCgmc(data.getCgmc());
        }
        //关键词
        if (!StringUtils.isBlank(data.getGjc())) {
            tJsCg.setGjc(data.getGjc());
        }
        //成果完成时间
        if (!StringUtils.isBlank(data.getCgwcsj())) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = simpleDateFormat.parse(data.getCgwcsj());
            tJsCg.setCgwcsj(parse);
        }
        //资助情况
        if (!StringUtils.isBlank(data.getZzqk())) {
            tJsCg.setZzqk(data.getZzqk());
        }
        //成果应用领域
        if (!StringUtils.isBlank(data.getLyId())) {
            tJsCg.setLyId(Long.parseLong(data.getLyId()));
        }
        //成果获得方式
        if (!StringUtils.isBlank(data.getCghqfs())) {
            tJsCg.setCghqfs(data.getCghqfs());
        }
        //技术成熟度
        if (!StringUtils.isBlank(data.getJscsd())) {
            tJsCg.setJscsd(data.getJscsd());
        }
        //获奖情况
        if (!StringUtils.isBlank(data.getHjqk())) {
            tJsCg.setHjqk(data.getHjqk());
        }
        //合作价格
        if (data.getHzjg()!=null) {
            tJsCg.setHzjg(data.getHzjg());
        }
        //合作方式
        if (!StringUtils.isBlank(data.getHzfs())) {
            tJsCg.setHzfs(data.getHzfs());
        }
        //单位名称
        if (!StringUtils.isBlank(data.getDwmc())) {
            tJsCg.setDwmc(data.getDwmc());
        }
        //地址
        if (!StringUtils.isBlank(data.getDz())) {
            tJsCg.setDz(data.getDz());
        }
        //法人
        if (!StringUtils.isBlank(data.getFr())) {
            tJsCg.setFr(data.getFr());
        }
        //联系人
        if (!StringUtils.isBlank(data.getContracts())) {
            tJsCg.setContracts(data.getContracts());
        }
        //手机号码
        if (!StringUtils.isBlank(data.getSjhm())) {
            tJsCg.setContracts(data.getContracts());
        }
        //座机
        if (!StringUtils.isBlank(data.getZj())) {
            tJsCg.setZj(data.getZj());
        }
        //电子邮箱
        if (!StringUtils.isBlank(data.getDzyx())) {
            tJsCg.setDzyx(data.getDzyx());
        }
        //知识产权形式
        if (!StringUtils.isBlank(data.getZscqxs())) {
            tJsCg.setZscqxs(data.getZscqxs());
        }
        //成果简介
        if (!StringUtils.isBlank(data.getCgjs())) {
            tJsCg.setCgjs(data.getCgjs());
        }
        //技术指标
        if (!StringUtils.isBlank(data.getJszb())) {
            tJsCg.setJszb(data.getJszb());
        }
        //商业分析
        if (!StringUtils.isBlank(data.getSyfx())) {
            tJsCg.setSyfx(data.getSyfx());
        }
        //成果图片
        if (!StringUtils.isBlank(data.getCgtp())) {
            tJsCg.setCgtp(data.getCgtp());
        }
        //成果视频
        if (!StringUtils.isBlank(data.getCgsp())) {
            tJsCg.setCgsp(data.getCgsp());
        }
        //备注
        if (!StringUtils.isBlank(data.getBz())) {
            tJsCg.setBz(data.getBz());
        }
        //活动id
        if (!StringUtils.isBlank(data.getJshdId())) {
            tJsCg.setJshdId(Integer.parseInt(data.getJshdId()));
        }
        //发布类型
        tJsCg.setReleaseType("技术成果");
        save(tJsCg);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        result.append("导入完成，成功导入");
        result.append(count);
        result.append("条数据");
        log.info("所有数据解析完成！");
    }

//    @Override
//    public void onException(Exception exception, AnalysisContext context) {
//        exception.printStackTrace();
//        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
//        // 如果是某一个单元格的转换异常 能获取到具体行号
//        // 如果要获取头的信息 配合invokeHeadMap使用
//        if (exception instanceof ExcelDataConvertException) {
//            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
//            log.error("第{}行，第{}列解析异常", excelDataConvertException.getRowIndex(),
//                    excelDataConvertException.getColumnIndex());
//        }
//    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        System.out.println("helloTwo");
        throw exception;
    }

    private void save(TJsCg tJsCg) {
        TJsCg tJsCgOld = jsCgMapper.selectByName(tJsCg.getCgmc());
        if (tJsCgOld != null) {
            tJsCg.setId(tJsCgOld.getId());

            try {
                jsCgMapper.updateById(tJsCg);
                count++;
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        } else {
            try {
                LoginUser loginUser = SystemConstant.threadLocal.get();
                Long fjjgId = loginUser.getJgId();
                tJsCg.setFjjgId(fjjgId);
                tJsCg.setUserId(loginUser.getUserId().intValue());
                tJsCg.setCjsj(new Date());
                tJsCg.setGxsj(new Date());
                jsCgMapper.insert(tJsCg);
                Integer id = tJsCg.getId();
                TJsSh tJsSh = new TJsSh();
                tJsSh.setLx(1);
                tJsSh.setCgId(id);
                tJsSh.setFbshzt(1);
                tJsSh.setCjsj(new Date());
                tJsSh.setGxsj(new Date());
                jsShMapper.insert(tJsSh);
                count++;
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
    }

    public String getResult() {
        return result.toString();
    }
}
