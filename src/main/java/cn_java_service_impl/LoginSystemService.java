package cn_java_service_impl;

import cn_java_PO.User;
import cn_java_mapper.UserMapper;
import cn_java_utils.MD5;
import cn_java_utils.TokenRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class LoginSystemService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 根据token判断是否登录
     * @param token
     * @return
     */
    public Map<String, Object> loginOrOut(String token){
        ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
        //去工具类判断token是否在redis中存在来判断是否登录
        return TokenRedis.hasLogin(redis, token);
    }

    /**
     * 根据账号和密码来判断用户是否存在从而进行登录
     * @param account:账号
     * @param password:密码
     * @return
     * @throws Exception
     */
    public Map<String, Object> userIsExists(String account, String password) throws Exception{
        ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
        Map<String, Object> map = new HashMap<>();
        //如果传过来的数据有一个为null则直接返回错误信息
        if (account == null || password == null){
            map.put("loginStatus", false);
            return map;
        }
        String encryption = MD5.getEncryption(password);
        //去数据库判断用户是否存在
        int result = userMapper.userIsExists(account, encryption);
        //如果存在
        if (result == 1){
            //生成token,将token返回给前端,并且将token存入redis来保持用户的登录状态
            String token = UUID.randomUUID().toString().replaceAll("-", "") + account;
            String avatar = userMapper.getAvatar(account);
            TokenRedis.tokenToRedis(redis, token, account, avatar);
            map.put("token", token);
            map.put("loginStatus", true);
        }else {
            map.put("loginStatus", false);
        }
        return map;
    }

    /**
     * 判断注册时的账号是否已经存在
     * @param account
     * @return
     */
    public Map<String, Object> accountIsExists(String account){
        int result = userMapper.accountIsExists(account);
        Map<String, Object> map = new HashMap<>();
        map.put("result", result);
        return map;
    }

    public Map<String, Object> registerUser(User user, BindingResult errorResult) throws Exception{
        Map<String, Object> map = new HashMap<>();
        //对数据进行校验
        if (!errorResult.hasErrors()){
            //将account再次拿到数据库中进行校验是否已经存在
            int result1 = userMapper.accountIsExists(user.getAccount());
            //如果已经存在则直接返回错误信息
            if (result1 == 1){
                map.put("result", 0);
                return map;
            }
            ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
            //对password进行加密
            user.setPassword(MD5.getEncryption(user.getPassword()));
//            将数据插入数据库中,从而完成用户的注册
            int result = userMapper.insertSelective(user);
            //生成token
            String token = UUID.randomUUID().toString().replaceAll("-", "") + user.getAccount();
            //将token存放到redis中,并且将初始的头像信息也存放到redis中
            TokenRedis.tokenToRedis(redis, token, user.getAccount(), "default.png");
            //返回是否成功的结果和token
            map.put("token", token);
            map.put("result", result);
            return map;
        }
        map.put("result", 0);
        return map;
    }
}
