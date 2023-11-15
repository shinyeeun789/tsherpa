package com.chunjae.pro05.ctrl;

import com.chunjae.pro05.biz.PaymentService;
import com.chunjae.pro05.biz.TradeService;
import com.chunjae.pro05.biz.UserService;
import com.chunjae.pro05.entity.*;
import com.chunjae.pro05.exception.NoSuchDataException;
import com.chunjae.pro05.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@CrossOrigin("http://localhost:8085")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TradeService tradeService;
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/userList.do")
    @ResponseBody
    public List<User> getUserList() throws Exception {
        return userService.getUserList();
    }

    @GetMapping("/userInfo.do")
    public String getUser(@RequestParam String name, Model model) throws Exception {
        User user = userService.getUser(name);
        model.addAttribute("user", user);
        return "user/userInfo";
    }

    @GetMapping("/term.do")
    public String term(Model model) throws Exception {
        return "user/term";
    }

    @GetMapping("/login.do")
    public String loginForm(Model model) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            return "user/login";
        return "redirect:/";
    }

    @GetMapping("/join.do")
    public String joinForm(Model model) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken) {
            return "user/join";
        }
        return "redirect:/";
    }

    @PostMapping("/join.do")
    public String joinPro(User user, Model model) throws Exception {
        int cnt = userService.userJoin(user);
        if(cnt == 0) {
            throw new NoSuchDataException("No Insert Process Data");
        }
        return "redirect:/login.do";
    }

    //중복 id 검증(Ajax)
    @PostMapping("/idCheck.do")
    @ResponseBody
    public boolean idCheckAjax(@RequestBody User test) throws Exception {
        boolean result = false;
        User user = userService.getByName(test.getName());
        if(user != null){
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    @GetMapping("/mypage.do")
    public String mypage(@RequestParam long id, Model model) throws Exception {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "/user/mypage";
    }

    @GetMapping("/userEdit.do")
    public String userEditForm(Principal principal, Model model) throws Exception {
        User user = userService.getUserById(Long.valueOf(principal.getName()));
        model.addAttribute("user", user);
        return "/user/userEdit";
    }

    @PostMapping("/userEdit.do")
    public String userEdit(User user, RedirectAttributes rttr) throws Exception {
        User beforuser = userService.getUser(user.getName());
        if(user.getPassword() == null || user.getPassword().equals("")) {
            user.setPassword(beforuser.getPassword());
        } else {
            PasswordEncoder pwdEncoder = userService.passwordEncoder();
            user.setPassword(pwdEncoder.encode(user.getPassword()));
        }

        int result = userService.updateUser(user);
        if(result > 0) {
            rttr.addFlashAttribute("msg", "개인정보를 변경하였습니다.");
        } else {
            rttr.addFlashAttribute("msg", "개인정보 변경에 실패했습니다. 잠시 후 다시 시도해주세요");
        }
        return "redirect:/user/detail.do";
    }

    @GetMapping("/user/detail.do")
    public String userDetail(Principal principal, Model model) throws Exception {
        User user = userService.getUserById(Long.valueOf(principal.getName()));

        UserRating userRating = userService.getUserRating(user.getName());
        model.addAttribute("userRating", userRating);

        List<UserRating> ratingList = userService.getUserRatingList(user.getName());
        model.addAttribute("ratingList", ratingList);

        List<TradeVO> userTrades = tradeService.getTradeByName(user.getName());
        model.addAttribute("userTrades", userTrades);

        return "/user/myDetail";
    }

    @GetMapping("/user/myAccount.do")
    public String myAccount(Principal principal, Model model) throws Exception {
        User user = userService.getUserById(Long.valueOf(principal.getName()));
        model.addAttribute("user", user);

        return "/user/myAccount";
    }

    @PostMapping("/user/addAccountInfo.do")
    public String addAccountInfo(User user, Principal principal) throws Exception {
        user.setId(Long.valueOf(principal.getName()));
        userService.updateAccount(user);
        return "redirect:myAccount.do";
    }

    @GetMapping("/user/myRecommend.do")
    public String myRecommend(Principal principal, HttpServletRequest request, Model model) throws Exception {
        User user = userService.getUserById(Long.valueOf(principal.getName()));

        int curPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        Page page = new Page(curPage);
        page.setName(user.getName());
        page.makePage(tradeService.totalTradeRecommend(page));

        List<TradeVO> recommends = tradeService.myTradeRecommend(page);
        model.addAttribute("recommends", recommends);
        model.addAttribute("page", page);
        return "/user/myRecommend";
    }

    @GetMapping("/user/myProduct.do")
    public String myProduct(Principal principal, HttpServletRequest request, Model model) throws Exception {
        User user = userService.getUserById(Long.valueOf(principal.getName()));

        int curPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        Page page = new Page(curPage);
        page.setName(user.getName());
        page.makePage(paymentService.totalProduct(page));

        List<AboutTradeVO> myProductList = paymentService.myProduct(page);
        model.addAttribute("myProductList", myProductList);
        model.addAttribute("page", page);

        return "/user/myProduct";
    }

    @GetMapping("/user/myPayment.do")
    public String myPayment(Principal principal, HttpServletRequest request, Model model) throws Exception {
        User user = userService.getUserById(Long.valueOf(principal.getName()));

        int curPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        Page page = new Page(curPage);
        page.setName(user.getName());
        page.makePage(paymentService.totalPayment(page));

        List<AboutTradeVO> paymentList = paymentService.myPayment(page);
        model.addAttribute("paymentList", paymentList);
        model.addAttribute("page", page);

        return "/user/myPayment";
    }

    @PostMapping("/user/addReview.do")
    public String addReview(Principal principal, UserRating userRating, RedirectAttributes rttr) throws Exception {
        User user = userService.getUserById(Long.valueOf(principal.getName()));
        userRating.setBuyer(user.getName());

        int result = userService.insertUserRating(userRating);
        if(result > 0) {
            rttr.addFlashAttribute("msg", "리뷰가 작성되었습니다.");
        } else {
            rttr.addFlashAttribute("msg", "리뷰 작성에 실패했습니다. 잠시후 다시 시도해주세요");
        }

        return "redirect:/user/myPayment.do";
    }

    @PostMapping("/user/editReview.do")
    public String editReview(UserRating userRating, RedirectAttributes rttr) throws Exception {
        int result = userService.editUserRating(userRating);
        return "redirect:/user/myPayment.do";
    }

}
