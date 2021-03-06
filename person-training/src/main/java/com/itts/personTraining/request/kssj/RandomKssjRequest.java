package com.itts.personTraining.request.kssj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.itts.personTraining.vo.sjpz.SjpzVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RandomKssjRequest implements Serializable {

    private static final long serialVersionUID = 4523402879345905996L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 试卷名称
     */
    @ApiModelProperty(value = "试卷名称", required = true)
    private String sjmc;
    /**
     * 教育类型
     */
    @ApiModelProperty(value = "教育类型")
    private String jylx;

    /**
     * 学员类型
     */
    @ApiModelProperty(value = "学员类型")
    private String xylx;
    private String sjlb;

    /**
     * 考试类型：single_subject - 单科；comprehensive - 综合
     */
    @ApiModelProperty(value = "考试类型：选课；综合", required = true)
    private String sjlx;



    private Long sjpzId;
    private List<Long> kcIdList;
}
