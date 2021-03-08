package com.myapp.sunnypickup.service;

import com.myapp.sunnypickup.mapper.HomeboardMapper;
import com.myapp.sunnypickup.vo.HomeboardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HomeboardService {

    private HomeboardMapper mapper;

    @Autowired
    public void setHomeboardMapper(HomeboardMapper homeboardMapper){
        this.mapper=homeboardMapper;
    }

    public List<HomeboardVO> getBoardList(Map<String,Object> param) throws Exception{
        return mapper.getBoardList(param);
    }
}
