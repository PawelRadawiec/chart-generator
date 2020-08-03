package com.info.chartgenerator.controller;

import com.info.chartgenerator.model.FileResponse;
import com.info.chartgenerator.service.UploadStorageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    private UploadStorageService storageService;

    public UploadController(UploadStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping(value = "/upload")
    public FileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = storageService.storeFile(file);
        return new FileResponse(fileName, "TYPE", "URI", file.getSize());
    }

}
