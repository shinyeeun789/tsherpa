package com.chunjae.pro05.persis;

import com.chunjae.pro05.entity.DataRoom;
import com.chunjae.pro05.entity.FileInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileInfoMapper {
    public List<FileInfo> fileInfoList(int articleNo) throws Exception;
    public void fileInfoInsert(DataRoom dataRoom) throws Exception;
    public void fileInfoDelete(int articleNo) throws Exception;

}
