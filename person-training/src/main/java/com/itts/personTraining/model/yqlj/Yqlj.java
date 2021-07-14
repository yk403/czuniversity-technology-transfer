package com.itts.personTraining.model.yqlj;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("t_yqlj")
public class Yqlj implements Serializable {


    private static final long serialVersionUID = 4487491520063062711L;
    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String mc;

    /**
     * 网址
     */
    private String url;

    /**
     * 类型
     */
    private String lx;

    /**
     * LOGO
     */
    private String logo;

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
