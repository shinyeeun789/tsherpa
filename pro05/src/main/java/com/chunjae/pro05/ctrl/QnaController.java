package com.chunjae.pro05.ctrl;

import com.chunjae.pro05.biz.QnaService;
import com.chunjae.pro05.entity.Qna;
import com.chunjae.pro05.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/qna/*")
public class QnaController {

    @Autowired
    private QnaService qnaService;

    //QnA 목록
    @GetMapping("list.do")
    public String getQnaList(HttpServletRequest request, Model model) throws Exception {
        //Page
        int curPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        Page page = new Page();
        page.setKeyword(request.getParameter("keyword"));   //검색 키워드 set
        page.setType(request.getParameter("type"));         //검색 타입 set

        //페이징에 필요한 데이터 저장
        page.makePage(qnaService.getCount(page));

        List<Qna> qnaList = qnaService.qnaList(page);
        model.addAttribute("qnaList", qnaList);     //QnA 목록
        model.addAttribute("curPage", curPage);     // 현재 페이지
        model.addAttribute("page", page);           // 페이징 데이터

        return "qna/qnaList";
    }

    //QnA 상세보기
    @GetMapping("detail.do")
    public String getQnaDetail(HttpServletRequest request, Model model) throws Exception {
        int qno = Integer.parseInt(request.getParameter("qno"));
        Qna detail = qnaService.qnaDetail(qno);
        model.addAttribute("detail", detail);

        Qna prev = qnaService.qnaRef(qno, "prev");
        Qna next = qnaService.qnaRef(qno, "next");
        model.addAttribute("prev", prev);
        model.addAttribute("next", next);
        model.addAttribute("curPage", request.getParameter("page"));
        return "qna/qnaDetail";
    }

    //Question 글 쓰기
    @GetMapping("questionInsert.do")
    public String getQuestionInsert(Model model) throws Exception {
        return "qna/questionInsert";
    }

    //Question 글쓰기 처리
    @PostMapping("questionInsert.do")
    public String getQuestionInsertPro(Qna qna, Principal principal, Model model) throws Exception {
        qna.setAuthor(principal.getName());
        qnaService.questionInsert(qna);
        return "redirect:list.do";
    }

    //Question 수정
    @GetMapping("edit.do")
    public String getQnaEdit(HttpServletRequest request, Model model) throws Exception {
        int qno = Integer.parseInt(request.getParameter("qno"));
        Qna detail = qnaService.qnaDetail(qno);
        model.addAttribute("detail", detail);
        return "qna/qnaEdit";
    }
    //Question 수정처리
    @PostMapping("edit.do")
    public String getQnaEditPro(HttpServletRequest request, Model model) throws Exception {
        int qno = Integer.parseInt(request.getParameter("qno"));
        Qna dto = new Qna();
        dto.setQno(qno);
        dto.setTitle(request.getParameter("title"));
        dto.setContent(request.getParameter("content"));
        qnaService.qnaEdit(dto);
        return "redirect:detail.do?qno="+qno+"&page=1";
    }

    //QnA 삭제
    @GetMapping("delete.do")
    public String getQnaDelete(HttpServletRequest request, Model model) throws Exception {
        int qno = Integer.parseInt(request.getParameter("qno"));
        qnaService.qnaDelete(qno);
        return "redirect:list.do";
    }

    //답변 등록
    @GetMapping("answerInsert.do")
    public String getAnswerInsert(HttpServletRequest request, Model model) throws Exception {
        int qno = Integer.parseInt(request.getParameter("qno"));
        Qna detail = qnaService.qnaDetail(qno);
        model.addAttribute("detail", detail);
        return "qna/answerInsert";
    }

    //답변 등록 처리
    @PostMapping("answerInsert.do")
    public String getAnswerInsertPro(Qna qna, Principal principal, HttpServletRequest request, Model model) throws Exception {
        qna.setAuthor(principal.getName());
        qnaService.answerInsert(qna);
        return "redirect:list.do";
    }
}
