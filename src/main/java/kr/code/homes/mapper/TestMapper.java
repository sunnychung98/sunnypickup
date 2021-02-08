package kr.code.homes.mapper;

import kr.code.homes.vo.TestVO;
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
