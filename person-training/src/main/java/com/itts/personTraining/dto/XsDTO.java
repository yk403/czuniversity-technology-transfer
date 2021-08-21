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
 * @Description: 学员导入实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ContentRowHeight(12)
@HeadRowHeight(12)
@ColumnWidth(20)
public class XsDTO implements Serializable {

    private static final long serialVersionUID = 5978711483112131859L;

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
     * 年级
     */
    @ExcelProperty(value = "年级", index = 2)
    private String nj;
    /**
     * 性别
     */
    @ExcelProperty(value = "性别", index = 3)
    private String xb;
    /**
     * 出生日期
     */
    @ExcelProperty(value = "出生日期", index = 4)
    private Date csrq;

    /**
     * 学生类别名称
     */
    @ExcelProperty(value = "学生类别名称", index = 5)
    private String xslbmc;
    /**
     * 身份证号
     */
    @ExcelProperty(value = "身份证号", index = 6)
    private String sfzh;
    /**
     * 研究方向
     */
    @ExcelProperty(value = "研究方向", index = 7)
    private String yjfx;
    /**
     * 籍贯
     */
    @ExcelProperty(value = "籍贯", index = 8)
    private String jg;
    /**
     * 民族
     */
    @ExcelProperty(value = "民族", index = 9)
    private String mz;
    /**
     * 政治面貌
     */
    @ExcelProperty(value = "政治面貌", index = 10)
    private String zzmm;
    /**
     * 入学方式
     */
    @ExcelProperty(value = "入学方式", index = 11)
    private String rxfs;
    /**
     * 原毕业院校
     */
    @ExcelProperty(value = "原毕业院校", index = 12)
    private String ybyyx;
    /**
     * 学制
     */
    @ExcelProperty(value = "学制", index = 13)
    private String xz;
    /**
     * 原专业导师编号
     */
    @ExcelProperty(value = "原专业导师编号", index = 14)
    private String yzydsbh;
    /**
     * 原专业导师姓名
     */
    @ExcelProperty(value = "原专业导师姓名", index = 15)
    private String yzydsxm;
    /**
     * 院系
     */
    @ExcelProperty(value = "院系", index = 16)
    private String yx;
    /**
     * 学习形式
     */
    @ExcelProperty(value = "学习形式", index = 17)
    private String xxxs;
    /**
     * 毕业结论
     */
    @ExcelProperty(value = "毕业结论", index = 18)
    private String byjl;

    /**
     * 联系电话(手机)
     */
    @ExcelProperty(value = "联系电话(手机)", index = 19)
    private String lxdh;
    /**
     * 家庭地址
     */
    @ExcelProperty(value = "家庭地址", index = 20)
    private String jtdz;
    /**
     * 专业代码
     */
    @ExcelProperty(value = "原专业代码", index = 21)
    private String yzydm;
    /**
     * 专业
     */
    @ExcelProperty(value = "原专业", index = 22)
    private String yzy;
    /**
     * 教育形式
     */
    @ExcelProperty(value = "教育形式", index = 23)
    private String jyxs;

}
