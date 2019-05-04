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

    /**
     * 获取access_token的url
     */
    @Value("${login.access_token_url}")
    private String loginAccessTokenUrl;

    /**
     * 获取用户信息的url
     */
    @Value("${login.user_info_url}")
    private String loginUserInfoUrl;

    /**
     * 商户号id
     */
    @Value("${pay.mer_id}")
    private String merId;

    /**
     * 支付key
     */
    @Value("${pay.key}")
    private String key;

    /**
     * 支付回调地址
     */
    @Value("${pay.callback}")
    private String payCallbackUrl;

    /**
     * 微信公众号appId
     */
    @Value("${pay.AppID}")
    private String payAppID;

    /**
     * 微信公众号appSecret
     */
    @Value("${pay.AppSecret}")
    private String payAppSecret;

    @Value("${pay.unifiedOrder}")
    private String payUnifiedOrderUrl;

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

    public String getLoginAccessTokenUrl() {
        return loginAccessTokenUrl;
    }

    public String getLoginUserInfoUrl() {
        return loginUserInfoUrl;
    }

    public String getMerId() {
        return merId;
    }

    public String getKey() {
        return key;
    }

    public String getPayCallbackUrl() {
        return payCallbackUrl;
    }

    public String getPayAppID() {
        return payAppID;
    }

    public String getPayAppSecret() {
        return payAppSecret;
    }

    public String getPayUnifiedOrderUrl() {
        return payUnifiedOrderUrl;
    }
}
