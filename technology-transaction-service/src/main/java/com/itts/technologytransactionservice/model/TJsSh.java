package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


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
	@TableId(value = "id")
	private Long id;
	/**
 	* 类型 1成果、2需求
 	*/
	private String lx;
	/**
 	* 技术成果需求id 外键
 	*/
	private Long cgxqId;
	/**
 	* 发布审核状态 1待审核、2通过、3不通过
 	*/
	private String fbshzt;
	/**
 	* 活动申请状态 0未审核、1待审核、2通过、3不通过
 	*/
	private String hdsqzt;
	/**
 	* 发布未通过说明
 	*/
	private String fbwtgsm;
	/**
 	* 活动申请未通过说明
 	*/
	private String hdsqwtgsm;
	/**
 	* 创建时间
 	*/
	private String cjsj;
	/**
 	* 更新时间
 	*/
	private String gxsj;
	/*
	发布状态 1 未发布 2已发布
	 */
	private String releaseStatus;

}
