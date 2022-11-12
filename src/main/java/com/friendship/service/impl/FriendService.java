package com.friendship.service.impl;

import com.friendship.mapper.FriendlyRelationshipMapper;
import com.friendship.mapper.UserMapper;
import com.friendship.pojo.FriendlyRelationship;
import com.friendship.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FriendService {
    @Autowired
    private FriendlyRelationshipMapper friendMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public int addFriend(String token, Long followId) {
        Long userId = Long.valueOf(Objects.requireNonNull(stringRedisTemplate.opsForValue().get(token)));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        FriendlyRelationship friend = new FriendlyRelationship(userId, followId, format);
        int result = friendMapper.relationshipIsExist(userId, followId);
        if (result == 1) {
            return friendMapper.addFriendlyRelationship(userId, followId);
        } else {
            return friendMapper.insert(friend);
        }
    }

    public int queryIsFollower(String token, Long followId) {
        Long userId = Long.valueOf(Objects.requireNonNull(stringRedisTemplate.opsForValue().get(token)));
        List<Map<String, Object>> mapList = friendMapper.queryRelation(userId, followId);
        //如果结果数量为0则代表没有关系, 为2则代表为朋友关系
        if (mapList.size() != 1) {
            return mapList.size();
        }
        //为1则代表两个人之间有关注关系(但是不知道谁关注了谁)
        Map<String, Object> map = mapList.get(0);
        Long user_id = Long.valueOf(map.get("user_id") + "");
        if (user_id.equals(userId)) {
            //访问者关注了被访问者
            return 4;
        }
        //被访问者为访问者的粉丝
        return 8;
    }

    public int deleteFriend(String token, Long followId) {
        Long userId = Long.valueOf(Objects.requireNonNull(stringRedisTemplate.opsForValue().get(token)));
        return friendMapper.deleteFriend(userId, followId);
    }

    public List<List<Map<String, Object>>> getAllFollowOrFansInfo(String token, int flag) {
        List<List<Map<String, Object>>> list = new LinkedList<>();
        List<Map<String, Object>> listFollowOrFans = new LinkedList<>();
        List<Map<String, Object>> listFriend = new LinkedList<>();
        List<Long> allFollowInfo;
        Long userId = Long.valueOf(Objects.requireNonNull(stringRedisTemplate.opsForValue().get(token)));
        if (flag == 1) {
            allFollowInfo = friendMapper.getFollow(userId);
        } else {
            allFollowInfo = friendMapper.getFans(userId);
        }
        getUserInfo(listFollowOrFans, allFollowInfo);
        List<Long> allFriendInfo = friendMapper.getFriends(userId);
        getUserInfo(listFriend, allFriendInfo);
        list.add(listFollowOrFans);
        list.add(listFriend);
        return list;
    }

    public List<Map<String, Object>> getAllFriendInfo(String token) {
        List<Map<String, Object>> listFriend = new LinkedList<>();
        Long userId = Long.valueOf(Objects.requireNonNull(stringRedisTemplate.opsForValue().get(token)));
        List<Long> allFriendInfo = friendMapper.getFriends(userId);
        getUserInfo(listFriend, allFriendInfo);
        return listFriend;
    }

    private void getUserInfo(List<Map<String, Object>> listFriend, List<Long> allFriendInfo) {
        if (allFriendInfo.size() != 0) {
            List<User> users = userMapper.selectBatchIds(allFriendInfo);
            for (User user : users) {
                Map<String, Object> map = new HashMap<>();
                map.put("homepageLocation", "http://localhost:8081/u/" + user.getId() + "/blog");
                map.put("id", user.getId());
                map.put("avatar", "https://localhost:8443/static/upload/" + user.getAvatar());
                map.put("nickname", user.getNickname());
                map.put("introduction", user.getIntroduction());
                listFriend.add(map);
            }
        }
    }
}
