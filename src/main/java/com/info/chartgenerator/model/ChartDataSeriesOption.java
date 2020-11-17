package com.info.chartgenerator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChartDataSeriesOption {

    private ChartType chartType;
    private DataSeriesType dataSeriesType;

}
