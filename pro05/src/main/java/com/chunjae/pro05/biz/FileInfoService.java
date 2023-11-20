package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.FileInfo;

import java.util.List;

public interface FileInfoService {
    public List<FileInfo> fileInfoList(int articleNo) throws Exception;
}
