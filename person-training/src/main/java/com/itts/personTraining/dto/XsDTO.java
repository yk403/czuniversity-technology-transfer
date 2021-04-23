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

    private static final long serialVersionUID = 1L;

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
     * 学生类别ID
     */
    @ExcelProperty(value = "学生类别ID", index = 5)
    private String xslbId;
    /**
     * 学生类别名称
     */
    @ExcelProperty(value = "学生类别名称", index = 6)
    private String xslbmc;
    /**
     * 身份证号
     */
    @ExcelProperty(value = "身份证号", index = 7)
    private String sfzh;
    /**
     * 毕业证号
     */
    @ExcelProperty(value = "毕业证号", index = 8)
    private String byzh;
    /**
     * 学位证号
     */
    @ExcelProperty(value = "学位证号", index = 9)
    private String xwzh;
    /**
     * 研究方向
     */
    @ExcelProperty(value = "研究方向", index = 10)
    private String yjfx;
    /**
     * 培养类别ID
     */
    @ExcelProperty(value = "培养类别ID", index = 11)
    private String pylbId;
    /**
     * 培养类别名称
     */
    @ExcelProperty(value = "培养类别名称", index = 12)
    private String pylbmc;
    /**
     * 籍贯
     */
    @ExcelProperty(value = "籍贯", index = 13)
    private String jg;
    /**
     * 民族
     */
    @ExcelProperty(value = "民族", index = 14)
    private String mz;
    /**
     * 政治面貌
     */
    @ExcelProperty(value = "政治面貌", index = 15)
    private String zzmm;
    /**
     * 入学方式
     */
    @ExcelProperty(value = "入学方式", index = 16)
    private String rxfs;
    /**
     * 原毕业院校
     */
    @ExcelProperty(value = "原毕业院校", index = 17)
    private String ybyyx;
    /**
     * 原工作单位
     */
    @ExcelProperty(value = "原工作单位", index = 18)
    private String ygzdw;
    /**
     * 原工作单位地址
     */
    @ExcelProperty(value = "原工作单位地址", index = 19)
    private String ygzdwdz;
    /**
     * 现工作单位
     */
    @ExcelProperty(value = "现工作单位", index = 20)
    private String xgzdw;
    /**
     * 现工作单位地址
     */
    @ExcelProperty(value = "现工作单位地址", index = 21)
    private String xgzdwdz;
    /**
     * 现工作单位邮编
     */
    @ExcelProperty(value = "现工作单位邮编", index = 22)
    private String xgzdwyb;
    /**
     * 学制
     */
    @ExcelProperty(value = "学制", index = 23)
    private String xz;
    /**
     * 学习地点
     */
    @ExcelProperty(value = "学习地点", index = 24)
    private String xxdd;
    /**
     * 本科毕业证号
     */
    @ExcelProperty(value = "本科毕业证号", index = 25)
    private String bkbyzh;
    /**
     * 本科学位证号
     */
    @ExcelProperty(value = "本科学位证号", index = 26)
    private String bkxwzh;
    /**
     * 论文题目
     */
    @ExcelProperty(value = "论文题目", index = 27)
    private String lwtm;
    /**
     * 论文主题词
     */
    @ExcelProperty(value = "论文主题词", index = 28)
    private String lwztc;
    /**
     * 答辩日期
     */
    @ExcelProperty(value = "答辩日期", index = 29)
    private Date dbrq;
    /**
     * 毕业日期
     */
    @ExcelProperty(value = "毕业日期", index = 30)
    private Date byrq;
    /**
     * 授学位日期
     */
    @ExcelProperty(value = "授学位日期", index = 31)
    private Date sxwrq;
    /**
     * 学位类型
     */
    @ExcelProperty(value = "学位类型", index = 32)
    private String xwlx;
    /**
     * 导师编号
     */
    @ExcelProperty(value = "导师编号", index = 33)
    private String dsbh;
    /**
     * 导师姓名
     */
    @ExcelProperty(value = "导师姓名", index = 34)
    private String dsxm;
    /**
     * 院系
     */
    @ExcelProperty(value = "院系", index = 35)
    private String yx;
    /**
     * 教委专业代码
     */
    @ExcelProperty(value = "教委专业代码", index = 36)
    private String jwzydm;
    /**
     * 学习形式
     */
    @ExcelProperty(value = "学习形式", index = 37)
    private String xxxs;
    /**
     * 毕业结论
     */
    @ExcelProperty(value = "毕业结论", index = 38)
    private String byjl;
    /**
     * 校长姓名
     */
    @ExcelProperty(value = "校长姓名", index = 39)
    private String xzxm;
    /**
     * 办学类型
     */
    @ExcelProperty(value = "办学类型", index = 40)
    private String bxlx;
    /**
     * 培养单位码
     */
    @ExcelProperty(value = "培养单位码", index = 41)
    private String pydwm;
    /**
     * 培养单位
     */
    @ExcelProperty(value = "培养单位", index = 42)
    private String pydw;
    /**
     * 招生季节
     */
    @ExcelProperty(value = "招生季节", index = 43)
    private String zsjj;
    /**
     * 入学日期
     */
    @ExcelProperty(value = "入学日期", index = 44)
    private Date rxrq;
    /**
     * 学制
     */
    @ExcelProperty(value = "学制", index = 45)
    private String xzTwo;
    /**
     * 准考证号
     */
    @ExcelProperty(value = "准考证号", index = 46)
    private String zkzh;
    /**
     * 联系电话(手机)
     */
    @ExcelProperty(value = "联系电话(手机)", index = 47)
    private String lxdh;
    /**
     * 家庭地址
     */
    @ExcelProperty(value = "家庭地址", index = 48)
    private String jtdz;
    /**
     * 备注
     */
    @ExcelProperty(value = "备注", index = 49)
    private String bz;
    /**
     * 专业代码
     */
    @ExcelProperty(value = "原专业代码", index = 50)
    private String yzydm;
    /**
     * 专业
     */
    @ExcelProperty(value = "原专业", index = 51)
    private String yzy;
    /**
     * 教育形式
     */
    @ExcelProperty(value = "教育形式", index = 52)
    private String jyxs;

}
