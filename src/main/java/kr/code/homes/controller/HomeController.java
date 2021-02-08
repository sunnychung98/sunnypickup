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
public class HomeController {

    private TestService service;

    @Autowired
    public void setTestService(TestService service){
        this.service=service;
    }

    @RequestMapping("/main/view")
    public ModelAndView main(@RequestParam(value="id", required=false) String userId) {
        ModelAndView view = new ModelAndView();
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            List<TestVO> list = service.getList(param);

            view.addObject("list", list);
            view.addObject("size", list == null ?  0 : list.size());
        }catch (Exception e){
            e.printStackTrace();
        }

        view.setViewName("views/contents/main");
        return view;
    }
}
