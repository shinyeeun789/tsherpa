package com.chunjae.pro05.ctrl;

import com.chunjae.pro05.biz.FaqService;
import com.chunjae.pro05.entity.Faq;
import com.chunjae.pro05.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/faq/*")
public class FaqController {

    @Autowired
    private FaqService faqService;

    @GetMapping("list.do")		//faq/list
    public String getFaqList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        int curPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        Page page = new Page();
        page.setKeyword(request.getParameter("keyword"));       // 검색 키워드 SET
        page.setType(request.getParameter("type"));             // 검색 타입 SET

        // 페이징에 필요한 데이터 저장
        page.makePage(faqService.getCount(page));

        List<Faq> faqList = faqService.faqList(page);

        model.addAttribute("faqList", faqList);          // Faq 목록
        model.addAttribute("curPage", curPage);             // 현재 페이지
        model.addAttribute("page", page);                   // 페이징 데이터

        return "faq/faqList";
    }
}
