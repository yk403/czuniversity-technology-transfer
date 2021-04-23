package com.itts.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 错误码枚举
 *
 * @param
 * @author liuyingming
 * @description 错误码为6位， 前三位服务码， 后三位接口码
 * @return
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCodeEnum {

    //====================      系统错误提示（服务码400）      ====================
    SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR(-400001, "请求参数不合法"),
    SYSTEM_NOT_FIND_ERROR(-400002, "数据不存在"),
    SYSTEM_UPLOAD_ERROR(-400003, "导入失败"),
    SYSTEM_FIND_ERROR(-400004, "数据已存在"),
    SYSTEM_REQUEST_METHOD_ERROR(-40005, "请求方式错误"),
    SYSTEM_REQUEST_PARAMS_TYPE_ERROR(-40006, "请求参数类型或请求参数格式错误"),
    SYSTEM_ERROR(-40007, "系统异常，请联系管理员"),
    //====================      系统错误提示  END     ====================

    //====================      技术交易服务错误提示（服务码410）      ====================
    UPLOAD_FAIL_ERROR(-410001, "文件上传失败"),
    UPLOAD_FAIL_ISEMPTY_ERROR(-410002, "文件上传不可为空"),
    NAME_EXIST_ERROR(-41000, "名称已存在"),
    DELETE_ERROR(-41003, "删除失败!"),
    MSG_AUDIT_FAIL(-41004, "信息审核未通过,无法申请招拍挂!"),
    REQUEST_PARAMS_ISEMPTY(-41005, "请求参数不可为空!"),
    ISSUE_BATCH_FAIL(-41006, "批量发布失败!"),
    AUDIT_MSG_ISEMPTY(-41007, "审核意见不可为空!"),
    UPDATE_FAIL(-41008, "更新失败"),
    INSERT_FAIL(-41009, "新增失败"),
    DELETE_FAIL(-41010, "删除失败"),
    //====================      技术交易服务错误提示  END     ====================


    //====================       支付服务错误提示（服务码420）      ====================
    PAY_FAIL_ERROR(-420001, "支付失败"),
    //====================       支付服务错误提示END     ====================

    //====================       登录、登出、注册错误提示（服务码430）      ====================
    NO_LOGIN_ERROR(-430001, "用户未登录或账号密码错误"),
    NO_PERMISSION_ERROR(-430002, "用户无权限，请联系管理员"),
    LOGIN_USERNAME_PASSWORD_ERROR(-430003, "用户账号或密码错误，请重试"),
    REGISTER_USERNAME_PARAMS_ILLEGAL_ERROR(-430004, "用户注册账号为空"),
    REGISTER_PASSWORD_PARAMS_ILLEGAL_ERROR(-430005, "用户注册密码为空"),
    REGISTER_SYSTEM_TYPE_PARAMS_ILLEGAL_ERROR(-430006, "用户注册系统类型为空"),
    REGISTER_USERNAME_EXISTS_ERROR(-430007, "用户注册账号已存在"),
    REGISTER_DEFAULT_ROLE_NOT_FIND_ERROR(-430008, "用户注册默认角色不存在"),
    //====================       登录、登出、注册错误提示END     ====================

    //====================      人次培养服务错误提示（服务码450）      ====================
    STUDENT_NUMBER_EXISTS_ERROR(-45000, "学员学号已存在!"),
    TEACHER_NUMBER_EXISTS_ERROR(-45001, "导师编号已存在!"),
    TEACHING_BUILDING_EXISTS_ERROR(-45002, "教学楼教室已存在!"),
    INSTITUTE_NAME_EXISTS_ERROR(-45003, "学院名称已存在!"),
    //====================      技术交易服务错误提示  END     ====================

    //====================       用户服务错误提示（服务码440）      ====================
    USER_NOT_FIND_ERROR(-440001, "用户不存在"),
    USER_DELETE_MENU_HAVE_CHILD_ERROR(-440002, "删除菜单失败，该菜单下有子级菜单"),
    USER_DELETE_GROUP_HAVE_CHILD_ERROR(-440003, "删除机构失败，该机构下有子级机构");
    //====================       用户服务错误提示END     ====================

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示信息
     */
    private String msg;
}
