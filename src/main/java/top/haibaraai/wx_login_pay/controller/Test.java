package top.haibaraai.wx_login_pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.haibaraai.wx_login_pay.config.WechatConfig;
import top.haibaraai.wx_login_pay.mapper.VideoMapper;

@RestController
public class Test {

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private VideoMapper videoMapper;

    @RequestMapping("test_config")
    public Object testConfig() {
        return wechatConfig.getLoginAppID()+" "+wechatConfig.getLoginAppSecret();
    }

    @RequestMapping("select")
    public Object selectAll() {
        return videoMapper.findAll();
    }

}
