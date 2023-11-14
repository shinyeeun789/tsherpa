package com.chunjae.pro05.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    private int dno;
    private int pno;
    private String address;
    private String dname;
    private String dtel;
    private String dstate;
    private String arrivalDate;

}
