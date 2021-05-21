package com.myapp.sunnypickup.controller;

import com.myapp.sunnypickup.service.HomeboardCommentService;
import com.myapp.sunnypickup.service.HomeboardService;
import com.myapp.sunnypickup.vo.HomeboardCommentVO;
import com.myapp.sunnypickup.vo.HomeboardVO;
import com.myapp.sunnypickup.vo.MemberVO;
import com.myapp.sunnypickup.vo.PagingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class HomeboardCommentController {

    private HomeboardCommentService service;

    @Autowired
    public void setHomeboardCommentService(HomeboardCommentService service){ this.service=service;}

    @RequestMapping("/commentList")
    @ResponseBody
    public List<HomeboardCommentVO> getCommentList(int bno) throws Exception {

           List<HomeboardCommentVO> commentList = service.getCommentList(bno);

        return commentList;
    }

    //집들이- 새로운 댓글쓰기
    @RequestMapping(value="/commentWrite", method= RequestMethod.GET)
    @ResponseBody
    public int commentInsert(HomeboardCommentVO vo, HttpSession ses) throws Exception {
        int result = service.commentInsert(vo);

        return result;
    }

    //집들이- 댓글수정하기
    @RequestMapping(value="/commentEdit")
    @ResponseBody
    public int commentEdit(HomeboardCommentVO vo) throws Exception {

        int result = service.commentEdit(vo);

        return result;
    }

    //댓글 삭제하기
    @RequestMapping(value="/commentDel")
    @ResponseBody
    public int commentDelete(int hbcno) throws Exception {

        int result = service.commentDel(hbcno);

        return result;
    }






}
