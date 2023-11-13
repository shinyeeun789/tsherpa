package com.chunjae.pro05.persis;

import com.chunjae.pro05.entity.Trade;
import com.chunjae.pro05.entity.TradeVO;
import com.chunjae.pro05.util.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TradeMapper {

    List<TradeVO> tradeList(Page page);
    int totalCnt(Page page);
    TradeVO getTradeVO(int tno);
    Trade getTrade(int tno);
    int tradeInsert(Trade trade);
    int tradeEdit(int tno);
    int tradeDelete(int tno);
    int updateRecommend(Map<String, Object> data);
    List<TradeVO> getTradeByName(String name);

}
