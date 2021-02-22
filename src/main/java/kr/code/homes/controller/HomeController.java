package kr.code.homes.controller;

import kr.code.homes.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping("/")
    public ModelAndView main(@RequestParam(value="id", required=false) String userId) {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("views/contents/main");
        mav.addObject("pageTitle", "홈페이지입니다!");

        return mav;
    }
}
