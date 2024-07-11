package com.sparta.trello.view;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api")
public class ViewController {
    @GetMapping("/user/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/user/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/create/board")
    public String createBoardPage() {
        return "createBoard";
    }

    @GetMapping("/main")
    public String mainPage(@RequestParam Long id, Model model) {
        // todo main 화면을 꾸밀 model을 만들어서 넘겨야 함
        return "main";
    }

    @GetMapping("/invite/board")
    public String inviteUserPage() {
        return "inviteUser";
    }
}
