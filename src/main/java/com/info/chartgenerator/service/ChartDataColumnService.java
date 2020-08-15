package com.info.chartgenerator.service;

import com.info.chartgenerator.model.ChartData;
import com.info.chartgenerator.model.ChartDataSet;
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

    public ChartData generateColumnChartData(Sheet sheet) {
        ChartData chartData = new ChartData();
        List<ChartDataSet> chartDataSets = chartData.getChartDataSet();
        List<String> lineChartLabels = chartData.getLineChartLabels();
        sheet.forEach(row -> chartDataSets.add(appendData(row, lineChartLabels)));
        chartData.setChartDataSet(chartDataSets);
        chartData.setLineChartLabels(lineChartLabels.stream()
                .distinct()
                .collect(Collectors.toList())
        );
        appendLabels(chartData);
        return chartData;
    }

    private ChartDataSet appendData(Row row, List<String> lineChartLabels) {
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
