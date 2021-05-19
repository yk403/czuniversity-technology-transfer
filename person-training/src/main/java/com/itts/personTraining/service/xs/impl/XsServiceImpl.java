package com.itts.personTraining.service.xs.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.dto.JwglDTO;
import com.itts.personTraining.dto.StuDTO;
import com.itts.personTraining.enums.EduTypeEnum;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.model.pcXs.PcXs;
import com.itts.personTraining.model.xs.Xs;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.service.pcXs.PcXsService;
import com.itts.personTraining.service.xs.XsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;
import static com.itts.common.enums.ErrorCodeEnum.STUDENT_MSG_NOT_EXISTS_ERROR;
import static com.itts.personTraining.enums.EduTypeEnum.ACADEMIC_DEGREE_EDUCATION;
import static com.itts.personTraining.enums.EduTypeEnum.ADULT_EDUCATION;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Service
@Slf4j
@Transactional
public class XsServiceImpl extends ServiceImpl<XsMapper, Xs> implements XsService {
    
    @Resource
    private XsMapper xsMapper;

    @Autowired
    private XsService xsService;

    @Autowired
    private PcXsService pcXsService;

    @Resource
    private PcXsMapper pcXsMapper;

    /**
     * 查询学员列表
     * @param pageNum
     * @param pageSize
     * @param pcId
     * @param jyxs
     * @return
     */
    @Override
    public PageInfo<StuDTO> findByPage(Integer pageNum, Integer pageSize, Long pcId, String xslbmc, String jyxs, String name) {
        log.info("【人才培养 - 分页条件查询学员列表,批次id:{},学生类别名称:{},教育形式:{},学号/姓名:{}】",pcId,xslbmc,jyxs,name);
        PageHelper.startPage(pageNum, pageSize);
        List<StuDTO> stuDTOList = xsMapper.findXsList(pcId,xslbmc,jyxs,name);
        for (StuDTO stuDTO : stuDTOList) {
            List<Long> pcIds = pcXsMapper.selectByXsId(stuDTO.getId());
            stuDTO.setPcIds(pcIds);
        }
        return new PageInfo<>(stuDTOList);
    }

    /**
     * 查询教务管理列表
     * @param pageNum
     * @param pageSize
     * @param string
     * @param yx
     * @param pcId
     * @return
     */
    @Override
    public PageInfo<JwglDTO> findJwglByPage(Integer pageNum, Integer pageSize, String string, String yx, Long pcId) {
        log.info("【人才培养 - 分页条件查询教务管理列表,编号/姓名:{},院系:{},批次id:{}】",string,yx,pcId);
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(xsMapper.findJwglList(string, yx, pcId));
    }

    /**
     * 根据id查询学员信息
     * @param id
     * @return
     */
    @Override
    public StuDTO get(Long id) {
        log.info("【人才培养 - 根据id:{}查询学员信息】",id);
        QueryWrapper<Xs> xsQueryWrapper = new QueryWrapper<>();
        xsQueryWrapper.eq("sfsc",false)
                      .eq("id",id);
        Xs xs = xsMapper.selectOne(xsQueryWrapper);
        if (xs == null) {
            throw new ServiceException(STUDENT_MSG_NOT_EXISTS_ERROR);
        }
        StuDTO stuDTO = new StuDTO();
        BeanUtils.copyProperties(xs,stuDTO);
        List<Long> pcIds = pcXsMapper.selectByXsId(id);
        stuDTO.setPcIds(pcIds);
        return stuDTO;
    }
    /**
     * 根据xh查询学员信息
     * @param xh
     * @return
     */
    @Override
    public StuDTO getByXh(String xh) {
        log.info("【人才培养 - 根据学号:{}查询学员信息】",xh);
        QueryWrapper<Xs> xsQueryWrapper = new QueryWrapper<>();
        xsQueryWrapper.eq("sfsc",false)
                .eq("xh",xh);
        Xs xs = xsMapper.selectOne(xsQueryWrapper);
        if (xs == null) {
            throw new ServiceException(STUDENT_MSG_NOT_EXISTS_ERROR);
        }
        StuDTO stuDTO = new StuDTO();
        BeanUtils.copyProperties(xs,stuDTO);
        List<Long> pcIds = pcXsMapper.selectByXsId(xs.getId());
        stuDTO.setPcIds(pcIds);
        return stuDTO;
    }

