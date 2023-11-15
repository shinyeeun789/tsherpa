package com.chunjae.pro05.entity;

import lombok.Data;

@Data
public class AboutTradeVO {
    private int pno;
    private int tno;
    private String title;
    private String name;
    private String state;
    private String buyer;
    private String address;
    private String tradeType;
    private int deliveryFee;
    private int price;
    private String dcode;
    private String dname;
    private String dtel;
    private String dstate;
    private String arrivalDate;
    private int uid;
    private int rating;
    private boolean trustTrade;
    private String content;
    private int isReview;
}
