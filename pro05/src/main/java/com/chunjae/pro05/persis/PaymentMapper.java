package com.chunjae.pro05.persis;

import com.chunjae.pro05.entity.AboutTradeVO;
import com.chunjae.pro05.entity.Payment;
import com.chunjae.pro05.util.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PaymentMapper {

    int addPayment(Payment payment);
    int totalPayment(Page page);
    List<AboutTradeVO> myPayment(Page page);
    int totalProduct(Page page);
    List<AboutTradeVO> myProduct(Page page);
    List<Map<String, Integer>> yearProfit();
    List<Map<String, Integer>> monthProfit();

}
