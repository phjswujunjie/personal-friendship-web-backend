package cn_java_service_impl;

import cn_java_PO.User;
import cn_java_mapper.BlogMapper;
import cn_java_mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DisplayBlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private UserMapper userMapper;

    public List<Map<String, Object>> displayAroundBlog() {
        List<Map<String, Object>> mapList = blogMapper.displayAroundBlog();
        for (Map<String, Object> map : mapList) {
            String userId = map.get("user_id") + "";
            Map<String, Object> nickname = userMapper.getNickname(Long.valueOf(userId));
            map.put("user_nickname", nickname.get("nickname"));
            map.put("user_avatar","https://localhost:8443/static/upload/" + nickname.get("avatar"));
            String[] image = map.get("image").toString().split(";");
            for (int i = 0; i < image.length; i++) {
                image[i] = "https://localhost:8443/static/upload/" + image[i];
            }
            map.put("image", image);
            String[] video = map.get("video").toString().split(";");
            for (int i = 0; i < video.length; i++) {
                video[i] = "https://localhost:8443/static/upload/" + video[i];
            }
            map.put("video", video);
        }
        return mapList;
    }
}
