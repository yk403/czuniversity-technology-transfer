package com.itts.technologytransactionservice.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;


/**
 *
 *
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 09:16:14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_js_bm")
public class TJsBm implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 单位名称
     */
    private String dwmc;
    /**
     * 地址
     */
    private String dz;
    /**
     * 法人
     */
    private String fr;
    /**
     * 联系人
     */
    private String lxr;
    /**
     * 手机号码
     */
    private String sjhm;
    /**
     * 公司资质
     */
    private String gszz;
    /**
     * 设计方案
     */
    private String sjfa;
    /**
     * 招标文件
     */
    private String zbwj;
    /**
     * 保证金
     */
    private String bzj;
    /**
     * 删除状态(0:未删除;1:已删除)
     */
    private Integer isDelete;
    /**
     * 创建时间
     */
    @TableField(value = "cjsj", fill = FieldFill.INSERT) // 新增执行
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date cjsj;
    /**
     * 更新时间
     */
    @TableField(value = "gxsj", fill = FieldFill.INSERT_UPDATE) // 新增和更新执行
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date gxsj;
    /**
     * 关联活动id
     */
    private Integer hdId;
    /**
     * 活动名称
     */
    private String hdmc;
    /**
     * 审核状态（0默认、1拒绝、2通过）
     */
    private String shzt;
    /**
     * 活动状态
     */
    private String hdzt;
    /**
     * 活动类型
     */
    private String hdlx;

}
