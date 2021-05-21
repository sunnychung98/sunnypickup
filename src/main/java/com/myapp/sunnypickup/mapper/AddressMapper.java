package com.myapp.sunnypickup.mapper;


import com.myapp.sunnypickup.vo.AddressVO;
import com.myapp.sunnypickup.vo.HomeboardVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface AddressMapper {

    //리스트
    List<AddressVO> getAddressList(Map<String, Object> param) throws SQLException;

    //입력
    int insertAddress(AddressVO vo) throws SQLException;

    //조회
    AddressVO addressSelect(int param) throws SQLException;

    //수정
    int addressEdit(HomeboardVO vo) throws SQLException;

    //삭제
    int addressDelete(int param) throws SQLException;

}


