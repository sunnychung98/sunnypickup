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

    public void sendEmail(String to, String body, String topic){
      /*  System.out.println("sending mail...");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("oboksobok.kr@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(topic);
        simpleMailMessage.setText(body);


        javaMailSender.send(simpleMailMessage);
        System.out.println("sent email..");*/

        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            //메일 제목 설정
            helper.setSubject("스프링 부트 메일 전송");
            //수신자 설정
            helper.setTo("eveing2@naver.com");
            //템플릿에 전달할 데이터 설정
            Context context = new Context();
            context.setVariable("authKey", "test_value");
            context.setVariable("confirmUrl", "http://localhost:8082/main");
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