    /**
     * 新增学员
     * @param stuDTO
     * @return
     */
    @Override
    public boolean add(StuDTO stuDTO) {
        log.info("【人才培养 - 新增学员:{}】",stuDTO);
        String jylx = stuDTO.getJylx();
        if (ACADEMIC_DEGREE_EDUCATION.getKey().equals(jylx)) {
            //学历学位教育(研究生)
        } else if (ADULT_EDUCATION.getKey().equals(jylx)) {
            //继续教育(经纪人)
        }
        if (xsMapper.selectById(stuDTO.getId()) != null) {
            return false;
        }
        Long userId = getUserId();
        stuDTO.setCjr(userId);
        stuDTO.setGxr(userId);
        Xs xs = new Xs();
        BeanUtils.copyProperties(stuDTO,xs);
        if (xsService.save(xs)) {
            List<Long> pcIds = stuDTO.getPcIds();
            if (pcIds != null && pcIds.size() > 0) {
                PcXs pcXs = new PcXs();
                pcXs.setPcId(stuDTO.getPcIds().get(0));
                pcXs.setXsId(xs.getId());
                return pcXsService.save(pcXs);
            }
        }
        return false;
    }

    /**
     * 更新学员
     * @param stuDTO
     * @return
     */
    @Override
    public boolean update(StuDTO stuDTO) {
        log.info("【人才培养 - 更新学员:{}】",stuDTO);
        Long userId = getUserId();
        stuDTO.setGxr(userId);
        Xs xs = new Xs();
        BeanUtils.copyProperties(stuDTO,xs);
        if (xsService.updateById(xs)) {
            List<Long> pcIds = stuDTO.getPcIds();
            if (pcIds != null && pcIds.size() > 0) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("xs_id",stuDTO.getId());
                List<PcXs> pcXsList = new ArrayList<>();
                if (pcXsService.removeByMap(map)) {
                    for (Long pcId : pcIds) {
                        PcXs pcXs = new PcXs();
                        pcXs.setPcId(pcId);
                        pcXs.setXsId(xs.getId());
                        pcXsList.add(pcXs);
                    }
                    return pcXsService.saveBatch(pcXsList);
                }
                return false;
            }
        }
        return false;
    }

    /**
     * 删除学员
     * @param stuDTO
     * @return
     */
    @Override
    public boolean delete(StuDTO stuDTO) {
        log.info("【人才培养 - 删除学员:{}】",stuDTO);
        //设置删除状态
        stuDTO.setSfsc(true);
        stuDTO.setGxr(getUserId());
        Xs xs = new Xs();
        BeanUtils.copyProperties(stuDTO,xs);
        if (xsService.updateById(xs)) {
            List<Long> pcIds = stuDTO.getPcIds();
            if (pcIds != null && pcIds.size() > 0) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("xs_id",stuDTO.getId());
                return pcXsService.removeByMap(map);
            }
        }
        return false;
    }

    /**
     * 根据条件查询学员信息
     * @param xh
     * @return
     */
    @Override
    public List<Xs> selectByCondition(String xh) {
        log.info("【人才培养 - 根据学号:{}查询学员信息】",xh);
        QueryWrapper<Xs> xsQueryWrapper = new QueryWrapper<>();
        xsQueryWrapper.eq("sfsc",false)
                .eq("xh",xh);
        return xsMapper.selectList(xsQueryWrapper);
    }

    @Override
    public Boolean addKcXs(Long id, Long kcId) {
        return xsMapper.addKcList(id, kcId);
    }

    /**
     * 获取当前用户id
     * @return
     */
    private Long getUserId() {
        LoginUser loginUser = threadLocal.get();
        Long userId;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        } else {
            throw new ServiceException(GET_THREADLOCAL_ERROR);
        }
        return userId;
    }
}
