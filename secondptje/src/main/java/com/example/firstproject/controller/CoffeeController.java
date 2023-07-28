package com.example.firstproject.controller;

import com.example.firstproject.dto.CoffeeForm;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Slf4j
@Controller
public class CoffeeController {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @GetMapping("coffee/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("coffee/createcoffee")
        public String CoffeeForm(CoffeeForm form) {

        log.info(form.toString());

        Coffee coffee = form.toEntity();

        log.info(form.toString());

        Coffee saved = coffeeRepository.save(coffee);
        log.info(form.toString());

        return "/articles/cnew/" + saved.getId();
    }

    @GetMapping("/coffee/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);

        Optional<Coffee> optionalCoffee = coffeeRepository.findById(id);

        Coffee coffeeEntity = coffeeRepository.findById(id).orElse(null);
        model.addAttribute("coffee",coffeeEntity);
        return  "articles/show";
    }

    @GetMapping("/coffee/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Coffee coffeeEntity = coffeeRepository.findById(id).orElse(null);
        model.addAttribute("coffee",coffeeEntity);
        return  "articles/edit";
    }
    @PostMapping("/coffee/update")
    public String update(CoffeeForm form) {
        log.info(form.toString());
        Coffee coffeeEntity = form.toEntity();
        log.info(coffeeEntity.toString());

        Coffee target = coffeeRepository.findById(coffeeEntity.getId()).orElse(null);

        if (target != null) {
            coffeeRepository.save(coffeeEntity);
        }
        return "redirect:/articles/" + coffeeEntity.getId();
    }
    @GetMapping("/coffee/{id}/delete")
    public String delete (@PathVariable Long id,
                          RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다.");
        Coffee target = coffeeRepository.findById(id).orElse(null);
        log.info(target.toString());

        if (target != null){
            coffeeRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제가 완료 되었습니다.");
        }
        return "redirect:/articles";
    }
}
