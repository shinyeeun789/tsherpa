package com.chunjae.pro05.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRatingVO {
    private int id;
    private String name;
    private String username;
    private String email;
    private String regdate;
    private float ratingAvg;
    private int trustTradeCnt;

    // userDetail에 사용
    private String act;
    private int uid;
    private String title;
    private String buyer;
    private int rating;
    private int trustTrade;
    private String content;
}
