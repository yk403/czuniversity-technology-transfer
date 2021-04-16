package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-20 17:05:41
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_zj")
public class TZj implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 专家id
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 姓名
     */
    private String xm;
    /**
     * 身份证号
     */
    private String sfz;
    /**
     * 性别
     */
    private String xb;
    /**
     * 生日
     */
    private String sr;
    /**
     * 民族
     */
    private String mz;
    /**
     * 党派
     */
    private String dp;
    /**
     * 学历
     */
    private String xl;
    /**
     * 专业技能职位
     */
    private String zyjszw;
    /**
     * 单位(大学)
     */
    private String dw;
    /**
     * 地址
     */
    private String dz;
    /**
     * 电话
     */
    private String dh;
    /**
     * 座机号
     */
    private String zjh;
    /**
     * 所属行业
     */
    private String sshy;
    /**
     * 从事学科
     */
    private String csxk;
    /**
     * 专长方向
     */
    private String zcfx;
    /**
     * 研究成果
     */
    private String yjcg;
    /**
     * 电子邮件
     */
    private String dzyj;
    /**
     * 研究领域
     */
    private String yjly;
    /**
     * 知识产权
     */
    private String zscq;
    /**
     * 在建项目
     */
    private String zjxm;
    /**
     * 论文
     */
    private String lw;
    /**
     * 专利号
     */
    private String zlh;
    /**
     * 备注
     */
    private String bz;

}
