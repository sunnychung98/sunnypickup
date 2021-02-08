package kr.code.homes.service;

import kr.code.homes.mapper.TestMapper;
import kr.code.homes.vo.TestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestService {

    private TestMapper mapper;

    @Autowired
    public void setTestMapper(TestMapper testMapper){
        this.mapper=testMapper;
    }

    public List<TestVO> getList(Map<String,Object> param) throws Exception{
        return mapper.getList(param);
    }
}
