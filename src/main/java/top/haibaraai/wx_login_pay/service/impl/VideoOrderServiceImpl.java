package top.haibaraai.wx_login_pay.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.haibaraai.wx_login_pay.config.WechatConfig;
import top.haibaraai.wx_login_pay.domain.User;
import top.haibaraai.wx_login_pay.domain.Video;
import top.haibaraai.wx_login_pay.domain.VideoOrder;
import top.haibaraai.wx_login_pay.mapper.UserMapper;
import top.haibaraai.wx_login_pay.mapper.VideoMapper;
import top.haibaraai.wx_login_pay.mapper.VideoOrderMapper;
import top.haibaraai.wx_login_pay.service.VideoOrderService;
import top.haibaraai.wx_login_pay.utils.HttpUtils;
import top.haibaraai.wx_login_pay.utils.UUIDUtil;
import top.haibaraai.wx_login_pay.utils.WXPayUtil;

import java.util.*;

@Service
public class VideoOrderServiceImpl implements VideoOrderService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Logger dataLogger = LoggerFactory.getLogger("dataLogger");

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private VideoOrderMapper videoOrderMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String insert(VideoOrder videoOrder) throws Exception {

        dataLogger.info("module=video_order`api=save`user_id={}`video_id={}", videoOrder.getUserId(), videoOrder.getVideoId());

        //查找视频信息
        Video video = videoMapper.findById(videoOrder.getVideoId());

        //查找用户信息
        User user = userMapper.findById(videoOrder.getUserId());

        //生成订单
        videoOrder.setTotalFee(video.getPrice());
        videoOrder.setVideoImg(video.getCoverImg());
        videoOrder.setVideoTitle(video.getTitle());
        videoOrder.setCreateTime(new Date());

        videoOrder.setState(0);
        videoOrder.setHeadImg(user.getHeadImg());
        videoOrder.setNickname(user.getName());

        videoOrder.setDel(0);
        videoOrder.setOutTradeNo(UUIDUtil.generateUUID());

        videoOrderMapper.insert(videoOrder);

        String codeUrl = unifiedOrder(videoOrder);

        return codeUrl;

    }

    /**
     * 统一下单
     * @param videoOrder
     * @return
     */
    private String unifiedOrder(VideoOrder videoOrder) throws Exception {

        //生成签名
        SortedMap<String, String> params = new TreeMap<>();
        params.put("appid", wechatConfig.getPayAppID());
        params.put("mch_id", wechatConfig.getMerId());
        params.put("nonce_str", UUIDUtil.generateUUID());
        params.put("body",videoOrder.getVideoTitle());
        params.put("out_trade_no", videoOrder.getOutTradeNo());
        params.put("total_fee", videoOrder.getTotalFee().toString());
        params.put("spbill_create_ip", videoOrder.getIp());
        params.put("notify_url", wechatConfig.getPayCallbackUrl());
        params.put("trade_type", "NATIVE");

        //sign签名
        String sign = WXPayUtil.createSign(params, wechatConfig.getKey());
        params.put("sign", sign);

        //map转xml
        String payXml = WXPayUtil.mapToXml(params);

        //统一下单
        String orderStr = HttpUtils.doPost(wechatConfig.getPayUnifiedOrderUrl(), payXml);
        if (orderStr == null) {
            return null;
        }

        Map<String, String> orderMap = WXPayUtil.xmlToMap(orderStr);

        return orderMap.get("code_url");

    }

    @Override
    public VideoOrder findById(int id) {
        return videoOrderMapper.findById(id);
    }

    @Override
    public VideoOrder findByOutTradeNo(String outTradeNo) {
        return videoOrderMapper.findByOutTradeNo(outTradeNo);
    }

    @Override
    public int delOrder(int id, int userId) {
        return videoOrderMapper.delOrder(id, userId);
    }

    @Override
    public List<VideoOrder> findMyOrder(int userId) {
        return videoOrderMapper.findMyOrder(userId);
    }

    @Override
    public int updateByOutTradeNo(VideoOrder videoOrder) {
        return videoOrderMapper.updateByOutTradeNo(videoOrder);
    }
}
