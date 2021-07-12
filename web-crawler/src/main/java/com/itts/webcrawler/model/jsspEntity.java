package com.itts.webcrawler.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/*
 *@Auther: yukai
 *@Date: 2021/07/12/11:11
 *@Desription:
 */
@Data
@Document(collection="jszyfw")
public class jsspEntity implements Serializable {
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
    * @Description: 
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/7/12
    */
    private String zptp;
}
