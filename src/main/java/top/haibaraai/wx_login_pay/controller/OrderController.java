package top.haibaraai.wx_login_pay.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.haibaraai.wx_login_pay.domain.JsonData;

/**
 * 订单接口
 */
@RestController
@RequestMapping("/user/order")
public class OrderController {

    @RequestMapping("add")
    public JsonData saveOrder() {
        return JsonData.buildSuccess("下单成功");
    }

}
