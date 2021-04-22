package com.itts.personTraining.model.xs;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 学生表
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_xs")
public class Xs implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 师资ID
     */
    private Long szId;

    /**
     * 批次Id
     */
    private Long pcId;

    /**
     * 学号
     */
    private String xh;

    /**
     * 姓名
     */
    private String xm;

    /**
     * 年级
     */
    private String nj;

    /**
     * 性别(0:男;1:女)
     */
    private String xb;

    /**
     * 出生日期
     */
    private Date csrq;

    /**
     * 学生类别ID	postgraduate - 研究生;	broker - 经纪人
     */
    private String xslbId;

    /**
     * 学生类别名称
     */
    private String xslbmc;

    /**
     * 身份证号
     */
    private String sfzh;

    /**
     * 毕业证号
     */
    private String byzh;

    /**
     * 学位证号
     */
    private String xwzh;

    /**
     * 研究方向
     */
    private String yjfx;

    /**
     * 培养类别ID
     */
    private String pylbId;

    /**
     * 培养类别名称
     */
    private String pylbmc;

    /**
     * 籍贯
     */
    private String jg;

    /**
     * 民族
     */
    private String mz;

    /**
     * 政治面貌
     */
    private String zzmm;

    /**
     * 入学方式
     */
    private String rxfs;

    /**
     * 原毕业院校
     */
    private String ybyyx;

    /**
     * 原工作单位
     */
    private String ygzdw;

    /**
     * 原工作单位地址
     */
    private String ygzdwdz;

    /**
     * 现工作单位
     */
    private String xgzdw;

    /**
     * 现工作单位地址
     */
    private String xgzdwdz;

    /**
     * 现工作单位邮编
     */
    private String xgzdwyb;

    /**
     * 学制
     */
    private String xz;

    /**
     * 学习地点
     */
    private String xxdd;

    /**
     * 本科毕业证号
     */
    private String bkbyzh;

    /**
     * 本科学位证号
     */
    private String bkxwzh;

    /**
     * 论文题目
     */
    private String lwtm;

    /**
     * 论文主题词
     */
    private String lwztc;

    /**
     * 答辩日期
     */
    private Date dbrq;

    /**
     * 毕业日期
     */
    private Date byrq;

    /**
     * 授学位日期
     */
    private Date sxwrq;

    /**
     * 学位类型
     */
    private String xwlx;

    /**
     * 导师编号
     */
    private String dsbh;

    /**
     * 导师姓名
     */
    private String dsxm;

    /**
     * 院系
     */
    private String yx;

    /**
     * 教委专业代码
     */
    private String jwzydm;

    /**
     * 学习形式
     */
    private String xxxs;

    /**
     * 毕业结论
     */
    private String byjl;

    /**
     * 校长姓名
     */
    private String xzxm;

    /**
     * 办学类型
     */
    private String bxlx;

    /**
     * 培养单位码
     */
    private String pydwm;

    /**
     * 培养单位
     */
    private String pydw;

    /**
     * 招生季节
     */
    private String zsjj;

    /**
     * 入学日期
     */
    private Date rxrq;

    /**
     * 学制
     */
    private String xzTwo;

    /**
     * 准考证号
     */
    private String zkzh;

    /**
     * 联系电话(手机)
     */
    private String lxdh;

    /**
     * 家庭地址
     */
    private String jtdz;

    /**
     * 备注
     */
    private String bz;

    /**
     * 专业代码
     */
    private String zydm;

    /**
     * 专业
     */
    private String zy;

    /**
     * 是否删除(0:未删除;1已删除)
     */
    private Boolean sfsc;

    /**
     * 创建时间
     */
    @TableField(value = "cjsj", fill = FieldFill.INSERT) // 新增执行
    private Date cjsj;

    /**
     * 创建人
     */
    private Long cjr;

    /**
     * 更新时间
     */
    @TableField(value = "gxsj", fill = FieldFill.INSERT_UPDATE) // 新增和更新执行
    private Date gxsj;

    /**
     * 更新人
     */
    private Long gxr;

    /**
     * 教育形式
     */
    private String jyxs;

}
