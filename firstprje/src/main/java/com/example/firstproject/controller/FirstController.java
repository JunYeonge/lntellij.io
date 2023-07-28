package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {
    @GetMapping("/hello")
    public  String niceToMeetYou() {
        return  "hello";
    }

    @GetMapping("/hi")
    public String greetings(Model model) {
        model.addAttribute("username","junsuk");
        return "greetings";
    }

    @GetMapping("/bye")
    public  String good(Model model) {
        model.addAttribute("uname","홍길동");
        return  "goodbye";
    }

}
