package com.myapp.sunnypickup.controller;

import com.myapp.sunnypickup.service.HomeboardService;
import com.myapp.sunnypickup.vo.HomeboardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private HomeboardService service;

    @RequestMapping("/")
    public ModelAndView main(@RequestParam(value="userid", required=false) String userid) {
        ModelAndView mav = new ModelAndView();

        try{
            Map<String, Object> param = new HashMap();
            List<HomeboardVO> mainList = service.getBoardListForMain(param);

            mav.addObject("mainList", mainList);

        }catch(Exception e){
            e.printStackTrace();
        }
        mav.setViewName("views/contents/main");
        return mav;
    }
}
