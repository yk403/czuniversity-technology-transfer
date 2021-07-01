package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author yukai
 * @since 2021-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_ly_zp")
public class LyZp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 展品主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @TableField(value = "cjsj", fill = FieldFill.INSERT) // 新增执行
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date cjsj;

    /**
     * 更新时间
     */
    @TableField(value = "gxsj", fill = FieldFill.INSERT_UPDATE) // 新增和更新执行
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date gxsj;

    /**
     * 展品类别
     */
    private String zplb;

    /**
     * 展品简介
     */
    private String zpjj;

    /**
     * 删除状态(0未删除;1已删除)
     */
    private Boolean isDelete;

    /**
     * 关联展位id
     */
    private Long zwId;
    /**
     * 展品类型(0:需求1:成果)
     */
    private Integer zplx;
    /**
    * @Description: 展品名称
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/6/10
    */
    private String zpmc;
    /**
     * 关联领域id
     */
    private Long lyId;
    /**
    * @Description: 单位名称
    * @Param:
    * @return:
    * @Author: yukai
    * @Date: 2021/6/10
    */
    private String dwmc;
    /**
     * 领域名称
     */
    private String lymc;
    /**
     * 联系方式
     */
    private String lxfs;
    /**
    * @Description: 合作价格
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/6/10
    */
    private BigDecimal hzjg;
    /**
    * @Description: 类别id
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/6/17
    */
    private Long lbId;
    /**
     * @Description: 关联活动id
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private Long hdId;
    /**
     * @Description: 出让方
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String crf;
    /**
     * @Description: 协议管理
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String xygl;
    /**
     * @Description: 协议管理名称
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String xyglmc;
    /**
     * @Description: 发布审核状态（0待提交、1待审核、2通过、3整改中、4拒绝、5整改完成）
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private Integer fbshzt;
    /**
     * @Description: 联系人
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String lxr;
    /**
     * @Description: 组织机构代码
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String zzjgdm;
    /**
     * @Description: 法人
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String fr;
    /**
     * @Description: 座机
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String zj;
    /**
     * @Description: 电子邮箱
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String dzyx;
    /**
     * @Description: 单位地址
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String dz;
    /**
     * @Description: 有效期
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String yxq;
    /**
     * @Description: 关键词
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String gjc;
    /**
     * @Description: 关键字
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String gjz;
    /**
     * @Description: 发布审核备注
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String fbshbz;
    /**
     * @Description: 合作方式
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String hzfs;
    /**
     * @Description: 意向合作单位
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String yxhzdw;
    /**
     * @Description: 意向合作专家
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String yxhzzj;
    /**
     * @Description: 展品图片
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String zptp;
    /**
     * @Description: 展品图片名称
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String zptpmc;
    /**
     * @Description: 手机号码
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String sjhm;
    /**
     * @Description: 展品视频
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String zpsp;
    /**
     * @Description: 展品视频名称
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String zpspmc;
    /**
     * 有效期开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date yxqkssj;
    /**
     * 有效期结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date yxqjssj;
    /**
     * @Description: 展品详情
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String zpxq;
    /**
     * @Description: 技术指标
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String jszb;
    /**
     * @Description: 备注
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String remarks;
    /**
     * 成果完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date cgwcsj;
    /**
    * @Description: 成果权属人
    * @Param:
    * @return:
    * @Author: yukai
    * @Date: 2021/6/17
    */
    private String cgqsr;
    /**
     * @Description: 权属人联系电话
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/17
     */
    private String qsrlxdh;
    private Long userId;
    /**
    * @Description: 展品详情图片
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/6/30
    */
    private String zpxqtp;
    /**
     * @Description: 展品详情图片名称
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/30
     */
    private String zpxqtpmc;

    /**
     * 展品详情图片
     */
    private String zpxqtp;

    /**
     * 展品详情图片名称
     */
    private String zpxqtpmc;
}
