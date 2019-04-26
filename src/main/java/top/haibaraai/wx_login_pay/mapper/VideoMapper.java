package top.haibaraai.wx_login_pay.mapper;

import org.apache.ibatis.annotations.*;
import top.haibaraai.wx_login_pay.domain.Video;

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
    @Update("UPDATE video SET title = #{title} WHERE id = #{id}")
    int update(Video video);

    @Delete("DELETE FROM video WHERE id = #{id}")
    int delete(int id);

    @Insert("INSERT INTO `wechat_pay`.`video`(`title`, `summary`," +
            " `cover_img`, `view_num`, `price`, `create_time`, `online`," +
            " `point`) VALUES (#{title},#{summary},#{coverImg},#{viewNum}," +
            "#{price},#{createTime},#{online},#{point});")
    //如果不加这条，虽然数据库中有id值，但无法通过video获取，加上Options后可获取
    @Options(keyProperty = "id", keyColumn = "id", useGeneratedKeys = true)
    int save(Video video);

}
