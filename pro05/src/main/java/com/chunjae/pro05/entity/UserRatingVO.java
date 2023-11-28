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
}
