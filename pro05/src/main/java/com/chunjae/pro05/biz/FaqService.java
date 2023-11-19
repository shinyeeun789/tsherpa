package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.Faq;
import com.chunjae.pro05.util.Page;

import java.util.List;

public interface FaqService {
    public List<Faq> faqList(Page page) throws Exception;
    public int getCount(Page page) throws Exception;
}
