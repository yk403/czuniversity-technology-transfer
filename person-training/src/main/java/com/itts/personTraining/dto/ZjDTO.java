package com.itts.personTraining.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Austin
 * @Data: 2021/6/7
 * @Version: 1.0.0
 * @Description: 专家实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ContentRowHeight(12)
@HeadRowHeight(12)
@ColumnWidth(20)
public class ZjDTO implements Serializable {
    private static final long serialVersionUID = -2777053893238572898L;

    /**
     * 编号
     */
    @ExcelProperty(value = "编号", index = 0)
    private String bh;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名", index = 1)
    private String xm;

    /**
     * 身份证号
     */
    @ExcelProperty(value = "身份证号", index = 2)
    private String sfzh;

    /**
     * 性别
     */
    @ExcelProperty(value = "性别", index = 3)
    private String xb;

    /**
     * 生日
     */
    @ExcelProperty(value = "性别", index = 4)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date sr;

    /**
     * 民族
     */
    @ExcelProperty(value = "性别", index = 5)
    private String mz;

    /**
     * 政治面貌(数据字典获取)
     */
    @ExcelProperty(value = "政治面貌", index = 6)
    private String zzmm;

    /**
     * 学历
     */
    @ExcelProperty(value = "学历", index = 7)
    private String xl;

    /**
     * 类型(校内; 校外; 领导)
     */
    @ExcelProperty(value = "类型(校内; 校外; 领导)", index = 8)
    private String lx;

    /**
     * 专业技术职位
     */
    @ExcelProperty(value = "专业技术职位", index = 9)
    private String zyjszw;

    /**
     * 单位(大学)
     */
    @ExcelProperty(value = "单位", index = 10)
    private String dw;

    /**
     * 地址
     */
    @ExcelProperty(value = "地址", index = 11)
    private String dz;

    /**
     * 电话
     */
    @ExcelProperty(value = "电话", index = 12)
    private String dh;

    /**
     * 座机号
     */
    @ExcelProperty(value = "座机号", index = 13)
    private String zjh;

    /**
     * 所属行业
     */
    @ExcelProperty(value = "所属行业", index = 14)
    private String sshy;

    /**
     * 从事学科
     */
    @ExcelProperty(value = "从事学科", index = 15)
    private String csxk;

    /**
     * 专长方向
     */
    @ExcelProperty(value = "专长方向", index = 16)
    private String zcfx;

    /**
     * 研究成果
     */
    @ExcelProperty(value = "研究成果", index = 17)
    private String yjcg;

    /**
     * 电子邮件
     */
    @ExcelProperty(value = "电子邮件", index = 18)
    private String dzyj;

    /**
     * 研究领域
     */
    @ExcelProperty(value = "研究领域", index = 19)
    private String yjly;

    /**
     * 知识产权
     */
    @ExcelProperty(value = "知识产权", index = 20)
    private String zscq;

    /**
     * 在建项目
     */
    @ExcelProperty(value = "在建项目", index = 21)
    private String zjxm;

    /**
     * 论文
     */
    @ExcelProperty(value = "论文", index = 22)
    private String lw;

    /**
     * 专利号
     */
    @ExcelProperty(value = "专利号", index = 23)
    private String zlh;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注", index = 24)
    private String bz;
}
