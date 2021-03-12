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
 * @date 2021-02-20 17:05:41
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_zj")
public class TZj implements Serializable {
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
	private String sfz;
	/**
 	* 
 	*/
	private String xb;
	/**
 	* 
 	*/
	private String sr;
	/**
 	* 
 	*/
	private String mz;
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
	private String zyjszw;
	/**
 	* 
 	*/
	private String dw;
	/**
 	* 
 	*/
	private String dz;
	/**
 	* 
 	*/
	private String dh;
	/**
 	* 
 	*/
	private String zjh;
	/**
 	* 
 	*/
	private String sshy;
	/**
 	* 
 	*/
	private String csxk;
	/**
 	* 
 	*/
	private String zcfx;
	/**
 	* 
 	*/
	private String yjcg;
	/**
 	* 
 	*/
	private String dzyj;
	/**
 	* 
 	*/
	private String yjly;
	/**
 	* 
 	*/
	private String zscq;
	/**
 	* 
 	*/
	private String zjxm;
	/**
 	* 
 	*/
	private String lw;
	/**
 	* 
 	*/
	private String zlh;
	/**
 	* 
 	*/
	private String bz;

}
