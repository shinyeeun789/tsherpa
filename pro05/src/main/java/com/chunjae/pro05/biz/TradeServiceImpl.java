package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.Trade;
import com.chunjae.pro05.entity.TradeRecommends;
import com.chunjae.pro05.entity.TradeVO;
import com.chunjae.pro05.persis.TradeCategoryMapper;
import com.chunjae.pro05.persis.TradeMapper;
import com.chunjae.pro05.persis.TradeRecommendsMapper;
import com.chunjae.pro05.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeMapper tradeMapper;

    @Autowired
    private TradeRecommendsMapper tradeRecommendsMapper;

    @Override
    public List<TradeVO> tradeList(Page page) throws Exception {
        return tradeMapper.tradeList(page);
    }

    @Override
    public int totalCnt(Page page) throws Exception {
        return tradeMapper.totalCnt(page);
    }

    @Override
    public TradeVO getTradeVO(int tno) throws Exception {
        return tradeMapper.getTradeVO(tno);
    }

    @Override
    public Trade getTrade(int tno) throws Exception {
        return tradeMapper.getTrade(tno);
    }

    @Override
    public int tradeInsert(Trade trade) throws Exception {
        return tradeMapper.tradeInsert(trade);
    }

    @Override
    public int tradeEdit(Trade trade) throws Exception {
        return tradeMapper.tradeEdit(trade);
    }

    @Override
    public int tradeDelete(int tno) throws Exception {
        return tradeMapper.tradeDelete(tno);
    }

    @Override
    @Transactional
    public int updateRecommend(int tno, String type, TradeRecommends tradeRecommend) throws Exception {
        int result = 0;
        Map<String, Object> data = new HashMap<>();
        data.put("tno", tno);
        data.put("type", type);
        result = tradeMapper.updateRecommend(data);

        if(type.equals("Plus")) {
            result += tradeRecommendsMapper.insertTradeRecommend(tradeRecommend);
        } else {
            result += tradeRecommendsMapper.deleteTradeRecommend(tradeRecommend);
        }

        return result;
    }

    @Override
    public List<TradeVO> getTradeByName(String name) throws Exception {
        return tradeMapper.getTradeByName(name);
    }

    @Override
    public int totalTradeRecommend(Page page) throws Exception {
        return tradeRecommendsMapper.totalTradeRecommend(page);
    }

    @Override
    public List<TradeVO> myTradeRecommend(Page page) throws Exception {
        return tradeRecommendsMapper.myTradeRecommend(page);
    }

    @Override
    public boolean isRecommend(int tno, String name) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("tno", tno);
        data.put("name", name);
        return tradeRecommendsMapper.isRecommend(data);
    }

    @Override
    public int updateTradeStates(Trade trade) {
        int result = tradeMapper.updateState(trade.getTno());


        return 0;
    }
}
