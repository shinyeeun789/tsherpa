package com.chunjae.pro05.config;

import com.chunjae.pro05.biz.ChatService;
import com.chunjae.pro05.entity.ChatMessage;
import com.chunjae.pro05.entity.ChatRoom;
import com.chunjae.pro05.entity.MessageType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;


@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    @Autowired
    private final ChatService chatService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception { }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());

        if (room != null) { // room이 null이 아닌 경우에만 처리
            Set<WebSocketSession> sessions = room.getSessions();   // 방에 있는 현재 사용자 한명이 WebsocketSession

            if (chatMessage.getType().equals(MessageType.ENTER)) {
                // 사용자가 방에 입장하면 Enter 메세지를 보내도록 해놓음.
                // 이것은 새로운 사용자가 socket 연결한 것과는 다름.
                // socket 연결은 이 메세지를 보내기 전에 이미 되어있는 상태
                sessions.add(session);
                chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");  // TALK일 경우 msg가 있을 거고, ENTER일 경우 메세지 없으니까 message set
                sendToEachSocket(sessions, new TextMessage(objectMapper.writeValueAsString(chatMessage)));
            } else if (chatMessage.getType().equals(MessageType.QUIT)) {
                sessions.remove(session);
                chatMessage.setMessage(chatMessage.getSender() + "님이 퇴장했습니다..");
                sendToEachSocket(sessions, new TextMessage(objectMapper.writeValueAsString(chatMessage)));
            } else {
                sendToEachSocket(sessions, message); // 입장, 퇴장 아닐 때는 클라이언트로부터 온 메세지 그대로 전달.
            }
        }
    }
    private  void sendToEachSocket(Set<WebSocketSession> sessions, TextMessage message){
        sessions.parallelStream().forEach( roomSession -> {
            try {
                roomSession.sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception { }
}
