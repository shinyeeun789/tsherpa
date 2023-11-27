package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.Delivery;
import com.chunjae.pro05.entity.AboutTradeVO;
import com.chunjae.pro05.entity.Payment;
import com.chunjae.pro05.persis.DeliveryMapper;
import com.chunjae.pro05.persis.PaymentMapper;
import com.chunjae.pro05.persis.TradeMapper;
import com.chunjae.pro05.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private DeliveryMapper deliveryMapper;
    @Autowired
    private TradeMapper tradeMapper;

    @Override
    @Transactional
    public int addPayment(Payment payment, Delivery delivery) throws Exception {
        // 1. 결제 정보 추가
        int result = paymentMapper.addPayment(payment);
        
        // 2. 배송 정보 추가
        result += deliveryMapper.addDelivery(delivery);

        // 3. 상품 정보 판매완료로 변경
        result += tradeMapper.updateState(payment.getTno());

        return result;
    }

    @Override
    public int totalPayment(Page page) throws Exception {
        return paymentMapper.totalPayment(page);
    }

    @Override
    public List<AboutTradeVO> myPayment(Page page) throws Exception {
        return paymentMapper.myPayment(page);
    }

    @Override
    public int totalProduct(Page page) throws Exception {
        return paymentMapper.totalProduct(page);
    }

    @Override
    public List<AboutTradeVO> myProduct(Page page) throws Exception {
        return paymentMapper.myProduct(page);
    }

    @Override
    @Transactional
    public int payComplete(Payment payment) throws Exception {
        int result = tradeMapper.updateState(payment.getTno());
        result += paymentMapper.addPayment(payment);
        return result;
    }

    @Override
    public List<Map<String, Integer>> yearProfit() throws Exception {
        return paymentMapper.yearProfit();
    }

    @Override
    public List<Map<String, Integer>> monthProfit() throws Exception {
        return paymentMapper.monthProfit();
    }
}
