package com.friendship.service.impl;

import com.friendship.pojo.Code;
import com.friendship.pojo.Result;
import com.friendship.pojo.User;
import com.friendship.mapper.UserMapper;
import com.friendship.utils.MD5;
import com.friendship.utils.TokenRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.text.SimpleDateFormat;
import java.util.Date;
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
     *
     * @param token
     * @return
     */
    public Map<String, Object> loginOrOut(String token) {
        ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
        //去工具类判断token是否在redis中存在来判断是否登录
        return TokenRedis.hasLogin(redis, token);
    }

    /**
     * 根据账号和密码来判断用户是否存在从而进行登录
     *
     * @param account:账号
     * @param password:密码
     * @return
     * @throws Exception
     */
    public Map<String, Object> userIsExists(String account, String password) throws Exception {
        ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
        Map<String, Object> map = new HashMap<>();
        //如果传过来的数据有一个为null则直接返回错误信息
        if (account == null || password == null) {
            map.put("loginStatus", false);
            return map;
        }
        String encryption = MD5.getEncryption(password);
        //去数据库判断用户是否存在
        int result = userMapper.userIsExists(account, encryption);
        //如果存在
        if (result == 1) {
            Map<String, Object> idByAccount = userMapper.getIdByAccount(account);
            String id = idByAccount.get("id") + "";
            //生成token,将token返回给前端,并且将token存入redis来保持用户的登录状态
            String token = UUID.randomUUID().toString().replaceAll("-", "") + id;
            String avatar = userMapper.getAvatar(id);
            TokenRedis.tokenToRedis(redis, token, id, avatar);
            map.put("token", token);
            map.put("loginStatus", true);
        } else {
            map.put("loginStatus", false);
        }
        return map;
    }

    /**
     * 判断注册时的账号是否已经存在
     *
     * @param account
     * @return
     */
    public Result accountIsExists(String account) {
        int result = userMapper.accountIsExists(account);
        if (result == 1) {
            return new Result(Code.ACCOUNT_EXIST, "账号已经存在");
        }
        return new Result(Code.ACCOUNT_NON_EXIST, "账号处于空闲状态");
    }

    public Result registerUser(User user, BindingResult errorResult) throws Exception {
        //对数据进行校验
        if (!errorResult.hasErrors()) {
            //将account再次拿到数据库中进行校验是否已经存在
            int result1 = userMapper.accountIsExists(user.getAccount());
            //如果已经存在则直接返回错误信息
            if (result1 == 1) {
                return new Result(Code.INSERT_ERR, "注册失败");
            }
            ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
            //对password进行加密
            user.setPassword(MD5.getEncryption(user.getPassword()));
//            将数据插入数据库中,从而完成用户的注册
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(date);
            user.setRegisterTime(format);
            int result = userMapper.insertSelective(user);
            //生成token
            String id = userMapper.getIdByAccount(user.getAccount()).get("id") + "";
            String token = UUID.randomUUID().toString().replaceAll("-", "") + id;
            //将token存放到redis中,并且将初始的头像信息也存放到redis中
            TokenRedis.tokenToRedis(redis, token, id, "default.png");
            Map<String, Object> map = new HashMap<>();
            //返回是否成功的结果和token
            map.put("token", token);
            return new Result(Code.INSERT_OK, map);
        }
        return new Result(Code.INSERT_ERR, "注册失败");
    }
}
