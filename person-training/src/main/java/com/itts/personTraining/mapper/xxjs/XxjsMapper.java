package com.itts.personTraining.mapper.xxjs;

import com.itts.personTraining.dto.XxjsDTO;
import com.itts.personTraining.model.xxjs.Xxjs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.personTraining.vo.xxzy.GetXxzyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 学校教室表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-04-22
 */
public interface XxjsMapper extends BaseMapper<Xxjs> {

//    /**
//     * 查询所有教学楼
//     * @return
//     */
//    List<Xxjs> findAllJxlmc();

    /**
     * 分页查询教室列表
     * @return
     */
    List<XxjsDTO> findByPage( @Param("xxjxlId") Long xxjxlId,@Param("fjjgId") Long fjjgId);

}
