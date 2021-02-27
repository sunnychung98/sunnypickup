package com.myapp.sunnypickup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @GetMapping("/main")
    public ModelAndView hbHome(){
        ModelAndView mav = new ModelAndView();

        mav.setViewName("views/contents/shop/shop_main");
        return mav;

    }

    @GetMapping("/theme")
    public ModelAndView hbTheme(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/contents/shop/shop_theme");
        return mav;
    }

    @GetMapping("/best")
    public ModelAndView hbBest(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/contents/shop/shop_best");
        return mav;
    }


}
