package com.chunjae.pro05.entity;

import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatRoom {

    private String roomId;
    private String name;
    private String buyer;
    private String seller;
    private int tno;
    private String lastChat;
    private String lastDate;
    private String act;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String name, String buyer, String seller, int tno, String act) {
        this.roomId = roomId;
        this.name = name;
        this.buyer = buyer;
        this.seller= seller;
        this.tno = tno;
        this.act = act;
    }

}
