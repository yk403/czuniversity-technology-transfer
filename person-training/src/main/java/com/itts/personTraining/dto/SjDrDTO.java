package com.itts.personTraining.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * @Author: Austin
 * @Data: 2021/6/15
 * @Version: 1.0.0
 * @Description: 实践导入实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ContentRowHeight(12)
@HeadRowHeight(12)
@ColumnWidth(20)
public class SjDrDTO {

    private static final long serialVersionUID = 115444119529585887L;

    /**
     * 学号
     */
    @ExcelProperty(value = "学号", index = 0)
    private String xh;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名", index = 1)
    private String xm;

    /**
     * 批次号
     */
    @ExcelProperty(value = "批次号", index = 2)
    private String pch;

    /**
     * 实践名称
     */
    @ExcelProperty(value = "实践名称", index = 3)
    private String sjmc;

    /**
     * 实践类型
     */
    @ExcelProperty(value = "实践类型", index = 4)
    private String sjlx;

    /**
     * 实践单位
     */
    @ExcelProperty(value = "实践单位", index = 5)
    private String sjdw;

    /**
     * 实践成绩
     */
    @ExcelProperty(value = "实践成绩", index = 6)
    private String sjcj;

    /**
     * 集萃奖学金1
     */
    @ExcelProperty(value = "集萃奖学金1", index = 7)
    private String jcjxjOne;

    /**
     * 集萃奖学金2
     */
    @ExcelProperty(value = "集萃奖学金2", index = 8)
    private String jcjxjTwo;

    /**
     * 基地基金1
     */
    @ExcelProperty(value = "基地基金1", index = 9)
    private String jdjjOne;

    /**
     * 基地基金2
     */
    @ExcelProperty(value = "基地基金2", index = 10)
    private String jdjjTwo;

    /**
     * 开始日期
     */
    @ExcelProperty(value = "开始日期", index = 11)
    private Date ksrq;

    /**
     * 结束日期
     */
    @ExcelProperty(value = "结束日期", index = 12)
    private Date jsrq;

}
