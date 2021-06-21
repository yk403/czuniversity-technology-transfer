package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yukai
 * @since 2021-05-18
 */
@Data
public class LyZpDto extends LyZp implements Serializable {
/**
* @Description: 活动名称
* @Param:
* @return:
* @Author: yukai
* @Date: 2021/6/17
*/
private String hdmc;
    /**
     * @Description: 展位名称(领域名称)
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/21
     */
    private String zwmc;


}
