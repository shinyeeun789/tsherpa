package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.Trade;
import com.chunjae.pro05.entity.TradeRecommends;
import com.chunjae.pro05.entity.TradeVO;
import com.chunjae.pro05.util.Page;

import java.util.List;
import java.util.Map;

public interface TradeService {
    public List<TradeVO> tradeList(Page page) throws Exception;
    public int totalCnt(Page page) throws Exception;
    public TradeVO getTradeVO(int tno) throws Exception;
    public Trade getTrade(int tno) throws Exception;
    public int tradeInsert(Trade trade) throws Exception;
    public int updateRecommend(int tno, String type, TradeRecommends tradeRecommend) throws Exception;
    public int tradeEdit(Trade trade) throws Exception;
    public int tradeDelete(int tno) throws Exception;
    public List<TradeVO> getTradeByName(String name) throws Exception;
    public int totalTradeRecommend(Page page) throws Exception;
    public List<TradeVO> myTradeRecommend(Page page) throws Exception;
    public boolean isRecommend(int tno, String name) throws Exception;
    public int updateTradeStates(Trade trade) throws Exception;
    public List<Map<String, Object>> locationRank() throws Exception;
    public List<Map<String, Integer>> tradeCntList() throws Exception;

}
