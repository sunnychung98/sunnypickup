package com.myapp.sunnypickup.controller;

import com.myapp.sunnypickup.service.HomeboardService;
import com.myapp.sunnypickup.vo.HomeboardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/homeboard")
public class HomeboardController {

    private HomeboardService service;

    @Autowired
    public void setHomeboardService(HomeboardService service){ this.service=service;}

    @GetMapping("/list")
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

    @GetMapping("/form")
    public ModelAndView homeboardForm(Model model){
      ModelAndView mav = new ModelAndView();
      model.addAttribute("vo", new HomeboardVO());
      mav.setViewName("views/contents/homeboard/homeboardForm");
      return mav;

    }

    @PostMapping("/formOk")
    public ModelAndView homeboardFormOk (@ModelAttribute HomeboardVO vo, HttpServletRequest request, HttpSession session){
        ModelAndView mav = new ModelAndView();

        try{
            int result = service.insertHomeboard(vo);

            if(result>0){
                mav.setViewName("redirect:/homeboard/list");
            }else{
                mav.setViewName("views/contents/homeboard/result");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return mav;
    }

    @GetMapping("/view")
    public ModelAndView homeboardView(@RequestParam(required = false) int bno){
        ModelAndView mav = new ModelAndView();

        try {
            List<HomeboardVO> list = service.homeboardSelect(bno);
            mav.addObject("list", list);

        }catch(Exception e){
            e.printStackTrace();
        }

        mav.setViewName("views/contents/homeboard/homeboardView");
        return mav;

    }

}
