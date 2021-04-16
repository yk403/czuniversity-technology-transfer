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
 * @date 2021-02-22 09:59:20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_qy")
public class TQy implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 企业主键id
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 单位名称
     */
    private String dwmc;
    /**
     * 单位代码
     */
    private String dwdm;
    /**
     * 注册时间
     */
    private String zcsj;
    /**
     * 法人姓名
     */
    private String frxm;
    /**
     * 联系人姓名
     */
    private String lxrxm;
    /**
     * 联系人电话
     */
    private String lxrdh;
    /**
     * 座机号
     */
    private String zjh;
    /**
     * 电子邮箱
     */
    private String dzyx;
    /**
     * 企业登记注册类型
     */
    private String qyzclx;
    /**
     * 企业资质
     */
    private String qyzz;
    /**
     * 产业投资资金规模
     */
    private String zjgm;
    /**
     * 示范企业
     */
    private String sfqy;
    /**
     * 国家级项目数量
     */
    private String gjxmsl;
    /**
     * 省级项目数量
     */
    private String cjxmsl;
    /**
     * 市级项目数量
     */
    private String sjxmsl;
    /**
     * 合作开发项目总金额
     */
    private String xmzje;
    /**
     * 产品简介
     */
    private String cpjj;
    /**
     * 关键技术简介
     */
    private String gjjsjj;

}
