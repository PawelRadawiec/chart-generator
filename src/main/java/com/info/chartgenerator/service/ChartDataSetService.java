package com.info.chartgenerator.service;

import com.info.chartgenerator.model.ChartData;
import com.info.chartgenerator.model.ChartDataSet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
            int columnIndex = 0;
            for (Cell cell : row) {
                ChartDataSet chartDataSet;
                if (columnIndex >= chartDataSets.size()) {
                    chartDataSet = new ChartDataSet();
                    chartDataSet.setLabel("column " + columnIndex);
                    chartDataSets.add(columnIndex, chartDataSet);
                } else {
                    chartDataSet = chartDataSets.get(columnIndex);
                }
                List<Double> data = chartDataSet.getData();
                if (CellType.STRING.equals(cell.getCellType())) {
                    chartDataSet.setLabel(cell.getStringCellValue());
                } else {
                    data.add(cell.getNumericCellValue());
                }
                chartDataSet.setData(data);
                chartDataSets.set(columnIndex, chartDataSet);
                columnIndex++;
            }
        }
        chartData.setChartDataSet(chartDataSets);
        chartData.setLineChartLabels(generateLineChartLabels(sheet));
        return chartData;
    }

    private Sheet getSheet(Workbook workbook) {
        return workbook.getSheetAt(0);
    }

    private List<String> generateLineChartLabels(Sheet sheet) {
        return IntStream.range(0, sheet.getPhysicalNumberOfRows())
                .boxed()
                .map(String::valueOf)
                .collect(Collectors.toList());
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
