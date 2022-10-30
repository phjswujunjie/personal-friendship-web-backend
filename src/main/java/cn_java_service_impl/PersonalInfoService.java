package cn_java_service_impl;

import cn_java_PO.User;
import cn_java_mapper.UserMapper;
import cn_java_utils.TokenRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import redis.clients.jedis.Jedis;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PersonalInfoService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Map<String, Object> getAvatar(String token){
        ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
        //去检测是否登录(根据传过来的token)
        Map<String, Object> map = TokenRedis.hasLogin(redis, token);
        //如果已经登录
        if ((boolean)map.get("loginStatus")){
            //则先去redis中通过token得到account,再拿account在redis中得到头像的地址
            String account = redis.get(token);
            String avatar = redis.get(account + "avatar");
            //如果redis中没有该头像地址,则再去数据库中取出,再把头像地址存放到redis中
            if (avatar == null){
                avatar = userMapper.getAvatar(account);
                redis.set(account+ "avatar", avatar, 7 * 24 * 60 * 60, TimeUnit.SECONDS);
            }
            map.put("avatar", avatar);
        }
        //返回结果信息
        return map;
    }

    public Map<String, Object> uploadAvatar(String avatar, String token) throws Exception{
        ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
        //去检测是否登录(根据传过来的token)
        Map<String, Object> map = TokenRedis.hasLogin(redis, token);
        //如果已经登录
        if ((boolean)map.get("loginStatus")){
            //则先去redis中通过token得到account,再拿account去存放头像的地址
            String account = redis.get(token);
            //对base64数据进行解码
            Base64.Decoder decoder = Base64.getDecoder();
            //得到解码后的数据
            byte[] decode = decoder.decode(avatar.substring(22));
            //得到文件的上传地址
            String path = ResourceUtils.getURL("classpath:").getPath() + "static/upload";
            //以日期和小时数来创建文件夹,以此来应对一个文件夹内图片过多的问题
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd/HH");
            String format = simpleDateFormat.format(date);
            //创建相应的文件夹
            File filePath = new File(path + "/" + format);
            if (!filePath.exists()){
                filePath.mkdirs();
            }
            //将图片存取到文件夹中
            String s = UUID.randomUUID().toString();
            FileOutputStream fileOutputStream = new FileOutputStream(path + "/" + format + "/"  + s + ".png");
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(decode);
            bufferedOutputStream.close();
            fileOutputStream.close();
            //将头像的地址信息保存到数据库中
            userMapper.setAvatar(account, format + "/" + s + ".png");
            //更新redis中头像的地址信息
            redis.set(account + "avatar", format + "/" + s + ".png", 7 * 24 * 60 * 60, TimeUnit.SECONDS);
        }
        return map;
    }

    public Map<String, Object> getAllInfo(String token){
        ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
        //去检测是否登录(根据传过来的token)
        Map<String, Object> map = TokenRedis.hasLogin(redis, token);
        //如果已经登录
        if ((boolean)map.get("loginStatus")){
            //则先去redis中通过token得到account,再拿account在数据库中得到个人数据
            String account = redis.get(token);
            Map<String, Object> allInfoByAccount = userMapper.getAllInfoByAccount(account);
            allInfoByAccount.put("loginStatus", true);
            return allInfoByAccount;
        }
        //返回结果信息
        return map;
    }

    public Map<String, Object> updateUserInfo(String token, User user){
        ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
        //去检测是否登录(根据传过来的token)
        Map<String, Object> map = TokenRedis.hasLogin(redis, token);
        //如果已经登录
        if ((boolean)map.get("loginStatus")){
            //则先去redis中通过token得到account,再拿account在数据库中更新个人数据
            String account = redis.get(token);
            user.setAccount(account);
            userMapper.UpdateUserInfoByAccount(user);
        }
        return map;
    }
}
