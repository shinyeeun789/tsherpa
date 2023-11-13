package com.chunjae.pro05.ctrl;

import com.chunjae.pro05.biz.UserService;
import com.chunjae.pro05.entity.User;
import com.chunjae.pro05.exception.NoSuchDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@CrossOrigin("http://localhost:8085")
public class UserController {

    @Autowired
    private UserService userService;

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

    @PostMapping("/joinPro.do")
    public String joinPro(User user, Model model) throws Exception {
        int cnt = userService.userJoin(user);
        if(cnt == 0) {
            throw new NoSuchDataException("No Insert Process Data");
        }
        return "redirect:/login";
    }

    //중복 id 검증(Ajax)
    @PostMapping("/idCheck.do")
    @ResponseBody
    public boolean idCheckAjax(@RequestBody User test) throws Exception {
        boolean result = false;
        User user = userService.getByName(test.getName());
        if(user!=null){
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
    public String userEditForm(@RequestParam long id, Model model) throws Exception {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "/user/userEdit";
    }

    @PostMapping("/userEdit.do")
    public String userEdit(User user, RedirectAttributes rttr) throws Exception {
        User beforuser = userService.getUser(user.getName());
        if(user.getPassword() == null || user.getPassword().equals("")) {
            user.setPassword(beforuser.getPassword());
        }

        int result = userService.updateUser(user);
        if(result > 0) {
            rttr.addFlashAttribute("msg", "개인정보를 변경하였습니다.");
        } else {
            rttr.addFlashAttribute("msg", "개인정보 변경에 실패했습니다. 잠시 후 다시 시도해주세요");
        }
        return "redirect:mypage?id="+beforuser.getId();
    }

}
