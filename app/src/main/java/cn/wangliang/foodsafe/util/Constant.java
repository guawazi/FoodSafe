package cn.wangliang.foodsafe.util;

/**
 * 通用常量
 */

public class Constant {
    public static final String REGEX_PHONE = "[1]\\d{10}";  //手机号正则
    public static final String REGEX_EMAIL = "^([a-zA-Z0-9_.-])+@(([a-zA-Z0-9-])+.)+([a-zA-Z0-9]{2,4})+$";  //邮箱正则

    public static final int STATUS_DISCONNECT = -3;// 网络连接断开
    public static final int STATUS_TIMEOUT = -2;// 网络超时
    public static final int STATUS_ERROR = 0;  //访问网络失败
    public static final int STATUS_SUCCESS = 0;  //访问网络成功
    public static final boolean DEBUG = true;


    public static final String REGEX_LOG = "\\s*\\{.*\\}\\s*";  // log 匹配规则

    public static final String LOGIN_USERID = "login_userid";
}
