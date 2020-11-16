package com.info.chartgenerator.service;

import com.info.chartgenerator.model.ChartData;
import com.info.chartgenerator.model.ChartType;
import com.info.chartgenerator.repository.ChartDataRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ChartDataSetService {

    private LineCharDataService lineService;
    private ChartDataColumnService columnService;
    private ChartDataRepository repository;
    private WorkBookService workBookService;

    public ChartDataSetService(
            LineCharDataService lineService,
            ChartDataColumnService columnService,
            ChartDataRepository repository, WorkBookService workBookService) {
        this.lineService = lineService;
        this.columnService = columnService;
        this.repository = repository;
        this.workBookService = workBookService;
    }

    private ChartData save(ChartData data) {
        return repository.save(data);
    }

    public List<ChartData> search() {
        return repository.findAll();
    }

    public ChartData generateChartData(Map<String, String> fileData, ChartType type) throws IOException {
        Workbook workbook = workBookService.generateWorkBook(fileData.get("filePath"));
        Sheet sheet = workBookService.getSheet(workbook);
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

}
