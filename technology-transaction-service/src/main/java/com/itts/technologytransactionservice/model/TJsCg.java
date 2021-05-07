package com.itts.technologytransactionservice.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;


/**
 * 
 * 
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 09:16:14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_js_cg")
public class TJsCg implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
 	* 主键id
 	*/
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id;
	/**
	 * soft(排序使用)
	 */
	private Integer soft;
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
 	* 成果权属人
 	*/
	private String cgqsr;
	/**
 	* 权属人联系电话
 	*/
	private String qsrlxdh;
	/**
 	* 成果名称
 	*/
	private String cgmc;
	/**
 	* 关键词
 	*/
	private String gjc;
	/**
 	* 成果完成时间
 	*/
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date cgwcsj;
	/**
 	* 资助情况
 	*/
	private String zzqk;
	/**
 	* 成果应用领域
 	*/
	private Long lyId;
	/**
 	* 成果获得方式
 	*/
	private String cghqfs;
	/**
 	* 技术成熟度
 	*/
	private String jscsd;
	/**
 	* 获奖情况
 	*/
	private String hjqk;
	/**
 	* 合作价格
 	*/
	private String hzjg;
	/**
 	* 合作方式
 	*/
	private String hzfs;
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
	 * 手机号码
	 */
	private String sjhm;
	/**
 	* 座机
 	*/
	private String zj;
	/**
 	* 电子邮箱
 	*/
	private String dzyx;
	/**
 	* 知识产权形式
 	*/
	private String zscqxs;
	/**
 	* 成果简介
 	*/
	private String cgjs;
	/**
 	* 技术指标
 	*/
	private String jszb;
	/**
 	* 商业分析
 	*/
	private String syfx;
	/**
 	* 成果图片
 	*/
	private String cgtp;
	/**
 	* 成果视频
 	*/
	private String cgsp;
	/**
	 * 组织机构代码
	 */
	private String zzjgdm;
	/**
 	* 备注
 	*/
	private String bz;
	/**
 	* 活动id
 	*/
	private Integer jshdId;
	/**
	*发布成果审核状态(0待提交;1审核中;2通过;3整改;4拒绝)
 	*/
	private Integer fbshzt;
	/**
	 * 发布时间
	 */
	private String createTime;
	/**
	 * 发布状态
	 */
	private String releaseStatus;
	/**
	 * 受理协办审核状态
	 */
	private Integer assistanceStatus;
	/**
	 * 受理协办发布状态
	 */
	private Integer releaseAssistanceStatus;
	/**
	 * 发布类型
	 */
	private String releaseType;
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
	 * 保证金
	 */
	private String bond;
	/**
	 * 技术成果类别
	 */
	private Long lbId;
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
	 * 发布审核备注
	 */
	private String fbshbz;
	/**
	 * 交易类型
	 */
	private Integer jylx;
	/**
	 * 活动状态(0:未开始;1:进行中;2已结束)
	 */
	private Integer hdzt;
	/*
受理协办备注
*/
	private String slxbbz;
	/**
	 * 协议
	 */
	private String agreementName;
	/**
	 * 成果视频名称
	 */
	private String cgspmc;
	/**
	 * 成果附件
	 */
	private String cgfj;
	/**
	 * 成果附件名称
	 */
	private String cgfjmc;
	/**
	 * 成交价格
	 */
	private String cjjg;
	/**
	 * 招拍挂拍卖状态
	 */
	private Integer auctionStatus;
	/**
	 * 结算状态(0:未结算;1:已结算)
	 */
	private Integer jszt;
}
