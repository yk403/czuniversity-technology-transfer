package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @date 2021-02-22 11:17:27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_js_hd")
public class TJsHd implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
 	* 主键
 	*/
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id;
	/**
 	* 活动名称
 	*/
	private String hdmc;
	/**
 	* 活动类型
 	*/
	private String hdlx;
	/**
 	* 活动简介
 	*/
	private String hdjj;
	/**
 	* 活动规则
 	*/
	private String hdgz;
	/**
 	* 活动开始时间
 	*/
	private Date hdkssj;
	/**
 	* 活动结束时间
 	*/
	private Date hdssj;
	/**
 	* 活动状态
 	*/
	private String hdzt;
	/**
 	* 创建时间
 	*/
	private Date cjsj;
	/**
	 * 咨询电话
	 */
	private String zxdh;
	/**
	 * 活动负责人
	 */
	private String hdfzr;
	/**
	 * 活动内容
	 */
	private String hdnr;

}
