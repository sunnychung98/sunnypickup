package com.myapp.sunnypickup.mapper;


import com.myapp.sunnypickup.vo.HomeboardVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface HomeboardMapper {

    //리스트 전체카운트
    int getTotalBoardCount(Map<String, Object> param) throws SQLException;
    //리스트
    List<HomeboardVO> getBoardList(Map<String, Object> param) throws SQLException;
    //입력
    int insertHomeboard(HomeboardVO vo) throws SQLException;
    //조회
    HomeboardVO homeboardSelect(int param) throws SQLException;
    //수정
    int homeboardEdit(HomeboardVO vo) throws SQLException;
    //삭제
    int homeboardDelete(int param) throws SQLException;
    //메인
    List<HomeboardVO> getBoardListForMain(Map<String, Object> param) throws SQLException;
}


