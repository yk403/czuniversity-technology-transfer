package com.itts.userservice.request.gngl;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/7/26
 */
@Data
public class AddGnglRequest implements Serializable {

    private static final long serialVersionUID = 6315200717168787760L;

    /**
     * 机构ID
     */
    private Long jgId;

    /**
     * 功能名称
     */
    private String gnmc;

    /**
     * 功能介绍
     */
    private String gnjs;

    /**
     * 使用起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date sysj;
}