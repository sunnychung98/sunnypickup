package com.myapp.sunnypickup.controller;

import com.myapp.sunnypickup.service.HomeboardService;
import com.myapp.sunnypickup.vo.HomeboardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeboardController {

    private HomeboardService service;

    @Autowired
    public void setHomeboardService(HomeboardService service){ this.service=service;}

    @RequestMapping("/homeboard")
    public ModelAndView homeboard(@RequestParam(value="id", required=false)String userid){
        ModelAndView mav = new ModelAndView();
        try{
            Map<String, Object> param = new HashMap();
            List<HomeboardVO> list = service.getBoardList(param);

            mav.addObject("list", list);
            mav.addObject("pageTitle", "게시판");
            mav.addObject("pageName", "homeboard");

        }catch(Exception e){
            e.printStackTrace();
        }
        mav.setViewName("views/contents/homeboard/homeboard");
        return mav;
    }

    @RequestMapping("/homeboardWrite")
    public ModelAndView homeboardForm(@RequestParam(value="id", required = false)String userid){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/contents/homeboard/homeboardForm");
        return mav;
    }

}
