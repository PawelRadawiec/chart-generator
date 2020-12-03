package com.info.chartgenerator.service.charts;

import com.info.chartgenerator.model.ChartData;
import com.info.chartgenerator.model.ChartDataSeriesOption;
import com.info.chartgenerator.model.ChartDataSet;
import com.info.chartgenerator.model.ChartType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SuppressWarnings("WeakerAccess")
public class LineCharDataService {

    public ChartData generateLineChartData(Sheet sheet, ChartDataSeriesOption option) {
        ChartData chartData = new ChartData();
        switch (option.getDataSeriesType()) {
            case ROWS:
                appendRowTypeData(sheet, chartData);
                break;
            case COLUMNS:
                appendColumnTypeData(sheet, chartData);
        }
        chartData.setType(ChartType.LINE);
        return chartData;
    }

    private void appendColumnTypeData(Sheet sheet, ChartData chartData) {
        Row firstRow = sheet.getRow(0);
        if (firstRow == null) {
            return;
        }
        int rowIndex = 0;
        for (int columnIndex = 0; columnIndex < firstRow.getLastCellNum(); columnIndex++) {
            ChartDataSet chartDataSet = new ChartDataSet();
            for (Row row : sheet) {
                Cell cell = row.getCell(columnIndex);
                boolean numericValue = (CellType.NUMERIC.equals(cell.getCellType()) || CellType.FORMULA.equals(cell.getCellType()));
                if (numericValue && columnIndex == 0) {
                    chartData.getLineChartLabels().add(String.valueOf(rowIndex));
                    rowIndex++;
                }
                if (numericValue) {
                    chartDataSet.getData().add(cell.getNumericCellValue());
                }
            }
            chartDataSet.setLabel(String.format("Column series %s", columnIndex + 1));
            chartData.getChartDataSet().add(chartDataSet);
        }
    }

    private void appendRowTypeData(Sheet sheet, ChartData chartData) {
        int rowIndex = 0;
        int cellCounter = 0;
        boolean firstRowAsLabels = false;
        for (Row row : sheet) {
            ChartDataSet rowData = new ChartDataSet();
            for (Cell cell : row) {
                firstRowAsLabels = (rowIndex == 0 && cellCounter == 0 && CellType.STRING.equals(cell.getCellType()));
                appendLineChartLabels(cell, chartData.getLineChartLabels(), rowIndex);
                appendRowData(cell, rowData);
                cellCounter++;
            }
            boolean dataNotEmpty = (!rowData.getData().isEmpty());
            if (!firstRowAsLabels && rowIndex == 0 && dataNotEmpty) {
                chartData.getChartDataSet().add(rowData);
            }
            if (rowIndex > 0 && dataNotEmpty) {
                chartData.getChartDataSet().add(rowData);
            }
            rowData.setLabel(String.format("Series %s", rowIndex));
            rowIndex++;
        }
    }

    private void appendLineChartLabels(Cell cell, List<String> lineChartLabels, int rowIndex) {
        boolean stringType = CellType.STRING.equals(cell.getCellType());
        if (rowIndex != 0) {
            return;
        }
        lineChartLabels.add(stringType ? cell.getStringCellValue() : String.valueOf(cell.getColumnIndex()));
    }

    private void appendRowData(Cell cell, ChartDataSet rowData) {
        if (CellType.NUMERIC.equals(cell.getCellType()) || CellType.FORMULA.equals(cell.getCellType())) {
            rowData.getData().add(cell.getNumericCellValue());
        }
    }

}
