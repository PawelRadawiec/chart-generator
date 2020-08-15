package com.info.chartgenerator.service;

import com.info.chartgenerator.model.ChartData;
import com.info.chartgenerator.model.ChartDataSet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("WeakerAccess")
public class ChartDataColumnService {

    public ChartData generateColumnChartData(Sheet sheet) {
        ChartData chartData = new ChartData();
        List<ChartDataSet> chartDataSets = chartData.getChartDataSet();
        List<String> lineChartLabels = chartData.getLineChartLabels();
        int index = 1;
        boolean lineChartLabelsSet = false;
        for (Row row : sheet) {
            ChartDataSet chartDataSet = new ChartDataSet();
            List<Double> data = new ArrayList<>();
            int columnIndex = 1;
            for (Cell cell : row) {
                data.add(cell.getNumericCellValue());
                chartDataSet.setLabel("Data " + index);
                if (!lineChartLabelsSet) {
                    lineChartLabels.add("set " + columnIndex);
                    columnIndex++;
                }
            }
            lineChartLabelsSet = true;
            chartDataSet.setData(data);
            chartDataSets.add(chartDataSet);
            index++;
        }
        chartData.setChartDataSet(chartDataSets);
        chartData.setLineChartLabels(lineChartLabels);
        return chartData;
    }

}
