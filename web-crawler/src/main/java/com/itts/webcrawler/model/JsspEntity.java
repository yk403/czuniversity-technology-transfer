package com.itts.webcrawler.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/*
 *@Auther: yukai
 *@Date: 2021/07/12/11:11
 *@Desription:
 */
@Data
@Document(collection="jszyfw")
public class JsspEntity implements Serializable {
    @Id
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
    private String zptp;
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

}
