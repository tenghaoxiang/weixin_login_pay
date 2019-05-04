package top.haibaraai.wx_login_pay.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.haibaraai.wx_login_pay.domain.VideoOrder;
import top.haibaraai.wx_login_pay.mapper.VideoOrderMapper;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoOrderServiceImplTest {

    @Autowired
    private VideoOrderMapper videoOrderMapper;

    @Test
    public void insert() {
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setCreateTime(new Date());
        videoOrder.setNickname("haibaraai");
        videoOrder.setState(1);
        videoOrder.setVideoTitle("mysql");
        videoOrder.setVideoId(1);
        videoOrder.setDel(0);
        videoOrder.setHeadImg("www.nwu.com");
        videoOrder.setTotalFee(10);
        videoOrderMapper.insert(videoOrder);
    }

    @Test
    public void findById() {
    }

    @Test
    public void findByOutTradeNo() {
    }

    @Test
    public void delOrder() {
    }

    @Test
    public void findMyOrder() {
    }

    @Test
    public void updateByOutTradeNo() {
    }
}