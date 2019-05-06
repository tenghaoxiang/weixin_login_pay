package top.haibaraai.wx_login_pay.mapper;

import org.apache.ibatis.annotations.*;
import top.haibaraai.wx_login_pay.domain.VideoOrder;

import java.util.List;

/**
 * 订单查询
 */
public interface VideoOrderMapper {

    /**
     * 保存订单
     * @param videoOrder
     * @return
     */
    @Insert("INSERT INTO `wechat_pay`.`video_order`(`openid`, `out_trade_no`, `state`, " +
            "`create_time`, `notify_time`, `total_fee`, `nickname`, `head_img`, `video_id`, " +
            "`video_title`, `video_img`, `user_id`, `ip`, `del`)" +
            " VALUES" +
            " (#{openid},#{outTradeNo},#{state},#{createTime},#{notifyTime},#{totalFee},#{nickname}," +
            "#{headImg},#{videoId},#{videoTitle},#{videoImg},#{userId},#{ip},#{del});")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(VideoOrder videoOrder);

    /**
     * 根据id查找订单
     * @param id
     * @return
     */
    @Select("select * from video_order where id = #{id} and del = 0")
    VideoOrder findById(int id);

    /**
     * 根据交易订单号查找订单
     * @param outTradeNo
     * @return
     */
    @Select("select * from video_order where out_trade_no = #{outTradeNo} and del = 0")
    VideoOrder findByOutTradeNo(String outTradeNo);

    /**
     * 逻辑删除
     * @param id
     * @param userId
     * @return
     */
    @Update("update video_order set del = 0 where id = #{id} and user_id = #{userId}")
    int delOrder(@Param("id") int id, @Param("userId") int userId);

    /**
     * 查找我的全部订单
     * @param userId
     * @return
     */
    @Select("select * from video_order where user_id = #{userId}")
    List<VideoOrder> findMyOrder(int userId);

    /**
     * 根据交易订单号更新订单信息
     * @param videoOrder
     * @return
     */
    @Update("UPDATE video_order SET state = #{state}, notify_time =#{notifyTime}, openid = #{openid}" +
            " WHERE out_trade_no = #{outTradeNo} and state = 0 and del = 0")
    int updateByOutTradeNo(VideoOrder videoOrder);

}
