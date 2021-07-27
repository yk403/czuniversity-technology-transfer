package com.itts.personTraining.service.kc.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.dto.KcDTO;
import com.itts.personTraining.dto.KcXsXfDTO;
import com.itts.personTraining.dto.KcbDTO;
import com.itts.personTraining.dto.XsMsgDTO;
import com.itts.personTraining.mapper.kcSz.KcSzMapper;
import com.itts.personTraining.mapper.pc.PcMapper;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.mapper.pk.PkMapper;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.mapper.kc.KcMapper;
import com.itts.personTraining.model.kcSz.KcSz;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.service.kc.KcService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.service.kcSz.KcSzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;

/**
 * <p>
 * 课程表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class KcServiceImpl extends ServiceImpl<KcMapper, Kc> implements KcService {

    @Resource
    private KcMapper kcMapper;
    @Autowired
    private KcService kcService;
    @Resource
    private KcSzMapper kcSzMapper;
    @Autowired
    private KcSzService kcSzService;
    @Autowired
    private XsMapper xsMapper;
    @Autowired
    private PcXsMapper pcXsMapper;
    @Autowired
    private PcMapper pcMapper;
    @Autowired
    private SzMapper szMapper;
    @Autowired
    private PkMapper pkMapper;

    /**
     * 分页条件查询课程列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<KcDTO> findByPage(Integer pageNum, Integer pageSize, String kclx, String name, String jylx, String xylx) {
        log.info("【人才培养 - 分页条件查询课程列表,课程类型:{},课程代码/名称:{},学院id:{}】",kclx,name,jylx,xylx);
        List<KcDTO> kcDTOs = null;
        if (pageNum == -1) {
            kcDTOs = kcMapper.findByPage(null,null,null,null);
            for (KcDTO kcDTO : kcDTOs) {
                QueryWrapper<KcSz> kcSzQueryWrapper = new QueryWrapper<>();
                kcSzQueryWrapper.eq("kc_id",kcDTO.getId());
                List<KcSz> kcSzList = kcSzMapper.selectList(kcSzQueryWrapper);
                List<Long> szIds = new ArrayList<>();
                for (KcSz kcSz : kcSzList) {
                    szIds.add(kcSz.getSzId());
                }
                kcDTO.setSzIds(szIds);
            }
        } else {
            PageHelper.startPage(pageNum, pageSize);
            String[] xylxArr = xylx.split(",");
            kcDTOs = kcMapper.findByPage(kclx,name,jylx,xylxArr);
            for (KcDTO kcDTO : kcDTOs) {
                QueryWrapper<KcSz> kcSzQueryWrapper = new QueryWrapper<>();
                kcSzQueryWrapper.eq("kc_id",kcDTO.getId());
                List<KcSz> kcSzList = kcSzMapper.selectList(kcSzQueryWrapper);
                List<Long> szIds = new ArrayList<>();
                for (KcSz kcSz : kcSzList) {
                    szIds.add(kcSz.getSzId());
                }
                kcDTO.setSzIds(szIds);
            }
        }

        return new PageInfo<>(kcDTOs);
    }

    /**
     * 根据id查询课程信息
     * @param id
     * @return
     */
    @Override
    public KcDTO get(Long id) {
        log.info("【人才培养 - 根据id:{}查询课程信息】",id);
        QueryWrapper<Kc> kcQueryWrapper = new QueryWrapper<>();
        kcQueryWrapper.eq("sfsc",false)
                      .eq("id",id);
        Kc kc = kcMapper.selectOne(kcQueryWrapper);
        KcDTO kcDTO = new KcDTO();
        BeanUtils.copyProperties(kc,kcDTO);
        QueryWrapper<KcSz> kcSzQueryWrapper = new QueryWrapper<>();
        kcSzQueryWrapper.eq("kc_id",kc.getId());
        List<KcSz> kcSzList = kcSzMapper.selectList(kcSzQueryWrapper);
        List<Long> szIds = new ArrayList<>();
        for (KcSz kcSz : kcSzList) {
            szIds.add(kcSz.getSzId());
        }
        kcDTO.setSzIds(szIds);
        return kcDTO;
    }

    /**
     * 课程批量下发
     * @param ids
     * @return
     */
    @Override
    public boolean issueBatch(List<Long> ids) {
        log.info("【人才培养 - 课程批量下发,ids:{}】",ids);
        List<Kc> kcs = kcMapper.selectBatchIds(ids);
        Long userId = getUserId();
        for (Kc kc : kcs) {
            kc.setGxr(userId);
            kc.setSfxf(true);
        }
        return kcService.updateBatchById(kcs);
    }

    /**
     * 删除课程
     * @param kcDTO
     * @return
     */
    @Override
    public boolean delete(KcDTO kcDTO) {
        log.info("【人才培养 - 删除课程:{}】", kcDTO);
        //设置删除状态
        kcDTO.setSfsc(true);
        kcDTO.setGxr(getUserId());
        Kc kc = new Kc();
        BeanUtils.copyProperties(kcDTO,kc);
        if (kcService.updateById(kc)) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("kc_id", kcDTO.getId());
            return kcSzService.removeByMap(map);
        }
        return false;
    }

    /**
     * 根据条件查询课程
     * @param xylx
     * @return
     */
    @Override
    public List<KcDTO> getByCondition(String xylx) {
        log.info("【人才培养 - 根据条件查询课程信息】");
        QueryWrapper<Kc> kcQueryWrapper = new QueryWrapper<>();
        kcQueryWrapper.eq("sfsc",false)
                      .eq(!StringUtils.isEmpty(xylx),"xylx",xylx);
        List<Kc> kcList = kcMapper.selectList(kcQueryWrapper);
        //将id放到kcId中,方便前端使用
        List<KcDTO> kcDTOs = new ArrayList<>();
        for (Kc kc : kcList) {
            KcDTO kcDTO = new KcDTO();
            BeanUtils.copyProperties(kc,kcDTO);
            kcDTO.setKcId(kc.getId());
            kcDTOs.add(kcDTO);
        }
        return kcDTOs;
    }

    /**
     * 新增课程
     * @param kcDTO
     * @return
     */
    @Override
    public boolean add(KcDTO kcDTO) {
        log.info("【人才培养 - 新增课程:{}】", kcDTO);
        Long userId = getUserId();
        Kc kc = new Kc();
        kcDTO.setCjr(userId);
        kcDTO.setGxr(userId);
        BeanUtils.copyProperties(kcDTO, kc);
        if (kcService.save(kc)) {
            return addKcSz(kcDTO, kc);
        }
        return false;
    }

    /**
     * 更新课程
     * @param kcDTO
     * @return
     */
    @Override
    public boolean update(KcDTO kcDTO) {
        log.info("【人才培养 - 更新课程:{}】",kcDTO);
        Kc kc = new Kc();
        kcDTO.setGxr(getUserId());
        BeanUtils.copyProperties(kcDTO,kc);
        if (kcService.updateById(kc)) {
            List<Long> szIds = kcDTO.getSzIds();
            if (szIds != null || szIds.size() > 0) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("kc_id",kcDTO.getId());
                if (kcSzService.removeByMap(map)) {
                    return addKcSz(kcDTO, kc);
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @Override
    public List<KcXsXfDTO> findByYh(Long pcId) {
        Long userId = getUserId();
        log.info("【人才培养 - 根据用户id:{}查询课程列表(前)】",userId);
        String userCategory = getUserCategory();
        List<KcXsXfDTO> kcXsXfDTOList =null;
        switch(userCategory) {
            case "postgraduate":
            case "broker":
                XsMsgDTO xsMsg = xsMapper.getByYhId(userId);
                if (xsMsg == null) {
                    throw new ServiceException(STUDENT_MSG_NOT_EXISTS_ERROR);
                }
                if (pcId == null) {
                    List<Pc>  pcList = pcXsMapper.findPcByXsId(xsMsg.getId());
                    if (CollectionUtils.isEmpty(pcList)) {
                        throw new ServiceException(BATCH_NUMBER_ISEMPTY_NO_MSG_ERROR);
                    }
                    String xylx = pcList.get(0).getXylx();
                    if(xylx!=null){
                        kcXsXfDTOList = getKcXsXfDTOList(xylx);
                    }else {
                        kcXsXfDTOList=null;
                    }
                } else {
                    Pc pcById = pcMapper.getPcById(pcId);
                    String xylx = pcById.getXylx();
                    kcXsXfDTOList = getKcXsXfDTOList(xylx);
                }
                break;
            case "tutor":
                Sz yzyds = szMapper.getSzByYhId(userId);
                if (yzyds == null) {
                    throw new ServiceException(TEACHER_MSG_NOT_EXISTS_ERROR);
                }
                if (pcId == null) {
                    List<Pc> pcList = pcXsMapper.findByYzydsIdOrQydsId(yzyds.getId(),null);
                    if (CollectionUtils.isEmpty(pcList)) {
                        throw new ServiceException(BATCH_NUMBER_ISEMPTY_NO_MSG_ERROR);
                    }
                    String xylx = pcList.get(0).getXylx();
                    if(xylx!=null){
                        kcXsXfDTOList = getKcXsXfDTOList(xylx);
                    }else {
                        kcXsXfDTOList=null;
                    }

                } else {
                    Pc pcById = pcMapper.getPcById(pcId);
                    String xylx = pcById.getXylx();
                    kcXsXfDTOList = getKcXsXfDTOList(xylx);
                }
                break;
            //企业导师
            case "corporate_mentor":
                Sz qyds = szMapper.getSzByYhId(userId);
                if (qyds == null) {
                    throw new ServiceException(TEACHER_MSG_NOT_EXISTS_ERROR);
                }
                if (pcId == null) {
                    List<Pc> pcList = pcXsMapper.findByYzydsIdOrQydsId(null,qyds.getId());
                    if (CollectionUtils.isEmpty(pcList)) {
                        throw new ServiceException(BATCH_NUMBER_ISEMPTY_NO_MSG_ERROR);
                    }
                    String xylx = pcList.get(0).getXylx();
                    if(xylx!=null){
                        kcXsXfDTOList = getKcXsXfDTOList(xylx);
                    }else {
                        kcXsXfDTOList=null;
                    }
                } else {
                    Pc pcById = pcMapper.getPcById(pcId);
                    String xylx = pcById.getXylx();
                    kcXsXfDTOList = getKcXsXfDTOList(xylx);
                }
                break;
            case "teacher":
                Sz skjs = szMapper.getSzByYhId(userId);
                if (skjs == null) {
                    throw new ServiceException(TEACHER_MSG_NOT_EXISTS_ERROR);
                }
                if (pcId == null) {
                    List<Pc> pcList = pkMapper.findPcsBySzId(skjs.getId());
                    if (CollectionUtils.isEmpty(pcList)) {
                        throw new ServiceException(BATCH_NUMBER_ISEMPTY_NO_MSG_ERROR);
                    }
                    String xylx = pcList.get(0).getXylx();
                    if(xylx!=null){
                        kcXsXfDTOList = getKcXsXfDTOList(xylx);
                    }else {
                        kcXsXfDTOList=null;
                    }
                } else {
                    Pc pcById = pcMapper.getPcById(pcId);
                    String xylx = pcById.getXylx();
                    kcXsXfDTOList = getKcXsXfDTOList(xylx);
                }
                break;
            case "school_leader":
            case "administrator":
                if (pcId == null) {
                    String xylx =null;
                    kcXsXfDTOList = getKcXsXfDTOList(xylx);
                } else {
                    Pc pcById = pcMapper.getPcById(pcId);
                    String xylx = pcById.getXylx();
                    kcXsXfDTOList = getKcXsXfDTOList(xylx);
                }
                break;
            default:
                break;
        }
        return kcXsXfDTOList;
    }

    @Override
    public List<KcbDTO> findByPcId(Long pcId) {
        Long userId = getUserId();
        log.info("【人才培养 - 根据用户id:{}查询课程表(前)】",userId);
        String userCategory = getUserCategory();
        List<KcbDTO> collect =null;
        switch(userCategory) {
            case "postgraduate":
            case "broker":
                XsMsgDTO xsMsg = xsMapper.getByYhId(userId);
                if (xsMsg == null) {
                    throw new ServiceException(STUDENT_MSG_NOT_EXISTS_ERROR);
                }
                if (pcId == null) {
                    List<Pc>  pcList = pcXsMapper.findPcByXsId(xsMsg.getId());
                    if (CollectionUtils.isEmpty(pcList)) {
                        throw new ServiceException(BATCH_NUMBER_ISEMPTY_NO_MSG_ERROR);
                    }
                    Long id = pcList.get(0).getId();
                    if(id!=null){
                        collect = kcMapper.findByPcId(id);
                    }else {
                        collect=null;
                    }
                } else {
                    collect = kcMapper.findByPcId(pcId);
                }
                break;
            case "tutor":
                Sz yzyds = szMapper.getSzByYhId(userId);
                if (yzyds == null) {
                    throw new ServiceException(TEACHER_MSG_NOT_EXISTS_ERROR);
                }
                if (pcId == null) {
                    List<Pc> pcList = pcXsMapper.findByYzydsIdOrQydsId(yzyds.getId(),null);
                    if (CollectionUtils.isEmpty(pcList)) {
                        throw new ServiceException(BATCH_NUMBER_ISEMPTY_NO_MSG_ERROR);
                    }
                    Long id = pcList.get(0).getId();
                    if(id!=null){
                        collect = kcMapper.findByPcId(id);
                    }else {
                        collect=null;
                    }
                } else {
                    collect = kcMapper.findByPcId(pcId);
                }
                break;
            //企业导师
            case "corporate_mentor":
                Sz qyds = szMapper.getSzByYhId(userId);
                if (qyds == null) {
                    throw new ServiceException(TEACHER_MSG_NOT_EXISTS_ERROR);
                }
                if (pcId == null) {
                    List<Pc> pcList = pcXsMapper.findByYzydsIdOrQydsId(null,qyds.getId());
                    if (CollectionUtils.isEmpty(pcList)) {
                        throw new ServiceException(BATCH_NUMBER_ISEMPTY_NO_MSG_ERROR);
                    }
                    Long id = pcList.get(0).getId();
                    if(id!=null){
                        collect = kcMapper.findByPcId(id);
                    }else {
                        collect=null;
                    }
                } else {
                    collect = kcMapper.findByPcId(pcId);
                }
                break;
            case "teacher":
                Sz skjs = szMapper.getSzByYhId(userId);
                if (skjs == null) {
                    throw new ServiceException(TEACHER_MSG_NOT_EXISTS_ERROR);
                }
                if (pcId == null) {
                    List<Pc> pcList = pkMapper.findPcsBySzId(skjs.getId());
                    if (CollectionUtils.isEmpty(pcList)) {
                        throw new ServiceException(BATCH_NUMBER_ISEMPTY_NO_MSG_ERROR);
                    }
                    Long id = pcList.get(0).getId();
                    if(id!=null){
                        collect = kcMapper.findByPcId(id);
                    }else {
                        collect=null;
                    }
                } else {
                    collect = kcMapper.findByPcId(pcId);
                }
                break;
            case "school_leader":
            case "administrator":
                if (pcId == null) {
                    QueryWrapper<Pc> pcQueryWrapper = new QueryWrapper<>();
                    pcQueryWrapper.eq("sfsc",false)
                            .orderByDesc("cjsj");
                    Long id = pcMapper.selectList(pcQueryWrapper).get(0).getId();
                    collect = kcMapper.findByPcId(id);
                } else {
                    collect = kcMapper.findByPcId(pcId);
                }
                break;
            default:
                break;
        }
        return collect;
    }

    @Override
    public List<KcDTO> findByXylx(String xylx) {
        log.info("【人才培养 - 根据学员类型:{}查询课程列表】",xylx);
        String[] xylxArr = xylx.split(",");
        List<KcDTO> kcDTOs = kcMapper.findByPage(null,null,null,xylxArr);
        for (KcDTO kcDTO : kcDTOs) {
            QueryWrapper<KcSz> kcSzQueryWrapper = new QueryWrapper<>();
            kcSzQueryWrapper.eq("kc_id",kcDTO.getId());
            List<KcSz> kcSzList = kcSzMapper.selectList(kcSzQueryWrapper);
            List<Long> szIds = new ArrayList<>();
            for (KcSz kcSz : kcSzList) {
                szIds.add(kcSz.getSzId());
            }
            kcDTO.setSzIds(szIds);
        }
        return kcDTOs;
    }


    /**
     * 新增课程师资关系
     * @param kcDTO
     * @param kc
     * @return
     */
    private boolean addKcSz(KcDTO kcDTO, Kc kc) {
        List<KcSz> kcSzList = new ArrayList<>();
        List<Long> szIds = kcDTO.getSzIds();
        for (Long szId : szIds) {
            KcSz kcSz = new KcSz();
            kcSz.setSzId(szId);
            kcSz.setKcId(kc.getId());
            kcSzList.add(kcSz);
        }
        return kcSzService.saveBatch(kcSzList);
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
    /**
     * 获取当前用户id所属类别
     * @return
     */
    private String getUserCategory() {
        LoginUser loginUser = threadLocal.get();
        String userCategory;
        if (loginUser != null) {
            userCategory = loginUser.getUserCategory();
        } else {
            throw new ServiceException(GET_THREADLOCAL_ERROR);
        }
        return userCategory;
    }

    /**
     * 获取课程学时列表
     * @param xylx
     * @return
     */
    private List<KcXsXfDTO> getKcXsXfDTOList(String xylx){
        List<KcXsXfDTO> kcXsXfDTOList = kcMapper.findByXylx(xylx);
        List<KcXsXfDTO> pkByXylx = kcMapper.findPkByXylx(xylx);
        for (int i = 0; i < kcXsXfDTOList.size(); i++) {
            KcXsXfDTO kcXsXfDTO = kcXsXfDTOList.get(i);
            Long id = kcXsXfDTO.getId();
            for (int i1 = 0; i1 < pkByXylx.size(); i1++) {
                KcXsXfDTO kcXsXfDTO1 = pkByXylx.get(i1);
                if(id == kcXsXfDTO1.getId()){
                    kcXsXfDTO.setQsz(kcXsXfDTO1.getQsz());
                    kcXsXfDTO.setJsz(kcXsXfDTO1.getJsz());
                    kcXsXfDTO.setXqs(kcXsXfDTO1.getXqs());
                }
            }
        }
        return kcXsXfDTOList;
    }
}
