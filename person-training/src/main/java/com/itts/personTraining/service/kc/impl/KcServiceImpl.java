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
import com.itts.personTraining.enums.EduTypeEnum;
import com.itts.personTraining.mapper.kcSz.KcSzMapper;
import com.itts.personTraining.mapper.pc.PcMapper;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.mapper.pk.PkMapper;
import com.itts.personTraining.mapper.pyjh.PyJhMapper;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.mapper.xxjs.XxjsMapper;
import com.itts.personTraining.mapper.xxjxl.XxjxlMapper;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.mapper.kc.KcMapper;
import com.itts.personTraining.model.kcSz.KcSz;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.model.xxjs.Xxjs;
import com.itts.personTraining.model.xxjxl.Xxjxl;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.personTraining.enums.JdlxEnum.HEADQUARTERS;

/**
 * <p>
 * ????????? ???????????????
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

    @Resource
    private XxjsMapper xxjsMapper;
    @Resource
    private XxjxlMapper xxjxlMapper;

    /**
     * ??????????????????????????????
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<KcDTO> findByPage(Integer pageNum, Integer pageSize, String kclx, String name, String jylx, String xylx, Long fjjgId, String userType) {
        log.info("??????????????? - ??????????????????????????????,????????????:{},????????????/??????:{},??????ID:{},????????????ID:{},????????????:{}???",kclx,name,jylx,xylx,fjjgId,userType);
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
     * ??????id??????????????????
     * @param id
     * @return
     */
    @Override
    public KcDTO get(Long id) {
        log.info("??????????????? - ??????id:{}?????????????????????",id);
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
     * ??????????????????
     * @param ids
     * @return
     */
    @Override
    public boolean issueBatch(List<Long> ids) {
        log.info("??????????????? - ??????????????????,ids:{}???",ids);
        List<Kc> kcs = kcMapper.selectBatchIds(ids);
        Long userId = getUserId();
        for (Kc kc : kcs) {
            kc.setGxr(userId);
            kc.setSfxf(true);
        }
        return kcService.updateBatchById(kcs);
    }

    /**
     * ????????????
     * @param kcDTO
     * @return
     */
    @Override
    public boolean delete(KcDTO kcDTO) {
        log.info("??????????????? - ????????????:{}???", kcDTO);
        //??????????????????
        kcDTO.setSfsc(true);
        kcDTO.setGxr(getUserId());
        Kc kc = new Kc();
        BeanUtils.copyProperties(kcDTO,kc);
        kcService.updateById(kc);
        return true;
    }

    /**
     * ????????????????????????
     * @param xylx
     * @return
     */
    @Override
    public List<KcDTO> getByCondition(String xylx, Long fjjgId) {
        log.info("??????????????? - ?????????????????????????????????");
        QueryWrapper<Kc> kcQueryWrapper = new QueryWrapper<>();
        kcQueryWrapper.eq("sfsc",false)
                      .eq("fjjg_id",fjjgId)
                      .eq(!StringUtils.isEmpty(xylx),"xylx",xylx);
        List<Kc> kcList = kcMapper.selectList(kcQueryWrapper);
        //???id??????kcId???,??????????????????
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
     * ????????????
     * @param kcDTO
     * @return
     */
    @Override
    public boolean add(KcDTO kcDTO) {
        log.info("??????????????? - ????????????:{}???", kcDTO);
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
     * ????????????
     * @param kcDTO
     * @return
     */
    @Override
    public boolean update(KcDTO kcDTO) {
        log.info("??????????????? - ????????????:{}???",kcDTO);
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
        log.info("??????????????? - ????????????id:{}??????????????????(???)???",userId);
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
            //????????????
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
        log.info("??????????????? - ????????????id:{}???????????????(???)???",userId);
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
            //????????????
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
    public List<KcDTO> findByXylx(String xylx, String jylx) {
        log.info("??????????????? - ??????????????????:{},????????????:{}?????????????????????",jylx,xylx);

        LoginUser loginUser = threadLocal.get();
        Long fjjgId = loginUser.getFjjgId();
        if(!StringUtils.isEmpty(xylx)){
            String[] xylxArr = xylx.split(",");
            List<KcDTO> kcDTOs = kcMapper.findByPage(null,null,jylx,xylxArr,fjjgId);
            return kcDTOs;
        }
        List<KcDTO> kcDTOs = kcMapper.findByPage(null,null,jylx,null,fjjgId);
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
     * ????????????????????????
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
     * ??????????????????id
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
     * ??????????????????id????????????
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
     * ????????????????????????
     * @param xylx
     * @return
     */
    private List<KcXsXfDTO> getKcXsXfDTOList(String xylx,Long pcId){
        Pc pc = pcMapper.selectOne(new QueryWrapper<Pc>().eq("sfsc", false).eq("id", pcId));
        String jylx = pc.getJylx();
        Date rxrq = pc.getRxrq();

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
                        //????????????
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
                        //????????????
                        if(Objects.equals(jylx, EduTypeEnum.ACADEMIC_DEGREE_EDUCATION.getKey())){
                            Xxjs xxjs = xxjsMapper.selectOne(new QueryWrapper<Xxjs>().eq("id", kcXsXfDTO1.getXxjsId())
                                    .eq("sfsc", false));
                            if(xxjs != null){
                                Xxjxl xxjxl = xxjxlMapper.selectOne(new QueryWrapper<Xxjxl>().eq("id", xxjs.getXxjxlId())
                                        .eq("sfsc", false));
                                if(xxjxl != null){
                                    if(kcXsXfDTO.getSkdd() != null){
                                        kcXsXfDTO.setSkdd(kcXsXfDTO.getSkdd() + "/" + xxjxl.getJxlmc() + xxjs.getJsbh());
                                    }else {
                                        kcXsXfDTO.setSkdd(xxjxl.getJxlmc() + xxjs.getJsbh());
                                    }
                                }
                            }
                        }else if(Objects.equals(jylx,EduTypeEnum.ADULT_EDUCATION.getKey())){
                            kcXsXfDTO.setSkdd(kcXsXfDTO1.getSkdd());
                            //???????????????????????????
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String s = sdf.format(rxrq);
                            try {
                                rxrq =  sdf.parse(s);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            kcXsXfDTO.setKkrq(rxrq);

                            Calendar instance = Calendar.getInstance();
                            instance.setTime(rxrq);
                            instance.add(Calendar.DAY_OF_YEAR,kcXsXfDTO.getJsz() * 7);
                            Date time = instance.getTime();
                            String format = sdf.format(time);
                            try {
                                time = sdf.parse(format);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            kcXsXfDTO.setJkrq(time);
                        }

                        if(kcXsXfDTO.getXqs() == null){
                            kcXsXfDTO.setXqs(kcXsXfDTO1.getXqs());
                        }else {
                            kcXsXfDTO.setXqs(kcXsXfDTO.getXqs()+"/"+kcXsXfDTO1.getXqs());
                        }
                        if(kcXsXfDTO.getSkszmc() == null){
                            kcXsXfDTO.setSkszmc(kcXsXfDTO1.getSkszmc());
                        }else {
                            String skszmc = kcXsXfDTO.getSkszmc();
                            String[] split = skszmc.split("/");
                            int n = 0;
                            for (String s : split) {
                                if(Objects.equals(s,kcXsXfDTO1.getSkszmc())){
                                    n++;
                                }
                            }
                            if(n == 0){
                                kcXsXfDTO.setSkszmc(kcXsXfDTO.getSkszmc()+"/"+kcXsXfDTO1.getSkszmc());
                            }

                        }

                    }
                }
            }

        }
        return kcXsXfDTOList;
    }

    /**
     * ??????????????????ID
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
