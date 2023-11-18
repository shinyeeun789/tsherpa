package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.ChatMessage;
import com.chunjae.pro05.entity.ChatRoom;

import java.util.List;

public interface ChatService {

    public List<ChatRoom> findAllRoom(String name);
    public ChatRoom findRoomById(String roomId);
    public int createRoom(ChatRoom chatRoom);
    public List<ChatMessage> findChatById(String roomId);
    public int insertChat(ChatMessage chatMessage);
    public int findChatDist(ChatRoom chatRoom);
    public int actUpdate(int tno);
    public int chatDisabled(String roomId);

}
