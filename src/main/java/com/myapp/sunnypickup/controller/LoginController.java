package com.myapp.sunnypickup.controller;

import com.myapp.sunnypickup.service.LoginService;
import com.myapp.sunnypickup.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
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



    private JavaMailSender javaMailSender;

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
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
    public ModelAndView signupOk(String userid, MemberVO vo,
                                 @RequestParam(value="file") MultipartFile mf){
        ModelAndView mav = new ModelAndView();

        try {
            vo.setUserid(userid);
            String email = vo.getEmail();

            // 파일 업로드
            String path = imageServerUrl + "\\profilePhoto\\";
            String originalFileName = mf.getOriginalFilename();
            String newProfileName= "profile_"+originalFileName;
            vo.setProfile(newProfileName);

            try{

                File f = new File(path+newProfileName);

                if(!f.exists()) {
                    if(f.getParentFile().mkdirs()) {
                        f.createNewFile();
                    }
                }

                mf.transferTo(f);
                System.out.println("==파일업로드=="+email);
            }catch (Exception e) {
                e.getStackTrace();
            }

            //이메일보내기
            String emailSubject = "[Sunny]회원가입을 환영합니다!!";
            String content = "<!DOCTYPE html>\r\n" +
                    "<html>\r\n" +
                    "<head>"
                    + "</head>"
                    +"<body>"
                    + "  		안녕하세요?<br/><br/>\r\n"
                    + "  		"+userid+"님, 안녕하세요.<br/>\r\n"
                    + "  		가입을 진심으로 환영합니다!!<br/>\r\n"
                    + "		아래 링크를 누르시면 회원가입이 완료되며 로그인 페이지로 이동합니다.<br/>\r\n"
                    + "		<a href=\"http://localhost:8082/myapp/accostatusChange?userid="+userid+"\"><u>회원가입 완료 링크</u></a><br/><br/>\r\n"
                    + "  		회원가입 중 불편하셨던 점은 메일 부탁드립니다!\r\n\n<br/>"
                    + "		감사합니다."
                    + "</div>"
                    +"</body>"
                    +"</html>";

            try {
                System.out.println("==이메일발송=="+email);
                MimeMessage message= javaMailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
                messageHelper.setFrom("obokbosok.kr@gmail.com");
                messageHelper.setTo(email);
                messageHelper.setSubject(emailSubject);
                messageHelper.setText("text/html;charset=UTF-8",content);
                javaMailSender.send(message);


            }catch(Exception e) {

                System.out.println(e.getMessage());
            }



            int result = service.addMember(vo);

            if(result>0){
                mav.setViewName("views/contents/account/result2");

            }else{
                mav.setViewName("views/contents/account/result");

            }
        }catch(Exception e){
            e.printStackTrace();
        }



        return mav;
    }

    //이메일인증

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
