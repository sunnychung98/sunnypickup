package com.myapp.sunnypickup.controller;

import com.myapp.sunnypickup.service.LoginService;
import com.myapp.sunnypickup.service.SendEmailService;
import com.myapp.sunnypickup.vo.MemberVO;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.UnsupportedEncodingException;
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

    @Autowired
    private SendEmailService sendEmailService;

//    @Autowired
//    public void setJavaMailSender(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }


    @RequestMapping(value="/mails")
    @ResponseBody
    public Map<String, Object> test(MemberVO vo){

        Map<String, Object> result = new HashMap<String, Object>();

        String userid = "admin";
        String email = "eveing2@naver.com";

        System.out.println("유저아이디"+userid);
        System.out.println("이메일주소"+email);

        //random number 생성
        String randomCode = RandomStringUtils.random(64);
        vo.setRegcode(randomCode);

        //이메일보내기
        String emailSubject = "[Sunny]회원가입을 환영합니다!!";
        String content = "email test";


        try{

            sendEmailService.sendEmail(email, emailSubject, content);
            System.out.println("sent email....");
            result.put("result", 200);

        }catch(Exception e){
            e.printStackTrace();
            result.put("result", 500);
        }
        return result;
    }

    @GetMapping("/signup")
    public ModelAndView signup(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/contents/account/signup");
        return mav;
    }

    @Value("E:/SpringBootProject/files/")
    private String imageServerUrl;


    @PostMapping(value="/signupOk")
    public ModelAndView signupOk(MemberVO vo,
                                 @RequestParam(value="file") MultipartFile mf,
                                 HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        String userid = vo.getUserid();
        String email = vo.getEmail();

        System.out.println("유저아이디"+userid);
        System.out.println("이메일주소"+email);

        //random number 생성
        String randomCode = RandomStringUtils.randomAlphanumeric(12);
        System.out.println("랜덤코드:"+randomCode);
        vo.setRegcode(randomCode);


        //이메일보내기
        String emailSubject = "[Sunny]회원가입을 환영합니다!!";
        String content = "email test";


        //파일업로드
        String path = imageServerUrl + "\\profilePhoto\\";
        String originalFileName = mf.getOriginalFilename();
        String newProfileName= "profile_"+originalFileName;
        vo.setProfile(newProfileName);

        File f = new File(path+newProfileName);
        String siteURL = request.getRequestURL().toString();

        try{
            int result = service.addMember(vo);


            if(result>0){
                sendVerificationEmail(vo, siteURL);
                try {
                    if (!f.exists()) {
                        if (f.getParentFile().mkdirs()) {
                            f.createNewFile();
                        }
                    }
                    mf.transferTo(f);
                }catch(Exception e) {
                    e.printStackTrace();
                }


                System.out.println("sent email....");
                mav.setViewName("views/contents/account/result2");

            }else{
                mav.setViewName("views/contents/account/result");

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return mav;
    }

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(MemberVO vo, String siteURL) throws UnsupportedEncodingException, MessagingException {
        String subject="Pleaes verify your registration";
        String senderName="Sunny Home";
        String mailContent = "<p>Dear " + vo.getUserid() + ", </p>";
        mailContent += "<p>Please click this to verify</p>";
        String verifyURL = siteURL + "/statusChange?userid="+vo.getUserid();
        mailContent += "<a = \"href="+ verifyURL + "\">VERIFY</a>";

        mailContent += "<p>Thank you!</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("sunnychungkr@gmail.com", senderName);
        helper.setTo(vo.getEmail());
        helper.setSubject(subject);

        helper.setText(mailContent, true);
        mailSender.send(message);

    }


    @RequestMapping("/statusChange")
    public ModelAndView statusChange(MemberVO vo, String userid, HttpServletRequest req){
        ModelAndView mav = new ModelAndView();
        vo.setUserid(userid);
        try {
            int resultVO = service.statusChange(vo);

            if(resultVO>0){
                mav.setViewName("views/contents/account/login");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return mav;
    }


    @RequestMapping(value="/dupFilter")
    @ResponseBody
    public String dupFilter(@RequestParam(value="userid") String userid) throws SQLException {

        String result = service.dupFilter(userid);
        System.out.println(result);

        return result;
    }



    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session){
        ModelAndView mav = new ModelAndView();
        session.invalidate();
        mav.setViewName("redirect:/");

        return mav;
    }





    @GetMapping("/login")
    public ModelAndView login(HttpSession session){
        ModelAndView mav = new ModelAndView();

        session.setAttribute("logStatus", "N");

        mav.addObject("pageTitle", "로그인하세요");
        mav.setViewName("views/contents/account/login");
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
            resultMap.put("resultMsg", "아이디 또는 비밀번호가 올바르지 않습니다.");
        }else{
            if(resultVO.getStatus() == 3) {
                resultMap.put("resultCode", 301);
                resultMap.put("resultMsg", "이미 탈퇴한 회원입니다.");
            }else if(resultVO.getStatus() == 0) {
                resultMap.put("resultCode", 302);
                resultMap.put("resultMsg", "인증이 되지않은 아이디입니다. 이메일 인증을 진행하세요.");
            }else {
                resultMap.put("resultCode", 200);
                ses.setAttribute("logStatus", "Y");
                ses.setMaxInactiveInterval(60*30);
                ses.setAttribute("userInfo", resultVO);
            }
        }
        return resultMap;
    }

}
