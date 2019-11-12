package komarov.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController{
    @RequestMapping("/")
    public String homepage() {
        return "index";
    }
}
