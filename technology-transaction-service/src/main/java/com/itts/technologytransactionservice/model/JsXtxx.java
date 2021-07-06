package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/*
 *@Auther: yukai
 *@Date: 2021/07/06/8:35
 *@Desription:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_js_xtxx")
public class JsXtxx implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 系统消息主键
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
     * 删除状态(0未删除;1已删除)
     */
    private Boolean isDelete;
    /**
    * @Description: 发送用户id
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/7/6
    */
    private Long sendId;
    /**
    * @Description: 消息名称
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/7/6
    */
    private String xxmc;
    /**
    * @Description: 消息类型
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/7/6
    */
    private Integer xxlx;
    /**
    * @Description: 消息内容
    * @Param:
    * @return:
    * @Author: yukai
    * @Date: 2021/7/6
    */
    private String xxnr;
    /**
    * @Description: 查看状态
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/7/6
    */
    private Integer ckzt;
    /**
     * @Description: 接收用户id
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/7/6
     */
    private Long receiveId;
    /**
    * @Description: 所属模块(信息采集，报名，招标发布，挂牌发布等)
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/7/6
    */
    private String ssmk;
    /**
     * @Description: 模块类型(0:技术交易,1:双创路演)
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/7/6
     */
    private Integer mklx;
    /**
     * @Description: 载体类型(如技术成果，技术需求，技术展品)
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/7/6
     */
    private String ztlx;
    /**
     * @Description: 操作内容
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/7/6
     */
    private String cznr;
}
