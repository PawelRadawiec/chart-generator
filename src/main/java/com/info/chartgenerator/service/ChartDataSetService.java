package com.info.chartgenerator.service;

import com.info.chartgenerator.model.ChartData;
import com.info.chartgenerator.model.ChartType;
import com.info.chartgenerator.repository.ChartDataRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@Service
public class ChartDataSetService {

    private LineCharDataService lineService;
    private ChartDataColumnService columnService;
    private ChartDataRepository repository;

    public ChartDataSetService(
            LineCharDataService lineService,
            ChartDataColumnService columnService,
            ChartDataRepository repository
    ) {
        this.lineService = lineService;
        this.columnService = columnService;
        this.repository = repository;
    }

    public ChartData generateChartData(Map<String, String> fileData, ChartType type) throws IOException {
        Workbook workbook = generateWorkBook(fileData.get("filePath"));
        Sheet sheet = getSheet(workbook);
        ChartData chartData;
        switch (type) {
            case LINE:
                chartData = lineService.generateLineChartData(sheet);
                break;
            case BAR:
                chartData = columnService.generateColumnChartData(sheet);
                break;
            default:
                chartData = new ChartData();
        }
        chartData.setFilePath(fileData.get("filePath"));
        chartData.setFileName(fileData.get("fileName"));
        return save(chartData);
    }

    private ChartData save(ChartData data) {
        return repository.create(data);
    }

    private Sheet getSheet(Workbook workbook) {
        return workbook.getSheetAt(0);
    }

    private Workbook generateWorkBook(String fileLocation) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(fileLocation));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new XSSFWorkbook(fileInputStream);
    }

}
