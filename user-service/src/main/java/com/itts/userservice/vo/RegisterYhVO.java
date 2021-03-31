package com.itts.userservice.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/26
 */
@Data
public class RegisterYhVO implements Serializable {

    private static final long serialVersionUID = -6774734591689088311L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String yhm;

    /**
     * 用户编号
     */
    private String yhbh;

    /**
     * 用户头像
     */
    private String yhtx;

    /**
     * 用户类型
     */
    private String yhlx;

    /**
     * 用户级别
     */
    private String yhjb;

    /**
     * 机构ID
     */
    private Long jgId;

    /**
     * 是否删除
     */
    private Boolean sfsc;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cjsj;

    /**
     * 创建人
     */
    private Long cjr;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gxsj;

    /**
     * 更新人
     */
    private Long gxr;
}