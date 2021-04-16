package com.itts.technologytransactionservice.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 09:19:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ContentRowHeight(12)
@HeadRowHeight(12)
@ColumnWidth(20)
public class TJsXqDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 技术需求名称
     */
    @ExcelProperty(value = "技术需求名称", index = 0)
    private String xqmc;
    /**
     * 关键字
     */
    @ExcelProperty(value = "关键字", index = 1)
    private String gjz;
    /**
     * 有效期
     */
    @ExcelProperty(value = "有效期", index = 2)
    private String yxq;
    /**
     * 需求领域
     */
    @ExcelProperty(value = "需求领域", index = 3)
    private String lyId;
    /**
     * 技术需求类别
     */
    @ExcelProperty(value = "技术需求类别", index = 4)
    private String lbId;
    /**
     * 合作方式
     */
    @ExcelProperty(value = "合作方式", index = 5)
    private String hzfs;
    /**
     * 合作价格
     */
    @ExcelProperty(value = "合作价格", index = 6)
    private String hzjg;
    /**
     * 意向合作单位
     */
    @ExcelProperty(value = "意向合作单位", index = 7)
    private String yxhzdw;
    /**
     * 意向合作专家
     */
    @ExcelProperty(value = "意向合作专家", index = 8)
    private String yxhzzj;
    /**
     * 技术需求详情
     */
    @ExcelProperty(value = "技术需求详情", index = 9)
    private String xqxq;
    /**
     * 技术指标
     */
    @ExcelProperty(value = "技术指标", index = 10)
    private String jszb;
    /**
     * 需求图片
     */
    @ExcelProperty(value = "需求图片", index = 11)
    private String xqtp;
    /**
     * 需求视频
     */
    @ExcelProperty(value = "需求视频", index = 12)
    private String xqsp;
    /**
     * 单位名称
     */
    @ExcelProperty(value = "单位名称", index = 13)
    private String dwmc;
    /**
     * 地址
     */
    @ExcelProperty(value = "地址", index = 14)
    private String dz;
    /**
     * 法人
     */
    @ExcelProperty(value = "法人", index = 15)
    private String fr;
    /**
     * 联系人
     */
    @ExcelProperty(value = "联系人", index = 16)
    private String contracts;
    /**
     * 联系人电话
     */
    @ExcelProperty(value = "联系人电话", index = 17)
    private String lxrdh;
    /**
     * 座机
     */
    @ExcelProperty(value = "座机", index = 18)
    private String zj;
    /**
     * 电子邮箱
     */
    @ExcelProperty(value = "电子邮箱", index = 19)
    private String dzyx;
    /**
     * 活动id
     */
    @ExcelProperty(value = "活动id", index = 20)
    private String jshdId;
}
