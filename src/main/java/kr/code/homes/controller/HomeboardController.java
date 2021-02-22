package kr.code.homes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/homeboard")
public class HomeboardController {

    @GetMapping("/main")
    public ModelAndView hbHome(){
        ModelAndView mav = new ModelAndView();

        mav.setViewName("views/contents/homeboard_main");
        return mav;

    }

    @GetMapping("/hbtheme")
    public ModelAndView hbTheme(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/contents/homeboard_theme");
        return mav;
    }

    @GetMapping("/bhbest")
    public ModelAndView hbBest(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/contents/homebaord_best");
        return mav;
    }


}
