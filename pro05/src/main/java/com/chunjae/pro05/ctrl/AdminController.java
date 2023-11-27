package com.chunjae.pro05.ctrl;

import com.chunjae.pro05.biz.*;
import com.chunjae.pro05.entity.Trade;
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
    private TradeService tradeService;

    @Autowired
    private PaymentService paymentService;

    @RequestMapping("dashboard.do")
    public String dashboard(Model model) throws Exception {
        List<Map<String, Object>> locationRank = tradeService.locationRank();
        model.addAttribute("locationRank", locationRank);

        return "/admin/dashboard";
    }

    @PostMapping("tradeCntList.do")
    public void getUserCnt(HttpServletResponse response) throws Exception {
        List<Map<String, Integer>> tradeCntList = tradeService.tradeCntList();
        JSONArray jsonArray = new JSONArray();
        for(Map<String, Integer> tradeCnt : tradeCntList) {
            JSONObject obj = new JSONObject();
            obj.put("label", tradeCnt.get("label"));
            obj.put("directCnt", tradeCnt.get("directCnt"));
            obj.put("deliCnt", tradeCnt.get("deliCnt"));
            jsonArray.put(obj);
        }
        PrintWriter out = response.getWriter();
        out.println(jsonArray);
    }

    @PostMapping("profitYearReport.do")
    public void profitYearReport(HttpServletResponse response) throws Exception {
        List<Map<String, Integer>> profitList = paymentService.yearProfit();
        JSONArray jsonArray = new JSONArray();
        for(Map<String, Integer> profits : profitList) {
            JSONObject obj = new JSONObject();
            obj.put("label", profits.get("label"));
            obj.put("profit", profits.get("profit"));
            jsonArray.put(obj);
        }
        PrintWriter out = response.getWriter();
        out.println(jsonArray);
    }

    @PostMapping("profitMonthReport.do")
    public void profitPayReport(HttpServletResponse response) throws Exception {
        List<Map<String, Integer>> profitList = paymentService.monthProfit();
        JSONArray jsonArray = new JSONArray();
        for(Map<String, Integer> profits : profitList) {
            JSONObject obj = new JSONObject();
            obj.put("label", profits.get("label"));
            obj.put("profit", profits.get("profit"));
            jsonArray.put(obj);
        }
        PrintWriter out = response.getWriter();
        out.println(jsonArray);
    }

}
