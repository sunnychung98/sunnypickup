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
    //리스트
    List<HomeboardVO> getBoardList(Map<String, Object> param) throws SQLException;
    //입력
    int insertHomeboard(HomeboardVO vo) throws SQLException;
    //조회
    List<HomeboardVO> homeboardSelect(int param) throws SQLException;
    //수정
    List<HomeboardVO> homeboardEdit(HomeboardVO vo) throws SQLException;
    //삭제
    int homeboardDelete(int param) throws SQLException;
}


