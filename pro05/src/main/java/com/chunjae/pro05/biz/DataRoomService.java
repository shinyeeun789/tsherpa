package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.DataRoom;
import com.chunjae.pro05.util.Page;

import java.util.List;

public interface DataRoomService {
    public List<DataRoom> dataRoomList(Page page) throws Exception;
    public int getCount(Page page) throws Exception;
    public void dataRoomInsert(DataRoom dataRoom) throws Exception;
    public DataRoom dataRoomDetail(int articleNo) throws Exception;
    public DataRoom dataRoomRef(int articleNo, String type) throws Exception;
    public void dataRoomEdit(DataRoom dataRoom) throws Exception;
    public void dataRoomDelete(int articleNo) throws Exception;
}
