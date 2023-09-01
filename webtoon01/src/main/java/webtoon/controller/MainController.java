package webtoon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping(value = "/main")
    public String main() {
        return "main/main";
    }
    @GetMapping(value = "/main/allWebtoon")
    public String allwebtoon() {
        return "main/allWebtoon";
    }

    @GetMapping(value = "/main/dailyWebtoon")
    public String dailyWebtoon() {
        return "main/dailyWebtoon";
    }
    @GetMapping(value = "/main/myPage")
    public String myPage() {
        return "main/myPage";
    }


}
