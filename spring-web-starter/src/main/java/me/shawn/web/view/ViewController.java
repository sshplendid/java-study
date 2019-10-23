package me.shawn.web.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/view")
@Controller
public class ViewController {
    @GetMapping
    public String getSampleView(Model model, @RequestParam(defaultValue = "john") String name, @RequestParam(defaultValue = "0") Integer age) {
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "/sample";
    }
}
