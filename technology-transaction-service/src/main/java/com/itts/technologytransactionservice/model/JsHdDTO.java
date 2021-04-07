package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: Austin
 * @Data: 2021/4/7
 * @Version: 1.0.0
 * @Description: 技术活动DTO
 */
@Data
public class JsHdDTO {
    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 活动名称
     */
    private String hdmc;
    /**
     * 活动类型（0：拍卖；1：招标；2：挂牌）
     */
    private Integer hdlx;
    /**
     * 活动简介
     */
    private String hdjj;
    /**
     * 活动规则
     */
    private String hdgz;
    /**
     * 活动开始时间
     */
    private Date hdkssj;
    /**
     * 活动结束时间
     */
    private Date hdssj;
    /**
     * 活动状态
     */
    private Integer hdzt;
    /**
     * 创建时间
     */
    private Date cjsj;
    /**
     * 咨询电话
     */
    private String zxdh;
    /**
     * 活动负责人
     */
    private String hdfzr;
    /**
     * 活动内容
     */
    private String hdnr;

    /**
     * 成果需求id集合
     */
    private List<Integer> ids;
}