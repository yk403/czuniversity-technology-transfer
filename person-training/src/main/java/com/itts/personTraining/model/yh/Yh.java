package com.itts.personTraining.model.yh;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Austin
 * @Data: 2021/5/19
 * @Version: 1.0.0
 * @Description: 用户对象
 */
@Data
public class Yh implements Serializable {

    private static final long serialVersionUID = 5792382643716241476L;
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String yhm;

    /**
     * 密码
     */
    private String mm;

    /**
     * 用户编号
     */
    private String yhbh;

    /**
     * 真实姓名
     */
    private String zsxm;

    /**
     * 联系电话
     */
    private String lxdh;

    /**
     * 用户头像
     */
    private String yhtx;

    /**
     * 用户类型
     */
    private String yhlx;

    /**
     * 用户类别：研究生；经纪人；导师；企业导师；任课教师；管理员
     */
    private String yhlb;

    /**
     * 用户级别
     */
    private String yhjb;

    /**
     * 机构ID
     */
    private Long jgId;

    /**
     * 用户邮箱
     */
    private String yhyx;

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
