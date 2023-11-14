package com.chunjae.pro05.persis;

import com.chunjae.pro05.entity.Payment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {

    int addPayment(Payment payment);

}
