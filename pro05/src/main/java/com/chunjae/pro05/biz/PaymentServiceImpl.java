package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.Delivery;
import com.chunjae.pro05.entity.Payment;
import com.chunjae.pro05.persis.DeliveryMapper;
import com.chunjae.pro05.persis.PaymentMapper;
import com.chunjae.pro05.persis.TradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
