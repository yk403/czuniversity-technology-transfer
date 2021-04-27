package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


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
	private Integer hdlx;
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
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date hdkssj;
	/**
 	* 活动结束时间
 	*/
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date hdjssj;
	/**
 	* 活动状态
 	*/
	private Integer hdzt;
	/**
 	* 创建时间
 	*/
	@TableField(value = "cjsj", fill = FieldFill.INSERT) // 新增执行
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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
	/**
	 * 活动方式(0:自动;1手动)
	 */
	private Integer hdfs;
	/**
	 * 活动图片
	 */
	private String hdtp;
	/**
	 * 活动发布状态
	 */
	private Integer hdfbzt;
	/**
	 * 报名人数
	 */
	private Integer bmrs;
	/**
	 * 删除状态(0:未删除;1:用户删除;2:管理员删除)
	 */
	private Integer isDelete;
	/**
	 * 更新时间
	 */
	@TableField(value = "gxsj", fill = FieldFill.INSERT_UPDATE) // 新增和更新执行
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date gxsj;
	/*
	是否报名
	 */
	private Integer isBm;
}
