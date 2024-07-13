package com.sparta.trello.view;

import ch.qos.logback.core.model.Model;
import com.sparta.trello.auth.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {
    @GetMapping("/view/auth/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping(value = {"/view/auth/login","/"})
    public String loginPage() {
        return "login";
    }

    @GetMapping("/view/index")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/view/create/board")
    public String createBoardPage() {
        return "createBoard";
    }

    @GetMapping("/view/main")
    public String mainPage(@RequestParam Long id, Model model) {
        return "main";
    }

    @GetMapping("/view/invite/board")
    public String inviteUserPage() {
        return "inviteUser";
    }

    @GetMapping("/view/user/invitation")
    public String invitaionPage() {
        return "invitationList";
    }

}
