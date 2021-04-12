package com.itts.userservice.model.shzd;

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
 * @since 2021-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_shzd")
public class Shzd implements Serializable {


    private static final long serialVersionUID = -635139985468084264L;
    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字典名称
     */
    private String zdmc;

    /**
     * 字典编码
     */
    private String zdbm;

    /**
     * 父级字段code, 如果是顶级则为000
     */
    private String fjzdCode;

    /**
     * 字典层级, 以“-”分隔
     */
    private String zdcj;

    /**
     * 系统类型：technology_transaction_portal - 技术交易门户；tech
     */
    private String xtlx;
    /**
     * 模块类型:front - 门户;admin - 后台管理
     */
    private String mklx;
    /**
     * 所属模块: group_manage-机构管理
     */
    private String ssmk;
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
