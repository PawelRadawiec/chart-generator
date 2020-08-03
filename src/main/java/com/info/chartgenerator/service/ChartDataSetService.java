package com.info.chartgenerator.service;

import com.info.chartgenerator.model.ChartData;
import com.info.chartgenerator.model.ChartDataSet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class ChartDataSetService {

    /**
     * generate line chart data
     *
     * @param fileLocation
     * @return
     * @throws IOException
     */
    public ChartData generateChartData(String fileLocation) throws IOException {
        ChartData chartData = new ChartData();
        Workbook workbook = generateWorkBook(fileLocation);
        Sheet sheet = getSheet(workbook);
        List<ChartDataSet> chartDataSets = chartData.getChartDataSet();
        for (Row row : sheet) {
            int cellsRowNumber = 0;
            for (Cell cell : row) {
                ChartDataSet chartDataSet;
                if (cellsRowNumber >= chartDataSets.size()) {
                    chartDataSet = new ChartDataSet();
                    chartDataSet.setLabel("column " + cellsRowNumber);
                    chartDataSets.add(cellsRowNumber, chartDataSet);
                } else {
                    chartDataSet = chartDataSets.get(cellsRowNumber);
                }
                List<Double> data = chartDataSet.getData();
                data.add(cell.getNumericCellValue());
                chartDataSet.setData(data);
                chartDataSets.set(cellsRowNumber, chartDataSet);
                cellsRowNumber++;
            }
        }
        chartData.setChartDataSet(chartDataSets);
        return chartData;
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
