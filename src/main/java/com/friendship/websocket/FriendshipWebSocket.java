package com.friendship.websocket;

import com.friendship.mapper.ChatMessageMapper;
import com.friendship.pojo.ChatMessage;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

//操作用户聊天信息的websocket
@ServerEndpoint("/chatWebSocket")
@Component
public class FriendshipWebSocket {

    private static ChatMessageMapper chatMessageMapper;

    private static StringRedisTemplate stringRedisTemplate;

    @Autowired
    public void setChatMessageMapper(ChatMessageMapper chatMessageMapper) {
        FriendshipWebSocket.chatMessageMapper = chatMessageMapper;
    }

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        FriendshipWebSocket.stringRedisTemplate = stringRedisTemplate;
    }

    private Session session;

    private Long userId;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    //连接建立触发
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("FriendshipWebSocket连接成功!!!!");
    }

    //收到信息触发
    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        Gson g = new Gson();
        Map map = g.fromJson(message, Map.class);
        System.out.println("传过来的数据" + message);
        if (map.size() == 2) {
            userId = Long.valueOf((String) map.get("user_id"));
            Long toId = Long.valueOf((String) map.get("to_id"));
            ChatMessageContainer.userMap.get(userId).setSession(session);
            if (ChatMessageContainer.messageMap.get(userId) != null
                    &&
                    ChatMessageContainer.messageMap.get(userId).get(toId) != null) {
                ChatMessageContainer.messageMap.get(userId).get(toId).clear();
            }
        } else {
            ChatMessage chatMessage = g.fromJson(message, ChatMessage.class);
            chatMessage.setUserId(Long.valueOf((String) map.get("user_id")));
            chatMessage.setToId(Long.valueOf((String) map.get("to_id")));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            chatMessage.setCreateTime(simpleDateFormat.format(new Date()));
            chatMessage.setIsDelete(0);
            chatMessageMapper.insert(chatMessage);
            FriendshipWebSocket toUser = ChatMessageContainer.userMap.get(chatMessage.getToId());
            Map<String, Object> messageMap = new HashMap<>();
            messageMap.put("content", chatMessage.getContent());
            messageMap.put("createTime", chatMessage.getCreateTime());
            messageMap.put("fromUserAvatar", "http://localhost:8080/static/upload/" +
                    stringRedisTemplate.opsForHash().get("user_" + chatMessage.getUserId(), "avatar"));
            messageMap.put("fromId", chatMessage.getUserId());
            if (toUser != null && toUser.getSession() != null) {
                toUser.getSession().getBasicRemote().sendText(g.toJson(messageMap));
            } else {
                Long toId = chatMessage.getToId();
                if (ChatMessageContainer.messageMap.get(toId) == null) {
                    List<Map<String, Object>> messageList = Collections.synchronizedList(new ArrayList<>());
                    messageList.add(messageMap);
                    Map<Long, List<Map<String, Object>>> unreadMessageMap = new ConcurrentHashMap<>();
                    unreadMessageMap.put(chatMessage.getUserId(), messageList);
                    ChatMessageContainer.messageMap.put(toId, unreadMessageMap);
                } else if (ChatMessageContainer.messageMap.get(toId).get(chatMessage.getUserId()) == null) {
                    List<Map<String, Object>> messageList = Collections.synchronizedList(new ArrayList<>());
                    messageList.add(messageMap);
                    ChatMessageContainer.messageMap.get(toId).put(chatMessage.getUserId(), messageList);
                } else {
                    ChatMessageContainer.messageMap.get(toId).get(chatMessage.getUserId()).add(messageMap);
                }
            }
        }
    }

    //连接关闭触发
    @OnClose
    public void onClose(Session session) {
        System.out.println("FriendshipWebSocket离开");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("错误代码");
        throwable.printStackTrace();
    }
}
