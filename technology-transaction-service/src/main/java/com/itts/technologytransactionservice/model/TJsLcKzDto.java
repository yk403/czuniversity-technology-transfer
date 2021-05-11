package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 *
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 09:16:14
 */
@Data
public class TJsLcKzDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private BigInteger id;
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
     * 落槌时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lcsj;
    /**
     * 活动状态：0未开始1开始2结束
     */
    private String hdzt;
    /**
     * 当前最高价格
     */
    private BigDecimal dqzgjg;
    /**
     * 幅度价格
     */
    private BigDecimal fdjg;
    /**
     * 底价
     */
    private BigDecimal dj;
    /**
     * 叫价间隔状态
     */
    private String jjjgzt;
    /**
     * 落槌定价
     */
    private BigDecimal lcdj;
    /**
     * 技术成果ID
     */
    private Integer cgId;
    /**
     * 技术需求ID
     */
    private Integer xqId;
    /**
     * 删除状态(0:未删除;1:已删除)
     */
    private Integer isDelete;
    /**
     * 更新技术商品(成果需求)的拍卖状态(0：即将开始，1：进行中，2：已完成，3：流拍)
     */
    private Integer auctionStatus;
    /**
     * 判断关联的技术商品类型是需求还是成果
     */
    private Integer type;
    /*
    活动id
     */
    private Integer jshdId;
    /*
   活动排序值
     */
    private Integer soft;
}
