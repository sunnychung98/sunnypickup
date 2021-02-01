package kr.code.homes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping("/main/view")
    public ModelAndView main() {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/contents/main");

        return view;
    }
}
