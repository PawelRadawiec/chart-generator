package com.info.chartgenerator.service;

import com.info.chartgenerator.model.ChartData;
import com.info.chartgenerator.model.ChartDataSet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@SuppressWarnings("WeakerAccess")
public class LineCharDataService {

    public ChartData generateLineChartData(Sheet sheet) {
        ChartData chartData = new ChartData();
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

    private List<String> generateLineChartLabels(Sheet sheet) {
        return IntStream.range(0, sheet.getPhysicalNumberOfRows())
                .boxed()
                .map(String::valueOf)
                .collect(Collectors.toList());
    }

}
