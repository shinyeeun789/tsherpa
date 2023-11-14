package com.chunjae.pro05.entity;

import lombok.Data;

@Data
public class User {

    private long id;         // 정수 타입을 null 처리 하고 싶은 경우 Integer 사용
    private String name;
    private String password;
    private String username;
    private String email;
    private String addr1;
    private String addr2;
    private String postcode;
    private String tel;
    private String birth;
    private String bank;
    private String accountNum;
    private String regdate;
    private String lev;
    private String act;

}
