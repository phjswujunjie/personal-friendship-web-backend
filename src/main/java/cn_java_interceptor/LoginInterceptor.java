package cn_java_interceptor;

import cn_java_PO.Code;
import cn_java_PO.Result;
import cn_java_utils.TokenRedis;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equals(request.getMethod())){
            return true;
        }
        if ("/blogs".equals(request.getRequestURI()) && "GET".equals(request.getMethod())){
            return true;
        }
        String token = request.getHeader("token");
        if (token != null){
            ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
            Map<String, Object> map = TokenRedis.hasLogin(redis, token);
            if ((boolean) map.get("loginStatus")){
                return true;
            }
        }
        Gson gson = new Gson();
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(gson.toJson(new Result(Code.LOGIN_ERR, "登录信息失效, 请重新登陆")).getBytes(StandardCharsets.UTF_8));
        return false;
    }
}
