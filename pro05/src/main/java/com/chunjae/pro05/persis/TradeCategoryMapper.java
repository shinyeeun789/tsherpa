package com.chunjae.pro05.persis;

import com.chunjae.pro05.entity.TradeCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TradeCategoryMapper {
    List<TradeCategory> tradeCategoryList();

}
