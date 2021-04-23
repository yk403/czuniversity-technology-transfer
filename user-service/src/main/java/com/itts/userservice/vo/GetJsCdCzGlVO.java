package com.itts.userservice.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/4/15
 */
@Data
@ApiModel("获取角色菜单操作关联对象")
public class GetJsCdCzGlVO implements Serializable {

    private static final long serialVersionUID = -9006104354310951089L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 操作名称
     */
    @ApiModelProperty("操作名称")
    private String czmc;

    /**
     * 操作编码
     */
    @ApiModelProperty("操作编码")
    private String czbm;
}