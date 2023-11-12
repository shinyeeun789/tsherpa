package com.chunjae.pro05.persis;

import com.chunjae.pro05.entity.TradeRecommends;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TradeRecommendsMapper {
    int insertTradeRecommend(TradeRecommends tradeRecommends);
    int deleteTradeRecommend(TradeRecommends tradeRecommends);

}
