package com.fei.ticket.common.exception;

/**
 * @Description: 基础错误枚举类
 * @Author: zhangy
 * @CreateDate: 2018/9/9 下午9:36
 * @UpdateUser: zhangy
 * @UpdateDate: 2018/9/9 下午9:36
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public enum BaseErrors implements ApiErrors {

    SUCCESS(10000, "SUCCESS", "请求成功"),
    PARAM_ILLEGAL(10001, "PARAM_ILLEGAL", "参数非法:%s"),
    PARAM_EMPTY(10002, "PARAM_EMPTY", "参数%s不能为空"),
    DATA_NOT_EXIST(10003, "DATA_NOT_EXIST", "%s不存在"),
    JSON_FORMAT_ERROR(10004, "JSON_FORMAT_ERROR", "JSON格式不正确"),
    JSON_DATA_FROMAT_ERROR(10005, "JSON_DATA_FROMAT_ERROR", "日期格式不正确,请使用yyyy-MM-dd HH:mm:ss"),
    NOT_UNLOCK_SHOP_MODULE(10006, "NOT_UNLOCK_SHOP_MODULE", "您还未解锁该模块"),
    BU_NENG_CHONG_FU(10007, "BU_NENG_CHONG_FU", "%s不能重复"),
    OPERATOR_ERROR(10008, "OPERATOR_ERROR", "操作失败，请重试"),
    SEARCH_RESULTS_NOT_EXIST(10009, "SEARCH_RESULTS_NOT_EXIST", "未匹配到搜索结果"),

    //用户异常
    TOKEN_ERROR(20000, "TOKEN_ERROR", "登录凭证不合法"),
    API_NO_PERMISSION(20001, "API_NO_PERMISSION", "您还没有开通该权限，请联系管理员"),
    ENCRYPT_ERROR(20003, "ENCRYPT_ERROR", "密码无法加密"),
    DATA_FORMAT_ERROR(20004, "DATA_FORMAT_ERROR", "数据格式不对"),
    LOGIN_ERROR_USER_NOT_EXIST(20005, "LOGIN_ERROR_USER_NOT_EXIST", "用户名不存在"),
    LOGIN_ERROR_SIGN_ERROR(20006, "LOGIN_ERROR_SIGN_ERROR", "登录失效，请重新登录"),
    LOGIN_ERROR_PASSWORD_INCORRECT(20007, "LOGIN_ERROR_PASSWORD_INCORRECT", "用户名或密码有误，密码为不包含中文的6-32为字符"),
    LOGIN_ERROR_STATUS_SUSPEND(20008, "LOGIN_ERROR_STATUS_SUSPEND", "账户异常，不能登录"),
    LOGIN_ERROR_STATUS_CLOSED(20009, "LOGIN_ERROR_STATUS_CLOSED", "账户已禁用，不能登录"),
    OLD_PASSWORD_INCORRECT(20007, "OLD_PASSWORD_INCORRECT", "旧密码错误"),
    LOCALE_NOT_SUPPORTED(20010, "LOCALE_NOT_SUPPORTED", "不支持请求的语言"),
    MOBILE_INVALD(20011, "MOBILE_INVALD", "手机号为空或无效"),
    NO_CREDENTIAL(20012, "NO_CREDENTIAL", "缺少认证信息"),

    //微信支付异常
    WX_POST_FAILED(30001, ",WX_POST_FAILED", "微信请求异常"),
    WX_UNIFIED_FAILED(30002, "WX_UNIFIED_FAILED", "微信生成订单失败"),
    LOGIN_CODE_INVALID(30003, "LOGIN_CODE_INVALID", "微信code无效"),
    LOGIN_MP_DECODE_ERROR(30004, "LOGIN_MP_DECODE_ERROR", "解析微信数据错误"),
    WX_CLOSE_FAILED(30005, "WX_CLOSE_FAILED", "微信关闭订单失败"),
    APPID_ERROR(30006, "APPID_ERROR", "appid不正确"),
    //微信公众号
    ACCESS_TOKEN_INVALID(30006, "ACCESS_TOKEN_INVALID", "微信access_token获取异常"),

    //订单异常
    ORDER_ERROR_INVENTORY_ZERO(30010, "ORDER_ERROR_INVENTORY_ZERO", "%s库存为0"),
    ORDER_ERROR_INVENTORY_DEFICIENCY(30011, "ORDER_ERROR_INVENTORY_DEFICIENCY", "%s库存不足"),
    ORDER_ERROR_ITEM_LIMIT_LASTCOUNT_INVALID(30012, "ORDER_ERROR_ITEM_LIMIT_LASTCOUNT_INVALID", "%s超过限购数量"),

    RESOURCE_UPLOAD_ERROR(700001, "RESOURCE_UPLOAD_ERROR", "上传失败,请重试"),
    IMG_ZIP_ERROR(700002, "IMG_ZIP_ERROR", "图片压缩失败"),
    RES_TYPE_DENY(700003, "RES_TYPE_DENY", "文件类型不合法"),
    XLSX_TYPE_DENY(700004, "XLSX_TYPE_DENY", "excel类型不合法"),
    IMG_TYPE_NOT_SUPPORT(700005, "IMG_TYPE_NOT_SUPPORT", "图片格式不支持"),
    XLSX_DOWLOAD_ERROR(700006, "XLSX_DOWLOAD_ERROR", "excel导出失败:%s"),
    PRODUCT_UPLOAD_IMAGE_NULL(800001, "PRODUCT_UPLOAD_IMAGE_NULL", "上传商品图片不能为空"),
    PRODUCT_UPLOAD_ERROR(800002, "PRODUCT_UPLOAD_ERROR", "上架失败，请重试"),

    DB_ERROR(80000, "DB_ERROR", "数据库异常:%s"),
    SYSTEM_ERROR(99999, "SYSTEM_ERROR", "服务器忙,请稍后再试"),
    ;
    private int      code;
    private String   errorCode;
    private String   errorMessage;
    private String[] args;

    BaseErrors(int code, String errorCode, String errorMessage) {
        this.code = code;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        if (args == null) {
            return this.errorMessage;
        } else {
            return String.format(errorMessage, args);
        }

    }

    @Override
    public int getCode() {
        return code;
    }

    public BaseErrors setArgs(String... args) {
        this.args = args;
        return this;
    }
}
