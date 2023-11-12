package com.chunjae.pro05.entity;

import lombok.Data;

@Data
public class FreeVO {

    private Integer fno;
    private String cate;
    private String id;              // user id
    private String name;
    private String title;
    private String content;
    private String resdate;
    private Integer views;
    private Integer recommend;

}
