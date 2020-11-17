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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("WeakerAccess")
public class ChartDataColumnService {

    public ChartData generateColumnChartData(Sheet sheet, ChartDataSeriesOption option) {
        ChartData chartData = new ChartData();
        List<ChartDataSet> chartDataSets = chartData.getChartDataSet();
        List<String> lineChartLabels = chartData.getLineChartLabels();
        switch (option.getDataSeriesType()) {
            case ROWS:
                sheet.forEach(row -> chartDataSets.add(appendRowsTypeData(row, lineChartLabels)));
                break;
            case COLUMNS:
                appendColumnTypeData(sheet, lineChartLabels, chartDataSets);
        }
        chartData.setChartDataSet(chartDataSets);
        chartData.setLineChartLabels(lineChartLabels.stream()
                .distinct()
                .collect(Collectors.toList())
        );
        appendLabels(chartData);
        chartData.setType(ChartType.BAR);
        return chartData;
    }

    private void appendColumnTypeData(Sheet sheet, List<String> lineChartLabels, List<ChartDataSet> chartDataSets) {
        int columnCount = 0;
        for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
            Row row = sheet.getRow(i);
            columnCount = row.getLastCellNum();
            lineChartLabels.add("Set " + i + 1);
        }
        for (int i = 0; i < columnCount; i++) {
            ChartDataSet chartDataSet = new ChartDataSet();
            List<Double> data = new ArrayList<>();
            for (Row row : sheet) {
                Cell cell = row.getCell(i);
                data.add(cell.getNumericCellValue());
            }
            chartDataSet.setOrder((long) i);
            chartDataSet.setData(data);
            chartDataSets.add(chartDataSet);
        }
    }

    private ChartDataSet appendRowsTypeData(Row row, List<String> lineChartLabels) {
        ChartDataSet chartDataSet = new ChartDataSet();
        List<Double> data = new ArrayList<>();
        int columnIndex = 1;
        for (Cell cell : row) {
            data.add(cell.getNumericCellValue());
            lineChartLabels.add("set " + columnIndex);
            columnIndex++;
        }
        chartDataSet.setData(data);
        return chartDataSet;
    }

    private void appendLabels(ChartData chartData) {
        AtomicInteger index = new AtomicInteger(1);
        chartData.getChartDataSet().forEach(data -> {
            data.setLabel("label " + index.get());
            index.getAndIncrement();
        });
    }

}
