package top.haibaraai.wx_login_pay.service;

import org.apache.ibatis.annotations.Param;
import top.haibaraai.wx_login_pay.domain.VideoOrder;

import java.util.List;

public interface VideoOrderService {

    String insert(VideoOrder videoOrder) throws Exception;

    VideoOrder findById(int id);

    VideoOrder findByOutTradeNo(int outTradeNo);

    int delOrder(@Param("id") int id, @Param("userId") int userId);

    List<VideoOrder> findMyOrder(int userId);

    int updateByOutTradeNo(VideoOrder videoOrder);

}
