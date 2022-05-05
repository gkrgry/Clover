package com.daelim.clover.board.domain;

import lombok.Data;

@Data
public class FileDTO {
    private String fileId;
    private String fileName;
    private String contentType;

    public FileDTO(){}

    public FileDTO(String fileId, String fileName, String contentType) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
    }
}
