package com.myapp.sunnypickup.mapper;


import com.myapp.sunnypickup.vo.HomeboardCommentVO;
import com.myapp.sunnypickup.vo.HomeboardVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface HomeboardCommentMapper {

    //리스트
    List<HomeboardCommentVO> getCommentList(int bno) throws SQLException;

}


