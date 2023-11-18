package com.chunjae.pro05.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TradeChat {

    private Integer no;
    private String title;
    private String roomId;
    private String buyer;
    private String seller;
    private String name;
    private String pact;
    private String cact;

}
