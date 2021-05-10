package com.myapp.sunnypickup.controller;

import com.myapp.sunnypickup.security.ZRsaSecurity;
import com.myapp.sunnypickup.service.LoginService;
import com.myapp.sunnypickup.service.SendEmailService;
import com.myapp.sunnypickup.vo.MemberVO;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/account")
public class LoginController {

    private LoginService service;


    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setBCryptPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setLoginService(LoginService service){
        this.service=service;
    }

    @Autowired
    private SendEmailService sendEmailService;

    @Value("E:/SpringBootProject/files/")
    private String imageServerUrl;


    @GetMapping("/signup")
    public ModelAndView signup(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/contents/account/signup");
        return mav;
    }


    @PostMapping(value="/signupOk")
    public ModelAndView signupOk(MemberVO vo,
                                 @RequestParam(value="file") MultipartFile mf,
                                 HttpServletRequest request){

        ModelAndView mav = new ModelAndView();
        String userid = vo.getUserid();
        String email = vo.getEmail();
        String username = vo.getUsername();

        System.out.println("유저아이디"+userid);
        System.out.println("이메일주소"+email);


        //파일업로드
        String path = imageServerUrl + "\\profilePhoto\\";
        String originalFileName = mf.getOriginalFilename();
        String newProfileName= "profile_"+originalFileName;
        vo.setProfile(newProfileName);

        File f = new File(path+newProfileName);
        String siteURL = request.getRequestURL().toString();


        try{
            vo.setUserpwd(passwordEncoder.encode(vo.getUserpwd()));

            int result = service.addMember(vo);

            if(result>0){
                sendEmailService.sendEmail(email, username, userid);
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



    @RequestMapping("/statusChange")
    public ModelAndView statusChange(MemberVO vo, String userid, HttpServletRequest req){
        ModelAndView mav = new ModelAndView();
        vo.setUserid(userid);
        try {
            int resultVO = service.statusChange(vo);

            if(resultVO>0){
                mav.setViewName("views/contents/account/statusChangeResult");
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
    public Map<String, Object> loginOk(MemberVO vo, HttpSession ses) throws Exception {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //복호화를 진행하자...
        ZRsaSecurity rsa = new ZRsaSecurity();
        //복호화 할 개인키를 세션에서 가져온다.
        PrivateKey privateKey = (PrivateKey) ses.getAttribute("_rsaPrivateKey_");

        String username = vo.getUserid(); //암호화 된 아이디를 담고
        String password = vo.getUserpwd(); //암호화 된 아이디를 담고

        String userId = rsa.decryptRSA(privateKey, username);  // 복호화 후 담고,
        String userKey = rsa.decryptRSA(privateKey, password);  // 복호화 하고 담고,

        //복호화된 정보를 넣어준다
        vo.setUserid(userId); // DB로 비교하기 위해서 복호화 된 아이디 / 패스워드를 다시 담아준다.
        vo.setUserpwd(userKey);

        MemberVO resultVO = service.loginCheck(vo);
        if(resultVO == null){
            resultMap.put("resultCode", 300);
            resultMap.put("resultMsg", "아이디를 확인해주세요.");
        }else{

            boolean match = passwordEncoder.matches(vo.getUserpwd(), resultVO.getUserpwd());

            if(match) {
                if (resultVO.getStatus() == 3) {
                    resultMap.put("resultCode", 301);
                    resultMap.put("resultMsg", "이미 탈퇴한 회원입니다.");
                } else if (resultVO.getStatus() == 0) {
                    resultMap.put("resultCode", 302);
                    resultMap.put("resultMsg", "인증이 되지않은 아이디입니다. 이메일 인증을 진행하세요.");
                } else {
                    resultMap.put("resultCode", 200);
                    ses.setAttribute("logStatus", "Y");
                    ses.setMaxInactiveInterval(60 * 30);
                    ses.setAttribute("userInfo", resultVO);
                    // 로그인이 성공하였으면, 키의 재사용을 막기 위해서, 개인키를 지워준다.
                    ses.removeAttribute("_rsaPrivateKey_");
                }
            }else {
                resultMap.put("resultCode", 401);
                resultMap.put("resultMsg", "비밀번호를 확인해주십시오.");
            }
        }
        return resultMap;
    }


    @RequestMapping("/getRSAKeyValue")
    @ResponseBody
    public Map<String, Object> getRSAKeyValue(HttpServletRequest req, HttpServletResponse res) throws Exception {

        Map<String,Object> resultMap = new HashMap<>();

        try {

            ZRsaSecurity zSecurity = new ZRsaSecurity();
            PrivateKey privateKey = zSecurity.getPrivateKey();

            HttpSession session = req.getSession();

            if(session.getAttribute("_rsaPrivateKey_") !=null) {
                session.removeAttribute("_rsaPrivateKey_");
            }

            session.setAttribute("_rsaPrivateKey_", privateKey);

            String publicKeyModulus = zSecurity.getRsaPublicKeyModulus();
            String publicKeyExponent = zSecurity.getRsaPublicKeyExponent();

            resultMap.put("publicKeyModulus", publicKeyModulus);
            resultMap.put("publicKeyExponent", publicKeyExponent);
        }catch (Exception e){

        }

        return resultMap;
    }


}
