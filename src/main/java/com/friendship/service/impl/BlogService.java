package com.friendship.service.impl;

import com.friendship.mapper.BlogLikeMapper;
import com.friendship.pojo.Blog;
import com.friendship.mapper.BlogMapper;
import com.friendship.mapper.UserMapper;
import com.friendship.utils.TokenRedis;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@SuppressWarnings("all")
public class BlogService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private FriendService friendService;
    @Autowired
    private BlogLikeMapper blogLikeMapper;

    /**
     * 用来上传博客
     *
     * @param files:    博客所包含的文件对象
     * @param text:     博客的内容
     * @param isPublic: 博客的权限等级
     * @param token:    传过来的token数据
     * @throws Exception
     * @return: 返回是否操作成功
     */
    public int uploadBlog(MultipartFile[] files, String text, String isPublic, String token) throws Exception {
        ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
        Blog blog = new Blog();
        String id = redis.get(token);
        LocalDate date = LocalDate.now();
        String image = "";
        String video = "";
        if (files != null) {
            String realPath = ResourceUtils.toURI(ResourceUtils.getURL("classpath:")).getPath();
//            String realPath = System.getProperty("user.dir");
            String path = DateTimeFormatter.ofPattern("yyyy/MM/dd/").format(date);
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
        String format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(date);
        blog.setCreateTime(format);
        blog.setIsPublic(isPublic);
        blog.setVideo(video);
        blog.setImage(image);
        blog.setContent(text);
        blog.setUserId(Long.valueOf(id));
        int i = blogMapper.insert(blog);
        return i;
    }

    /**
     * 处理要在around页面展示的博客或者在个人主页展示的博客信息
     *
     * @param request: 包含token的请求对象
     * @param id:      如果是展示个人主页的博客, 则根据这个用户id来获取相应用户的全部博客信息
     * @return: 博客信息
     */
    public List<Map<String, Object>> displayBlog(HttpServletRequest request, String... id) {
        List<Map<String, Object>> mapList;
        //如果id数组长度为0则代表是访问了around这个页面
        String token = request.getHeader("token");
        Map<String, Object> loginMap = TokenRedis.hasLogin(stringRedisTemplate, token);
        //通过token去判断登录情况
        Boolean loginStatus = (Boolean) loginMap.get("loginStatus");
        if (id.length == 0) {
            //得到公开的博客信息
            mapList = blogMapper.displayAroundBlog();
        } else {
            mapList = blogMapper.displayBlogByUserId(Long.valueOf(id[0]));
        }
        for (Map<String, Object> map : mapList) {
            String userId = map.get("user_id") + "";
            processBlogData(map);
            //访问around和主页博客页面时把登录状态信息封装到每一条博客信息中
            map.put("loginStatus", loginStatus);
            if (loginStatus) {
                //如果登录, 还查询用户是否点赞了该博客
                String realUserId = stringRedisTemplate.opsForValue().get(request.getHeader("token"));
                map.put("isLike", blogLikeMapper.queryIsLike(Long.valueOf(realUserId), Long.valueOf(map.get("id") + "")));
                //如果登录了, 就判断该博客的拥有者是否为本人
                boolean isSelf = TokenRedis.isSelf(stringRedisTemplate.opsForValue(), token, Long.valueOf(userId));
                map.put("isSelf", isSelf);
                //如果不是本人的博客, 则查询此博客的拥有者与本人之间的关系
                if (!isSelf) {
                    int i = friendService.queryRelation(token, Long.valueOf(userId));
                    map.put("relation", i);
                }
            }
        }
        return mapList;
    }

    /**
     * 根据id删除对应的博客
     *
     * @param id: 要删除博客的id
     * @return: 是否成功删除
     */
    public int deleteBlogById(Long id) {
        return blogMapper.deleteBlogById(id);
    }

    /**
     * 通过博客的id来获取博客信息进行展示
     *
     * @param request: 包含token的HttpServletRequest对象
     * @param id:博客id
     * @return: 返回处理好的博客数据
     */
    public Map<String, Object> commentBlogById(HttpServletRequest request, Long id) {
        String token = request.getHeader("token");
        Boolean loginStatus;
        String selfId = null;
        String selfAvatar = null;
        if (token != null) {
            selfId = stringRedisTemplate.opsForValue().get(token);
            selfAvatar = (String) stringRedisTemplate.opsForHash().get("user_" + selfId, "avatar");
            Map<String, Object> statusMap = TokenRedis.hasLogin(stringRedisTemplate, token);
            //通过token去判断登录情况
            loginStatus = (Boolean) statusMap.get("loginStatus");
        } else {
            loginStatus = false;
        }
        Map<String, Object> map = blogMapper.getBlogById(id);
        String userId = map.get("user_id") + "";
        processBlogData(map);
        //访问around页面时把登录状态信息封装到每一条博客信息中
        map.put("loginStatus", loginStatus);
        if (loginStatus) {
            //如果登录了, 就判断该博客的拥有者是否为本人
            boolean isSelf = TokenRedis.isSelf(stringRedisTemplate.opsForValue(), token, Long.valueOf(userId));
            //查询此博客是否被用户自己点赞
            map.put("isLike", blogLikeMapper.queryIsLike(Long.valueOf(selfId), Long.valueOf(map.get("id") + "")));
            map.put("is_self", isSelf);
            map.put("selfId", selfId);
            map.put("selfNickname", stringRedisTemplate.opsForHash().get("user_" + selfId, "nickname"));
            map.put("selfAvatar", "http://localhost:8080/static/upload/" + selfAvatar);
            //如果不是本人的博客, 则查询此博客的拥有者与本人之间的关系
            if (!isSelf) {
                int i = friendService.queryRelation(token, Long.valueOf(userId));
                map.put("relation", i);
            }
        }
        return map;
    }

    /**
     * 处理博客数据
     *
     * @param map: 要进行处理的博客数据
     */
    private void processBlogData(Map<String, Object> map) {
        String userId = map.get("user_id") + "";
        map.put("user_nickname", stringRedisTemplate.opsForHash().get("user_" + userId, "nickname"));
        map.put("user_avatar", "http://localhost:8080/static/upload/" + stringRedisTemplate.opsForHash().get("user_" + userId, "avatar"));
        String[] image = map.get("image").toString().split(";");
        List<String> imageStream = Stream.of(image).map(i -> "http://localhost:8080/static/upload/" + i).toList();
        map.put("image", imageStream);
        String[] video = map.get("video").toString().split(";");
        List<String> videoList = Stream.of(video).map(v -> "http://localhost:8080/static/upload/" + v).toList();
        map.put("video", videoList);
        map.put("user_url", "http://localhost:8081/u/" + map.get("user_id") + "/blog");
    }
}
