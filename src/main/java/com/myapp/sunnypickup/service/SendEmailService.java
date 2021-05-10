package com.myapp.sunnypickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;

@Service
public class SendEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendEmail(String email, String username, String userid){


        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            //메일 제목 설정
            helper.setSubject("메일테스트|"+username+"님 이메일 인증을 해주세요!");
            //수신자 설정
            helper.setTo(email);
            //템플릿에 전달할 데이터 설정
            Context context = new Context();
            context.setVariable("username", username);
            context.setVariable("userid", userid);
            context.setVariable("confirmUrl", "http://localhost:8082/account/statusChange?userid="+userid);
            //메일 내용 설정 : 템플릿 프로세스
            String html = templateEngine.process("mail-template", context);
            helper.setText(html, true);

            //메일 보내기
            javaMailSender.send(message);
        }catch(Exception e) {
            e.printStackTrace();
        }


    }

}
