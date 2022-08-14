package com.publishers.dto;

import java.time.LocalDateTime;

public class PublisherDto {

    private final LocalDateTime created;
    private final String publisherCode;
    private String fileName;

    public PublisherDto(LocalDateTime created, String publisherCode, String fileName) {
        this.created = created;
        this.publisherCode = publisherCode;
        this.fileName = fileName;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public String getPublisherCode() {
        return publisherCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
