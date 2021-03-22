package com.itts.personneltrainingservice.controller;

import com.itts.common.utils.R;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {


    public R success() {
        return R.ok();
    }

    public R success(String msg) {
        return R.ok(msg);
    }


    public R success(Object data) {
        return R.okData(data);
    }


    public R fail(int code, String msg) {
        return R.error(code, msg);
    }

    public R fail(){
        return R.error();
    }

    public R save(boolean b) {
        return b ? success("添加成功") : fail(500, "添加失败");
    }

    public R update(boolean b) {
        return b ? success("修改成功") : fail(500, "修改失败");
    }

    public R remove(boolean b) {
        return b ? success("删除成功") : fail(500, "删除失败");
    }




}