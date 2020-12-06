package com.info.chartgenerator.helper;

import com.info.chartgenerator.model.*;
import com.info.chartgenerator.service.charts.LineCharDataService;
import com.info.chartgenerator.service.charts.PieChartDataService;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ChartDataHelper {

    public static ChartData initChartData(String filePath, ChartType type, DataSeriesType dataSeriesType) {
        FileInputStream fileInputStream;
        Workbook workbook = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sheet sheet = null;
        if (workbook != null) {
            sheet = workbook.getSheetAt(0);
        }
        ChartDataSeriesOption option = new ChartDataSeriesOption(type, dataSeriesType);
        switch (type) {
            case LINE:
                LineCharDataService lineCharDataService = new LineCharDataService();
                return lineCharDataService.generateLineChartData(sheet, option);
            case PIE:
                PieChartDataService pieChartDataService = new PieChartDataService();
                return pieChartDataService.generatePieChartData(sheet, option);

        }
        return new ChartData();
    }

    public static List<String> getLabels(ChartData chartData) {
        return chartData.getChartDataSet()
                .stream()
                .map(ChartDataSet::getLabel)
                .collect(Collectors.toList());
    }

}
