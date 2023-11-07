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
        User user = userService.getUserById(Long.valueOf(principal.getName()));
        comment.setName(user.getName());

        freeCommentService.insertFreeComment(comment);

        Page page = new Page();
        page.makePage(freeCommentService.totalCnt(comment.getFno()));
        List<FreeComment> commentList = freeCommentService.commentList(comment.getFno(), page);
        return commentList;
    }

    @PostMapping("plusRecommend.do")
    @ResponseBody
    public FreeComment plusRecommend(@RequestBody FreeComment comment, HttpServletRequest request, HttpSession session) throws Exception {
        int result = freeCommentService.updateRecommend(comment.getCno(), "Plus");
        if(result > 0) {

            FreeComment resComment = freeCommentService.getComment(comment.getCno());
            return resComment;
        }
        return null;
    }

    @PostMapping("minusRecommend.do")
    public FreeComment minusRecommend(@RequestBody FreeComment comment, HttpServletRequest request, HttpSession session) throws Exception {
        int result = freeCommentService.updateRecommend(comment.getCno(), "Minus");
        if(result > 0) {

            FreeComment resComment = freeCommentService.getComment(comment.getCno());
            return resComment;
        }
        return null;
    }

}
