package com.chunjae.pro05.entity;

import lombok.Data;

@Data
public class User {

    private Integer id;         // 정수 타입을 null 처리 하고 싶은 경우 Integer 사용
    private String name;
    private String password;
    private String username;
    private String email;
    private String address;
    private String tel;
    private String regdate;
    private String lev;
    private String act;

}
