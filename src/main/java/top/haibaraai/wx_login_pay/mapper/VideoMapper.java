package top.haibaraai.wx_login_pay.mapper;

import org.apache.ibatis.annotations.*;
import top.haibaraai.wx_login_pay.domain.Video;
import top.haibaraai.wx_login_pay.provider.VideoProvider;

import java.util.List;

public interface VideoMapper {

    /**
     * 查找全部视频
     *
     * @return
     */
    @Select("SELECT * FROM video")
    List<Video> findAll();

    /**
     * 根据id查找视频
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM video WHERE id = #{id}")
    Video findById(int id);

    /**
     * 更新视频信息
     *
     * @param video
     * @return
     */
    @UpdateProvider(type = VideoProvider.class, method = "updateVideo")
    int update(Video video);

    /**
     * 删除视频
     * @param id
     * @return
     */
    @Delete("DELETE FROM video WHERE id = #{id}")
    int delete(int id);

    /**
     * 插入新的视频信息
     * @param video
     * @return
     */
    @Insert("INSERT INTO `wechat_pay`.`video`(`title`, `summary`," +
            " `cover_img`, `view_num`, `price`, `create_time`, `online`," +
            " `point`) VALUES (#{title},#{summary},#{coverImg},#{viewNum}," +
            "#{price},#{createTime},#{online},#{point});")
    //如果不加这条，虽然数据库中有id值，但无法通过video获取，加上Options后可获取
    @Options(keyProperty = "id", keyColumn = "id", useGeneratedKeys = true)
    int save(Video video);

}
