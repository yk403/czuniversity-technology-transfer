package com.itts.userservice.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 字典表
 */
@Data
public class ShzdVO implements Serializable {
    private static final long serialVersionUID = 8640002509739394257L;
    private String zdmc;
    private String zdbm;
    private String fjzdCode;
    private String zdcj;
    private Date cjsj;
    private List<ShzdVO> shzdVOList;
}
