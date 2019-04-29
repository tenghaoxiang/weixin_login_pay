package top.haibaraai.wx_login_pay.provider;

import org.apache.ibatis.jdbc.SQL;
import top.haibaraai.wx_login_pay.domain.Video;

/**
 * video构建动态sql语句
 */
public class VideoProvider {

    /**
     * 更新video动态语句
     * @param video
     * @return
     */
    public String updateVideo(Video video) {
        return new SQL(){{

            UPDATE("video");

            if (video.getTitle() != null) {
                SET("title = #{title}");
            }
            if (video.getCoverImg() != null) {
                SET("cover_img = #{coverImg}");
            }
            if (video.getSummary() != null) {
                SET("summary = #{summary}");
            }
            if (video.getViewNum() != null) {
                SET("view_num = #{viewNum}");
            }
            if (video.getPrice() != null) {
                SET("price = #{price}");
            }
            if (video.getOnline() != null) {
                SET("online = #{online}");
            }
            if (video.getPoint() != null) {
                SET("point = #{point}");
            }

            WHERE("id = #{id}");

        }}.toString();
    }

}
