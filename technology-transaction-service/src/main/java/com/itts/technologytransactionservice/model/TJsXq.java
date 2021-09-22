package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;


/**
 * 
 * 
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 09:19:20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_js_xq")
public class TJsXq implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
 	* 主键
 	*/
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id;
	/**
	 * soft(排序使用)
	 */
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private Integer soft;
	/**
	 * 技术需求id
	 */
	private Integer userId;
	/**
 	* 技术需求名称
 	*/
	private String xqmc;
	/**
 	* 关键字
 	*/
	private String gjz;
	/**
 	* 有效期
 	*/
	private String yxq;
	/**
 	* 需求领域id
 	*/
	private String lyId;
	/**
 	* 技术需求类别id
 	*/
	private String lbId;
	/**
 	* 合作方式
 	*/
	private String hzfs;
	/**
 	* 合作价格
 	*/
	private BigDecimal hzjg;
	/**
 	* 意向合作单位
 	*/
	private String yxhzdw;
	/**
 	* 意向合作专家
 	*/
	private String yxhzzj;
	/**
 	* 技术需求详情
 	*/
	private String xqxq;
	/**
 	* 技术指标
 	*/
	private String jszb;
	/**
 	* 需求图片
 	*/
	private String xqtp;
	/**
 	* 需求视频
 	*/
	private String xqsp;
	/**
 	* 单位名称
 	*/
	private String dwmc;
	/**
 	* 地址
 	*/
	private String dz;
	/**
 	* 法人
 	*/
	private String fr;
	/**
 	* 联系人
 	*/
	private String contracts;
	/**
 	* 联系人电话
 	*/
	private String lxrdh;
	/**
 	* 座机
 	*/
	private String zj;
	/**
 	* 电子邮箱
 	*/
	private String dzyx;
	/**
 	* 技术活动id
 	*/
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private Integer jshdId;
	/**
	 * 发布需求审核状态(0待提交、1待审核、2通过、3整改、4拒绝)
	 */
	private Integer fbshzt;
	/**
	 * 发布时间
	 */
	//private String createTime;
	/**
	 * 发布状态
	 */
	private String releaseStatus;

	/**
	 * 发布类型
	 */
	private String releaseType;
	/**
	 * 受理协办审核状态
	 */
	private Integer assistanceStatus;
	/**
	 * 领域名称
	 */
	private String lyName;
	/**
	 * 类别名称
	 */
	private String lbName;
	/**
	 * 协议
	 */
	private String agreement;
	/**
	 * 协议名称
	 */
	private String agreementName;
	/**
	 * 保证金
	 */
	private BigDecimal bond;
	/**
	 * 需求简介
	 */
	private String introduction;
	/**
	 * 组织机构代码
	 */
	private String zzjgdm;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 删除状态(0:未删除;1:已删除)
	 */
	private Integer isDelete;
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
	 * 手机号码
	 */
	private String sjhm;
	/**
	 * 交易类型
	 */
	private Integer jylx;
	/**
	 * 受理协办发布状态（1 未发布 2 已发布）
	 */
	private Integer releaseAssistanceStatus;
	/*
	发布审核备注
	 */
	private String fbshbz;
	/**
	 * 活动状态(0:未开始;1:进行中;2已结束)
	 */
	private Integer hdzt;
	/*
受理协办备注
 */
	private String slxbbz;
	/**
	 * 需求视频名称
	 */
	private String xqspmc;
	/**
	 * 需求附件
	 */
	private String xqfj;
	/**
	 * 需求附件名称
	 */
	private String xqfjmc;
	/**
	 * 招标项目概述
	 */
	private String zbxmgs;
	/**
	 * 招标项目清单
	 */
	private String zbxmqd;
	/**
	 * 技术参数
	 */
	private String jscs;
	/**
	 * 服务内容
	 */
	private String fwnr;
	/**
	 * 成交价格
	 */
	private BigDecimal cjjg;
	/**
	 * 交货日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date jhrq;
	/**
	 * 验收日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date ysrq;
	/**
	 * 招拍挂拍卖状态
	 */
	private Integer auctionStatus;
	/**
	 * 结算状态(0:未结算;1:已结算)
	 */
	private Integer jszt;
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
	 * 服务费(默认为落锤金额的5%)
	 */
	private BigDecimal fwf;
	/**
	 * 落锤定价
	 */
	private Integer lcdj;
	/**
	 * 受让方
	 */
	private String srfmc;
	/**
	 * 受让方联系电话
	 */
	private String srfhm;
	/**
	 * 底价
	 */
	private BigDecimal dj;
	/**
	 * 活动名称
	 */
	private String hdmc;
	/**
	* @Description: 需求详情图片名称
	* @Param: 
	* @return: 
	* @Author: yukai
	* @Date: 2021/6/30
	*/
	private String xqxqtpmc;
	/**
	 * @Description: 需求详情图片
	 * @Param:
	 * @return:
	 * @Author: yukai
	 * @Date: 2021/6/30
	 */
	private String xqxqtp;
	/**
	 * 采集方式(0为审核采集，1为管理员采集)
	 */
	private Integer cjfs;

	/**
	 * 父级机构id
	 */
	private Long fjjgId;

	private String jsgf;
	private String zscq;
}
