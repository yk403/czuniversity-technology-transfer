package com.itts.userservice.vo;

import com.itts.userservice.model.cd.Cd;
import com.itts.userservice.model.cz.Cz;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Descripti * @Author：lym
 * @Date: 2021/4/15
 */
@Data
@ApiModel("菜单树形对象")
public class CdTreeVO extends Cd implements Serializable {

    private static final long serialVersionUID = -8319190364654211745L;

    /**
     * 当前菜单拥有的操作
     */
    @ApiModelProperty("当前菜单拥有的操作")
    private List<Cz> czs;

    /**
     * 子级菜单信息
     */
    @ApiModelProperty("子级菜单信息")
    private List<CdTreeVO> childCds;
}