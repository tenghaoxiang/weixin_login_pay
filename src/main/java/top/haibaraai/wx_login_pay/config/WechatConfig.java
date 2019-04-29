package top.haibaraai.wx_login_pay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class WechatConfig {

    /**
     * 开放平台appID
     */
    @Value("${login.AppID}")
    private String loginAppID;

    /**
     * 开发平台appSecret
     */
    @Value("${login.AppSecret}")
    private String loginAppSecret;

    /**
     * 开放平台回调地址
     */
    @Value("${login.redirect_url}")
    private String loginRedirectUrl;

    /**
     * 请求二维码连接url
     */
    @Value("${login.qrcode_url}")
    private String loginQrcodeUrl;

    public String getLoginAppID() {
        return loginAppID;
    }

    public String getLoginAppSecret() {
        return loginAppSecret;
    }

    public String getLoginRedirectUrl() {
        return loginRedirectUrl;
    }

    public String getLoginQrcodeUrl() {
        return loginQrcodeUrl;
    }
}
