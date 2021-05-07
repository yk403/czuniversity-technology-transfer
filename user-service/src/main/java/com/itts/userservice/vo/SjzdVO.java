package com.itts.userservice.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 字典表
 */
@Data
public class SjzdVO implements Serializable {
    private static final long serialVersionUID = 8640002509739394257L;
    private String zdmc;
    private String zdbm;
    private String xtlb;
    private String mklx;
    private String ssmk;
    private Date cjsj;
    private List<SjzdVO> sjzdVOList;
}
