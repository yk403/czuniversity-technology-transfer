package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @TableId(value = "id", type = IdType.AUTO)
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date hdkssj;
    /**
     * 活动结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date hdjssj;
    /**
     * 活动状态
     */
    private Integer hdzt;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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
    private List<Map<String,Integer>> ids;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date gxsj;
    /**
     * 活动方式
     */
    private Integer hdfs;
    /**
     * 活动图片
     */
    private Integer hdtp;
    /*
是否报名
 */
    private Integer isBm;
}
