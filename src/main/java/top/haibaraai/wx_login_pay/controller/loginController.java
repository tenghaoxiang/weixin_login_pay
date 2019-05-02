package top.haibaraai.wx_login_pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.haibaraai.wx_login_pay.config.WechatConfig;
import top.haibaraai.wx_login_pay.domain.JsonData;
import top.haibaraai.wx_login_pay.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("wechat")
public class loginController {

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private UserService userService;

    @GetMapping("login_url")
    public JsonData loginUrl(@RequestParam(value = "state", required = true) String state) throws UnsupportedEncodingException {

        /**
         * 获取登录二维码链接
         */
        String url = String.format(wechatConfig.getLoginQrcodeUrl(),
                wechatConfig.getLoginAppID(),
                URLEncoder.encode(wechatConfig.getLoginRedirectUrl(), "GBK"),
                state);
        return JsonData.buildSuccess(url);

    }

    @RequestMapping("/user/callback")
    public void wechatUserCallback(@RequestParam(value = "code") String code,
                                   @RequestParam(value = "state") String state,
                                   HttpServletResponse response) {
        userService.saveWechatUser(code);
    }

}
