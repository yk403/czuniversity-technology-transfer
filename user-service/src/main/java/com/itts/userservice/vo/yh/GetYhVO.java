package com.itts.userservice.vo.yh;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.itts.userservice.model.js.Js;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/5/11
 */
@Data
@ApiModel(value = "获取用户")
public class GetYhVO implements Serializable {

    private static final long serialVersionUID = 8326070328878109207L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    private String yhm;

    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号", required = true)
    private String yhbh;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名", required = true)
    private String zsxm;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话", required = true)
    private String lxdh;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像", required = true)
    private String yhtx;

    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型", required = true)
    private String yhlx;

    /**
     * 用户类别
     */
    @ApiModelProperty(value = "用户类别：研究生；经纪人；导师；企业导师；任课教师；管理员")
    private String yhlb;

    /**
     * 用户级别
     */
    @ApiModelProperty(value = "用户级别", required = true)
    private String yhjb;

    /**
     * 机构ID
     */
    @ApiModelProperty(value = "机构ID", required = true)
    private Long jgId;

    /**
     * 最顶级机构ID
     */
    @ApiModelProperty(value = "最顶级机构ID", required = true)
    private Long fjjgId;

    /**
     * 最顶级机构编码
     */
    @ApiModelProperty(value = "最顶级机构编码")
    private String jgbm;

    /**
     * 机构类型
     */
    @ApiModelProperty(value = "机构类型：headquarters - 总基地；branch - 分基地", required = true)
    private String jglx;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "用户邮箱", required = true)
    private String yhyx;

    /**
     * 用户积分
     */
    @ApiModelProperty(value = "用户积分")
    private Integer yhjf;

    /**
     * 是否会员
     */
    @ApiModelProperty(value = "是否会员", required = true)
    private Boolean sfhy;

    /**
     * 会员类型
     */
    @ApiModelProperty(value = "会员类型")
    private String hylx;

    /**
     * 用户角色
     */
    @ApiModelProperty(value = "用户角色")
    private List<Js> jsList;
}