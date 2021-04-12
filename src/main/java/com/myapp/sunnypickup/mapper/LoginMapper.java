package com.myapp.sunnypickup.mapper;

import com.myapp.sunnypickup.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
@Mapper
public interface LoginMapper {

    MemberVO loginCheck(MemberVO vo) throws SQLException;

    String dupFilter(String userid) throws SQLException;
    int addMember(MemberVO vo) throws SQLException;
}
