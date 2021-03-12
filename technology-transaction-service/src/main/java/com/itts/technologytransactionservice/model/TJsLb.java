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
 * @date 2021-02-22 11:14:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_js_lb")
public class TJsLb implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
 	* 技术类别主键
 	*/
	@TableId(value = "id")
	private Long id;
	/**
 	* 名称
 	*/
	private String mc;
	/**
 	* 编号
 	*/
	private String bh;

}
