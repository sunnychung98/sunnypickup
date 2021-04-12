package com.myapp.sunnypickup.vo;

import lombok.Data;

@Data
public class MemberVO {
    private String userid;
    private String username;
    private String userpwd;
    private String regcode;
    private String nickname;
    private String tel;
    private int status;
    private String regdate;
    private String email;
    private int point;

}
