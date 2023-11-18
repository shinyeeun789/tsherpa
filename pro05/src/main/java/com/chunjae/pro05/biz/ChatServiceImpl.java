package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.ChatMessage;
import com.chunjae.pro05.entity.ChatRoom;
import com.chunjae.pro05.persis.ChatMapper;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private Map<String, ChatRoom> chatRooms;

    @Autowired
    private ChatMapper chatMapper;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    @Override
    public List<ChatRoom> findAllRoom(String name) {
        List<ChatRoom> chatRoomList = chatMapper.findAllRoom(name);
        for (ChatRoom cr : chatRoomList) {
            chatRooms.put(cr.getRoomId(), cr);
        }
        return new ArrayList<>(chatRooms.values());
    }

    @Override
    public ChatRoom findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    @Override
    public int createRoom(ChatRoom chatRoom) {
        String randomId = UUID.randomUUID().toString();
        ChatRoom newChat = chatRoom.builder()
                .roomId(randomId)
                .name(chatRoom.getName())
                .build();
        chatRooms.put(randomId, newChat);
        chatRoom.setRoomId(randomId);
        int result = chatMapper.createRoom(chatRoom);
        return result;
    }

    @Override
    public List<ChatMessage> findChatById(String roomId) {
        return chatMapper.findChatById(roomId);
    }

    @Override
    public int insertChat(ChatMessage chatMessage) {
        return chatMapper.insertChat(chatMessage);
    }

    @Override
    public int findChatDist(ChatRoom chatRoom) {
        return chatMapper.findChatDist(chatRoom);
    }

    @Override
    public int actUpdate(int tno) {
        return chatMapper.actUpdate(tno);
    }

    @Override
    public int chatDisabled(String roomId) {
        return chatMapper.chatDisabled(roomId);
    }
}
