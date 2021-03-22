package com.myapp.sunnypickup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
public class CkeditorFileUploadController {

    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    Date today = new Date();
    String dateForFile = format1.format(today);

    //							ckeditor Upload 주소
    @RequestMapping(value="/homeboard/upload", method = RequestMethod.POST)
    public void imageUpload(HttpServletRequest request,
                            HttpServletResponse response, MultipartHttpServletRequest multiFile
            , @RequestParam MultipartFile upload) throws Exception{


        // 랜덤 문자 생성: 중복 파일이름 방지를 위해
        UUID uid = UUID.randomUUID();
        HttpSession ses = request.getSession();
        OutputStream out = null;
        PrintWriter printWriter = null;

        //인코딩
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        try{
            //파일 이름 가져오기
            String fileName = upload.getOriginalFilename();
            byte[] bytes = upload.getBytes();

            //이미지 경로 생성						집들이이미지 파일 경로 + 저장되는날 날짜
            String path = "E:/SpringBootProject/files/"+dateForFile+"\\";
            String ckUploadPath = path + uid + "_" + fileName;

            System.out.println(path);
            File folder = new File(path);

            //해당 디렉토리 확인
            if(!folder.exists()){
                try{
                    folder.mkdirs(); // 폴더 생성
                }catch(Exception e){
                    e.getStackTrace();
                }
            }

            out = new FileOutputStream(new File(ckUploadPath));
            out.write(bytes);
            out.flush(); // outputStram에 저장된 데이터를 전송하고 초기화

            //String callback = request.getParameter("content");
            printWriter = response.getWriter();
            String fileUrl = "/uploadImg/" + dateForFile+ "/" + uid + "_" + fileName;  // 블로그 화면에 뿌려줄때

            // 업로드시 메시지 출력
            printWriter.println("{\"filename\" : \""+fileName+"\", \"uploaded\" : 1, \"url\":\""+fileUrl+"\"}");
            printWriter.flush();


        }catch(IOException e){
            e.printStackTrace();
        } finally {
            try {
                if(out != null) { out.close(); }
                if(printWriter != null) { printWriter.close(); }
            } catch(IOException e) { e.printStackTrace(); }
        }

        return;
    }

}
