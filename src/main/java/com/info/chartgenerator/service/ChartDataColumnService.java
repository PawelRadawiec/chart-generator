package com.info.chartgenerator.service;

import com.info.chartgenerator.model.ChartData;
import com.info.chartgenerator.model.ChartDataSeriesOption;
import com.info.chartgenerator.model.ChartDataSet;
import com.info.chartgenerator.model.ChartType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@SuppressWarnings("WeakerAccess")
public class ChartDataColumnService {

    public ChartData generateColumnChartData(Sheet sheet, ChartDataSeriesOption option) {
        ChartData chartData = new ChartData();
        switch (option.getDataSeriesType()) {
            case ROWS:
                appendRowsData(sheet, chartData);
                break;
            case COLUMNS:
                appendColumnTypeData(sheet, chartData);
        }
        appendLabels(chartData);
        chartData.setType(ChartType.BAR);
        return chartData;
    }

    private void appendColumnTypeData(Sheet sheet, ChartData chartData) {
        Row firstRow = sheet.getRow(sheet.getFirstRowNum());
        int rowIndex = 1;
        boolean firstRowIteration = true;
        for (int i = 0; i < firstRow.getLastCellNum(); i++) {
            ChartDataSet chartDataSet = new ChartDataSet();
            for (Row row : sheet) {
                if (firstRowIteration) {
                    chartData.getLineChartLabels().add(String.format("Set %s", rowIndex));
                    rowIndex++;
                }
                Cell cell = row.getCell(i);
                chartDataSet.getData().add(cell.getNumericCellValue());
            }
            firstRowIteration = false;
            chartData.getChartDataSet().add(chartDataSet);
        }
    }

    private void appendRowsData(Sheet sheet, ChartData chartData) {
        boolean firstCellIteration = true;
        for (Row row : sheet) {
            ChartDataSet chartDataSet = new ChartDataSet();
            int columnIndex = 1;
            for (Cell cell : row) {
                if (firstCellIteration) {
                    chartData.getLineChartLabels().add(String.format("Set %s", columnIndex));
                }
                chartDataSet.getData().add(cell.getNumericCellValue());
                columnIndex++;
            }
            chartData.getChartDataSet().add(chartDataSet);
            firstCellIteration = false;
        }
    }


    private void appendLabels(ChartData chartData) {
        AtomicInteger index = new AtomicInteger(1);
        chartData.getChartDataSet().forEach(data -> {
            data.setLabel("label " + index.get());
            index.getAndIncrement();
        });
    }

}
