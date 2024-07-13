package com.sparta.trello.view;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/view")
public class ViewController {
    @GetMapping("/auth/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/auth/login")
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

    @GetMapping("/user/invitation")
    public String invitaionPage() {
        return "invitationList";
    }

    @GetMapping("/board/{id}/column")
    public String createColumnPage(@PathVariable("id") Long id) {
        return "createColumn";
    }
    @GetMapping("/board/{id}")
    public String kanbanboardPage(@PathVariable("id") Long id) {
        return "kanbanboard";
    }
    @GetMapping("/column/{id}")
    public String updateColumnPage(@PathVariable("id") Long id) {
        return "updateColumn";
    }

    @GetMapping("/columns/{id}/create/card")
    public String createCard(@PathVariable("id") Long id) {
        return "createCard";
    }

    @GetMapping("/read/card/{cardId}")
    public String readCard(@PathVariable("cardId") Long id) {
        return "readCard";
    }
}
