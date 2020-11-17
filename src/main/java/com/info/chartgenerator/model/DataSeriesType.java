package com.info.chartgenerator.model;

public enum DataSeriesType {

    ROWS("ROWS"),
    COLUMNS("COLUMNS");

    private String value;

    DataSeriesType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
