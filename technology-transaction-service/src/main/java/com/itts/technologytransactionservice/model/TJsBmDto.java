package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 *
 *
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 09:16:14
 */
@Data
public class TJsBmDto extends TJsBm implements Serializable {
    /**
     * 用户名
     */
    private String yhm;
    /**
     * 活动类型
     */
    private String hdlx;
    /**
     * 活动状态
     */
    private String hdzt;
    /**
     * 活动名称
     */
    private String hdmc;
}
