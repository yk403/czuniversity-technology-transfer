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
import com.itts.personTraining.mapper.pyjh.PyJhMapper;
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
import java.util.*;
import java.util.stream.Collectors;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.personTraining.enums.JdlxEnum.HEADQUARTERS;

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
    @Resource
    private PyJhMapper pyJhMapper;

    /**
     * 分页条件查询课程列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<KcDTO> findByPage(Integer pageNum, Integer pageSize, String kclx, String name, String jylx, String xylx, Long fjjgId, String userType) {
        log.info("【人才培养 - 分页条件查询课程列表,课程类型:{},课程代码/名称:{},学院ID:{},父级机构ID:{},用户类型:{}】",kclx,name,jylx,xylx,fjjgId,userType);
        List<KcDTO> kcDTOs = null;
        if (HEADQUARTERS.getKey().equals(userType)) {
            if (pageNum == -1) {
                String[] xylxArr = null;
                if (xylx != null&& !xylx.equals("")) {
                    xylxArr = xylx.split(",");
                }
                kcDTOs = kcMapper.findByPage(null,null,null,xylxArr,null);
            } else {
                PageHelper.startPage(pageNum, pageSize);
                String[] xylxArr = null;
                if (xylx != null&& !xylx.equals("")) {
                    xylxArr = xylx.split(",");
                }
                kcDTOs = kcMapper.findByPage(kclx,name,jylx,xylxArr,fjjgId);
            }
        } else {
            if (pageNum == -1) {
                String[] xylxArr = null;
                if (xylx != null&& !xylx.equals("")) {
                    xylxArr = xylx.split(",");
                }
                kcDTOs = kcMapper.findByPage(null,null,null,xylxArr,fjjgId);
            } else {
                PageHelper.startPage(pageNum, pageSize);
                String[] xylxArr = null;
                if (xylx != null&& !xylx.equals("")) {
                    xylxArr = xylx.split(",");
                }
                kcDTOs = kcMapper.findByPage(kclx,name,jylx,xylxArr,fjjgId);
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
        /*QueryWrapper<KcSz> kcSzQueryWrapper = new QueryWrapper<>();
        kcSzQueryWrapper.eq("kc_id",kc.getId());
        List<KcSz> kcSzList = kcSzMapper.selectList(kcSzQueryWrapper);
        List<Long> szIds = new ArrayList<>();
        for (KcSz kcSz : kcSzList) {
            szIds.add(kcSz.getSzId());
        }
        kcDTO.setSzIds(szIds);*/
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
        kcService.updateById(kc);
        return true;
    }

    /**
     * 根据条件查询课程
     * @param xylx
     * @return
     */
    @Override
    public List<KcDTO> getByCondition(String xylx, Long fjjgId) {
        log.info("【人才培养 - 根据条件查询课程信息】");
        QueryWrapper<Kc> kcQueryWrapper = new QueryWrapper<>();
        kcQueryWrapper.eq("sfsc",false)
                      .eq("fjjg_id",fjjgId)
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
        /*if () {
            return addKcSz(kcDTO, kc);
        }*/
        return kcService.save(kc);
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
        /*if () {
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
        }*/
        return kcService.updateById(kc);
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
                        kcXsXfDTOList = getKcXsXfDTOList(xylx,pcList.get(0).getId());
                    }else {
                        kcXsXfDTOList=null;
                    }
                } else {
                    Pc pcById = pcMapper.getPcById(pcId);
                    String xylx = pcById.getXylx();
                    kcXsXfDTOList = getKcXsXfDTOList(xylx,pcId);
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
                        kcXsXfDTOList = getKcXsXfDTOList(xylx,pcList.get(0).getId());
                    }else {
                        kcXsXfDTOList=null;
                    }

                } else {
                    Pc pcById = pcMapper.getPcById(pcId);
                    String xylx = pcById.getXylx();
                    kcXsXfDTOList = getKcXsXfDTOList(xylx,pcId);
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
                        kcXsXfDTOList = getKcXsXfDTOList(xylx,pcList.get(0).getId());
                    }else {
                        kcXsXfDTOList=null;
                    }
                } else {
                    Pc pcById = pcMapper.getPcById(pcId);
                    String xylx = pcById.getXylx();
                    kcXsXfDTOList = getKcXsXfDTOList(xylx,pcId);
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
                        kcXsXfDTOList = getKcXsXfDTOList(xylx,pcList.get(0).getId());
                    }else {
                        kcXsXfDTOList=null;
                    }
                } else {
                    Pc pcById = pcMapper.getPcById(pcId);
                    String xylx = pcById.getXylx();
                    kcXsXfDTOList = getKcXsXfDTOList(xylx,pcId);
                }
                break;
            case "school_leader":
            case "administrator":
                if (pcId == null) {
                    List<Pc> pcList = pyJhMapper.findAllPc(getFjjgId());
                    Pc pc = pcList.get(0);
                    String xylx =pc.getXylx();
                    Long id = pc.getId();
                    kcXsXfDTOList = getKcXsXfDTOList(xylx,id);
                } else {
                    Pc pcById = pcMapper.getPcById(pcId);
                    String xylx = pcById.getXylx();
                    kcXsXfDTOList = getKcXsXfDTOList(xylx,pcId);
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
            case "broker":
                XsMsgDTO xs = xsMapper.getByYhId(userId);
                if (xs == null) {
                    throw new ServiceException(STUDENT_MSG_NOT_EXISTS_ERROR);
                }
                if (pcId == null) {
                    List<Pc>  pcList = pcXsMapper.findPcByXsId(xs.getId());
                    if (CollectionUtils.isEmpty(pcList)) {
                        throw new ServiceException(BATCH_NUMBER_ISEMPTY_NO_MSG_ERROR);
                    }
                    Long id = pcList.get(0).getId();
                    if(id!=null){
                        collect = kcMapper.findPcId(id);
                    }else {
                        collect=null;
                    }
                } else {
                    collect = kcMapper.findPcId(pcId);
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
                    Pc pc = pcList.get(0);
                    collect = findKcb(pc);

                } else {
                    collect = findKcbByPcId(pcId);
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
                    Pc pc = pcList.get(0);
                    collect = findKcb(pc);
                } else {
                    collect = findKcbByPcId(pcId);
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
                    Pc pc = pcList.get(0);
                    collect = findKcb(pc);
                } else {
                    collect = findKcbByPcId(pcId);
                }
                break;
            case "school_leader":
            case "administrator":
                if (pcId == null) {
                    List<Pc> pcList = pyJhMapper.findAllPc(getFjjgId());
                    Pc pc = pcList.get(0);
                    collect = findKcb(pc);
                } else {
                    collect = findKcbByPcId(pcId);
                }
                break;
            default:
                break;
        }
        return collect;
    }
    private List<KcbDTO> findKcb(Pc pc){
        List<KcbDTO> collect = null;
        if(pc == null){
            return collect;
        }else {
            Long id = pc.getId();
            if(Objects.equals(pc.getJylx(),"A")){
                collect = kcMapper.findByPcId(id);
            }else if(Objects.equals(pc.getJylx(),"C")){
                collect = kcMapper.findPcId(id);
            }
            return collect;
        }

    }

    private List<KcbDTO> findKcbByPcId(Long pcId){
        List<KcbDTO> collect = null;
        Pc pcById = pcMapper.getPcById(pcId);
        if(Objects.equals(pcById.getJylx(),"A")){
            collect = kcMapper.findByPcId(pcId);
        }else if(Objects.equals(pcById.getJylx(),"C")){
            collect = kcMapper.findPcId(pcId);
        }
        return collect;
    }

    @Override
    public List<KcDTO> findByXylx(String xylx) {
        log.info("【人才培养 - 根据学员类型:{}查询课程列表】",xylx);
        String[] xylxArr = xylx.split(",");
        LoginUser loginUser = threadLocal.get();
        Long fjjgId = loginUser.getFjjgId();
        List<KcDTO> kcDTOs = kcMapper.findByPage(null,null,null,xylxArr,fjjgId);
        /*for (KcDTO kcDTO : kcDTOs) {
            QueryWrapper<KcSz> kcSzQueryWrapper = new QueryWrapper<>();
            kcSzQueryWrapper.eq("kc_id",kcDTO.getId());
            List<KcSz> kcSzList = kcSzMapper.selectList(kcSzQueryWrapper);
            List<Long> szIds = new ArrayList<>();
            for (KcSz kcSz : kcSzList) {
                szIds.add(kcSz.getSzId());
            }
            kcDTO.setSzIds(szIds);
        }*/
        return kcDTOs;
    }


    /**
     * 新增课程师资关系
     * @param kcDTO
     * @param kc
     * @return
     */
    /*private boolean addKcSz(KcDTO kcDTO, Kc kc) {
        List<KcSz> kcSzList = new ArrayList<>();
        List<Long> szIds = kcDTO.getSzIds();
        for (Long szId : szIds) {
            KcSz kcSz = new KcSz();
            kcSz.setSzId(szId);
            kcSz.setKcId(kc.getId());
            kcSzList.add(kcSz);
        }
        return kcSzService.saveBatch(kcSzList);
    }*/

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
    private List<KcXsXfDTO> getKcXsXfDTOList(String xylx,Long pcId){
        Pc pc = pcMapper.selectOne(new QueryWrapper<Pc>().eq("sfsc", false).eq("id", pcId));
        Long fjjgId = pc.getFjjgId();
        List<KcXsXfDTO> kcXsXfDTOList = kcMapper.findByXylx(xylx,fjjgId);

        List<KcXsXfDTO> pkByXylx= kcMapper.findPkByXylx(xylx,pcId);
        for (int i = 0; i < kcXsXfDTOList.size(); i++) {
            KcXsXfDTO kcXsXfDTO = kcXsXfDTOList.get(i);
            Long id = kcXsXfDTO.getId();

            if (id != null) {
                for (int i1 = 0; i1 < pkByXylx.size(); i1++) {
                    KcXsXfDTO kcXsXfDTO1 = pkByXylx.get(i1);
                    if(id == kcXsXfDTO1.getId()){
                        if(kcXsXfDTO.getQsz()!=null){
                            if(kcXsXfDTO.getQsz()>kcXsXfDTO1.getQsz()){
                                kcXsXfDTO.setQsz(kcXsXfDTO1.getQsz());
                            }
                        }else {
                            kcXsXfDTO.setQsz(kcXsXfDTO1.getQsz());
                        }
                        if(kcXsXfDTO.getJsz() !=null){
                            if(kcXsXfDTO.getJsz()<kcXsXfDTO1.getJsz()){
                                kcXsXfDTO.setJsz(kcXsXfDTO1.getJsz());
                            }
                        }else {
                            kcXsXfDTO.setJsz(kcXsXfDTO1.getJsz());
                        }

                        if(kcXsXfDTO.getXqs() == null){
                            kcXsXfDTO.setXqs(kcXsXfDTO1.getXqs());
                        }else {
                            kcXsXfDTO.setXqs(kcXsXfDTO.getXqs()+"/"+kcXsXfDTO1.getXqs());
                        }
                        if(kcXsXfDTO.getSkszmc() == null){
                            kcXsXfDTO.setSkszmc(kcXsXfDTO1.getSkszmc());
                        }else {
                            kcXsXfDTO.setSkszmc(kcXsXfDTO.getSkszmc()+"/"+kcXsXfDTO1.getSkszmc());
                        }

                    }
                }
            }

        }
        return kcXsXfDTOList;
    }

    /**
     * 获取父级机构ID
     */
    public Long getFjjgId() {
        LoginUser loginUser = threadLocal.get();
        Long fjjgId;
        if (loginUser != null) {
            fjjgId = loginUser.getFjjgId();
        } else {
            throw new ServiceException(GET_THREADLOCAL_ERROR);
        }
        return fjjgId;
    }

}
