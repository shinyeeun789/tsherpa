package com.chunjae.pro05.ctrl;

import com.chunjae.pro05.biz.PaymentService;
import com.chunjae.pro05.biz.TradeService;
import com.chunjae.pro05.biz.UserService;
import com.chunjae.pro05.entity.Delivery;
import com.chunjae.pro05.entity.Payment;
import com.chunjae.pro05.entity.TradeVO;
import com.chunjae.pro05.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/payment/*")
public class PaymentController {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @GetMapping("addPayment.do")
    public String addPayment(@RequestParam int tno, Principal principal, Model model) throws Exception {
        User user = userService.getByName(principal.getName());
        model.addAttribute("user", user);

        TradeVO trade = tradeService.getTradeVO(tno);
        model.addAttribute("trade", trade);

        return "trade/addPayment";
    }

    @PostMapping("addPayment.do")
    public String addPayment(Payment payment, Delivery delivery) throws Exception {
        paymentService.addPayment(payment, delivery);
        return "redirect:/user/detail.do";
    }

}
