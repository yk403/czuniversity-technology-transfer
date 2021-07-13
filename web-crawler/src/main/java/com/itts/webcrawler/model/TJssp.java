package com.itts.webcrawler.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/*
 *@Auther: yukai
 *@Date: 2021/07/12/11:11
 *@Desription:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_jssp")
public class TJssp implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
    * @Description: 商品名称
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/7/12
    */
    private String title;
    /**
    * @Description: 商品图片
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/7/12
    */
    private String sptp;
    /**
    * @Description: 交易价格
    * @Param:
    * @return:
    * @Author: yukai
    * @Date: 2021/7/12
    */
    private String jyjg;
    /**
    * @Description: 专利类型
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/7/12
    */
    private String zllx;
    /**
    * @Description: 有效期
    * @Param:
    * @return:
    * @Author: yukai
    * @Date: 2021/7/12
    */
    private String yxq;
    /**
    * @Description: 商品类型(0需求，1:成果)
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/7/12
    */
    private Integer splx;
    /**
    * @Description: 发布日期
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/7/12
    */
    private Date fbrq;
    /**
    * @Description: 技术成熟度
    * @Param:
    * @return:
    * @Author: yukai
    * @Date: 2021/7/12
     *
    */
    private String jscsd;
    /**
    * @Description: 行业类别
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/7/12
    */
    private String hylb;
    /**
    * @Description: 联系人
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/7/12
    */
    private String lxr;
    /**
    * @Description: 发明人
    * @Param:
    * @return:
    * @Author: yukai
    * @Date: 2021/7/12
    */
    private String fmr;
    /**
    * @Description: 项目单位
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/7/12
    */
    private String dwmc;
    /**
    * @Description: 申请号或专业号
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/7/12
    */
    private String sqh;
    /**
     * 创建时间
     */
    @TableField(value = "cjsj", fill = FieldFill.INSERT) // 新增执行
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cjsj;

    /**
     * 更新时间
     */
    @TableField(value = "gxsj", fill = FieldFill.INSERT_UPDATE) // 新增和更新执行
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gxsj;
    /**
     * 删除状态(0未删除;1已删除)
     */
    private Boolean isDelete;
    /**
     * @Description: 需求类型
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/7/12
     */
    private String xqlx;
    /**
    * @Description: 单位性质
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/7/13
    */
    private String dwxz;
    /**
    * @Description: 内容目标
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/7/13
    */
    private String nrmb;
    /**
    * @Description: 需求详情
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/7/13
    */
    private String xqxq;
}
