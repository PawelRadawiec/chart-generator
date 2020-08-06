package com.info.chartgenerator.controller;

import com.info.chartgenerator.model.ChartData;
import com.info.chartgenerator.service.ChartDataSetService;
import com.info.chartgenerator.service.UploadStorageService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin("*")
public class UploadController {

    private UploadStorageService storageService;
    private ChartDataSetService chartDataSetService;

    public UploadController(UploadStorageService storageService, ChartDataSetService chartDataSetService) {
        this.storageService = storageService;
        this.chartDataSetService = chartDataSetService;
    }

    @PostMapping(value = "/upload")
    public ChartData uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = storageService.storeFile(file);
        return chartDataSetService.generateChartData(fileName);
    }

}
