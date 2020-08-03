package com.info.chartgenerator.model;

import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@ToString
public class ChartData {

    private List<ChartDataSet> chartDataSet;
    private List<String> lineChartLabels;

    public List<ChartDataSet> getChartDataSet() {
        if (chartDataSet == null) {
            return new ArrayList<>();
        }
        return chartDataSet;
    }

    public List<String> getLineChartLabels() {
        if (lineChartLabels == null) {
            return new ArrayList<>();
        }
        return lineChartLabels;
    }
}
