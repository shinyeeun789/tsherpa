package com.chunjae.pro05.persis;

import com.chunjae.pro05.entity.Delivery;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeliveryMapper {

    int addDelivery(Delivery delivery);

}
