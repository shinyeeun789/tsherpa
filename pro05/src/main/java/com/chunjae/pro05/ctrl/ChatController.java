package com.chunjae.pro05.ctrl;

import com.chunjae.pro05.biz.ChatService;
import com.chunjae.pro05.biz.TradeService;
import com.chunjae.pro05.biz.UserService;
import com.chunjae.pro05.entity.*;
import com.chunjae.pro05.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/chat/*")
public class ChatController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private TradeService tradeService;
    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("list.do")
    public String chatList(Principal principal, HttpServletRequest request, Model model) throws Exception {
        Page page = new Page(1, request.getParameter("type"), request.getParameter("keyword"));

        List<ChatRoom> roomList = chatService.findAllRoom(principal.getName());
        logger.info(roomList.toString());
        model.addAttribute("roomList",roomList);
        model.addAttribute("page", page);
        return "chat/chatList";
    }

    @PostMapping("createRoom.do")
    public String createRoom(Model model, Principal principal, ChatRoom chatRoom, HttpServletResponse response) throws Exception {
        User buyerInfo = userService.getByName(principal.getName());
        chatRoom.setBuyer(buyerInfo.getName());

        int ck = chatService.findChatDist(chatRoom);            // 채팅방 존재 여부 확인
        if(ck == 0) {
            int ck2 = chatService.createRoom(chatRoom);

        } else {
            response.setContentType("text/html;charset=UTF-8");;
        }
        return "redirect:list.do";
    }

    @GetMapping("chatRoom.do")
    public String chatRoom(Model model, @RequestParam String roomId, Principal principal) throws Exception {
        List<ChatMessage> chatMsg = chatService.findChatById(roomId);
        ChatRoom chatRoom = chatService.findRoomById(roomId);
        Trade trade = tradeService.getTrade(chatRoom.getTno());

        if(chatRoom.getSeller().equals(principal.getName())) {          // 현재 로그인한 사람이 파는 사람이라면
            UserRating user = userService.getUserRating(chatRoom.getBuyer());
            model.addAttribute("user", user);
        } else {
            UserRating user = userService.getUserRating(chatRoom.getSeller());
            model.addAttribute("user", user);
        }

        model.addAttribute("beforeChat", chatMsg);
        model.addAttribute("chatRoom", chatRoom);
        model.addAttribute("state", trade.getState());
        return "chat/chatRoom";
    }

    @PostMapping("insertChat.do")
    public void insertChat(Principal principal, ChatMessage chatMessage, HttpServletResponse response) throws Exception {
        chatMessage.setSender(principal.getName());
        chatService.insertChat(chatMessage);

        PrintWriter out = response.getWriter();
        out.println("success");
    }

}
