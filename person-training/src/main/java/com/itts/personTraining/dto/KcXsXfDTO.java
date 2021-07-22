package com.itts.personTraining.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("课程时间对象")
public class KcXsXfDTO {

    /**
     * 主键
     */
    private Long id;


    /**
     * 教育类型
     */
    private String jylx;

    /**
     * 学员类型
     */
    private String xylx;

    /**
     * 课程类型(theory_class：理论课；practical_training：实训课；practice_course：实践课)
     */
    private String kclx;

    /**
     * 课程图片;
     */
    private String kctp;

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
     * 开课学期
     */
    private String kkxq;

    /**
     * 开课学院
     */
    private String kkxy;



    /**
     * 是否必修(0:否;1:是)
     */
    private Boolean sfbx;


    /**
     * 导师姓名
     */
    private String dsxm;

    /**
     * 起始周
     */
    private Integer qsz;

    /**
     * 结束周
     */
    private Integer jsz;

    /**
     * 星期数
     */
    private String xqs;


}
