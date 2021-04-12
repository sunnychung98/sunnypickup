package com.myapp.sunnypickup.controller;

import com.myapp.sunnypickup.service.HomeboardService;
import com.myapp.sunnypickup.vo.HomeboardVO;
import com.myapp.sunnypickup.vo.MemberVO;
import com.myapp.sunnypickup.vo.PagingVO;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/homeboard")
public class HomeboardController {

    private HomeboardService service;

    @Autowired
    public void setHomeboardService(HomeboardService service){ this.service=service;}

    @GetMapping("/list")
    public ModelAndView homeboard(@RequestParam(value = "currentPage", defaultValue = "0")String currentPage){
        ModelAndView mav = new ModelAndView();
        try{
            Map<String, Object> param = new HashMap();
            //전체 카운트
            int totalCount = service.getTotalBoardCount(param);

            PagingVO pagingVO = new PagingVO();
            pagingVO.setTotalCount(totalCount);
            pagingVO.setCurrentPage(currentPage);

            param.put("startRow", pagingVO.getStartRow());
            param.put("pageInitPerPage", pagingVO.getCOUNT_PER_PAGE());


            List<HomeboardVO> list = service.getBoardList(param);

            mav.addObject("paging", pagingVO.getPager());
            mav.addObject("currentPage", pagingVO.getCurrentPage());
            mav.addObject("list", list);
            mav.addObject("pageTitle", "게시판");
            mav.addObject("pageName", "homeboard");

        }catch(Exception e){
            e.printStackTrace();
        }
        mav.setViewName("views/contents/homeboard/homeboard");
        return mav;
    }

    @GetMapping("/form")
    public ModelAndView homeboardForm(){
      ModelAndView mav = new ModelAndView();
      mav.setViewName("views/contents/homeboard/homeboardForm");
      return mav;

    }

    @Value("E:/SpringBootProject/files/")
    private String imageServerUrl;

    @PostMapping("/formOk")
    public ModelAndView homeboardFormOk (HomeboardVO vo, HttpServletRequest request, HttpSession session,
                                         @RequestParam(value="file") MultipartFile mf){
        ModelAndView mav = new ModelAndView();
        try{
            MemberVO member = (MemberVO)request.getSession().getAttribute("userInfo");
            vo.setUserid(member.getUserid());
            vo.setNickname(member.getNickname());
            vo.setIp(request.getRemoteAddr());


            //Thumbnail이미지 파일 경로 지정
            String path = imageServerUrl + "\\thumbnail\\";// 파일 저장할 위치
            String originFileName = mf.getOriginalFilename(); // 파일 이름
            UUID uid = UUID.randomUUID();
            String newThumbnailName = uid + "_" + originFileName;

            vo.setThumbnail(newThumbnailName);

            // 파일 업로드
            try {
                mf.transferTo(new File(path+"/"+newThumbnailName));
            } catch (Exception e) {
                e.getStackTrace();
            }


            int result = service.insertHomeboard(vo);

            if(result>0){
                mav.setViewName("redirect:/homeboard/list");
            }else{
                mav.setViewName("views/contents/homeboard/result");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return mav;
    }

    @GetMapping("/view")
    public ModelAndView homeboardView(@RequestParam(required = false) int bno){
        ModelAndView mav = new ModelAndView();

        try {
            List<HomeboardVO> list = service.homeboardSelect(bno);
            mav.addObject("list", list);

        }catch(Exception e){
            e.printStackTrace();
        }

        mav.setViewName("views/contents/homeboard/homeboardView");
        return mav;

    }

    @GetMapping("/edit")
    public ModelAndView homeboardEdit(@RequestParam("bno") int bno){
        ModelAndView mav = new ModelAndView();

        try{
            List<HomeboardVO> list = service.homeboardSelect(bno);
            mav.addObject("list", list);
        }catch(Exception e){
            e.printStackTrace();
        }

        mav.setViewName("views/contents/homeboard/homeboardEdit");
        return mav;

    }

    @PostMapping("/editOk")
    public ModelAndView homeboardEditOk(HomeboardVO vo, @RequestParam(value="file") MultipartFile mf){
        ModelAndView mav = new ModelAndView();

            try{
                String originalThumbnail = vo.getThumbnail();

                if(originalThumbnail == null ){

                }

                //Thumbnail이미지 파일 경로 지정


                String path = imageServerUrl + "\\thumbnail\\";// 파일 저장할 위치
                String originFileName = mf.getOriginalFilename(); // 파일 이름
                UUID uid = UUID.randomUUID();
                String newThumbnailName = uid + "_" + originFileName;

                vo.setThumbnail(newThumbnailName);

                // 파일 업로드
                try {
                    mf.transferTo(new File(path+"/"+newThumbnailName));
                } catch (Exception e) {
                    e.getStackTrace();
                }

                int result = service.homeboardEdit(vo);
                if(result>0){
                    mav.setViewName("redirect:/homeboard/list");
                }else{
                    mav.setViewName("views/contents/homeboard/result");
                }
            }catch(Exception e){
            e.printStackTrace();
            }

        return mav;

    }

    @GetMapping("/del")
    public ModelAndView homeboardDelete(@RequestParam("bno") int bno){
        ModelAndView mav = new ModelAndView();
        try{
            int result = service.homeboardDelete(bno);
            if(result>0){
                mav.setViewName("redirect:/homeboard/list");
            }else{
                mav.setViewName("views/contents/homeboard/result");
            }

        }catch(Exception e){
            e.printStackTrace();
        }


        return mav;
    }


}
