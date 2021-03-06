package com.itts.userservice.controller.yh.admin;


import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.dto.YhDTO;
import com.itts.userservice.enmus.GroupTypeEnum;
import com.itts.userservice.enmus.UserTypeEnum;
import com.itts.userservice.model.jggl.Jggl;
import com.itts.userservice.model.js.Js;
import com.itts.userservice.model.yh.Yh;
import com.itts.userservice.request.yh.AddYhRequest;
import com.itts.userservice.request.yh.RpcAddYhRequest;
import com.itts.userservice.service.jggl.JgglService;
import com.itts.userservice.service.js.JsService;
import com.itts.userservice.service.yh.YhService;
import com.itts.userservice.vo.yh.GetSystemsVO;
import com.itts.userservice.vo.yh.GetYhVO;
import com.itts.userservice.vo.yh.YhListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.itts.common.constant.SystemConstant.threadLocal;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author fl
 * @since 2021-03-18
 */
@Api(tags = "用户后台管理")
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/v1/yh")
@Slf4j
public class YhAdminController {

    @Autowired
    private YhService yhService;

    @Autowired
    private JgglService jgglService;

    @Autowired
    private JsService jsService;

    /**
     * 获取列表 - 分页
     *
     * @param pageNum pageSize
     * @author fl
     */
    @GetMapping("/list/")
    @ApiOperation(value = "获取列表")
    public ResponseUtil findByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "type", required = false) String type,
                                   @RequestParam(value = "groupId", required = false) Long groupId,
                                   @RequestParam(value = "condition", required = false) String condition) {
        Jggl group = null;

        if (groupId != null) {
            group = jgglService.get(groupId);

            if (group == null) {

                log.error("【用户管理 - 后台管理】查询用户列表，机构不存在，机构ID: ", groupId);
                throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
            }
        }

        if (StringUtils.isNotBlank(type) && (!UserTypeEnum.check(type))) {

            log.error("【用户管理 - 后台管理】查询用户列表，请求参数不合法: ", type);
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        PageInfo<YhListVO> byPage = yhService.findByPage(pageNum, pageSize, type, group, condition);
        return ResponseUtil.success(byPage);
    }

    /**
     * 获取指定列表 - 分页
     *
     * @param pageNum pageSize
     * @author fl
     */
    @GetMapping("/getlist/")
    @ApiOperation(value = "获取列表")
    public ResponseUtil findByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "type") String type,
                                   @RequestParam(value = "string") String string) {
        if (StringUtils.isBlank(type)) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (string == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        PageInfo<YhDTO> yhPageInfo = yhService.selectByString(pageNum, pageSize, type, string);
        return ResponseUtil.success(yhPageInfo);
    }

    @ApiOperation(value = "获取登录用户信息")
    @GetMapping("/get/login/user/")
    public ResponseUtil getLoginUser() {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        Yh yh = yhService.get(loginUser.getUserId());

        if (yh == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        if (yh.getSfsc()) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        GetYhVO getYhVO = DTO2VO(yh);

        return ResponseUtil.success(getYhVO);
    }

    /**
     * 获取当前用户所属哪些系统
     */
    @ApiOperation(value = "获取当前用户所属哪些系统")
    @GetMapping("/find/systems/")
    public ResponseUtil findSystemByUser() {

        LoginUser loginUser = threadLocal.get();
        if(loginUser == null){
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        List<GetSystemsVO> result = yhService.findSystems(loginUser.getUserId());

        return ResponseUtil.success(result);
    }

    /**
     * 获取详情
     *
     * @param id
     * @author fl
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取详情")
    public ResponseUtil get(@PathVariable("id") Long id) {

        Yh yh = yhService.get(id);

        if (yh == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        if (yh.getSfsc()) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        GetYhVO getYhVO = DTO2VO(yh);

        return ResponseUtil.success(getYhVO);
    }
    /**
     * 获取详情
     *
     * @param id
     * @author fl
     */
    @GetMapping("/getBy/{id}")
    @ApiOperation(value = "获取详情")
    public ResponseUtil getById(@PathVariable("id") Long id) {

        Yh yh = yhService.get(id);

        return ResponseUtil.success(yh);
    }


    @ApiOperation(value = "通过用户编号查询")
    @GetMapping("/get/by/code/")
    public ResponseUtil getByCode(@ApiParam("用户编号") @RequestParam("code") String code) {

        GetYhVO yh = yhService.getByCode(code);



        return ResponseUtil.success(yh);
    }

    @ApiOperation(value = "通过用户手机号查询")
    @GetMapping("/get/by/phone/")
    public ResponseUtil getByPhone(@ApiParam("用户手机号") @RequestParam("phone") String phone) {

        GetYhVO yh = yhService.getByphone(phone);



        return ResponseUtil.success(yh);
    }

    /**
     * 新增
     *
     * @author fl
     */
    @PostMapping("/add/")
    @ApiOperation(value = "新增")
    public ResponseUtil add(@RequestBody AddYhRequest addYhRequest, HttpServletRequest request) throws WebException {

        //检查参数是否合法
        checkRequest(addYhRequest);

        GetYhVO result = yhService.add(addYhRequest, request.getHeader(SystemConstant.TOKEN_PREFIX));

        return ResponseUtil.success(result);
    }

    /**
     * 新增
     *
     * @author fl
     */
    @PostMapping("/rpc/add/")
    @ApiOperation(value = "新增")
    public ResponseUtil rpcAdd(@RequestBody RpcAddYhRequest yh) throws WebException {

        //检查参数是否合法
        if (yh == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(yh.getYhlx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(yh.getYhlb())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(yh.getYhm())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(yh.getMm())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        GetYhVO result = yhService.rpcAdd(yh);

        return ResponseUtil.success(result);
    }

    /**
     * 更新
     *
     * @author fl
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/")
    @Transactional(rollbackFor = Exception.class)
    public ResponseUtil update(@RequestBody AddYhRequest addYhRequest) throws WebException {

        //检查参数是否合法
        if (addYhRequest.getId() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (addYhRequest == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(addYhRequest.getYhlx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        //检查数据库中是否存在要更新的数据
        Yh old = yhService.get(addYhRequest.getId());

        GetYhVO result = yhService.update(addYhRequest, old);

        return ResponseUtil.success(result);
    }
    /**
     * 更新
     *
     * @author fl
     */
    @ApiOperation(value = "更新")
    @PutMapping("/updateYh")
    @Transactional(rollbackFor = Exception.class)
    public ResponseUtil updateYh(@RequestBody Yh yh) throws WebException {
        Yh byId = yhService.getById(yh.getId());
        if(byId == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        BeanUtils.copyProperties(yh,byId);
        byId.setMm(new BCryptPasswordEncoder().encode(byId.getMm()));
        byId.setGxsj(new Date());
        byId.setGxr(yh.getId());
        return ResponseUtil.success(yhService.updateById(byId));
    }

    @ApiOperation(value = "重置密码")
    @PutMapping("/reset/password/{id}")
    public ResponseUtil resetPassword(@PathVariable("id") Long id) {

        Yh yh = yhService.get(id);
        if (yh == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        yhService.resetPassword(yh);

        return ResponseUtil.success();
    }

    /**
     * 删除
     *
     * @author fl
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {

        //检查参数是否为空
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        Yh yh = yhService.get(id);
        if (yh == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        yhService.delete(yh);

        return ResponseUtil.success();
    }

    /**
     * 校验参数是否合法
     */
    private void checkRequest(AddYhRequest yh) throws WebException {

        //如果参数为空，抛出异常
        if (yh == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(yh.getYhlx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }



        if (StringUtils.isBlank(yh.getYhm())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(yh.getMm())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }

    /**
     * 用户DTO转VO
     */
    private GetYhVO DTO2VO(Yh yh) {

        GetYhVO getYhVO = new GetYhVO();
        BeanUtils.copyProperties(yh, getYhVO);

        //获取当前用户最顶级机构信息
        Jggl jg = jgglService.get(getYhVO.getJgId());
        if (jg != null) {

            //总基地
            if (Objects.equals(jg.getLx(), GroupTypeEnum.HEADQUARTERS.getKey())) {

                getYhVO.setFjjgId(jg.getId());
                getYhVO.setJglx(GroupTypeEnum.HEADQUARTERS.getKey());
            }

            //分基地
            if (Objects.equals(jg.getLx(), GroupTypeEnum.BRANCH.getKey())) {

                getYhVO.setFjjgId(jg.getId());
                getYhVO.setJglx(GroupTypeEnum.BRANCH.getKey());
            }

            //其他
            if (Objects.equals(jg.getLx(), GroupTypeEnum.OTHER.getKey())) {

                String jgCode = jg.getJgbm().substring(0, 6);
                Jggl checkJg = jgglService.selectByJgbm(jgCode);
                if (checkJg != null) {

                    //分基地
                    if (Objects.equals(checkJg.getLx(), GroupTypeEnum.BRANCH.getKey())) {

                        getYhVO.setFjjgId(checkJg.getId());
                        getYhVO.setJglx(GroupTypeEnum.BRANCH.getKey());
                    }

                    //如果是其他，则上级一定是总基地
                    if (Objects.equals(checkJg.getLx(), GroupTypeEnum.OTHER.getKey())) {

                        String fjcode = checkJg.getJgbm().substring(0, 3);
                        Jggl fjjg = jgglService.selectByJgbm(fjcode);
                        if (fjjg != null) {

                            getYhVO.setFjjgId(fjjg.getId());
                            getYhVO.setJglx(fjjg.getLx());
                        }
                    }
                }
            }
        }

        //获取用户角色
        List<Js> js = jsService.findByUserId(getYhVO.getId());
        getYhVO.setJsList(js);


        return getYhVO;
    }
}

