package com.info.chartgenerator.helper;

import com.info.chartgenerator.model.ChartData;
import com.info.chartgenerator.model.ChartDataSeriesOption;
import com.info.chartgenerator.model.ChartType;
import com.info.chartgenerator.model.DataSeriesType;
import com.info.chartgenerator.service.charts.LineCharDataService;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ChartDataHelper {

    public static ChartData initChartData(String filePath, DataSeriesType dataSeriesType) {
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
        ChartDataSeriesOption option = new ChartDataSeriesOption(ChartType.LINE, dataSeriesType);
        LineCharDataService lineCharDataService = new LineCharDataService();
        return lineCharDataService.generateLineChartData(sheet, option);
    }

}
