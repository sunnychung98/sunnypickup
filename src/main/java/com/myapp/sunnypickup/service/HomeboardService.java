package com.myapp.sunnypickup.service;

import com.myapp.sunnypickup.mapper.HomeboardMapper;
import com.myapp.sunnypickup.vo.HomeboardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class HomeboardService {

    private HomeboardMapper mapper;

    @Autowired
    public void setHomeboardMapper(HomeboardMapper homeboardMapper){
        this.mapper=homeboardMapper;
    }


    public  int getTotalBoardCount(Map<String, Object> param) throws SQLException {
        return mapper.getTotalBoardCount(param);
    }
    public List<HomeboardVO> getBoardList(Map<String,Object> param) throws Exception{
        return mapper.getBoardList(param);
    }

    public int insertHomeboard(HomeboardVO vo) throws Exception{
        return mapper.insertHomeboard(vo);
    }

    public List<HomeboardVO> homeboardSelect(int param) throws Exception{
        return mapper.homeboardSelect(param);
    }

    public List<HomeboardVO> homeboardEdit(HomeboardVO vo) throws Exception{
        return mapper.homeboardEdit(vo);
    }

    public int homeboardDelete(int param) throws Exception{
        return mapper.homeboardDelete(param);
    }
}
