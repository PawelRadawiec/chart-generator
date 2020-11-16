package com.info.chartgenerator.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Document(collection = "chart_data")
public class ChartData {
    @Id
    private String id;
    private List<ChartDataSet> chartDataSet;
    private List<String> lineChartLabels;
    private List<Double> data;
    private ChartType type;
    private String fileName;
    private String filePath;

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
