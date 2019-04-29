package top.haibaraai.wx_login_pay.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.haibaraai.wx_login_pay.domain.Video;
import top.haibaraai.wx_login_pay.service.VideoService;

import java.util.List;

@RestController
@RequestMapping("video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 分页接口
     * @param page　当前第几页，默认第一页
     * @param size　每页显示几条，默认10条
     * @return
     */
    @GetMapping("page")
    public Object pageVideo(@RequestParam(value = "page", defaultValue = "1")int page,
                            @RequestParam(value = "size", defaultValue = "10")int size) {
        PageHelper.startPage(page, size);
        List<Video> list = videoService.findAll();
        /**
         * pageInfo包含我们所需要的各种信息，如当前第几页，显示了多少条，一共多少页等
         */
        PageInfo<Video> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 根据id查找视频
     * @param videoId
     * @return
     */
    @GetMapping("find_by_id")
    public Object findById(@RequestParam(value = "video_id", required = true) int videoId) {
        return videoService.findById(videoId);
    }

}