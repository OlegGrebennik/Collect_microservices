package com.og;


import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class PersonalFile {
    private byte[] file;
    private String fileName;

    private CommonsMultipartFile mFile;

    public PersonalFile() {
    }

    public PersonalFile(byte[] file, String fileName) {
        this.file = file;
        this.fileName = fileName;
    }

    public PersonalFile(byte[] file, String fileName, CommonsMultipartFile mFile) {
        this.file = file;
        this.fileName = fileName;
        this.mFile = mFile;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public CommonsMultipartFile getmFile() {
        return mFile;
    }

    public void setmFile(CommonsMultipartFile mFile) {
        this.mFile = mFile;
    }
}
