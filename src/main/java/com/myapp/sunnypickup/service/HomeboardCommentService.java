package com.myapp.sunnypickup.service;

import com.myapp.sunnypickup.mapper.HomeboardCommentMapper;
import com.myapp.sunnypickup.mapper.HomeboardMapper;
import com.myapp.sunnypickup.vo.HomeboardCommentVO;
import com.myapp.sunnypickup.vo.HomeboardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class HomeboardCommentService {

    private HomeboardCommentMapper mapper;

    @Autowired
    public void setHomeboarCommentdMapper(HomeboardCommentMapper homeboardCommentMapper){
        this.mapper=homeboardCommentMapper;
    }


    public List<HomeboardCommentVO> getCommentList(int bno) throws Exception{
        return mapper.getCommentList(bno);
    }

    public int commentInsert(HomeboardCommentVO vo) throws Exception{
        return mapper.commentInsert(vo);
    }

    public int commentEdit(HomeboardCommentVO vo) throws Exception{
        return mapper.commentEdit(vo);
    }

    public int commentDel(int bno) throws Exception{
        return mapper.commentDel(bno);
    }


}
