package com.itts.technologytransactionservice.controller.unchecked;

import com.itts.common.exception.WebException;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.controller.BaseController;
import com.itts.technologytransactionservice.service.*;
import com.itts.technologytransactionservice.service.cd.JsCgAdminService;
import com.itts.technologytransactionservice.service.cd.JsXqAdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.itts.common.constant.SystemConstant.BASE_URL;
import static com.itts.common.constant.SystemConstant.UNCHECK_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.UPLOAD_FAIL_ISEMPTY_ERROR;

@RequestMapping(UNCHECK_BASE_URL)
@Api(value = "UnCheckedController", tags = "活动报名管理")
public class UnCheckedController extends BaseController {
    @Autowired
    private JsCgService jsCgService;
    @Autowired
    private JsCgAdminService jsCgAdminService;
    @Autowired
    private JsXqService jsXqService;
    @Autowired
    private JsXqAdminService jsXqAdminService;
    @Autowired
    private JsLyService jsLyService;
    @Autowired
    private JsLbService jsLbService;
    @Autowired
    private FileService fileService;
    @Autowired
    private JsBmService jsBmService;
    @Autowired
    private JsHdService jsHdService;
    @Autowired
    private JsCjRcService jsCjRcService;
    @Autowired
    private JsLcKzService jsLcKzService;
    /**
     * 分页条件查询成果(前台)
     *
     * @param params
     * @return
     */
    @PostMapping("/v1/JsCg/page")
    public ResponseUtil findJsCgFront(@RequestBody Map<String, Object> params) {
        return ResponseUtil.success(jsCgService.findJsCgFront(params));
    }
    /**
     * 根据ID查询成果信息
     *
     * @param id
     * @return
     */
    @GetMapping("/v1/JsCg/getById/{id}")
    public R getByCgId(@PathVariable("id") String id) {
        return success(jsCgAdminService.getById(Integer.valueOf(id)));
    }
    /**
     * 分页条件查询需求(前台)
     *
     * @param params
     * @return
     */
    @PostMapping("/v1/JsXq/page")
    public ResponseUtil findJsXqFront(@RequestBody Map<String, Object> params) {
        return ResponseUtil.success(jsXqService.findJsXqFront(params));
    }
    /**
     * 根据ID查询需求信息
     *
     * @param id
     * @return
     */
    @GetMapping("/v1/JsXq/getById/{id}")
    public R getByXqId(@PathVariable("id") String id) {
        return success(jsXqAdminService.getById(Integer.valueOf(id)));
    }
    /**
     * 分页条件查询
     *
     * @param params
     * @return
     */
    @PostMapping("/v1/JsXq/PageByTJsFb")
    public ResponseUtil PageByTJsFb(@RequestBody Map<String, Object> params) {
        //查询邻域类别审核状态列表数据
        Query query = new Query(params);
        return ResponseUtil.success(jsXqService.PageByTJsFb(query));
    }
    /**
     * (前台)分页查询
     *
     * @param params
     * @return
     */
    @PostMapping("/v1/JsLy/page")
    public ResponseUtil lyPage(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        return ResponseUtil.success(jsLyService.page(query));
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @GetMapping("/v1/JsLy/getById/{id}")
    public R getByLyId(@PathVariable("id") String id) {
        return success(jsLyService.getById(Long.parseLong(id)));
    }
    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @PostMapping("/v1/JsLb/page")
    public ResponseUtil lbPage(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        return ResponseUtil.success(jsLbService.page(query));
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @GetMapping("/v1/JsLb/getById/{id}")
    public R getByLbId(@PathVariable("id") String id) {
        return success(jsLbService.getById(Long.parseLong(id)));
    }
    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/v1/File/upload")
    public ResponseUtil fileUpload(@RequestParam MultipartFile file) {
        if (file.isEmpty()) {
            throw new WebException(UPLOAD_FAIL_ISEMPTY_ERROR);
        }
        return ResponseUtil.success("上传成功", fileService.fileUpload(file));
    }
    /**
     * 分页查询
     * @param params
     * @return
     */
    @PostMapping("/v1/JsBm/page/usr")
    public ResponseUtil bmPage(@RequestBody Map<String, Object> params) {
        //params.put("userId",2);
        //查询列表数据
        Query query = new Query(params);
        return ResponseUtil.success(jsBmService.page(query));
    }
    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @PostMapping("/v1/JsHd/page")
    public ResponseUtil hdPage(@RequestBody Map<String, Object> params) {

        //查询列表数据
        Query query = new Query(params);
        return ResponseUtil.success(jsHdService.page(query));
    }
    /**
     * 分页查询
     * @param params
     * @return
     */
    @PostMapping("/v1/JsCjRc/page")
    public ResponseUtil cjRcPage(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        return ResponseUtil.success(jsCjRcService.page(query));
    }
    /**
     * 分页查询
     * @param params
     * @return
     */
    @PostMapping("/v1/JsLcKz/page")
    public ResponseUtil lcKzPage(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        return ResponseUtil.success(jsLcKzService.page(query));
    }
}
