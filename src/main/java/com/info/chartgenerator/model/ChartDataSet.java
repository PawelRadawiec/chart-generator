package com.info.chartgenerator.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ChartDataSet {

    private String label;
    private List<Double> data;

    public List<Double> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

}
