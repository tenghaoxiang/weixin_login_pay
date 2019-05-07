package top.haibaraai.wx_login_pay.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.haibaraai.wx_login_pay.domain.JsonData;
import top.haibaraai.wx_login_pay.domain.Video;
import top.haibaraai.wx_login_pay.service.VideoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//        return JsonData.buildSuccess(pageInfo);
        Map<String,Object> data = new HashMap<>();
        data.put("total_size",pageInfo.getTotal());//总条数
        data.put("total_page",pageInfo.getPages());//总页数
        data.put("current_page",page);//当前页
        data.put("data",pageInfo.getList());//数据
        return data;
    }

    /**
     * 根据id查找视频
     * @param videoId
     * @return
     */
    @GetMapping("find_by_id")
    public JsonData findById(@RequestParam(value = "video_id", required = true) int videoId) {
        return JsonData.buildSuccess(videoService.findById(videoId));
    }

}
