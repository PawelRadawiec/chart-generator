package com.info.chartgenerator.service;

import com.info.chartgenerator.model.ChartData;
import com.info.chartgenerator.model.ChartDataSeriesOption;
import com.info.chartgenerator.model.ChartDataSet;
import com.info.chartgenerator.model.ChartType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("WeakerAccess")
public class LineCharDataService {

    public ChartData generateLineChartData(Sheet sheet, ChartDataSeriesOption option) {
        ChartData chartData = new ChartData();
        List<ChartDataSet> chartDataSets = chartData.getChartDataSet();
        List<String> lineChartLabels = new ArrayList<>();
        switch (option.getDataSeriesType()) {
            case ROWS:
                appendRowTypeData(sheet, chartDataSets, lineChartLabels);
                break;
            case COLUMNS:
                appendColumnTypeData(sheet, chartDataSets, lineChartLabels);
        }
        chartData.setChartDataSet(chartDataSets
                .stream()
                .filter(data -> !data.getData().isEmpty())
                .collect(Collectors.toList())
        );
        chartData.setLineChartLabels(lineChartLabels);
        chartData.setType(ChartType.LINE);
        return chartData;
    }

    private void appendColumnTypeData(Sheet sheet, List<ChartDataSet> chartDataSets, List<String> lineChartLabels) {
        Row firstRow = sheet.getRow(0);
        if (firstRow == null) {
            return;
        }
        for (int columnIndex = 0; columnIndex < firstRow.getLastCellNum(); columnIndex++) {
            ChartDataSet chartDataSet = new ChartDataSet();
            List<Double> data = new ArrayList<>();
            int rowIndex = 1;
            for (Row row : sheet) {
                Cell cell = row.getCell(columnIndex);
                boolean cellTypeString = CellType.STRING.equals(cell.getCellType());
                if (!cellTypeString && columnIndex == 0) {
                    lineChartLabels.add(String.valueOf(rowIndex));
                    rowIndex++;
                }
                if (!cellTypeString) {
                    data.add(cell.getNumericCellValue());
                }
            }
            chartDataSet.setData(data);
            chartDataSets.add(chartDataSet);
        }
    }

    private void appendRowTypeData(Sheet sheet, List<ChartDataSet> chartDataSets, List<String> lineChartLabels) {
        int rowIndex = 0;
        int cellCounter = 0;
        boolean firstRowAsLabels = false;
        for (Row row : sheet) {
            ChartDataSet rowData = new ChartDataSet();
            List<Double> rowDataValue = new ArrayList<>();
            for (Cell cell : row) {
                firstRowAsLabels = (rowIndex == 0 && cellCounter == 0 && CellType.STRING.equals(cell.getCellType()));
                appendLineChartLabels(cell, lineChartLabels, rowIndex);
                appendRowData(cell, rowData, rowDataValue, rowIndex);
                cellCounter++;
            }
            rowData.setData(rowDataValue);
            if (!firstRowAsLabels && rowIndex == 0) {
                chartDataSets.add(rowData);
            }
            if (rowIndex > 0) {
                chartDataSets.add(rowData);
            }
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

    private void appendColumnData() {

    }

    private void appendRowData(Cell cell, ChartDataSet rowData, List<Double> rowDataValue, int rowIndex) {
        if (CellType.NUMERIC.equals(cell.getCellType()) || CellType.FORMULA.equals(cell.getCellType())) {
            rowDataValue.add(cell.getNumericCellValue());
            rowData.setLabel("Series " + rowIndex);
        }
    }

}
