package com.info.chartgenerator.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
public class ChartData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Transient
    private List<ChartDataSet> chartDataSet;
    @Transient
    private List<String> lineChartLabels;
    @Transient
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
