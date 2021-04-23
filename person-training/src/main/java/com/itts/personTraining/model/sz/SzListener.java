package com.itts.personTraining.model.sz;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.itts.personTraining.dto.SzDTO;
import com.itts.personTraining.dto.XsDTO;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.service.sz.SzService;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: Austin
 * @Data: 2021/4/21
 * @Version: 1.0.0
 * @Description: 师资解析
 */
@Slf4j
@Data
@Component
public class SzListener extends AnalysisEventListener<SzDTO> {
    private StringBuilder result = new StringBuilder();
    private Integer count = 0;
    @Resource
    private SzMapper szMapper;
    @Autowired
    private SzService szService;

    public static SzListener szListener;

    @PostConstruct
    public void init() {
        szListener = this;
    }

    @SneakyThrows
    @Override
    public void invoke(SzDTO data, AnalysisContext analysisContext) {
        ReadRowHolder rrh = analysisContext.readRowHolder();
        int rowIndex = rrh.getRowIndex() + 1;
        log.info("解析第" + rowIndex + "行数据:{}", JSON.toJSONString(data));
        Sz sz = new Sz();
        //导师编号
        if (!StringUtils.isBlank(data.getDsbh())) {
            sz.setDsbh(data.getDsbh());
        }
        //导师姓名
        if (!StringUtils.isBlank(data.getDsxm())) {
            sz.setDsxm(data.getDsxm());
        }
        //性别
        if (!StringUtils.isBlank(data.getXb())) {
            sz.setXb(data.getXb());
        }
        //身份证号
        if (!StringUtils.isBlank(data.getSfzh())) {
            sz.setSfzh(data.getSfzh());
        }
        //籍贯
        if (!StringUtils.isBlank(data.getJg())) {
            sz.setJg(data.getJg());
        }
        //出生日期
        if (!StringUtils.isBlank(data.getCsrq().toString())) {
            sz.setCsrq(data.getCsrq());
        }
        //民族
        if (!StringUtils.isBlank(data.getMz())) {
            sz.setMz(data.getMz());
        }
        //政治面貌
        if (!StringUtils.isBlank(data.getZzmm())) {
            sz.setZzmm(data.getZzmm());
        }
        //文化程度
        if (!StringUtils.isBlank(data.getWhcd())) {
            sz.setWhcd(data.getWhcd());
        }
        //院系
        if (!StringUtils.isBlank(data.getYx())) {
            sz.setYx(data.getYx());
        }
        //干部职务
        if (!StringUtils.isBlank(data.getGbzw())) {
            sz.setGbzw(data.getGbzw());
        }
        //从事专业1
        if (!StringUtils.isBlank(data.getCszyOne())) {
            sz.setCszyOne(data.getCszyOne());
        }
        //从事专业2
        if (!StringUtils.isBlank(data.getCszyTwo())) {
            sz.setCszyTwo(data.getCszyTwo());
        }
        //从事专业3
        if (!StringUtils.isBlank(data.getCszyThree())) {
            sz.setCszyThree(data.getCszyThree());
        }
        //技术职称
        if (!StringUtils.isBlank(data.getJszc())) {
            sz.setJszc(data.getJszc());
        }
        //单位电话
        if (!StringUtils.isBlank(data.getDwdh())) {
            sz.setDwdh(data.getDwdh());
        }
        //住宅电话
        if (!StringUtils.isBlank(data.getZzdh())) {
            sz.setZzdh(data.getZzdh());
        }
        //获学位时间
        if (!StringUtils.isBlank(data.getHxwsj().toString())) {
            sz.setHxwsj(data.getHxwsj());
        }
        //获学历院校
        if (!StringUtils.isBlank(data.getHxlyx())) {
            sz.setHxlyx(data.getHxlyx());
        }
        //获学历时间
        if (!StringUtils.isBlank(data.getHxlsj().toString())) {
            sz.setHxlsj(data.getHxlsj());
        }
        //所学专业
        if (!StringUtils.isBlank(data.getSxzy())) {
            sz.setSxzy(data.getSxzy());
        }
        //最高学历
        if (!StringUtils.isBlank(data.getZgxl())) {
            sz.setZgxl(data.getZgxl());
        }
        //批准硕导年月
        if (!StringUtils.isBlank(data.getPzsdny())) {
            sz.setPzsdny(data.getPzsdny());
        }
        //批准博导年月
        if (!StringUtils.isBlank(data.getPzbdny())) {
            sz.setPzbdny(data.getPzbdny());
        }
        //带硕士起始年月
        if (!StringUtils.isBlank(data.getDssqsny())) {
            sz.setDssqsny(data.getDssqsny());
        }
        //带博士起始年月
        if (!StringUtils.isBlank(data.getDbsqsny())) {
            sz.setDbsqsny(data.getDbsqsny());
        }
        //在岗状态
        if (!StringUtils.isBlank(data.getZgzt())) {
            sz.setZgzt(data.getZgzt());
        }
        //导师类别
        if (!StringUtils.isBlank(data.getDslb())) {
            sz.setDslb(data.getDslb());
        }
        //个人照片
        if (!StringUtils.isBlank(data.getGrzp())) {
            sz.setGrzp(data.getGrzp());
        }
        //行业领域
        if (!StringUtils.isBlank(data.getHyly())) {
            sz.setHyly(data.getHyly());
        }
        //所属机构
        if (!StringUtils.isBlank(data.getSsjg())) {
            sz.setSsjg(data.getSsjg());
        }
        //专属资格证
        if (!StringUtils.isBlank(data.getZszgz())) {
            sz.setZszgz(data.getZszgz());
        }
        //驻入时间
        if (!StringUtils.isBlank(data.getZrsj().toString())) {
            sz.setZrsj(data.getZrsj());
        }
        save(sz);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        result.append("导入完成，成功导入");
        result.append(count);
        result.append("条数据");
        log.info("所有数据解析完成！");
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        System.out.println("helloTwo");
        throw exception;
    }

    private void save(Sz sz) {
        Sz szOld = szService.selectByDsbh(sz.getDsbh());
        if (szOld != null) {
            sz.setId(szOld.getId());
            sz.setGxsj(new Date());
            try {
                szMapper.updateById(sz);
                count++;
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        } else {
            try {
                sz.setCjsj(new Date());
                sz.setGxsj(new Date());
                szMapper.insert(sz);
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
