package top.haibaraai.wx_login_pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.haibaraai.wx_login_pay.domain.Video;
import top.haibaraai.wx_login_pay.mapper.VideoMapper;
import top.haibaraai.wx_login_pay.service.VideoService;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<Video> findAll() {
        return videoMapper.findAll();
    }

    @Override
    public Video findById(int id) {
        return videoMapper.findById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int update(Video video) {
        return videoMapper.update(video);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int delete(int id) {
        return videoMapper.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int save(Video video) {
        return videoMapper.save(video);
    }

}
