package kr.code.homes.controller;

import kr.code.homes.service.LoginService;
import kr.code.homes.service.TestService;
import kr.code.homes.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
@RequestMapping("/account")
public class LoginController {

    private LoginService service;

    @Autowired
    public void setLoginService(LoginService service){
        this.service=service;
    }

    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("pageTitle", "로그인하세요");
        mav.setViewName("views/contents/login");
        mav.addObject("pageName", "login");
        return mav;
    }

    @RequestMapping(value="/loginOk", method=RequestMethod.POST)
    public ModelAndView loginOk(MemberVO vo, HttpSession ses) throws SQLException {
        ModelAndView mav = new ModelAndView();
        MemberVO resultVO = service.loginCheck(vo);
        if(resultVO == null){
            ses.setAttribute("logStatus", "N");
            mav.setViewName("views/contents/login");
            System.out.println("로그인실패");
        }else{
            mav.addObject("LoginName", resultVO.getUsername());
            mav.setViewName("/views/contents/main");
            ses.setAttribute("logStatus", "Y");
            ses.setAttribute("logId", vo.getUsername());


            System.out.println("로그인성공");
        }
        return mav;
    }



}