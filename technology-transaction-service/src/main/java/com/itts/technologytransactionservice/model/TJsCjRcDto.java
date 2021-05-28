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
public class TJsCjRcDto implements Serializable {
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
     * 叫价时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date jjsj;
    /**
     * 叫价金额
     */
    private BigDecimal jjje;
    /**
     * 报名id(关联报名表)
     */
    private Integer bmId;
    /**
     * 技术成果ID
     */
    private Integer cgId;
    /**
     * 技术需求ID
     */
    private Integer xqId;
    /**
     * 拍品名称
     */
    private String ppmc;
    /**
     * 出价方
     */
    private String cjf;
    /**
     * 删除状态(0:未删除;1:已删除)
     */
    private Integer isDelete;
    /*
    判断查询的是需求还是成果的出价记录0为需求2委成果
     */
    private Integer type;
    /*
    活动id
     */
    private Integer hdId;
    /**
     * 活动开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date hdkssj;
    /**
     * 活动结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date hdjssj;
    /**
     * 删除状态(0:未删除;1:已删除)
     */
    private Integer userId;
}
