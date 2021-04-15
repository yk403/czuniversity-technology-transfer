package com.itts.userservice.vo;

import com.itts.userservice.model.cd.Cd;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

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
     * 子级菜单信息
     */
    private List<CdTreeVO> childCds;
}