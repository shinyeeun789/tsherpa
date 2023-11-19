package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.Qna;
import com.chunjae.pro05.persis.QnaMapper;
import com.chunjae.pro05.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QnaServiceImpl implements QnaService{

    @Autowired
    private QnaMapper qnaMapper;

    @Override
    public List<Qna> qnaList(Page page) throws Exception {
        return qnaMapper.qnaList(page);
    }

    @Override
    public List<Qna> noAnswerList(Page page) throws Exception {
        return qnaMapper.noAnswerList(page);
    }

    @Override
    public int noAnswerCount() throws Exception {
        return qnaMapper.noAnswerCount();
    }

    @Override
    public Qna qnaDetail(int qno) throws Exception {
        return qnaMapper.qnaDetail(qno);
    }

    @Override
    public Qna qnaRef(int qno, String type) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("qno", qno);
        data.put("type", type);
        return qnaMapper.qnaRef(data);
    }

    @Override
    public int getCount(Page page) throws Exception {
        return qnaMapper.getCount(page);
    }

    @Override
    public int noAnswerCount(Page page) throws Exception {
        return qnaMapper.noAnswerCount(page);
    }

    @Override
    @Transactional
    public void questionInsert(Qna dto) throws Exception {
        qnaMapper.questionInsert(dto);
        qnaMapper.parUpdate(dto);
    }

    @Override
    public void answerInsert(Qna dto) throws Exception {
        qnaMapper.answerInsert(dto);
    }

    @Override
    public void qnaDelete(int qno) throws Exception {
        qnaMapper.qnaDelete(qno);
    }

    @Override
    public void qnaEdit(Qna dto) throws Exception {
        qnaMapper.qnaEdit(dto);
    }
}
