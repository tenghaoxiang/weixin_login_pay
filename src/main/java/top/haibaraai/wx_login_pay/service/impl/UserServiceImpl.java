package top.haibaraai.wx_login_pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.haibaraai.wx_login_pay.config.WechatConfig;
import top.haibaraai.wx_login_pay.domain.User;
import top.haibaraai.wx_login_pay.mapper.UserMapper;
import top.haibaraai.wx_login_pay.service.UserService;
import top.haibaraai.wx_login_pay.utils.HttpUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private UserMapper userMapper;

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

        User dbUser = userMapper.findByOpenId(openid);

        if (dbUser != null) {
            /**
             * 已经有用户信息，直接返回
             */
            return dbUser;
        }

        if (accessToken != null && openid != null) {

            String userInfoUrl = String.format(wechatConfig.getLoginUserInfoUrl(), accessToken, openid);
            Map<String, Object> userInfo = HttpUtils.doGet(userInfoUrl);

            String nickname = (String) userInfo.get("nickname");
            Integer sex = ((Double)userInfo.get("sex")).intValue();
            String province = (String) userInfo.get("province");
            String city = (String) userInfo.get("city");
            String country = (String) userInfo.get("country");
            String headImg = (String) userInfo.get("headimgurl");

            /**
             * 解决乱码问题
             */
            try {
                nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
                province = new String(province.getBytes("ISO-8859-1"), "UTF-8");
                city = new String(city.getBytes("ISO-8859-1"), "UTF-8");
                country = new String(country.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            /**
             * 创建一个user保存用户信息
             */
            String address = country + "||" + province + "||" + city;
            User user = new User();
            user.setOpenid(openid);
            user.setName(nickname);
            user.setSex(sex);
            user.setCity(address);
            user.setHeadImg(headImg);
            user.setCreateTime(new Date());

            userMapper.save(user);
            return user;

        }

        return null;

    }

}
