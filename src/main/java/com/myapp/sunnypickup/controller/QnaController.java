package com.myapp.sunnypickup.controller;

import com.myapp.sunnypickup.service.TestService;
import com.myapp.sunnypickup.vo.TestVO;
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
public class QnaController {

    private TestService service;

    @Autowired
    public void setTestService(TestService service){
        this.service=service;
    }

    @RequestMapping("/qna")
    public ModelAndView qna(@RequestParam(value="id", required=false) String userId) {
        ModelAndView mav = new ModelAndView();
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            List<TestVO> list = service.getList(param);

            mav.addObject("list", list);
            mav.addObject("size", list == null ?  0 : list.size());
            mav.addObject("pageTitle", "질문게시판");
            mav.addObject("pageName", "qna");
        }catch (Exception e){
            e.printStackTrace();
        }

        mav.setViewName("views/contents/qna/qna");

        return mav;
    }


}
