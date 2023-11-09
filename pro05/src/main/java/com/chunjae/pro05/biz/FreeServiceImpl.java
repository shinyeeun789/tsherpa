package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.Category;
import com.chunjae.pro05.entity.Free;
import com.chunjae.pro05.persis.FreeMapper;
import com.chunjae.pro05.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreeServiceImpl implements FreeService {

    @Autowired
    private FreeMapper freeMapper;

    @Override
    public List<Category> cateList() {
        return freeMapper.cateList();
    }

    @Override
    public List<Free> freeList(Page page) {
        return freeMapper.freeList(page);
    }

    @Override
    public Free getFree(int fno) {
        return freeMapper.getFree(fno);
    }

    @Override
    public int insertFree(Free free) {
        return freeMapper.insertFree(free);
    }

    @Override
    public int updateFree(Free free) {
        return freeMapper.updateFree(free);
    }

    @Override
    public int deleteFree(int fno) {
        return freeMapper.deleteFree(fno);
    }

    @Override
    public int updateViews(int fno) {
        return freeMapper.updateViews(fno);
    }

    @Override
    public int updateRecommend(int fno) {
        return freeMapper.updateRecommend(fno);
    }

    @Override
    public int totalCnt() {
        return freeMapper.totalCnt();
    }
}
