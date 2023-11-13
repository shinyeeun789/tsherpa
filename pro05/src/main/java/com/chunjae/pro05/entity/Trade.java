package com.chunjae.pro05.entity;

import lombok.Data;

@Data
public class Trade {

    private int tno;
    private int cno;
    private String title;
    private String name;
    private String resdate;
    private String location;
    private String tradeType;
    private int price;
    private int deliveryFee;
    private String demage;
    private String content;
    private int recommend;
    private String itemImg;
    private String state;

}
