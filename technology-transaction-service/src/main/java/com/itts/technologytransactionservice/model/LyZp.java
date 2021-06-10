package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author yukai
 * @since 2021-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_ly_zp")
public class LyZp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 展品主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
     * 展品类别
     */
    private String zplb;

    /**
     * 展品简介
     */
    private String zpjj;

    /**
     * 删除状态(0未删除;1已删除)
     */
    private Boolean isDelete;

    /**
     * 关联展位id
     */
    private Long zwId;
    /**
     * 展品类型(0:需求1:成果)
     */
    private Integer zplx;
    /**
    * @Description: 展品名称
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/6/10
    */
    private String zpmc;
    /**
     * 关联领域id
     */
    private Long lyId;
    /**
    * @Description: 单位名称
    * @Param:
    * @return:
    * @Author: yukai
    * @Date: 2021/6/10
    */
    private String dwmc;
    /**
     * 领域名称
     */
    private String lymc;
    /**
     * 联系方式
     */
    private String lxfs;
    /**
    * @Description: 合作价格
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/6/10
    */
    private BigDecimal hzjg;

}
