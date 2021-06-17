package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 会展展位中间表
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_zw_hz")
@ApiModel(value = "会展展位中间表")
public class TZwHz implements Serializable {

    private static final long serialVersionUID = -5047340813320574593L;

    /**
     * 主键ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 展位ID
     */
    @ApiModelProperty(value = "展位ID", required = true)
    private Long zwId;
    /**
     * 会展ID
     */
    @ApiModelProperty(value = "展位ID", required = true)
    private Long hzId;


}
