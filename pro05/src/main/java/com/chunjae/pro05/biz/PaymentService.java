package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.Delivery;
import com.chunjae.pro05.entity.Payment;

public interface PaymentService {

    public int addPayment(Payment payment, Delivery delivery) throws Exception;

}
