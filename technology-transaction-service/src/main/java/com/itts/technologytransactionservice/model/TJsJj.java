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
 * @date 2021-02-22 11:15:49
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_js_jj")
public class TJsJj implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 技术经纪人id
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
    private String sfzh;
    /**
     * 性别
     */
    private String xb;
    /**
     * 党派
     */
    private String dp;
    /**
     * 学历
     */
    private String xl;
    /**
     * 毕业院校
     */
    private String byyx;
    /**
     * 所属专业
     */
    private String sszy;
    /**
     * 电话
     */
    private String dz;
    /**
     * 服务领域
     */
    private String fwly;
    /**
     * 职业能力认定
     */
    private String zynlrd;
    /**
     * 职业证书编号
     */
    private String zyzsbh;
    /**
     * 证书发放时间
     */
    private String zsffsj;
    /**
     * 培训学时
     */
    private String pxxs;
    /**
     * 培训学分
     */
    private String pxxf;
    /**
     * 能力自评
     */
    private String nlzp;
    /**
     * 服务内容
     */
    private String fwnr;
    /**
     * 服务案例
     */
    private String fwal;
    /**
     * 成功成效
     */
    private String cgcx;
    /**
     * 能力评价
     */
    private String nlpj;
    /**
     * 个人图片
     */
    private String grtp;
    /**
     * 视频
     */
    private String sp;

}
