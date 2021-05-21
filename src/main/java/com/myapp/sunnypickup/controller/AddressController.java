package com.myapp.sunnypickup.controller;

import com.myapp.sunnypickup.service.AddressService;
import com.myapp.sunnypickup.service.TestService;
import com.myapp.sunnypickup.vo.AddressVO;
import com.myapp.sunnypickup.vo.MemberVO;
import com.myapp.sunnypickup.vo.TestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/address")
public class AddressController {

    private AddressService service;

    @Autowired
    public void setAddressService(AddressService service){
        this.service=service;
    }

    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value="id", required=false) String userId) {
        ModelAndView mav = new ModelAndView();
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            List<AddressVO> list = service.getAddressList(param);

            mav.addObject("list", list);
            mav.addObject("size", list == null ?  0 : list.size());
            mav.addObject("pageTitle", "주소");
            mav.addObject("pageName", "Address");
        }catch (Exception e){
            e.printStackTrace();
        }

        mav.setViewName("views/contents/address/list");

        return mav;
    }

    @RequestMapping("/form")
    public ModelAndView form() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/contents/address/addressForm");

        return mav;
    }


    @RequestMapping("/formOk")
    public ModelAndView formOk(AddressVO vo, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        try{
            MemberVO member = (MemberVO)request.getSession().getAttribute("userInfo");
            vo.setUserid(member.getUserid());

            int result = service.insertAddress(vo);

            if(result>0){
                mav.setViewName("redirect:/address/list");
            }


        }catch(Exception e){
            e.printStackTrace();
        }


        return mav;
    }



}
