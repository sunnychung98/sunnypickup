package com.myapp.sunnypickup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mypage")
public class MypageController {

    @GetMapping("/dashboard")
    public ModelAndView dashboard(@RequestParam(value="id", required=false) String userid){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/contents/mypage/mypage_main");

        return mav;
    }

    @GetMapping("/myshopping")
    public ModelAndView myshopping(@RequestParam(value="id", required=false) String userid){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/contents/mypage/mypage_myshopping");

        return mav;
    }

    @GetMapping("/memberedit")
    public ModelAndView memberedit(@RequestParam(value="id", required=false) String userid){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/contents/mypage/mypage_memberedit");

        return mav;
    }

    @GetMapping("/wishlist")
    public ModelAndView wishlist(@RequestParam(value="id", required=false) String userid){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/contents/mypage/mypage_wishlist");

        return mav;
    }


    @GetMapping("/mycontent")
    public ModelAndView mycontent(@RequestParam(value="id", required=false) String userid){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/contents/mypage/mypage_mycontent");

        return mav;
    }

}
