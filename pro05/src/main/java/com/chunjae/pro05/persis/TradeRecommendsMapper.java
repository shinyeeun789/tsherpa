package com.chunjae.pro05.persis;

import com.chunjae.pro05.entity.TradeRecommends;
import com.chunjae.pro05.entity.TradeVO;
import com.chunjae.pro05.util.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TradeRecommendsMapper {
    int insertTradeRecommend(TradeRecommends tradeRecommends);
    int deleteTradeRecommend(TradeRecommends tradeRecommends);
    int totalTradeRecommend(Page page);
    List<TradeVO> myTradeRecommend(Page page);

}
