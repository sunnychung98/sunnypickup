package kr.code.homes.controller;

import kr.code.homes.service.LoginService;
import kr.code.homes.service.TestService;
import kr.code.homes.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/account")
public class LoginController {

    private LoginService service;

    @Autowired
    public void setLoginService(LoginService service){
        this.service=service;
    }

    @GetMapping("/login")
    public ModelAndView login(HttpSession session){
        ModelAndView mav = new ModelAndView();

        session.setAttribute("logStatus", "N");

        mav.addObject("pageTitle", "로그인하세요");
        mav.setViewName("views/contents/login");
        mav.addObject("pageName", "login");
        return mav;
    }

    /*@RequestMapping(value="/loginOk", method=RequestMethod.POST)
    public ModelAndView loginOk(MemberVO vo, HttpSession ses) throws SQLException {
        ModelAndView mav = new ModelAndView();
        MemberVO resultVO = service.loginCheck(vo);
        if(resultVO == null){

            mav.setViewName("views/contents/login");
            System.out.println("로그인실패");
        }else{
            mav.addObject("LoginName", resultVO.getUsername());
            mav.setViewName("views/contents/main");
            ses.setAttribute("logStatus", "Y");
            ses.setMaxInactiveInterval(60*30);
            ses.setAttribute("userInfo", resultVO);


            System.out.println("로그인성공");
        }
        return mav;
    }*/


    @RequestMapping(value="/loginOk", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loginOk(MemberVO vo, HttpSession ses) throws SQLException {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        MemberVO resultVO = service.loginCheck(vo);
        if(resultVO == null){

            resultMap.put("resultCode", 300);
            resultMap.put("resultMsg", "아이디 또는 비밀번호가 옳바르지 않습니다.");
        }else{

            if(resultVO.getStatus() == 3) {
                resultMap.put("resultCode", 301);
                resultMap.put("resultMsg", "이미 탈퇴한 회원입니다.");
            }else if(resultVO.getStatus() == 0) {
                resultMap.put("resultCode", 302);
                resultMap.put("resultMsg", "인증이 되지않은 아이디입니다. 이메일 인증을 진행하세요.");
            }else {
                resultMap.put("resultCode", 200);
                resultMap.put("resultMsg", "");

                ses.setAttribute("logStatus", "Y");
                ses.setMaxInactiveInterval(60*30);
                ses.setAttribute("userInfo", resultVO);
            }
        }
        return resultMap;
    }

}