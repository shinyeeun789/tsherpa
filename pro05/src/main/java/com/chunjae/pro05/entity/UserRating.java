package com.chunjae.pro05.entity;

import lombok.Data;

@Data
public class UserRating {

    private int uid;
    private int tno;
    private String seller;
    private String buyer;
    private int rating;
    private boolean trustTrade;
    private String content;

    private int cntProducts;
    private float avgRating;
    private int cntTrustTrade;

}
