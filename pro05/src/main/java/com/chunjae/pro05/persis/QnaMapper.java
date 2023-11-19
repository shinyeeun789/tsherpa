package com.chunjae.pro05.persis;

import com.chunjae.pro05.entity.Qna;
import com.chunjae.pro05.util.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface QnaMapper {

    public List<Qna> qnaList(Page page) throws Exception;
    public List<Qna> noAnswerList(Page page) throws Exception;
    public Qna qnaDetail(int qno) throws Exception;
    public Qna qnaRef(Map<String, Object> data) throws Exception;
    public int getCount(Page page) throws Exception;
    public int noAnswerCount(Page page) throws Exception;
    public int noAnswerCount() throws Exception;
    public void questionInsert(Qna dto) throws Exception;
    public void parUpdate(Qna dto) throws Exception;
    public void answerInsert(Qna dto) throws Exception;
    public void qnaDelete(int qno) throws Exception;
    public void qnaEdit(Qna dto) throws Exception;



}
