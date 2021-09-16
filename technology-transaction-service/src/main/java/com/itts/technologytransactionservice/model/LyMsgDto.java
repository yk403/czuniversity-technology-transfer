package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yukai
 * @since 2021-05-18
 */
@Data
public class LyMsgDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 双创路演留言记录主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 创建时间
     */
    @TableField(value = "cjsj", fill = FieldFill.INSERT) // 新增执行
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cjsj;
    /**
     * 更新时间
     */
    @TableField(value = "gxsj", fill = FieldFill.INSERT_UPDATE) // 新增和更新执行
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gxsj;

    /**
     * 删除状态(0未删除;1已删除)
     */
    private Boolean isDelete;

    /**
     * 关联用户id
     */
    private Long yhId;

    /**
     * 关联人员id(路演活动)
     */
    private Long ryId;
    /**
     * 留言内容
     */
    private String lynr;
    /**
     * 回复内容
     */
    private String hfnr;
    /**
     * 留言时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lysj;
    /**
     * 留言状态(0:未回复,1:已回复)
     */
    private Integer lyzt;
    /**
    * @Description: 用户头像
    * @Param:
    * @return:
    * @Author: yukai
    * @Date: 2021/6/7
    */
    private String yhtx;
    /**
     * @Description: 用户名
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/7
     */
    private String yhm;
    /**
     * @Description: 真实姓名
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/7
     */
    private String zsxm;
    /**
     * @Description: 用户类型
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/7
     */
    private String yhlx;
    /**
     * @Description: 用户类型
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/7
     */
    private String yhlb;
    /**
     * 前端查看状态(0:可以查看,1:不可查看)
     */
    private Integer ckzt;
    /**
     * @Description: 是否查看状态(0:未查看 1:已查看)
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/7/2
     */
    private Integer sfckzt;
    /**
     * @Description:研究领域
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/7/2
     */
    private String yjly;
    /**
     * @Description:所属行业
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/7/2
     */
    private String sshy;
    /**
     * 回复时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date hfsj;
    /**
    * @Description: 专家姓名
    * @Param:
    * @return:
    * @Author: yukai
    * @Date: 2021/7/2
    */
    private String xm;
    /**
     * @Description: 专家头像
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/7/2
     */
    private String zjtx;
    /**
    * @Description: 活动名称
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/7/5
    */
    private String hdmc;



}
