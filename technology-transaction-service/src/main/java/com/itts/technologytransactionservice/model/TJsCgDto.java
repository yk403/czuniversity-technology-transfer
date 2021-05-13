package com.itts.technologytransactionservice.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 09:16:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ContentRowHeight(12)
@HeadRowHeight(12)
@ColumnWidth(20)
public class TJsCgDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 成果权属人
     */
    @ExcelProperty(value = "成果权属人", index = 0)
    private String cgqsr;
    /**
     * 权属人联系电话
     */
    @ExcelProperty(value = "权属人联系电话", index = 1)
    private String qsrlxdh;
    /**
     * 成果名称
     */
    @ExcelProperty(value = "成果名称", index = 2)
    private String cgmc;
    /**
     * 关键词
     */
    @ExcelProperty(value = "关键词", index = 3)
    private String gjc;
    /**
     * 成果完成时间
     */
    @ExcelProperty(value = "成果完成时间", index = 4)
    private String cgwcsj;
    /**
     * 资助情况
     */
    @ExcelProperty(value = "资助情况", index = 5)
    private String zzqk;
    /**
     * 成果应用领域
     */
    @ExcelProperty(value = "成果应用领域", index = 6)
    private String lyId;
    /**
     * 成果获得方式
     */
    @ExcelProperty(value = "成果获得方式", index = 7)
    private String cghqfs;
    /**
     * 技术成熟度
     */
    @ExcelProperty(value = "技术成熟度", index = 8)
    private String jscsd;
    /**
     * 获奖情况
     */
    @ExcelProperty(value = "获奖情况", index = 9)
    private String hjqk;
    /**
     * 合作价格
     */
    @ExcelProperty(value = "合作价格", index = 10)
    private BigDecimal hzjg;
    /**
     * 合作方式
     */
    @ExcelProperty(value = "合作方式", index = 11)
    private String hzfs;
    /**
     * 单位名称
     */
    @ExcelProperty(value = "单位名称", index = 12)
    private String dwmc;
    /**
     * 地址
     */
    @ExcelProperty(value = "地址", index = 13)
    private String dz;
    /**
     * 法人
     */
    @ExcelProperty(value = "法人", index = 14)
    private String fr;
    /**
     * 联系人
     */
    @ExcelProperty(value = "联系人", index = 15)
    private String contracts;
    /**
     * 手机号码
     */
    @ExcelProperty(value = "手机号码", index = 16)
    private String sjhm;
    /**
     * 座机
     */
    @ExcelProperty(value = "座机", index = 17)
    private String zj;
    /**
     * 电子邮箱
     */
    @ExcelProperty(value = "电子邮箱", index = 18)
    private String dzyx;
    /**
     * 知识产权形式
     */
    @ExcelProperty(value = "知识产权形式", index = 19)
    private String zscqxs;
    /**
     * 成果简介
     */
    @ExcelProperty(value = "成果简介", index = 20)
    private String cgjs;
    /**
     * 技术指标
     */
    @ExcelProperty(value = "技术指标", index = 21)
    private String jszb;
    /**
     * 商业分析
     */
    @ExcelProperty(value = "商业分析", index = 22)
    private String syfx;
    /**
     * 成果图片
     */
    @ExcelProperty(value = "成果图片", index = 23)
    private String cgtp;
    /**
     * 成果视频
     */
    @ExcelProperty(value = "成果视频", index = 24)
    private String cgsp;
    /**
     * 备注
     */
    @ExcelProperty(value = "备注", index = 25)
    private String bz;
    /**
     * 活动id
     */
    @ExcelProperty(value = "活动id", index = 26)
    private String jshdId;

}
