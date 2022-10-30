package cn_java_controller;

import cn_java_PO.User;
import cn_java_mapper.UserMapper;
import cn_java_service_impl.LoginSystemService;
import cn_java_utils.TokenRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin(originPatterns = {"https://localhost:8443"}, allowCredentials = "true")
@SuppressWarnings("all")
public class LoginSystemController {

    @Autowired
    private LoginSystemService loginSystemService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/login")
    public String login(){
       return "/Login.html";
    }


    @RequestMapping("/register")
    public String register(){
        return "/Register.html";
    }

    @RequestMapping("/homepage")
    public String homepage(){
        return "/Homepage.html";
    }

    @RequestMapping("/")
    public String index(){
        return "/Homepage.html";
    }

    /**
     * 检查是否登录
     * @param request
     * @return
     */
    @RequestMapping("/loginOrOut")
    @ResponseBody
    public Map<String, Object> loginOrOut(HttpServletRequest request){
        //得到前端带过来的token
        String token = request.getHeader("token");
        //去业务层进行判断
        Map<String, Object> map = loginSystemService.loginOrOut(token);
        return map;
    }

    /**
     * 检查账号和密码是否存在
     * @param account
     * @param password
     * @return
     * @throws Exception
     */
    @RequestMapping("/loginExamine")
    @ResponseBody
    public Map<String, Object> loginExamine(String account, String password) throws Exception{
        Map<String, Object> map = loginSystemService.userIsExists(account, password);
        return map;
    }

    /**
     * 判断注册时用户输入的账号是否已经存在
     * @param account:前端传过来的账号
     * @return
     */
    @RequestMapping("/accountIsExists")
    @ResponseBody
    public Map<String, Object> accountIsExists(String account){
        Map<String, Object> map = loginSystemService.accountIsExists(account);
        return map;
    }

    /**
     * 实现用户的注册
     * @param user:传过来的注册信息
     * @param result
     * @return
     * @throws Exception
     */
    @RequestMapping("/registerUser")
    @ResponseBody
    public Map<String, Object> registerUser(@Valid User user, BindingResult result) throws Exception{
        Map<String, Object> map = loginSystemService.registerUser(user, result);
        return map;
    }
}
