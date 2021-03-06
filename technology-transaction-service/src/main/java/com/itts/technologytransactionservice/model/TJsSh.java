package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 11:13:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_js_sh")
public class TJsSh implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
 	* 审核主键
 	*/
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id;
	/**
 	* 类型 1技术成果、2技术需求
 	*/
	private Integer lx;
	/**
 	* 技术成果id
 	*/
	private Integer cgId;
	/**
	 * 技术需求id
	 */
	private Integer xqId;
	/**
 	* 发布审核状态 0待提交、1待审核、2通过、3整改、4拒绝
 	*/
	private Integer fbshzt;
	/**
 	* 活动申请状态 1待审核、2通过、3整改、4拒绝
 	*/
	private String hdsqzt;
	/**
 	* 发布审核备注
 	*/
	private String fbshbz;
	/**
 	* 活动申请备注
 	*/
	private String hdsqbz;
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
	 * 发布状态 1 未发布 2已发布
	 */
	private Integer releaseStatus;
	/**
	 * 受理协办审核状态
	 * 1待审核、2通过、3整改、4拒绝
	 */
	private Integer assistanceStatus;
	/**
	 * 交易类型（0：拍卖；1：招标；2：挂牌）
	 */
	private Integer jylx;
	/**
	 * 受理协办发布状态
	 * 1 未发布 2已发布
	 */
	private Integer releaseAssistanceStatus;
	/**
	 * 受理协办备注
	 */
	private String slxbbz;
	/**
	 * 删除状态(0:未删除;1:已删除)
	 */
	private Integer isDelete;
	/**
	 * 结算状态(0:未结算;1:已结算)
	 */
	private Integer jszt;
}
