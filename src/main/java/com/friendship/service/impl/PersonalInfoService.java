package com.friendship.service.impl;

import com.friendship.mapper.FriendlyRelationshipMapper;
import com.friendship.pojo.User;
import com.friendship.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class PersonalInfoService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FriendlyRelationshipMapper friendMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Map<String, Object> getAvatar(String token) {
        ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
        Map<String, Object> map = new HashMap<>();
        //先去redis中通过token得到id,再拿id在redis中得到头像的地址
        String id = redis.get(token);
        String avatar = redis.get(id + "avatar");
        //如果redis中没有该头像地址,则再去数据库中取出,再把头像地址存放到redis中
        if (avatar == null) {
            avatar = userMapper.getAvatar(Long.valueOf(id));
            redis.set(id + "avatar", avatar, 7 * 24 * 60 * 60, TimeUnit.SECONDS);
        }
        map.put("id", id);
        map.put("avatar", avatar);
        //返回结果信息
        return map;
    }

    public int uploadAvatar(String avatar, String token) throws Exception {
        ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
        //则先去redis中通过token得到id,再拿id去存放头像的地址
        String id = redis.get(token);
        //对base64数据进行解码
        Base64.Decoder decoder = Base64.getDecoder();
        //得到解码后的数据
        byte[] decode = decoder.decode(avatar.substring(22));
        //得到文件的上传地址
        String path = ResourceUtils.toURI(ResourceUtils.getURL("classpath:")).getPath() + "static/upload";
        //打包成jar包后, 要将保存的上传目录路径改为这个
//        String path = System.getProperty("user.dir") + "/static/upload";
        //以日期和小时数来创建文件夹,以此来应对一个文件夹内图片过多的问题
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd/HH");
        String format = simpleDateFormat.format(date);
        //创建相应的文件夹
        File filePath = new File(path + "/" + format);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        //将图片存取到文件夹中
        String s = UUID.randomUUID().toString();
        FileOutputStream fileOutputStream = new FileOutputStream(path + "/" + format + "/" + s + ".png");
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(decode);
        bufferedOutputStream.close();
        fileOutputStream.close();
        //将头像的地址信息保存到数据库中
        int i = userMapper.setAvatar(Long.valueOf(id), format + "/" + s + ".png");
        //更新redis中头像的地址信息
        redis.set(id + "avatar", format + "/" + s + ".png", 7 * 24 * 60 * 60, TimeUnit.SECONDS);
        return i;
    }

    public Map<String, Object> getUserInfo(String token) {
        ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
        //则先去redis中通过token得到id,再拿id在数据库中得到个人数据
        String id = redis.get(token);
        //返回结果信息
        return userMapper.getAllInfoById(Long.valueOf(id));
    }

    public Map<String, Object> getUserInfoById(String id) {
        Map<String, Object> allInfoById = userMapper.getAllInfoById(Long.valueOf(id));
        Long userId = Long.valueOf(allInfoById.get("id") + "");
        int allFollowNumber = friendMapper.getAllFollowNumber(userId);
        int allFansNumber = friendMapper.getAllFansNumber(userId);
        allInfoById.put("followNumber", allFollowNumber);
        allInfoById.put("fansNumber", allFansNumber);
        return allInfoById;
    }

    public int updateUserInfo(String token, User user) {
        ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
        //则先去redis中通过token得到id,再拿id在数据库中更新个人数据
        String id = redis.get(token);
        user.setId(Long.valueOf(id));
        return userMapper.UpdateUserInfoById(user);
    }
}
