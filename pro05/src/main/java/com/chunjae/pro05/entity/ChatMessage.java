package com.chunjae.pro05.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatMessage {

    private long no;                // 번호
    private MessageType type;       // 메시지 타입 : 입장, 채팅, 나감
    private String roomId;          // 방번호
    private String sender;          // 메시지 보낸 사람
    private String message;         // 메시지
    private String resdate;         // 채팅 보낸 날짜

}
