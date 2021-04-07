package com.itts.userservice.model.jggl;

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
 * @author fl
 * @since 2021-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_jggl")
public class Jggl implements Serializable {


    private static final long serialVersionUID = -5373003775770802085L;
    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 机构名称
     */
    private String jgmc;

    /**
     * 机构编码
     */
    private String jgbm;

    /**
     * 机构类别ID
     */
    private String jglbCode;

    /**
     * 机构类别
     */
    private String jglb;

    /**
     * 父机构code
     */
    private String fatherCode;

    /**
     * 菜单code层级, 以“-”分隔
     */
    private String cj;

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
