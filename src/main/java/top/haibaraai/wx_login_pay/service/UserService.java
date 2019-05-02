package top.haibaraai.wx_login_pay.service;

import top.haibaraai.wx_login_pay.domain.User;

/**
 * 用户业务接口
 */
public interface UserService {

    User saveWechatUser(String code);

}
