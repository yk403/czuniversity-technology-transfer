package com.itts.userservice.model.ggtz;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author fuli
 * @since 2021-07-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_ggtz")
public class Ggtz implements Serializable {


    private static final long serialVersionUID = -4672068901247847224L;
    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 通知标题
     */
    private String tzbt;

    /**
     * 通知内容
     */
    private String tzwnr;

    /**
     * 通知图片名称
     */
    private String tztpmc;

    /**
     * 通知图片地址
     */
    private String tztpdz;
    /**
     * 时间
     */
    private Date sj;

    /**
     * 类型
     */
    private String lx;

    /**
     * 机构ID
     */
    private Long jgId;

    /**
     * 状态已发布未发布已停用
     */
    private String zt;

    /**
     * 发布时间
     */
    private Date fbsj;

    /**
     * 备注
     */
    private String bz;

    /**
     * 是否删除
     */
    private Boolean sfsc;

    /**
     * 创建时间
     */
    private Date cjsj;

    /**
     * 创建人
     */
    private Long cjr;

    /**
     * 更新时间
     */
    private Date gxsj;

    /**
     * 更新人
     */
    private Long gxr;


}
