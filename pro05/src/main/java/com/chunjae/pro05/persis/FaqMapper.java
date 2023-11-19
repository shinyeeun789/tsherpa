package com.chunjae.pro05.persis;

import com.chunjae.pro05.entity.Faq;
import com.chunjae.pro05.util.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FaqMapper {
    public List<Faq> faqList(Page page) throws Exception;
    public int getCount(Page page) throws Exception;
}
