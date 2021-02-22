package kr.code.homes.mapper;

import kr.code.homes.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
@Mapper
public interface LoginMapper {

    MemberVO loginCheck(MemberVO vo) throws SQLException;

}
