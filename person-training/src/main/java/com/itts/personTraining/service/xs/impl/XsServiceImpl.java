package com.itts.personTraining.service.xs.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.DateUtils;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.JwglDTO;
import com.itts.personTraining.dto.StuDTO;
import com.itts.personTraining.enums.UserTypeEnum;
import com.itts.personTraining.mapper.pc.PcMapper;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.pcXs.PcXs;
import com.itts.personTraining.model.xs.Xs;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.model.yh.Yh;
import com.itts.personTraining.service.pc.PcService;
import com.itts.personTraining.service.pcXs.PcXsService;
import com.itts.personTraining.service.xs.XsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.service.yh.YhService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PrimitiveIterator;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;
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
    @Autowired
    private YhService yhService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private PcMapper pcMapper;

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
     * 根据xh或lxdh查询学员信息
     * @param xh
     * @return
     */
    @Override
    public StuDTO selectByXhOrLxdh(String xh,String lxdh) {
        log.info("【人才培养 - 根据学号:{},;联系电话:{}查询学员信息】",xh,lxdh);
        QueryWrapper<Xs> xsQueryWrapper = new QueryWrapper<>();
        xsQueryWrapper.eq("sfsc",false)
                .eq(StringUtils.isNotBlank(xh),"xh",xh)
                .eq(StringUtils.isNotBlank(lxdh),"lxdh",lxdh);
        Xs xs = xsMapper.selectOne(xsQueryWrapper);
        if (xs == null) {
            return null;
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
    public boolean add(StuDTO stuDTO,String token) {
        log.info("【人才培养 - 新增学员:{}】",stuDTO);
        Long userId = getUserId();
        stuDTO.setCjr(userId);
        stuDTO.setGxr(userId);
        String jylx = stuDTO.getJylx();
        StuDTO byXh = xsService.selectByXhOrLxdh(stuDTO.getXh(),stuDTO.getLxdh());
        if (ACADEMIC_DEGREE_EDUCATION.getKey().equals(jylx)) {
            //学历学位教育(研究生)
            if (byXh != null) {
                stuDTO.setId(byXh.getId());
                return updateXsAndAddPcXs(stuDTO);
            } else {
                //调用户服务新增并获取到用户id然后新增到学生表
                String xh = stuDTO.getXh();
                Yh yh = new Yh();
                yh.setYhbh(xh);
                yh.setYhm(xh);
                yh.setMm(xh);
                yh.setZsxm(stuDTO.getXm());
                yh.setYhlx("in");
                yh.setYhlb(UserTypeEnum.POSTGRADUATE.getKey());
                ResponseUtil util = yhService.add(yh,token);
                Yh yh1 = JSONObject.parseObject(JSON.toJSON(util.getData()).toString(), Yh.class);
                stuDTO.setYhId(yh1.getId());
                return updateXsAndAddPcXs(stuDTO);
            }
        } else if (ADULT_EDUCATION.getKey().equals(jylx)) {
            //继续教育(经纪人)
            String phone = stuDTO.getLxdh();
            if (phone != null) {
                StuDTO stuDTO1 = xsService.selectByXhOrLxdh(null, phone);
                if (stuDTO1 != null) {
                    //存在
                    ResponseUtil util = yhService.getByPhone(phone, token);
                    Yh yh1 = JSONObject.parseObject(JSON.toJSON(util.getData()).toString(), Yh.class);
                    stuDTO.setId(stuDTO1.getId());
                    QueryWrapper<Pc> pcQueryWrapper = new QueryWrapper<>();
                    pcQueryWrapper.eq("sfsc",false)
                                  .eq("id",stuDTO.getPcIds().get(0));
                    Pc pc = pcMapper.selectOne(pcQueryWrapper);
                    String bh = redisTemplate.opsForValue().increment(pc.getPch()).toString();
                    String xh = pc.getJylx() + StringUtils.replace(DateUtils.toString(pc.getRxrq()),"/","") + String.format("%03d", Long.parseLong(bh));
                    stuDTO.setXh(xh);
                    yh1.setYhbh(xh);
                    yh1.setZsxm(stuDTO.getXm());
                    yh1.setJgId(stuDTO.getJgId());
                    yhService.update(yh1);
                    return updateXsAndAddPcXs(stuDTO);
                } else {
                    //不存在

                }
            } else {
                throw new ServiceException(PHONE_NUMBER_ISEMPTY_ERROR);
            }
        } else {
            throw new ServiceException(EDU_TYPE_ERROR);
        }
        return false;
    }



    /**
     * 新增学员(外部调用)
     * @param stuDTO
     * @return
     */
    @Override
    public boolean addUser(StuDTO stuDTO) {
        log.info("【人才培养 - 新增学员(外部调用):{}】",stuDTO);
        Long userId = getUserId();
        stuDTO.setCjr(userId);
        stuDTO.setGxr(userId);
        Xs xs = new Xs();
        BeanUtils.copyProperties(stuDTO,xs);
        return xsService.save(xs);
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
                .eq(StringUtils.isNotBlank(xh),"xh",xh);
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

    private boolean updateXsAndAddPcXs(StuDTO stuDTO) {
        Xs xs = new Xs();
        BeanUtils.copyProperties(stuDTO,xs);
        if (xsService.updateById(xs)) {
            Long pcId = stuDTO.getPcIds().get(0);
            if (pcId != null) {
                PcXs pcXs = new PcXs();
                pcXs.setXsId(xs.getId());
                pcXs.setPcId(pcId);
                return pcXsService.save(pcXs);
            }
        }
        return false;
    }
}
