package com.itts.userservice.request.stgl;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/7/23
 */
@Data
public class AddStglRequest implements Serializable {

    private static final long serialVersionUID = -994592844111126025L;

    /**
     * 名称
     */
    private String mc;

    /**
     * 排序
     */
    private String px;

    /**
     * 简介
     */
    private String jj;

    /**
     * 机构ID
     */
    private Long jgId;

    /**
     * 创建时间
     */
    private Date cjsj;

    /**
     * 创建人
     */
    private Long cjr;

    /**
     * 更新时间
     */
    private Date gxsj;

    /**
     * 更新人
     */
    private Long gxr;
}