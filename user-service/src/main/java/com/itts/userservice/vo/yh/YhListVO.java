package com.itts.userservice.vo.yh;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.itts.userservice.model.yh.Yh;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/4/9
 */

@Data
public class YhListVO  implements Serializable {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名",required = true)
    private String yhm;

    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号",required = true)
    private String yhbh;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名",required = true)
    private String zsxm;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话",required = true)
    private String lxdh;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像",required = true)
    private String yhtx;

    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型",required = true)
    private String yhlx;

    /**
     * 用户级别
     */
    @ApiModelProperty(value = "用户级别",required = true)
    private String yhjb;

    /**
     * 机构ID
     */
    @ApiModelProperty(value = "机构ID",required = true)
    private Long jgId;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "用户邮箱",required = true)
    private String yhyx;

    /**
    *用户角色名称
    */
    private String yhjsmc;
}