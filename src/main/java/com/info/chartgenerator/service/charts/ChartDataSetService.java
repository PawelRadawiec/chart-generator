package com.info.chartgenerator.service.charts;

import com.info.chartgenerator.model.ChartData;
import com.info.chartgenerator.model.ChartDataSeriesOption;
import com.info.chartgenerator.repository.ChartDataRepository;
import com.info.chartgenerator.service.WorkBookService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ChartDataSetService {

    private LineCharDataService lineService;
    private ChartDataColumnService columnService;
    private PieChartDataService pieService;
    private ChartDataRepository repository;
    private WorkBookService workBookService;

    public ChartDataSetService(
            LineCharDataService lineService,
            ChartDataColumnService columnService,
            PieChartDataService pieService, ChartDataRepository repository, WorkBookService workBookService) {
        this.lineService = lineService;
        this.columnService = columnService;
        this.pieService = pieService;
        this.repository = repository;
        this.workBookService = workBookService;
    }

    private ChartData save(ChartData data) {
        return repository.save(data);
    }

    public Page<ChartData> search(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public ChartData generateChartData(Map<String, String> fileData, ChartDataSeriesOption option) throws IOException {
        Workbook workbook = workBookService.generateWorkBook(fileData.get("filePath"));
        Sheet sheet = workBookService.getSheet(workbook);
        ChartData chartData;
        switch (option.getChartType()) {
            case LINE:
                chartData = lineService.generateLineChartData(sheet, option);
                break;
            case BAR:
                chartData = columnService.generateColumnChartData(sheet, option);
                break;
            case PIE:
                chartData = pieService.generatePieChartData(sheet, option);
                break;
            default:
                chartData = new ChartData();
        }
        chartData.setOption(option);
        chartData.setFilePath(fileData.get("filePath"));
        chartData.setFileName(fileData.get("fileName"));
        return save(chartData);
    }

}
