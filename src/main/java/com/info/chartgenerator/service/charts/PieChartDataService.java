package com.info.chartgenerator.service.charts;

import com.info.chartgenerator.model.ChartData;
import com.info.chartgenerator.model.ChartDataSeriesOption;
import com.info.chartgenerator.model.ChartDataSet;
import com.info.chartgenerator.model.ChartType;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PieChartDataService {

    public ChartData generatePieChartData(Sheet sheet, ChartDataSeriesOption option) {
        ChartData chartData = new ChartData();
        appendPieData(sheet, chartData, option);
        chartData.setType(ChartType.PIE);
        return chartData;
    }

    private void appendPieData(Sheet sheet, ChartData chartData, ChartDataSeriesOption option) {
        Row firstRow = sheet.getRow(sheet.getFirstRowNum());
        Row secondRow = sheet.getRow(sheet.getFirstRowNum() + 1);
        boolean firstRowAsLegend = false;
        if (secondRow != null) {
            firstRowAsLegend = CellType.STRING.equals(firstRow.getCell(0).getCellType());
        }
        ChartDataSet rowData = new ChartDataSet();
        switch (option.getDataSeriesType()) {
            case COLUMNS: {
                for (int i = 0; i < firstRow.getLastCellNum(); i++) {
                    appendDataValue(firstRowAsLegend ? secondRow.getCell(i) : firstRow.getCell(i), rowData.getData());
                    appendLineChartLabels(firstRow.getCell(i), chartData.getLineChartLabels(), i);
                }
                break;
            }
            case ROWS: {
                int rowIndex = 0;
                for (Row row : sheet) {
                    appendDataValue(row.getCell(firstRowAsLegend ? 1 : 0), rowData.getData());
                    appendLineChartLabels(row.getCell(0), chartData.getLineChartLabels(), rowIndex);
                    rowIndex++;
                }
            }
        }
        chartData.getChartDataSet().add(rowData);
    }

    private void appendDataValue(Cell cell, List<Double> rowDataValue) {
        if (!CellType.STRING.equals(cell.getCellType())) {
            rowDataValue.add(cell.getNumericCellValue());
        }
    }

    private void appendLineChartLabels(Cell cell, List<String> lineChartLabels, int index) {
        lineChartLabels.add(CellType.STRING.equals(cell.getCellType()) ? cell.getStringCellValue()
                : String.format("Data %s", index + 1));
    }

}
