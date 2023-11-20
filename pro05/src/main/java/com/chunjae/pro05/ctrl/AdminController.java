package com.chunjae.pro05.ctrl;

import com.chunjae.pro05.biz.FreeService;
import com.chunjae.pro05.biz.QnaService;
import com.chunjae.pro05.biz.UserService;
import com.chunjae.pro05.entity.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/admin/*")
public class AdminController {

    @Autowired
    HttpSession session;

    @Autowired
    private UserService userService;

    @Autowired
    private FreeService freeService;

    @Autowired
    private QnaService qnaService;

    @RequestMapping("dashboard.do")
    public String dashboard(Model model) throws Exception {
//        List<User> visitRank = userService.visitRank();
//        model.addAttribute("visitRank", visitRank);

        return "/admin/dashboard";
    }

    @PostMapping("userCntList")
    public void getUserCnt(HttpServletResponse response) throws Exception {
//        List<Map<String, Integer>> userCntList = userService.userCntList();
//        JSONArray jsonArray = new JSONArray();
//        for(Map<String, Integer> userCnt : userCntList) {
//            JSONObject obj = new JSONObject();
//            obj.put("label", userCnt.get("label"));
//            obj.put("teaCnt", userCnt.get("teaCnt"));
//            obj.put("stuCnt", userCnt.get("stuCnt"));
//            jsonArray.put(obj);
//        }
//        PrintWriter out = response.getWriter();
//        out.println(jsonArray);
    }

    @PostMapping("profitYearReport")
    public void profitYearReport(HttpServletResponse response) throws Exception {
//        List<Map<String, Integer>> profitList = registerService.yearProfit();
//        JSONArray jsonArray = new JSONArray();
//        for(Map<String, Integer> profits : profitList) {
//            JSONObject obj = new JSONObject();
//            obj.put("label", profits.get("label"));
//            obj.put("profit", profits.get("profit"));
//            jsonArray.put(obj);
//        }
//        PrintWriter out = response.getWriter();
//        out.println(jsonArray);
    }

    @PostMapping("profitPayReport")
    public void profitPayReport(HttpServletResponse response) throws Exception {
//        List<Map<String, Integer>> profitList = registerService.payProfit();
//        JSONArray jsonArray = new JSONArray();
//        for(Map<String, Integer> profits : profitList) {
//            JSONObject obj = new JSONObject();
//            obj.put("label", profits.get("label"));
//            obj.put("profit", profits.get("profit"));
//            jsonArray.put(obj);
//        }
//        PrintWriter out = response.getWriter();
//        out.println(jsonArray);
    }

}