package com.itts.userservice.mapper.jggl;

import com.itts.userservice.dto.JgglDTO;
import com.itts.userservice.model.jggl.Jggl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.userservice.vo.JgglVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author fl
 * @since 2021-03-30
 */
public interface JgglMapper extends BaseMapper<Jggl> {


    Jggl selectByName(String jgmc);


    Jggl selectByCode(String jgbm);

    /**
     * 查询机构
     */
    List<JgglVO> selectJggl();

    /**
     * 查询机构
     */
    List<JgglVO> selectStringJggl(@Param("name") String string);

    /**
     * 通过机构编码获取该机构及下属机构信息
     */
    @Select({"<script>",
            "SELECT * " +
            "FROM t_jggl " +
            "WHERE sfsc = false " +
            "<if test=\" code != null and code != ''\">" +
                "AND cj LIKE CONCAT('%-', #{code}, '-%')" +
            "</if>",
    "</script>"})
    List<Jggl> findThisAndChildByCode(@Param("code") String code);

}
