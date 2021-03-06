package com.itts.userservice.controller.cd;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.RedisConstant;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.enums.SystemTypeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.dto.GetCdAndCzDTO;
import com.itts.userservice.enmus.CdEnum;
import com.itts.userservice.enmus.JgTpyeEnum;
import com.itts.userservice.enmus.LmglEnum;
import com.itts.userservice.enmus.UserTypeEnum;
import com.itts.userservice.feign.persontraining.lmgl.LmglRpcService;
import com.itts.userservice.model.cd.Cd;
import com.itts.userservice.model.cz.Cz;
import com.itts.userservice.model.js.Js;
import com.itts.userservice.model.lmgl.Lmgl;
import com.itts.userservice.request.AddCdRequest;
import com.itts.userservice.service.cd.CdService;
import com.itts.userservice.service.js.JsService;
import com.itts.userservice.service.yh.YhService;
import com.itts.userservice.vo.CdTreeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.itts.common.constant.SystemConstant.threadLocal;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author fl
 * @since 2021-03-24
 */
@Api(tags = "菜单控制")
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/v1/cd")
public class CdController {

    @Resource
    private CdService cdService;

    @Autowired
    private JsService jsService;

    @Autowired
    private YhService yhService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private LmglRpcService lmglRpcService;

    /**
     * 获取列表
     */
    @ApiOperation(value = "获取列表")
    @GetMapping("/list/")
    public ResponseUtil find(@ApiParam(value = "当前页数") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam(value = "菜单名称") @RequestParam(value = "name", required = false) String name,
                             @ApiParam(value = "系统类型") @RequestParam(value = "systemType", required = false) String systemType,
                             @ApiParam(value = "模块类型") @RequestParam(value = "modelType", required = false) String modelType) {

        PageInfo<GetCdAndCzDTO> pageInfo = cdService.findByPage(pageNum, pageSize, name, systemType, modelType);
        return ResponseUtil.success(pageInfo);
    }

    /**
     * 通过ID获取当前菜单及其子菜单（树形）
     */
    @GetMapping("/tree/")
    @ApiOperation(value = "通过ID获取当前菜单及其子菜单（树形）")
    public ResponseUtil findByTree(@ApiParam(value = "菜单ID(可不填写，默认查询所有)") @RequestParam(value = "id", required = false) Long id,
                                   @ApiParam(value = "类型(不填写查询所有)：in - 内部系统；out - 外部系统") @RequestParam(value = "type", required = false) String systemType,
                                   @RequestParam(value = "jgId") Long jgId,
                                   @RequestParam(value = "jglx", required = false) String jglx) {

        List<Cd> cds = Lists.newArrayList();

        if (StringUtils.isNotBlank(systemType)) {

            if (Objects.equals(systemType, UserTypeEnum.IN_USER.getCode())) {

                systemType = SystemTypeEnum.TALENT_TRAINING.getKey();
            } else if (Objects.equals(systemType, UserTypeEnum.OUT_USER.getCode())) {

                systemType = null;
            }
        }

        if (id == null) {

            cds = cdService.findByParentId(0L, systemType);
        } else {

            Cd cd = cdService.getById(id);
            cds.add(cd);
        }

        if (!CollectionUtils.isEmpty(cds)) {
            //分基地菜单树
            if(Objects.equals(jglx, JgTpyeEnum.BRANCH.getKey())){
                //人才培养模块
                ResponseUtil one = lmglRpcService.getOne(jgId, LmglEnum.TALENT_TRAINING.getKey());
                if(one == null || one.getErrCode().intValue() != 0){
                    throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
                }
                Lmgl lmgl1 = one.conversionData(new TypeReference<Lmgl>() {});
                Boolean sfsy1 = lmgl1.getSfsy();
                //技术交易
                ResponseUtil two = lmglRpcService.getOne(jgId, LmglEnum.TALENT_TRAINING.getKey());
                if(two == null || two.getErrCode().intValue() != 0){
                    throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
                }
                Lmgl lmgl2 = two.conversionData(new TypeReference<Lmgl>() {});
                Boolean sfsy2 = lmgl2.getSfsy();

                Iterator<Cd> iterator = cds.iterator();

                while (iterator.hasNext()){
                    Cd next = iterator.next();
                    //删除
                    if(Objects.equals(next.getCdmc(), CdEnum.DMGL.getMsg())){
                        iterator.remove();
                    }
                    if(!sfsy1){
                        if(Objects.equals(next.getCdmc(),CdEnum.XXGL.getMsg()) || Objects.equals(next.getCdmc(),CdEnum.JSZB.getMsg()) || Objects.equals(next.getCdmc(),CdEnum.JSPM.getMsg()) || Objects.equals(next.getCdmc(),CdEnum.JSGP.getMsg()) || Objects.equals(next.getCdmc(),CdEnum.SCLY.getMsg())){
                            iterator.remove();
                        }
                    }
                    if(!sfsy2){
                        if(Objects.equals(next.getCdmc(),CdEnum.ZHGL.getMsg()) || Objects.equals(next.getCdmc(),CdEnum.RLZYGL.getMsg()) || Objects.equals(next.getCdmc(),CdEnum.YTHJX.getMsg()) || Objects.equals(next.getCdmc(),CdEnum.SZHXXZX.getMsg())){
                            iterator.remove();
                        }
                    }
                }

            }

            List<CdTreeVO> tree = cdService.findByTree(cds,jglx);
            return ResponseUtil.success(tree);
        }

        return ResponseUtil.success();
    }

