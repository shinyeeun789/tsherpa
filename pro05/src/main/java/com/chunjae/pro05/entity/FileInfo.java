package com.chunjae.pro05.entity;

import lombok.Data;

@Data
public class FileInfo {
    private int no;
    private int articleNo;
    private String saveFolder;
    private String originFile;
    private String saveFile;
}
