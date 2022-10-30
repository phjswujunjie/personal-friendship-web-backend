package cn_java_utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class TokenRedis {


    public static void tokenToRedis(ValueOperations<String, String> redis, String token, String account, String avatar) {
        //登录或者注册的话就将生成的token和account存入redis,以此来保持用户的登录状态(为什么要存放两个相反的键值对?用来实现单点登录,当同一个账号
        // 在主机1登录时会生成token1,并且将account和token1存放到redis中, 当在主机2登录时会生成token2,此时account对应的token就会从token1
        // 变成token2,从而使主机1的token失效(看loginOrOut工具类判断是否登录的源代码),登录状态解除)
        redis.set(token, account, 7 * 24 * 60 * 60, TimeUnit.SECONDS);
        redis.set(account, token, 7 * 24 * 60 * 60, TimeUnit.SECONDS);
        redis.set(account+ "avatar", avatar, 7 * 24 * 60 * 60, TimeUnit.SECONDS);
    }

    /**
     * 根据token来判断是否登录
     * @param redis
     * @param token
     * @return
     */
    public static Map<String, Object> hasLogin(ValueOperations<String, String> redis, String token){
        Map<String, Object> map = new HashMap<>();
        //如果发过来的token为null,则直接返回没有登录的状态信息
        if (token == null){
            map.put("loginStatus", false);
            return map;
        }
        //通过token得到account
        String account = redis.get(token);
        //通过account不为null的话,则开始判断account对应的token是否与用户发过来的token相同,相同的话则说明已经登录,不相同则说明该账号为多点登录
        // ,保留最新的登录状态,解除其它的登录状态,从而实现单点登录,
        if (account != null){
            String dbToken = redis.get(account);
            if (Objects.equals(dbToken, token)){
                //如果返回结果显示已经登录,则将redis中的登录状态时间重新刷新
                map.put("loginStatus", true);
                //这里的重新设置状态时间操作所用的值全为redis中的值,从而避免了对数据库读取的操作
                String avatar = redis.get(account + "avatar");
                TokenRedis.tokenToRedis(redis, token, account, avatar);
                return map;
            }
        }
        //如果account不存在或者account对应的token与用户发送过来的token不一样,则直接返回没有登录的状态信息
        map.put("loginStatus", false);
        return map;
    }
}
