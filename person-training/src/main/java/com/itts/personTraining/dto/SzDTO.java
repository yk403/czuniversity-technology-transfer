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

    private static final long serialVersionUID = -2091097008684577812L;


    /**
     * 编号
     */
    @ExcelProperty(value = "编号", index = 0)
    private String dsbh;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名", index = 1)
    private String dsxm;

    /**
     * 电话
     */
    @ExcelProperty(value = "电话", index = 2)
    private String dh;

    /**
     * 性别:	male - 男;	female - 女
     */
    @ExcelProperty(value = "性别", index = 3)
    private String xb;

    /**
     * 身份证号
     */
    @ExcelProperty(value = "身份证号", index = 4)
    private String sfzh;

    /**
     * 籍贯
     */
    @ExcelProperty(value = "籍贯", index = 5)
    private String jg;

    /**
     * 出生日期
     */
    @ExcelProperty(value = "出生日期", index = 6)
    private Date csrq;

    /**
     * 民族
     */
    @ExcelProperty(value = "民族", index = 7)
    private String mz;

    /**
     * 政治面貌
     */
    @ExcelProperty(value = "政治面貌", index = 8)
    private String zzmm;

    /**
     * 文化程度
     */
    @ExcelProperty(value = "文化程度", index = 9)
    private String whcd;

    /**
     * 院系
     */
    @ExcelProperty(value = "院系", index = 10)
    private String yx;

    /**
     * 干部职务
     */
    @ExcelProperty(value = "干部职务", index = 11)
    private String gbzw;

    /**
     * 从事专业1
     */
    @ExcelProperty(value = "从事专业1", index = 12)
    private String cszyOne;

    /**
     * 从事专业2
     */
    @ExcelProperty(value = "从事专业2", index = 13)
    private String cszyTwo;

    /**
     * 从事专业3
     */
    @ExcelProperty(value = "从事专业3", index = 14)
    private String cszyThree;

    /**
     * 技术职称
     */
    @ExcelProperty(value = "技术职称", index = 15)
    private String jszc;

    /**
     * 单位电话
     */
    @ExcelProperty(value = "单位电话", index = 16)
    private String dwdh;

    /**
     * 批准硕导年月
     */
    @ExcelProperty(value = "批准硕导年月", index = 17)
    private String pzsdny;

    /**
     * 批准博导年月
     */
    @ExcelProperty(value = "批准博导年月", index = 18)
    private String pzbdny;

    /**
     * 在岗状态
     */
    @ExcelProperty(value = "在岗状态", index = 19)
    private String zgzt;

    /**
     * 导师类别:	tutor - 研究生导师;	corporate_mentor - 企业导师;	teacher - 授课教师
     */
    @ExcelProperty(value = "导师类别", index = 20)
    private String dslb;

    @ExcelProperty(value = "从事技术转移工作时长", index = 21)
    private String csjszysj;
    @ExcelProperty(value = "最后学历", index = 22)
    private String zhxl;
    @ExcelProperty(value = "最后学位", index = 23)
    private String zhxw;
    @ExcelProperty(value = "最后毕业院校", index = 24)
    private String byyx;
    @ExcelProperty(value = "最后专业", index = 25)
    private String zhzy;
    @ExcelProperty(value = "最后毕业时间", index = 26)
    private Date zhsj;
    @ExcelProperty(value = "学位授予时间", index = 27)
    private Date xwsysj;
    @ExcelProperty(value = "是否硕导", index = 28)
    private Boolean sfsd;
    @ExcelProperty(value = "曾任硕导单位及专业", index = 29)
    private String crsddwjzy;
    @ExcelProperty(value = "所属一级学科名称", index = 30)
    private String ssyjxkmc;
    @ExcelProperty(value = "二级学科（一）名称", index = 31)
    private String ejxky;
    @ExcelProperty(value = "二级学科（二）名称", index = 32)
    private String ejxke;
    @ExcelProperty(value = "研究成果", index = 33)
    private String yjcg;
    @ExcelProperty(value = "出版作品", index = 34)
    private String cbzp;
    @ExcelProperty(value = "各种奖励", index = 35)
    private String gzjl;
    @ExcelProperty(value = "科研项目", index = 36)
    private String kyxm;
    @ExcelProperty(value = "职务", index = 37)
    private String zw;
    @ExcelProperty(value = "定职时间", index = 38)
    private Date dzsj;
    /**
     * 个人照片
     *//*
    @ExcelProperty(value = "个人照片", index = 21)
    private String grzp;

    *//**
     * 行业领域
     *//*
    @ExcelProperty(value = "行业领域", index = 22)
    private String hyly;

    *//**
     * 所属机构
     *//*
    @ExcelProperty(value = "所属机构", index = 23)
    private String ssjg;

    *//**
     * 专属资格证
     *//*
    @ExcelProperty(value = "专属资格证", index = 24)
    private String zszgz;

    *//**
     * 驻入时间
     *//*
    @ExcelProperty(value = "驻入时间", index = 25)
    private Date zrsj;*/
}
