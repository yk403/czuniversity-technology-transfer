package com.itts.personneltrainingservice.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: Austin
 * @Data: 2021/3/22
 * @Version: 1.0.0
 * @Description:
 */
@Data
@ToString
public class PlanDTO {
    /**
     * 批次名称
     */
    private String pcmc;

    /**
     * 批次号
     */
    private Integer pch;

    /**
     * 批次类型（0：研究生；1：经纪人）
     */
    private Integer pclx;

    /**
     * 学院名称
     */
    private String xymc;

    /**
     * 下发状态（0：未下发；1：已下发）
     */
    private Integer xfzt;

    /**
     * 培养方案
     */
    private String pyfa;

    /**
     * 培养计划
     */
    private String pyjh;

    /**
     * 教学大纲
     */
    private String jxdg;

    /**
     * 上传时间
     */
    private Date scsj;

    /**
     * 下发时间
     */
    private Date xfsj;
}
