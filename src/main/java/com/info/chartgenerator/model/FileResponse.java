package com.info.chartgenerator.model;

public class FileResponse {
    private String fileName;
    private String fileType;
    private String downloadUri;
    private long size;

    public FileResponse(String fileName, String fileType, String downloadUri, long size) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.downloadUri = downloadUri;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getDownloadUri() {
        return downloadUri;
    }

    public void setDownloadUri(String downloadUri) {
        this.downloadUri = downloadUri;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

}
