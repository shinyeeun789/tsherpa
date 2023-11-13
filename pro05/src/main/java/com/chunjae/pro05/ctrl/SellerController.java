package com.chunjae.pro05.ctrl;

import com.chunjae.pro05.biz.TradeService;
import com.chunjae.pro05.biz.UserService;
import com.chunjae.pro05.entity.TradeVO;
import com.chunjae.pro05.entity.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/seller/*")
public class SellerController {

    @Autowired
    private UserService userService;

    @Autowired
    private TradeService tradeService;

    @GetMapping("detail.do")
    public String sellerDetail(@RequestParam String name, Model model) throws Exception {
        UserRating userRating = userService.getUserRating(name);
        model.addAttribute("userRating", userRating);

        List<UserRating> ratingList = userService.getUserRatingList(name);
        model.addAttribute("ratingList", ratingList);

        List<TradeVO> userTrades = tradeService.getTradeByName(name);
        model.addAttribute("userTrades", userTrades);

        return "/seller/sellerDetail";
    }

}
