package com.friendship;

import com.friendship.mapper.CommentMapper;
import com.friendship.mapper.FriendlyRelationshipMapper;
import com.friendship.mapper.UserMapper;
import com.friendship.service.impl.FriendService;
import com.friendship.service.impl.PersonalInfoService;
import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;

@SpringBootTest
public class SpringApplicationTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FriendlyRelationshipMapper friendlyRelationshipMapper;

    @Autowired
    private PersonalInfoService personalInfoService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test(){
//        HashOperations<String, Object, Object> hashOperations = stringRedisTemplate.opsForHash();
//        long l = System.currentTimeMillis();
//        for (int i = 0; i < 200000; i++) {
//            Map<String, Object> allInfoById = userMapper.getAllInfoById(3L);
//        }
//        long l1 = System.currentTimeMillis();
//        for (int i = 0; i < 200000; i++) {
//            Map<Object, Object> user_3 = hashOperations.entries("user_3");
//        }
//        long l2 = System.currentTimeMillis();
//        System.out.println("数据库:l1 - l:  " + (l1 - l));
//        System.out.println("redis:l2 - l1:  " + (l2 - l1));
    }

    @Test
    public void redis(){
        Map<String, Object> allInfoById = userMapper.getAllInfoById(3L);
        Map<String, Object> allInfoById1 = userMapper.getAllInfoById(4L);
        Map<String, Object> allInfoById2 = userMapper.getAllInfoById(11L);
        Map<String, Object> allInfoById3 = userMapper.getAllInfoById(18L);
        HashOperations<String, Object, Object> hashOperations = stringRedisTemplate.opsForHash();
        allInfoById.put("id", allInfoById.get("id") + "");
        allInfoById1.put("id", allInfoById1.get("id") + "");
        allInfoById2.put("id", allInfoById2.get("id") + "");
        allInfoById3.put("id", allInfoById3.get("id") + "");
        hashOperations.putAll("user_3", allInfoById);
        hashOperations.putAll("user_4", allInfoById1);
        hashOperations.putAll("user_11", allInfoById2);
        hashOperations.putAll("user_18", allInfoById3);
//        user_3.put("id", "3");
//        hashOperations.putAll("user_3", user_3);
//        Map<Object, Object> user_2 = hashOperations.entries("user_3");
//        System.out.println(user_2);
//        Map<Object, Object> userInfoById = personalInfoService.getUserInfoById("4");
//        System.out.println(userInfoById);
    }
}