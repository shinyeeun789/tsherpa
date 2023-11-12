package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.Category;
import com.chunjae.pro05.entity.Free;
import com.chunjae.pro05.entity.FreeVO;
import com.chunjae.pro05.util.Page;

import java.util.List;

public interface FreeService {
    public List<Category> cateList();

    public List<Free> freeList(Page page);
    public FreeVO getFreeVO(int fno);
    public Free getFree(int fno);
    public int insertFree(Free free);
    public int updateFree(Free free);
    public int deleteFree(int fno);
    public int updateViews(int fno);
    public int updateRecommend(int fno, String type);
    public int totalCnt(Page page);

}
