package com.chunjae.pro05.ctrl;

import com.chunjae.pro05.biz.PaymentService;
import com.chunjae.pro05.biz.TradeService;
import com.chunjae.pro05.biz.UserService;
import com.chunjae.pro05.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("payComplete.do")
    public String payComplete(Payment payment, RedirectAttributes rttr) throws Exception {
        Trade trade = tradeService.getTrade(payment.getTno());
        payment.setPrice(trade.getPrice());
        int result = paymentService.payComplete(payment);
        if(result > 0) {
            rttr.addFlashAttribute("msg", "거래완료 처리되었습니다.");
        } else {
            rttr.addFlashAttribute("msg", "거래완료 처리에 실패했습니다. 잠시 후 다시 시도해주세요.");
        }

        return "redirect:/chat/list.do";
    }

}
