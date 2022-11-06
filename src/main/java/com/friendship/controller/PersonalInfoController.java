package com.friendship.controller;

import com.friendship.pojo.Code;
import com.friendship.pojo.Result;
import com.friendship.pojo.User;
import com.friendship.service.impl.PersonalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@SuppressWarnings("all")
@RequestMapping("/users")
@CrossOrigin(originPatterns = {"http://localhost:8081/"}, allowCredentials = "true")
public class PersonalInfoController {

    @Autowired
    private PersonalInfoService personalInfoService;

    /**
     * 根据token得到是否登录的状态信息,如果登录,则返回头像的地址,没有登录则展示没有登录的图标信息
     * @param request
     * @return
     */
    @GetMapping("/avatars")
    public Result getAvatar(HttpServletRequest request){
        String token = request.getHeader("token");
        //将token带到Service层去redis中校验是否登录,登录则获取头像的地址
        Map<String, Object> avatar = personalInfoService.getAvatar(token);
        //将得到的结果返回给前端
        return new Result(Code.SELECT_OK, avatar);
    }

    /**
     * 实现用户头像的上传
     * @param avatar:前端传过来的裁剪图片的base64格式的数据
     * @param request
     * @return
     * @throws Exception
     */
    @PutMapping("/avatars")
    public Result updateAvatar(String avatar, HttpServletRequest request) throws Exception{
        //得到token
        String token = request.getHeader("token");
        int result = personalInfoService.uploadAvatar(avatar, token);
        if (result == 1){
            return new Result(Code.UPDATE_OK, "更新成功");
        }else {
            return new Result(Code.UPDATE_ERR, "更新失败");
        }
    }

    //得到用户的全部信息
    @GetMapping
    public Result getUserInfo(HttpServletRequest request){
//        System.out.println("访问了.............................");
        String token = request.getHeader("token");
        Map<String, Object> allInfo = personalInfoService.getUserInfo(token);
        return new Result(Code.SELECT_OK, allInfo);
    }

    @GetMapping("/{id}")
    public Result getUserInfoById(@PathVariable String id){
        Map<String, Object> allInfo = personalInfoService.getUserInfoById(id);
        if (allInfo == null){
            return new Result(Code.SELECT_ERR, "没有该用户");
        }
        return new Result(Code.SELECT_OK, allInfo);
    }

    //更新用户的信息
    @PutMapping
    public Result updateUserInfo(HttpServletRequest request, User user){
        String token = request.getHeader("token");
        int result = personalInfoService.updateUserInfo(token, user);
        if (result == 1){
            return new Result(Code.UPDATE_OK, "更新成功");
        }else {
            return new Result(Code.UPDATE_ERR, "更新失败");
        }
    }
}
