package com.myapp.sunnypickup.vo;

import lombok.Data;

@Data
public class MemberVO {
    private String userid;
    private String username;
    private String userpwd;
    private String regcode;
    private int status;
}
