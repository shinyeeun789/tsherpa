package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.TradeCategory;
import com.chunjae.pro05.persis.TradeCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TradeCategoryImpl implements TradeCategoryService {

    @Autowired
    private TradeCategoryMapper tradeCategoryMapper;

    @Override
    public List<TradeCategory> tradeCategoryList() {
        return tradeCategoryMapper.tradeCategoryList();
    }
}
