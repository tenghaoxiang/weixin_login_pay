package top.haibaraai.wx_login_pay.utils;

import java.util.UUID;

public class UUIDUtil {

    /**
     * 生成uuid
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }

}