    @ApiOperation(value = "获取当前用户角色菜单操作列表")
    @GetMapping("/get/menu/options")
    public ResponseUtil getMenuOptions(@RequestParam("menuId") Long menuId) {

        if (menuId == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        //获取用户角色
        List<Js> js = jsService.findByUserId(loginUser.getUserId());
        if (CollectionUtils.isEmpty(js)) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        //获取缓存
        String cacheKey = getJsCdCzCacheKey(js, menuId);
        Object cacheObj = redisTemplate.opsForValue().get(cacheKey);
        if (cacheObj == null) {

            List<Cz> czs = cdService.getOptionsByRole(js, menuId);
            redisTemplate.opsForValue().set(cacheKey, JSONUtil.toJsonStr(czs));
            return ResponseUtil.success(czs);
        }

        JSONArray jsonArr = JSONUtil.parseArray(cacheObj.toString());
        List<Cz> czs = JSONUtil.toList(jsonArr, Cz.class);
        return ResponseUtil.success(czs);
    }

    @ApiOperation(value = "获取当前用户角色菜单列表")
    @GetMapping("/get/user/menu")
    public ResponseUtil getMenuByUser(@RequestParam(value = "xtlx",required = false) String xtlx) {

        //获取登录用户信息
        LoginUser loginUser = threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        //获取当前用户角色
        List<Js> js = jsService.findByUserId(loginUser.getUserId());
        if (CollectionUtils.isEmpty(js)) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        List<CdTreeVO> vos = cdService.getMenuByRole(js,xtlx);


       /* Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("children", vos);
        resultMap.put("path", "/Home");
        resultMap.put("name", "Home");
        resultMap.put("redirect", "");*/

        return ResponseUtil.success(vos);
    }


    /**
     * 通过名称和编码获取列表
     */
    @ApiOperation(value = "通过名称和编码获取列表")
    @GetMapping("/get/list/by/name/code/")
    public ResponseUtil findByNameOrCode(@ApiParam("当前页数") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @ApiParam("每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                         @ApiParam("菜单名称或菜单编码") @RequestParam(value = "query") String parameter,
                                         @ApiParam("系统类型") @RequestParam(value = "systemType", required = false) String systemType,
                                         @ApiParam("模块类型") @RequestParam(value = "modelType", required = false) String modelType) {
        if (parameter == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        PageInfo<GetCdAndCzDTO> pageInfo = cdService.findByNameOrCodePage(pageNum, pageSize, parameter, systemType, modelType);
        return ResponseUtil.success(pageInfo);
    }

    /**
     * 获取详情
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取详情")
    public ResponseUtil get(@PathVariable("id") Long id) {

        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        GetCdAndCzDTO cd = cdService.getCdAndCzById(id);
        if (cd == null || cd.getSfsc()) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        return ResponseUtil.success(cd);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/add/")
    public ResponseUtil add(@RequestBody AddCdRequest cd) throws WebException {

        checkRequest(cd);
        Cd add = cdService.add(cd);

        return ResponseUtil.success(add);
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/")
    public ResponseUtil update(@RequestBody AddCdRequest cd) {

        checkRequest(cd);

        Long id = cd.getId();
        //检查参数是否合法
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        //检查数据库中是否存在要更新的数据
        Cd old = cdService.getById(id);
        if (old == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        cdService.update(cd, old);
        return ResponseUtil.success(old);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {

        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        Cd cd = cdService.getById(id);
        if (cd == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        //判断当前菜单下是否有子级
        Long count = cdService.countByParentId(id);
        if (count > 0) {
            throw new WebException(ErrorCodeEnum.USER_DELETE_MENU_HAVE_CHILD_ERROR);
        }

        cdService.delete(cd);
        return ResponseUtil.success();
    }

    /**
     * 校验参数
     */
    private void checkRequest(Cd cd) throws WebException {

        if (cd == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(cd.getCdmc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(cd.getCdbm())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (cd.getFjcdId() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(cd.getXtlx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(cd.getMklx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }

    /**
     * 获取角色菜单操作缓存Key
     */
    private String getJsCdCzCacheKey(List<Js> js, Long menuId) {

        //获取缓存key
        StringBuffer stringBuffer = new StringBuffer();
        for (Js j : js) {

            stringBuffer = stringBuffer.append(j.getId());
        }

        String cacheKey = RedisConstant.ROLE_MENU_OPTIONS_PREFIX + ":" + stringBuffer.toString() + ":" + menuId;

        return cacheKey;
    }
}

