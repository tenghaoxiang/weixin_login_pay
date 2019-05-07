package top.haibaraai.wx_login_pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.haibaraai.wx_login_pay.config.WechatConfig;
import top.haibaraai.wx_login_pay.domain.JsonData;
import top.haibaraai.wx_login_pay.domain.User;
import top.haibaraai.wx_login_pay.domain.VideoOrder;
import top.haibaraai.wx_login_pay.service.UserService;
import top.haibaraai.wx_login_pay.service.VideoOrderService;
import top.haibaraai.wx_login_pay.utils.JwtUtils;
import top.haibaraai.wx_login_pay.utils.WXPayUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@RestController
@RequestMapping("wechat")
public class WechatController {

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private VideoOrderService videoOrderService;

    /**
     * 获取登录二维码链接
     */
    @GetMapping("login_url")
    public JsonData loginUrl(@RequestParam(value = "state", required = true) String state) throws UnsupportedEncodingException {

        String url = String.format(wechatConfig.getLoginQrcodeUrl(),
                wechatConfig.getLoginAppID(),
                URLEncoder.encode(wechatConfig.getLoginRedirectUrl(), "GBK"),
                state);
        return JsonData.buildSuccess(url);

    }

    /**
     * 登录微信回调接口
     * @param code
     * @param state
     * @param response
     * @throws IOException
     */
    @RequestMapping("/user/callback")
    public void wechatUserCallback(@RequestParam(value = "code") String code,
                                   @RequestParam(value = "state") String state,
                                   HttpServletResponse response) throws IOException {
        User user = userService.saveWechatUser(code);
        if (user != null) {
            //生成jwt
            String token = JwtUtils.geneJsonWebToken(user);
            //state 当前用户的页面地址，需要拼接http://，这样才不会站内跳转
            response.sendRedirect(state + "?token=" + token + "&head_img=" + user.getHeadImg() + "&name=" + URLEncoder.encode(user.getName(), "UTF-8"));
        }

    }

    /**
     * 支付微信回调接口
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/order/callback")
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
