package top.haibaraai.wx_login_pay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class WechatConfig {

    @Value("${login.AppID}")
    private String loginAppID;

    @Value("${login.AppSecret}")
    private String loginAppSecret;

    public String getLoginAppID() {
        return loginAppID;
    }

    public String getLoginAppSecret() {
        return loginAppSecret;
    }
}
