package com.friendship;

import com.friendship.mapper.CommentMapper;
import com.friendship.mapper.FriendlyRelationshipMapper;
import com.friendship.mapper.UserMapper;
import com.friendship.service.impl.FriendService;
import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
public class SpringApplicationTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FriendlyRelationshipMapper friendlyRelationshipMapper;

    @Autowired
    private FriendService friendService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test(){
//        long l = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++) {
//            List<Long> friends = friendlyRelationshipMapper.getFriends1(3L);
//        }
//        long l1 = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++) {
//            List<Long> friends1 = friendlyRelationshipMapper.getFriends(3L);
//        }
//        long l2 = System.currentTimeMillis();
//        System.out.println("l1 - l: " + (l1 - l));
//        System.out.println("l2 - l1: " + (l2 - l1));
//        List<Map<String, Object>> i = friendlyRelationshipMapper.queryRelation(3L, 19L);
//        System.out.println(i);
//        List<List<Map<String, Object>>> allFollowInfo = friendService.getAllFollowInfo("1c6fe1ae38fb4a32a42199ee0fda140a3");
//        System.out.println(allFollowInfo);
        Long aLong = commentMapper.selectCount(null);
        System.out.println(aLong);
        System.out.println(commentMapper.selectList(null));
    }

    @Test
    public void redis(){
        HashOperations<String, Object, Object> hashOperations = stringRedisTemplate.opsForHash();
//        Map<String, Object> allInfoById = userMapper.getAllInfoById(3L);
//        System.out.println(allInfoById);
//        allInfoById.put("id", allInfoById.get("id") + "");
//        hashOperations.putAll("user_3", allInfoById);
        Map<String, Object> map = new HashMap<>();
        Map<Object, Object> user_3 = hashOperations.entries("user_3");
        System.out.println(user_3.get("avatar"));
    }
}