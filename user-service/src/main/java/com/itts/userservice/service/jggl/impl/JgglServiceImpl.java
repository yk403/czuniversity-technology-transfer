package com.itts.userservice.service.jggl.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.userservice.dto.JgglDTO;
import com.itts.userservice.model.jggl.Jggl;
import com.itts.userservice.mapper.jggl.JgglMapper;
import com.itts.userservice.service.jggl.JgglService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.userservice.vo.JgglVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fl
 * @since 2021-03-30
 */
@Service
public class JgglServiceImpl implements JgglService {

    @Resource
    private JgglMapper jgglMapper;

    /**
     * 获取分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Jggl> findByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Jggl> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("sfsc",false);

        List<Jggl> jggls = jgglMapper.selectList(objectQueryWrapper);
        PageInfo<Jggl> jgglPageInfo = new PageInfo<>(jggls);
        return jgglPageInfo;
    }

    /**
     * 获取机构管理树
     * @return
     */
    @Override
    public List<JgglVO> findJgglVO() {
        //获取所有机构
        List<JgglVO> jgglVOS = jgglMapper.selectJggl();
        //获取机构树
        List<JgglVO> jgglVOList=buildJgglVOTree(jgglVOS,"000");
        return jgglVOList;
    }
    /**
     * 获取关键字机构管理树
     * @return
     */
    @Override
    public List<JgglVO> findStringJgglVO(String string) {
        List<JgglVO> jgglVOS = jgglMapper.selectStringJggl(string);
        List<JgglVO> jgglVOList=buildJgglVOTree(jgglVOS,"000");
        String[] sisoi="100-199".split("-");
        return jgglVOList;
    }

    private List<JgglVO> buildJgglVOTree(List<JgglVO> jgglVOS,String fatherCode){
        List<JgglVO> treeList = new ArrayList<>();
        jgglVOS.forEach(jgglVO -> {
            if(jgglVO.getFatherCode().equals(fatherCode)){
                jgglVO.setJgglVOList(buildJgglVOTree(jgglVOS,jgglVO.getJgbm()));
                treeList.add(jgglVO);
            }
        });
        return treeList;
    }

    @Override
    public Jggl get(Long id) {
        Jggl jggl = jgglMapper.selectById(id);
        return jggl;
    }

    /**
     * 查询，通过机构名称
     */
    @Override
    public Jggl selectByJgmc(String jgmc) {
        Jggl jggl = jgglMapper.selectByName(jgmc);
        return jggl;
    }

    /**
     * 查询，通过机构代码
     */
    @Override
    public Jggl selectByJgbm(String jgbm) {
        Jggl jggl = jgglMapper.selectByCode(jgbm);
        return jggl;
    }

    /**
     * 新增
     *
     */
    @Override
    public Jggl add(Jggl jggl) {
        jgglMapper.insert(jggl);
        return jggl;
    }

    /**
     * 更新
     */
    @Override
    public Jggl update(Jggl jggl) {
        jgglMapper.updateById(jggl);
        return jggl;
    }


}
