package com.chunjae.pro05.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private int pno;
    private int tno;
    private String buyer;
    private String impUid;
    private String merchantUid;
    private String applyNum;
}
