package com.info.chartgenerator.controller;

import com.info.chartgenerator.model.ChartData;
import com.info.chartgenerator.model.ChartDataSeriesOption;
import com.info.chartgenerator.model.ChartType;
import com.info.chartgenerator.model.DataSeriesType;
import com.info.chartgenerator.service.charts.ChartDataSetService;
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
    public ChartData uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") ChartType type,
            @RequestParam("dataSeriesType") DataSeriesType dataSeriesType
    ) throws IOException {
        ChartDataSeriesOption option = new ChartDataSeriesOption(type, dataSeriesType);
        return chartDataSetService.generateChartData(storageService.storeFile(file), option);
    }

}
