package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.*;

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
@TableName("t_ly_zw")
public class LyZw implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 双创路演展位主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父级机构ID
     */
    private Long fjjgId;

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
     * 删除状态(0未删除;1已删除)
     */
    private Boolean isDelete;

    /**
     * 展位简介
     */
    private String zwjj;

    /**
     * 关联会展id
     */
    private Long hzId;
    /**
     * 关联活动id
     */
    private Long hdId;
    /**
    * @Description: 展位名称
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/6/15
    */
    private String zwmc;
    /**
    * @Description: 展位图片
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/6/15
    */
    private String zwtp;
    /**
    * @Description: 展位图片名称
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/6/15
    */
    private String zwtpmc;
    /**
    * @Description: 展位分类关联数据字典id
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/6/15
    */
    private Long sjzdId;

}
