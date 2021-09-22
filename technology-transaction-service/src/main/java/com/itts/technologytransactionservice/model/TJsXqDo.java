package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * 
 * @author yukai
 * @email 15161575502@163.com
 * @date 2021-02-22 09:19:20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_js_xq")
public class TJsXqDo implements Serializable {
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
	//private Integer fbshzt;
	/**
	 * 发布时间
	 */
	//private String createTime;
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
	/**xqm
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
	 * 父级机构id
	 */
	private Long fjjgId;
	private String jsgf;
	private String zscq;
}
