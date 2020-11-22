package com.info.chartgenerator.service;

import com.info.chartgenerator.model.ChartData;
import com.info.chartgenerator.model.ChartDataSeriesOption;
import com.info.chartgenerator.model.ChartDataSet;
import com.info.chartgenerator.model.ChartType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PieChartDataService {

    public ChartData generatePieChartData(Sheet sheet, ChartDataSeriesOption option) {
        ChartData chartData = new ChartData();
        List<ChartDataSet> chartDataSets = chartData.getChartDataSet();
        List<String> lineChartLabels = new ArrayList<>();

        switch (option.getDataSeriesType()) {
            case ROWS:
                appendRowPieData(sheet, chartDataSets, lineChartLabels);
                break;
            case COLUMNS:
                appendColumnPieData(sheet, chartDataSets, lineChartLabels);
        }
        chartData.setChartDataSet(chartDataSets);
        chartData.setLineChartLabels(lineChartLabels);
        chartData.setType(ChartType.PIE);
        return chartData;
    }

    private void appendRowPieData(Sheet sheet, List<ChartDataSet> chartDataSets, List<String> lineChartLabels) {
        ChartDataSet rowData = new ChartDataSet();
        List<Double> rowDataValue = new ArrayList<>();
        int columnIndex = 1;
        for (Row row : sheet) {
            Cell cell = row.getCell(0);
            rowDataValue.add(cell.getNumericCellValue());
            lineChartLabels.add("Row " + columnIndex);
            columnIndex++;
        }
        rowData.setData(rowDataValue);
        chartDataSets.add(rowData);
    }

    private void appendColumnPieData(Sheet sheet, List<ChartDataSet> chartDataSets, List<String> lineChartLabels) {
        Row firstRow = sheet.getRow(sheet.getLastRowNum());
        if (firstRow == null) {
            return;
        }
        ChartDataSet rowData = new ChartDataSet();
        List<Double> rowDataValue = new ArrayList<>();
        for (int i = 0; i < firstRow.getLastCellNum(); i++) {
            Cell cell = firstRow.getCell(i);
            rowDataValue.add(cell.getNumericCellValue());
            lineChartLabels.add("Cell " + i + 1);
        }
        rowData.setData(rowDataValue);
        chartDataSets.add(rowData);
    }

}
