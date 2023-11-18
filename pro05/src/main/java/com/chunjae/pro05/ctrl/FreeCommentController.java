package com.chunjae.pro05.ctrl;

import com.chunjae.pro05.biz.FreeCommentService;
import com.chunjae.pro05.biz.UserService;
import com.chunjae.pro05.entity.FreeComment;
import com.chunjae.pro05.entity.User;
import com.chunjae.pro05.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/freeComment/*")
public class FreeCommentController {

    @Autowired
    private UserService userService;

    @Autowired
    private FreeCommentService freeCommentService;

    @PostMapping("insert.do")
    @ResponseBody
    public List<FreeComment> freeCommentInsert(@RequestBody FreeComment comment, Principal principal) throws Exception {
        comment.setName(principal.getName());

        freeCommentService.insertFreeComment(comment);

        Page page = new Page();
        page.makePage(freeCommentService.totalCnt(comment.getFno()));
        List<FreeComment> commentList = freeCommentService.commentList(comment.getFno(), page);
        return commentList;
    }

    @PostMapping("plusRecommend.do")
    @ResponseBody
    public FreeComment plusRecommend(@RequestBody FreeComment comment, HttpServletRequest request, HttpSession session) throws Exception {
        Cookie[] cookieFromRequest = request.getCookies();
        String cookieValue = null;
        for(int i=0; i< cookieFromRequest.length; i++) {
            cookieValue = cookieFromRequest[0].getValue();
        }
        // 쿠키 세션 입력
        if(session.getAttribute(comment.getCno() + ":commentCookie") == null) {
            session.setAttribute(comment.getCno() + ":commentCookie", comment.getCno() + ":" + cookieValue);
        } else {
            session.setAttribute(comment.getCno() + ":commentCookie ex", session.getAttribute(comment.getCno() + ":commentCookie"));
            if(!session.getAttribute(comment.getCno() + ":commentCookie").equals(comment.getCno() + ":" + cookieValue)) {
                session.setAttribute(comment.getCno() + ":commentCookie", comment.getCno() + ":" + cookieValue);
            }
        }
        // 쿠키와 세션이 없는 경우 조회수 카운트
        if(!session.getAttribute(comment.getCno() + ":commentCookie").equals(session.getAttribute(comment.getCno() + ":commentCookie ex"))) {
            freeCommentService.updateRecommend(comment.getCno(), "Plus");
        }
        FreeComment resComment = freeCommentService.getComment(comment.getCno());
        return resComment;
    }

    @PostMapping("minusRecommend.do")
    @ResponseBody
    public FreeComment minusRecommend(@RequestBody FreeComment comment, HttpServletRequest request, HttpSession session) throws Exception {
        session.removeAttribute(comment.getCno() + ":commentCookie");
        session.removeAttribute(comment.getCno() + ":commentCookie ex");

        freeCommentService.updateRecommend(comment.getCno(), "Minus");
        
        FreeComment resComment = freeCommentService.getComment(comment.getCno());
        return resComment;
    }

}
