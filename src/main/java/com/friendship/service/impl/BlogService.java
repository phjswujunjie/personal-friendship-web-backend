package com.friendship.service.impl;

import com.friendship.pojo.Blog;
import com.friendship.mapper.BlogMapper;
import com.friendship.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@SuppressWarnings("all")
public class BlogService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BlogMapper blogMapper;

    public int uploadBlog(MultipartFile[] files, String text, String isPublic, String token) throws Exception {
        ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
        Blog blog = new Blog();
        String id = redis.get(token);
        Date date = new Date();
        String image = "";
        String video = "";
        if (files != null) {
            String realPath = ResourceUtils.toURI(ResourceUtils.getURL("classpath:")).getPath();
//            String realPath = System.getProperty("user.dir");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd/");
            String path = simpleDateFormat.format(date);
            for (MultipartFile file : files) {
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                String fileName = UUID.randomUUID().toString() + suffix;
                File directory = new File(realPath + "/static/upload/" + path);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                if ((".mp4").equals(suffix)) {
                    video += path + fileName + ";";
                } else {
                    image += path + fileName + ";";
                }
                file.transferTo(new File(directory.getPath() + "/" + fileName));
            }
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(date);
        blog.setCreateTime(format);
        blog.setIsPublic(isPublic);
        blog.setVideo(video);
        blog.setImage(image);
        blog.setContent(text);
        blog.setUserId(Long.valueOf(id));
        int i = blogMapper.insert(blog);
        return i;
    }

    public List<Map<String, Object>> displayBlog(String... id) {
        List<Map<String, Object>> mapList;
        if (id.length == 0){
            mapList = blogMapper.displayAroundBlog();
        }else {
            mapList = blogMapper.displayBlogById(Long.valueOf(id[0]));
        }
        for (Map<String, Object> map : mapList) {
            String userId = map.get("user_id") + "";
            Map<String, Object> nickname = userMapper.getNickname(Long.valueOf(userId));
            map.put("user_nickname", nickname.get("nickname"));
            map.put("user_avatar", "https://localhost:8443/static/upload/" + nickname.get("avatar"));
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
            map.put("user_id", "http://localhost:8081/u/" + map.get("user_id") + "/blog");
        }
        return mapList;
    }

    public int deleteBlogById(Long id){
        return blogMapper.deleteBlogById(id);
    }
}
