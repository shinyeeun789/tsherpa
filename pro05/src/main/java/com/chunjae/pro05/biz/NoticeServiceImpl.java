package com.chunjae.pro05.biz;


import com.chunjae.pro05.entity.Notice;
import com.chunjae.pro05.persis.NoticeMapper;
import com.chunjae.pro05.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public List<Notice> noticeList(Page page) throws Exception {
        return noticeMapper.noticeList(page);
    }

    @Override
    public List<Notice> noticeListLimit() throws Exception {
        return noticeMapper.noticeListLimit();
    }

    @Override
    public int getCount(Page page) throws Exception {
        return noticeMapper.getCount(page);
    }

    @Override
    @Transactional
    public Notice noticeDetail(int seq) throws Exception {
        noticeMapper.countUp(seq);
        return noticeMapper.noticeDetail(seq);
    }

    @Override
    public Notice noticeRef(int seq, String type) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("seq", seq);
        data.put("type", type);
        return noticeMapper.noticeRef(data);
    }

    @Override
    public void noticeInsert(Notice notice) throws Exception {
        noticeMapper.noticeInsert(notice);
    }

    @Override
    public void noticeDelete(int seq) throws Exception {
        noticeMapper.noticeDelete(seq);
    }

    @Override
    public void noticeEdit(Notice notice) throws Exception {
        noticeMapper.noticeEdit(notice);
    }
}
