package com.itts.personTraining.vo.jjrpxjh;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/1
 */
@Data
public class GetJjrpxhKcVO implements Serializable {

    private static final long serialVersionUID = 2400040070747976252L;

    /**
     * 课程名称
     */
    private String kcmc;

    /**
     * 课程代码
     */
    private String kcdm;

    /**
     * 课程学时(如总学时为36学时)
     */
    private String kcxs;

    /**
     * 课程学分
     */
    private Integer kcxf;

    /**
     * 授课方式:	seminar - 讲授/研讨;	discuss - 讲授讨论;	practice - 实训
     */
    private String skfs;

    /**
     * 是否必修(0:否;1:是)
     */
    private Boolean sfbx;
}