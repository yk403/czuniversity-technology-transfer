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
 * @date 2021-02-22 11:15:49
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_js_jj")
public class TJsJj implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
 	* 
 	*/
	@TableId(value = "id")
	private Long id;
	/**
 	* 
 	*/
	private String xm;
	/**
 	* 
 	*/
	private String sfzh;
	/**
 	* 
 	*/
	private String xb;
	/**
 	* 
 	*/
	private String dp;
	/**
 	* 
 	*/
	private String xl;
	/**
 	* 
 	*/
	private String byyx;
	/**
 	* 
 	*/
	private String sszy;
	/**
 	* 
 	*/
	private String dz;
	/**
 	* 
 	*/
	private String fwly;
	/**
 	* 
 	*/
	private String zynlrd;
	/**
 	* 
 	*/
	private String zyzsbh;
	/**
 	* 
 	*/
	private String zsffsj;
	/**
 	* 
 	*/
	private String pxxs;
	/**
 	* 
 	*/
	private String pxxf;
	/**
 	* 
 	*/
	private String nlzp;
	/**
 	* 
 	*/
	private String fwnr;
	/**
 	* 
 	*/
	private String fwal;
	/**
 	* 
 	*/
	private String cgcx;
	/**
 	* 
 	*/
	private String nlpj;
	/**
 	* 
 	*/
	private String grtp;
	/**
 	* 
 	*/
	private String sp;

}
