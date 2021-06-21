package com.itts.personTraining.mapper.xxzy;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.personTraining.model.xxzy.Xxzy;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 学习资源 Mapper 接口
 * </p>
 *
 * @author liuyingming
 *
 * @since 2021-05-12
 */
@Repository
public interface XxzyMapper extends BaseMapper<Xxzy> {

    @Update("UPDATE t_xxzy " +
            "SET mc=#{mc}, bm=#{mc}, zylb=#{zylb}, zyyjfl=#{zyyjfl}, zyejfl=#{zyejfl}, zylx=#{zylx}, " +
            " zyfx=#{zyfx}, jj=#{jj}, jg=#{jg}, zz=#{zz}, zstp=#{zstp}, sffx=#{sffx}, " +
            "sfsj=#{sfsj}, sfgk=#{sfgk}, gkkssj = #{gkkssj}, gkjssj = #{gkjssj} " +
            " WHERE (id = #{id})")
    int update(Xxzy xxzy);

}
