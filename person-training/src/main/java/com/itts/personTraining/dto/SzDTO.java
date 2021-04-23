package com.itts.personTraining.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Austin
 * @Data: 2021/4/21
 * @Version: 1.0.0
 * @Description: 师资实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ContentRowHeight(12)
@HeadRowHeight(12)
@ColumnWidth(20)
public class SzDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 导师编号
     */
    @ExcelProperty(value = "导师编号", index = 0)
    private String dsbh;

    /**
     * 导师姓名
     */
    @ExcelProperty(value = "导师姓名", index = 1)
    private String dsxm;

    /**
     * 性别:	male - 男;	female - 女
     */
    @ExcelProperty(value = "性别", index = 2)
    private String xb;

    /**
     * 身份证号
     */
    @ExcelProperty(value = "身份证号", index = 3)
    private String sfzh;

    /**
     * 籍贯
     */
    @ExcelProperty(value = "籍贯", index = 4)
    private String jg;

    /**
     * 出生日期
     */
    @ExcelProperty(value = "出生日期", index = 5)
    private Date csrq;

    /**
     * 民族
     */
    @ExcelProperty(value = "民族", index = 6)
    private String mz;

    /**
     * 政治面貌
     */
    @ExcelProperty(value = "政治面貌", index = 7)
    private String zzmm;

    /**
     * 文化程度
     */
    @ExcelProperty(value = "文化程度", index = 8)
    private String whcd;

    /**
     * 院系
     */
    @ExcelProperty(value = "院系", index = 9)
    private String yx;

    /**
     * 干部职务
     */
    @ExcelProperty(value = "干部职务", index = 10)
    private String gbzw;

    /**
     * 从事专业1
     */
    @ExcelProperty(value = "从事专业1", index = 11)
    private String cszyOne;

    /**
     * 从事专业2
     */
    @ExcelProperty(value = "从事专业2", index = 12)
    private String cszyTwo;

    /**
     * 从事专业3
     */
    @ExcelProperty(value = "从事专业3", index = 13)
    private String cszyThree;

    /**
     * 技术职称
     */
    @ExcelProperty(value = "技术职称", index = 14)
    private String jszc;

    /**
     * 单位电话
     */
    @ExcelProperty(value = "单位电话", index = 15)
    private String dwdh;

    /**
     * 住宅电话
     */
    @ExcelProperty(value = "住宅电话", index = 16)
    private String zzdh;

    /**
     * 获学位时间
     */
    @ExcelProperty(value = "获学位时间", index = 17)
    private Date hxwsj;

    /**
     * 获学历院校
     */
    @ExcelProperty(value = "获学历院校", index = 18)
    private String hxlyx;

    /**
     * 获学历时间
     */
    @ExcelProperty(value = "获学历时间", index = 19)
    private Date hxlsj;

    /**
     * 所学专业
     */
    @ExcelProperty(value = "所学专业", index = 20)
    private String sxzy;

    /**
     * 最高学历
     */
    @ExcelProperty(value = "最高学历", index = 21)
    private String zgxl;

    /**
     * 批准硕导年月
     */
    @ExcelProperty(value = "批准硕导年月", index = 22)
    private String pzsdny;

    /**
     * 批准博导年月
     */
    @ExcelProperty(value = "批准博导年月", index = 23)
    private String pzbdny;

    /**
     * 带硕士起始年月
     */
    @ExcelProperty(value = "带硕士起始年月", index = 24)
    private String dssqsny;

    /**
     * 带博士起始年月
     */
    @ExcelProperty(value = "带博士起始年月", index = 25)
    private String dbsqsny;

    /**
     * 在岗状态
     */
    @ExcelProperty(value = "在岗状态", index = 26)
    private String zgzt;

    /**
     * 导师类别:	tutor - 研究生导师;	corporate_mentor - 企业导师;	teacher - 授课教师
     */
    @ExcelProperty(value = "导师类别", index = 27)
    private String dslb;

    /**
     * 个人照片
     */
    @ExcelProperty(value = "个人照片", index = 28)
    private String grzp;

    /**
     * 行业领域
     */
    @ExcelProperty(value = "行业领域", index = 29)
    private String hyly;

    /**
     * 所属机构
     */
    @ExcelProperty(value = "所属机构", index = 30)
    private String ssjg;

    /**
     * 专属资格证
     */
    @ExcelProperty(value = "专属资格证", index = 31)
    private String zszgz;

    /**
     * 驻入时间
     */
    @ExcelProperty(value = "驻入时间", index = 32)
    private Date zrsj;
}
