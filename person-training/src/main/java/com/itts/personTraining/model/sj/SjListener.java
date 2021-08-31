package com.itts.personTraining.model.sj;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.exception.WebException;
import com.itts.personTraining.dto.SjDTO;
import com.itts.personTraining.dto.SjDrDTO;
import com.itts.personTraining.mapper.pc.PcMapper;
import com.itts.personTraining.mapper.sj.SjMapper;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.xs.Xs;
import com.itts.personTraining.model.zj.Zj;
import com.itts.personTraining.model.zj.ZjListener;
import com.itts.personTraining.service.sj.SjService;
import com.itts.personTraining.service.xy.XyService;
import com.itts.personTraining.service.yh.YhService;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;

/**
 * @Author: Austin
 * @Data: 2021/6/15
 * @Version: 1.0.0
 * @Description: 实践解析
 */
@Slf4j
@Data
@Component
public class SjListener extends AnalysisEventListener<SjDrDTO> {
    private StringBuilder result = new StringBuilder();
    private Integer count = 0;
    private String token;
    @Resource
    private XsMapper xsMapper;
    @Resource
    private PcMapper pcMapper;
    @Resource
    private SjMapper sjMapper;
    @Resource
    private SjService sjService;
    @Resource
    private XyService xyService;
    @Resource
    private YhService yhService;

    public static SjListener sjListener;

    @PostConstruct
    public void init() {
        sjListener = this;
    }

    @SneakyThrows
    @Override
    public void invoke(SjDrDTO data, AnalysisContext analysisContext) {
        ReadRowHolder rrh = analysisContext.readRowHolder();
        int rowIndex = rrh.getRowIndex() + 1;
        log.info("解析第" + rowIndex + "行数据:{}", JSON.toJSONString(data));
        Sj sj = new Sj();
        //姓名
        if (StringUtils.isNotBlank(data.getXh()) && StringUtils.isNotBlank(data.getXm())) {
            Xs xs = xsMapper.getByXhAndXm(data.getXh(),data.getXm());
            if (xs.getId() != null) {
                sj.setXsId(xs.getId());
            }
            //批次号和实践单位
            if (StringUtils.isNotBlank(data.getPch()) && StringUtils.isNotBlank(data.getSjdw())) {
                sj.setSjdw(data.getSjdw());
                Pc pc = pcMapper.getByPch(data.getPch());
                if (pc.getId() != null) {
                    sj.setPcId(pc.getId());
                    sj.setSjmc(data.getSjdw()+xs.getXm()+xs.getXh());
                    //实践类型
                    if (StringUtils.isNotBlank(data.getSjlx())) {
                        sj.setSjlx(data.getSjlx());
                    }
                    //实践成绩
                    if (StringUtils.isNotBlank(data.getSjcj())) {
                        sj.setSjcj(data.getSjcj());
                    }
                    //集萃奖学金1
                    if (StringUtils.isNotBlank(data.getJcjxjOne())) {
                        sj.setJcjxjOne(data.getJcjxjOne());
                    }
                    //集萃奖学金2
                    if (StringUtils.isNotBlank(data.getJcjxjTwo())) {
                        sj.setJcjxjTwo(data.getJcjxjTwo());
                    }
                    //基地基金1
                    if (StringUtils.isNotBlank(data.getJdjjOne())) {
                        sj.setJdjjOne(data.getJdjjOne());
                    }
                    //基地基金2
                    if (StringUtils.isNotBlank(data.getJdjjTwo())) {
                        sj.setJdjjTwo(data.getJdjjTwo());
                    }
                    //评价结果
                    if (StringUtils.isNotBlank(data.getPjjg())) {
                        sj.setPjjg(data.getPjjg());
                    }
                    save(sj);
                }

            }
        }
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

    private void save(Sj sj) {
        Sj sj1 = sjMapper.getByPcIdAndXsId(sj.getPcId(),sj.getXsId());
        if (sj1 == null) {
            //新增
            if (sjService.save(sj)) {
                count++;
            } else {
                throw new WebException(INSERT_FAIL);
            }
        } else {
            //更新
            sj.setId(sj1.getId());
            if (sjService.updateById(sj)) {
                count++;
            } else {
                throw new WebException(UPDATE_FAIL);
            }
        }
    }

    public String getResult() {
        return result.toString();
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
