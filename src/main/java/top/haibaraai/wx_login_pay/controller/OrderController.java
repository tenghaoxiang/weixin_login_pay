package top.haibaraai.wx_login_pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.haibaraai.wx_login_pay.domain.JsonData;
import top.haibaraai.wx_login_pay.domain.VideoOrder;
import top.haibaraai.wx_login_pay.service.VideoOrderService;
import top.haibaraai.wx_login_pay.utils.IpUtils;
import top.haibaraai.wx_login_pay.utils.QrCodeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 订单接口
 */
@RestController
@RequestMapping("/wechat/order")
public class OrderController {

    @Autowired
    private VideoOrderService videoOrderService;

    @GetMapping("add")
    public JsonData saveOrder(@RequestParam(value = "video_id")int videoId,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String ip = IpUtils.getIpAddr(request);
        String ip = "120.25.1.43";
        int userId = 6;
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setUserId(userId);
        videoOrder.setIp(ip);
        videoOrder.setVideoId(videoId);
        String codeUrl = videoOrderService.insert(videoOrder);
        if (codeUrl != null) {
            QrCodeUtil.geneQrCode(codeUrl, response);
        }
        return JsonData.buildSuccess("下单成功");
    }

}
