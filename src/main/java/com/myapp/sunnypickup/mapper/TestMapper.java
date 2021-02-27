package com.myapp.sunnypickup.mapper;

import com.myapp.sunnypickup.vo.TestVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface TestMapper {
    List<TestVO> getList(Map<String, Object> param) throws SQLException;
}
