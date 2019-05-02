package top.haibaraai.wx_login_pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.haibaraai.wx_login_pay.config.WechatConfig;
import top.haibaraai.wx_login_pay.domain.User;
import top.haibaraai.wx_login_pay.service.UserService;
import top.haibaraai.wx_login_pay.utils.HttpUtils;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private WechatConfig wechatConfig;

    @Override
    public User saveWechatUser(String code) {

        /**
         * 获得access_token的url
         */
        String accessTokenUrl = String.format(wechatConfig.getLoginAccessTokenUrl(),
                wechatConfig.getLoginAppID(),
                wechatConfig.getLoginAppSecret(),
                code);
        Map<String, Object> map = HttpUtils.doGet(accessTokenUrl);
        /**
         * 获得接口调用凭证
         */
        String accessToken = (String) map.get("access_token");
        /**
         * 获得授权用户唯一标识
         */
        String openid = (String) map.get("openid");

        if (accessToken != null && openid != null) {

            String userInfoUrl = String.format(wechatConfig.getLoginUserInfoUrl(), accessToken, openid);
            Map<String, Object> userInfo = HttpUtils.doGet(userInfoUrl);


        }

        return null;

    }

}
