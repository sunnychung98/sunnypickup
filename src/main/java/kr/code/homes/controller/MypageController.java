package kr.code.homes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MypageController {

    @RequestMapping("/mypage")
    public ModelAndView mypage(@RequestParam(value="id", required=false) String userid){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/contents/mypage");

        return mav;
    }
}
