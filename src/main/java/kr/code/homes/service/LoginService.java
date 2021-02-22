package kr.code.homes.service;

import kr.code.homes.mapper.LoginMapper;
import kr.code.homes.mapper.TestMapper;
import kr.code.homes.vo.MemberVO;
import kr.code.homes.vo.TestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
}
