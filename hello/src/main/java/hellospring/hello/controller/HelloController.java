package hellospring.hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello") //주소줄에 입력값
    public String hello(Model model){
        model.addAttribute("data","Hello!!");
       return "hello";
    }
    // @GetMapping("hello")    /hello 경로 들어오는 get 요청이 들어오면
// hello(Model model) 뷰(html)에 데이터전달
//"data"라는 이름으로 "Hello!!"라는 값을 모델에 추가
    @GetMapping("spring") //주소줄에 입력값
    public String spring(Model model){
        model.addAttribute("data","spring!!");
        return "spring";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name1, Model model){
        model.addAttribute("name",name1);
        return "hello-template";
    }
    //http://localhost:8080/hello-mvc?name=babo
    // @RequestParam("name") 은 클라이언트가 요청 파라메로 전달한 
    //  name 값(babo)  name1 매개변수에 
    // model 객체는 뷰에 데이터를 전달하는데 사용

    @GetMapping("hello-string")
    @ResponseBody // http 에서 바디부에 직접 내용을 넣어주겠다는 의미
    public String helloString(@RequestParam("name") String name){
        return "hello "+name;
    }
    //http://localhost:8080/hello-string?name=spring!!!!

    @GetMapping("hello-api")
    @ResponseBody // http 에서 바디부에 직접 내용을 넣어주겠다는 의미
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }
    static class Hello {
        private String name;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }


}


