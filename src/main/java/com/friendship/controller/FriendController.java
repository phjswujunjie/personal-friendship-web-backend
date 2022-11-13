package com.friendship.controller;

import com.friendship.pojo.Code;
import com.friendship.pojo.Result;
import com.friendship.service.impl.FriendService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@SuppressWarnings("all")
@RequestMapping("/friends")
@CrossOrigin(originPatterns = {"http://localhost:8081/"}, allowCredentials = "true")
public class FriendController {
    @Autowired
    private FriendService friendService;

    //当用户访问其他用户主页时, 判断其他用户是否被使用者关注
    @GetMapping("/{otherId}")
    public Result queryIsFollower(@PathVariable Long otherId, HttpServletRequest request){
        String token = request.getHeader("token");
        int i = friendService.queryRelation(token, otherId);
        if(i == 2){
            return new Result(Code.IS_A_FRIEND, "是朋友关系");
        }else if (i == 0){
            return new Result(Code.NOT_HAVE_RELATION, "二者没有关系");
        } else if (i == 4) {
            return new Result(Code.IS_A_FOLLOW, "访问者关注了被访问者");
        }else {
            return new Result(Code.IS_A_FANS, "被访问者为访问者的粉丝");
        }
    }

    //添加关注
    @PostMapping("/{followId}")
    public Result addFriend(@PathVariable Long followId, HttpServletRequest request){
        String token = request.getHeader("token");
        int i = friendService.addFriend(token, followId);
        if (i == 1){
            return new Result(Code.INSERT_OK, "添加成功");
        }else {
            return new Result(Code.INSERT_ERR, "添加失败");
        }
    }

    //取消关注
    @DeleteMapping("/{followId}")
    public Result deleteFriend(@PathVariable Long followId, HttpServletRequest request){
        String token = request.getHeader("token");
        int i = friendService.deleteFriend(token, followId);
        if (i == 1){
            return new Result(Code.DELETE_OK, "取消关注成功!");
        }else {
            return new Result(Code.DELETE_ERR, "取消关注失败!");
        }
    }

    //得到用户的全部关注
    @GetMapping("/follow")
    public Result getAllFollowInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        List<List<Map<String, Object>>> allFollowInfo = friendService.getAllFollowOrFansInfo(token, 1);
        return new Result(Code.SELECT_OK, allFollowInfo);
    }

    @GetMapping
    public Result getAllFriendInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        List<Map<String, Object>> allFollowInfo = friendService.getAllFriendInfo(token);
        return new Result(Code.SELECT_OK, allFollowInfo);
    }

    @GetMapping("/fans")
    public Result getAllFansInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        List<List<Map<String, Object>>> allFansInfo = friendService.getAllFollowOrFansInfo(token, 2);
        return new Result(Code.SELECT_OK, allFansInfo);
    }
}
