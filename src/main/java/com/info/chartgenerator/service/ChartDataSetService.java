package com.info.chartgenerator.service;

import com.info.chartgenerator.model.ChartData;
import com.info.chartgenerator.model.ChartType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class ChartDataSetService {

    private LineCharDataService lineService;
    private ChartDataColumnService columnService;

    public ChartDataSetService(LineCharDataService lineService, ChartDataColumnService columnService) {
        this.lineService = lineService;
        this.columnService = columnService;
    }

    public ChartData generateChartData(String fileLocation, ChartType type) throws IOException {
        Workbook workbook = generateWorkBook(fileLocation);
        Sheet sheet = getSheet(workbook);
        switch (type) {
            case LINE:
                return lineService.generateLineChartData(sheet);
            case BAR:
                return columnService.generateColumnChartData(sheet);
            default:
                return new ChartData();
        }
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
