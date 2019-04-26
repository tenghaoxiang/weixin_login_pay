package top.haibaraai.wx_login_pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.haibaraai.wx_login_pay.domain.Video;
import top.haibaraai.wx_login_pay.service.VideoService;

@RestController
@RequestMapping("video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("page")
    public Object pageVideo() {
        return videoService.findAll();
    }

    @GetMapping("find_by_id")
    public Object findById(int id) {
        return videoService.findById(id);
    }

    @DeleteMapping("delete_by_id")
    public Object deleteById(int id) {
        return videoService.delete(id);
    }

    @PutMapping("update_by_id")
    public Object update(int id, String title) {
        Video video = new Video();
        video.setId(id);
        video.setTitle(title);
        return videoService.update(video);
    }

    @PostMapping("save")
    public Object save(String title) {
        Video video = new Video();
        video.setTitle(title);
        return videoService.save(video);
    }

}
