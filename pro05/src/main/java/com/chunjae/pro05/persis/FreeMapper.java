package com.chunjae.pro05.persis;

import com.chunjae.pro05.entity.Category;
import com.chunjae.pro05.entity.Free;
import com.chunjae.pro05.util.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FreeMapper {
    List<Category> cateList();
    List<Free> freeList(Page page);
    Free getFree(int fno);
    int insertFree(Free free);
    int updateFree(Free free);
    int deleteFree(int fno);
    int updateViews(int fno);
    int updateRecommend(Map<String, Object> data);
    int totalCnt();

}
