package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.FreeComment;
import com.chunjae.pro05.util.Page;
import java.util.List;

public interface FreeCommentService {
    public List<FreeComment> commentList(int fno, Page page);
    public FreeComment getComment(int cno);
    public int totalCnt(int fno);
    public int insertFreeComment(FreeComment comment);
    public int updateRecommend(int cno, String type);

}
