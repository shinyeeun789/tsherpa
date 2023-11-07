package com.chunjae.pro05.persis;

import com.chunjae.pro05.entity.FreeComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FreeCommentMapper {
    public List<FreeComment> commentList(Map<String, Object> data);
    public FreeComment getComment(int cno);
    public int totalCnt(int fno);
    public int insertFreeComment(FreeComment freeComment);
    public int updateRecommend(Map<String, Object> data);

}
