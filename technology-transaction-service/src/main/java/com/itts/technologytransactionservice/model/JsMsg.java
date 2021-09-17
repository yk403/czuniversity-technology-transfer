package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author fuli
 * @since 2021-09-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_js_msg")
public class JsMsg implements Serializable {


    private static final long serialVersionUID = -1459865839333256205L;
    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联咨询用户id
     */
    private Long yhId;

    /**
     * 关联经纪人管理表id
     */
    private Long jjrId;

    /**
     * 机构id
     */
    private Long fjjgId;

    /**
     * 留言内容
     */
    private String lynr;

    /**
     * 回复内容
     */
    private String hfnr;

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
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gxsj;

    /**
     * 留言时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lysj;

    /**
     * 留言状态(0:未回复,1:已回复)
     */
    private Boolean lyzt;

    /**
     * 是否查看状态(0:未查看 1:已查看)
     */
    private Boolean sfckzt;

    /**
     * 回复时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date hfsj;


}
