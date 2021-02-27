package com.myapp.sunnypickup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @RequestMapping("/")
    public ModelAndView main(@RequestParam(value="userid", required=false) String userid) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/contents/main");
        return mav;
    }
}
