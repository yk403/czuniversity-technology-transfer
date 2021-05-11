package com.itts.userservice.vo.yh;


import com.itts.userservice.dto.JsDTO;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户角色菜单
 */
@Data
public class YhVO implements Serializable {


    private static final long serialVersionUID = -6912495371946045832L;

    /**
     * 主键ID
     */

    private Long id;

    /**
     * 用户名
     */
    private String yhm;

    /**
     * 用户编号
     */
    private String yhbh;

    /**
     * 真实姓名
     */
    private String zsxm;

    /**
     * 联系电话
     */
    private String lxdh;

    /**
     * 用户邮箱
     */
    private String yhyx;

    /**
     * 用户类型
     */
    private String yhlx;

    /**
     * 用户级别
     */
    private String yhjb;

    /**
     * 是否会员
     */
    private Boolean sfhy;

    /**
     * 会员类型
     */
    private Long hylxId;
    /**
     * 用户角色及菜单
     */
    private List<JsDTO> jsDTOList;
}
