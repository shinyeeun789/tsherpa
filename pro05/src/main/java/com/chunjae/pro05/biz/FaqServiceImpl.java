package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.Faq;
import com.chunjae.pro05.persis.FaqMapper;
import com.chunjae.pro05.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaqServiceImpl implements FaqService {

    @Autowired
    private FaqMapper faqMapper;

    @Override
    public List<Faq> faqList(Page page) throws Exception {
        return faqMapper.faqList(page);
    }

    @Override
    public int getCount(Page page) throws Exception {
        return faqMapper.getCount(page);
    }
}
