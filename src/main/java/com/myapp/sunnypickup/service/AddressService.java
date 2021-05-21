package com.myapp.sunnypickup.service;

import com.myapp.sunnypickup.mapper.AddressMapper;
import com.myapp.sunnypickup.mapper.HomeboardMapper;
import com.myapp.sunnypickup.vo.AddressVO;
import com.myapp.sunnypickup.vo.HomeboardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class AddressService {

    private AddressMapper mapper;

    @Autowired
    public void setAddressMapper(AddressMapper addressMapper){
        this.mapper = addressMapper;
    }

    public List<AddressVO> getAddressList(Map<String,Object> param) throws Exception{
        return mapper.getAddressList(param);
    }

    public int insertAddress(AddressVO vo) throws Exception{
        return mapper.insertAddress(vo);
    }

    public AddressVO addressSelect(int param) throws Exception{
        return mapper.addressSelect(param);
    }

    public int AddressEdit(HomeboardVO vo) throws Exception{
        return mapper.addressEdit(vo);
    }

    public int addressDelete(int param) throws Exception{
        return mapper.addressDelete(param);
    }

}
