package top.haibaraai.wx_login_pay.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.haibaraai.wx_login_pay.domain.Video;
import top.haibaraai.wx_login_pay.service.VideoService;

@RestController
@RequestMapping("admin/video")
public class VideoAdminController {

    @Autowired
    private VideoService videoService;

    /**
     * 根据id删除视频
     * @param videoId
     * @return
     */
    @DeleteMapping("delete_by_id")
    public Object deleteById(@RequestParam(value = "video_id", required = true) int videoId) {
        return videoService.delete(videoId);
    }

    /**
     * 更新视频信息
     * 请求体映射实体类，需要指定http头content-type为application/json charset=utf-8
     * @param video
     * @return
     */
    @PutMapping("update_by_id")
    public Object update(@RequestBody Video video) {
        return videoService.update(video);
    }

    /**
     * 保存视频信息
     * @param video
     * @return
     */
    @PostMapping("save")
    public Object save(@RequestBody Video video) {
        return videoService.save(video);
    }

}
