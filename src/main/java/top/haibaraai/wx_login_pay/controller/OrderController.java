package top.haibaraai.wx_login_pay.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.haibaraai.wx_login_pay.config.WechatConfig;
import top.haibaraai.wx_login_pay.domain.JsonData;
import top.haibaraai.wx_login_pay.domain.VideoOrder;
import top.haibaraai.wx_login_pay.service.VideoOrderService;
import top.haibaraai.wx_login_pay.utils.IpUtils;
import top.haibaraai.wx_login_pay.utils.QrCodeUtil;
import top.haibaraai.wx_login_pay.utils.WXPayUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 订单接口
 */
@RestController
@RequestMapping("/wechat/order")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Logger dataLogger = LoggerFactory.getLogger("dataLogger");

    @Autowired
    private WechatConfig wechatConfig;

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
        if (codeUrl == null) {
            return JsonData.buildError("下单失败");
        }
        QrCodeUtil.geneQrCode(codeUrl, response);
        return JsonData.buildSuccess("下单成功");
    }

    /**
     * 微信回调接口
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("callback")
    public void orderCallback(HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        //获得微信回调内容
        InputStream inputStream = request.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        while ((line = in.readLine()) != null) {
            stringBuffer.append(line);
        }
        in.close();
        inputStream.close();
        Map<String, String> callbackMap = WXPayUtil.xmlToMap(stringBuffer.toString());

        //转为有序map
        SortedMap<String, String> sortedMap = new TreeMap<>(callbackMap);

        //判断签名是否正确
        if (WXPayUtil.isCorrectSign(sortedMap, wechatConfig.getKey())) {
            if (sortedMap.get("return_code").equals("SUCCESS")) {

                //根据流水号查找订单
                String outTradeNo = sortedMap.get("out_trade_no");
                VideoOrder videoOrder = videoOrderService.findByOutTradeNo(outTradeNo);

                //处于未支付状态的进行处理
                if (videoOrder.getState() == 0) {
                    VideoOrder order = new VideoOrder();
                    order.setOpenid(sortedMap.get("openid"));
                    order.setOutTradeNo(outTradeNo);
                    order.setNotifyTime(new Date());
                    order.setState(1);

                    int row = videoOrderService.updateByOutTradeNo(order);

                    if (row == 1) {
                        //支付成功，通知微信
                        response.setContentType("text/xml");
                        SortedMap<String, String> map = new TreeMap<>();
                        map.put("return_code", "SUCCESS");
                        map.put("return_msg", "OK");
                        response.getWriter().println(WXPayUtil.mapToXml(map));
                    }
                }
            }
        } else {
            //支付失败，通知微信
            response.setContentType("text/xml");
            SortedMap<String, String> map = new TreeMap<>();
            map.put("return_code", "FAIL");
            map.put("return_msg", "???");
            response.getWriter().println(WXPayUtil.mapToXml(map));
        }

    }

}
