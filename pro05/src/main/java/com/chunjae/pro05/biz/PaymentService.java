package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.Delivery;
import com.chunjae.pro05.entity.AboutTradeVO;
import com.chunjae.pro05.entity.Payment;
import com.chunjae.pro05.util.Page;

import java.util.List;
import java.util.Map;

public interface PaymentService {

    public int addPayment(Payment payment, Delivery delivery) throws Exception;
    public int totalPayment(Page page) throws Exception;
    public List<AboutTradeVO> myPayment(Page page) throws Exception;
    public int totalProduct(Page page) throws Exception;
    public List<AboutTradeVO> myProduct(Page page) throws Exception;
    public int payComplete(Payment payment) throws Exception;
    public List<Map<String, Integer>> yearProfit() throws Exception;
    public List<Map<String, Integer>> monthProfit() throws Exception;

}
