package com.chunjae.pro05.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfo {

    private int ano;
    private int id;
    private String bank;
    private String accountNum;

}