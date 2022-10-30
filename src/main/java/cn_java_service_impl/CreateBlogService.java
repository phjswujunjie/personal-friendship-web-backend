package cn_java_service_impl;

import cn_java_PO.Blog;
import cn_java_mapper.BlogMapper;
import cn_java_mapper.UserMapper;
import cn_java_utils.TokenRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class CreateBlogService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BlogMapper blogMapper;

    public Map<String, Object> uploadBlog(MultipartFile[] files, String text, String isPublic, String token) throws Exception {
        ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
        //去检测是否登录(根据传过来的token)
        Map<String, Object> map = TokenRedis.hasLogin(redis, token);
        //如果已经登录
        if ((boolean) map.get("loginStatus")) {
            Blog blog = new Blog();
            String account = redis.get(token);
            Map<String, Object> idByAccount = userMapper.getIdByAccount(account);
            String id = idByAccount.get("id") + "";
            Date date = new Date();
            String image = "";
            String video = "";
            if (files != null) {
                String realPath = ResourceUtils.getURL("classpath:").getPath();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd/");
                String path = simpleDateFormat.format(date);
                for (MultipartFile file : files) {
                    String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                    String fileName = UUID.randomUUID().toString() + suffix;
                    File directory = new File(realPath + "/static/upload/" + path);
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }
                    if ((".mp4").equals(suffix)){
                        video += path + fileName + ";";
                    }else {
                        image += path + fileName + ";";
                    }
                    System.out.println("路径为" + directory.getPath());
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
            blogMapper.insertSelective(blog);
        }
        return map;
    }
}
