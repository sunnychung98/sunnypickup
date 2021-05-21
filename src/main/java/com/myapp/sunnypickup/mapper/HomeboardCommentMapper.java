package com.myapp.sunnypickup.mapper;


import com.myapp.sunnypickup.vo.HomeboardCommentVO;
import com.myapp.sunnypickup.vo.HomeboardVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface HomeboardCommentMapper {

    //리스트
    List<HomeboardCommentVO> getCommentList(int bno) throws SQLException;

    //댓글입력하기
    int commentInsert(HomeboardCommentVO vo) throws SQLException;

    //댓글 수정하기
    int commentEdit(HomeboardCommentVO vo) throws SQLException;

    //댓글 삭제하기
    int commentDel(int bno) throws SQLException;

}


