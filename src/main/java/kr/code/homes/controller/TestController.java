package kr.code.homes.controller;

import kr.code.homes.service.TestService;
import kr.code.homes.vo.TestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    private TestService service;

    @Autowired
    public void setTestService(TestService service){
        this.service=service;
    }

    @RequestMapping("/test")
    public ModelAndView test(@RequestParam(value="id", required=false) String userId) {
        ModelAndView mav = new ModelAndView();
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            List<TestVO> list = service.getList(param);

            mav.addObject("list", list);
            mav.addObject("size", list == null ?  0 : list.size());
            mav.addObject("pageTitle", "테스트페이지");
            mav.addObject("pageName", "test");
        }catch (Exception e){
            e.printStackTrace();
        }

        mav.setViewName("views/contents/test");

        return mav;
    }

    @RequestMapping("/form")
    public ModelAndView form(){
        ModelAndView mav = new ModelAndView();

        mav.setViewName("views/contents/test_form");
        return mav;
    }
}
