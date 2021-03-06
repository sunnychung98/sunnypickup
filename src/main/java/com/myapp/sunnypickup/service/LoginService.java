package com.myapp.sunnypickup.service;

import com.myapp.sunnypickup.vo.MemberVO;
import com.myapp.sunnypickup.mapper.LoginMapper;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class LoginService {

    private LoginMapper mapper;



    @Autowired
    public void setLoginMapper(LoginMapper loginMapper){
        this.mapper=loginMapper;
    }

    public MemberVO loginCheck(MemberVO vo) throws SQLException {
        return mapper.loginCheck(vo);
    }

    public String dupFilter(String userid) throws SQLException{
        return mapper.dupFilter(userid);
    }

    public int addMember(MemberVO vo) throws SQLException{
        return mapper.addMember(vo);
    }

    public int statusChange(MemberVO vo) throws SQLException{
        return mapper.statusChange(vo);
    }



}
