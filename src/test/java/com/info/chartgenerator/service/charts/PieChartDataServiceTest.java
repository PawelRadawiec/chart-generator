package com.info.chartgenerator.service.charts;

import com.info.chartgenerator.helper.ChartDataHelper;
import com.info.chartgenerator.model.ChartData;
import com.info.chartgenerator.model.ChartType;
import com.info.chartgenerator.model.DataSeriesType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PieChartDataServiceTest {

    private Map<String, ChartData> chartDataMap;

    @BeforeAll
    void beforeAll() {
        chartDataMap = new HashMap<>();
        chartDataMap.put("pieColumnNoLabels", ChartDataHelper.initChartData(
                "src/test/resources/documents/pie/pie-column-no-labels.xlsx",
                ChartType.PIE,
                DataSeriesType.COLUMNS
        ));
        chartDataMap.put("pieColumnLabels", ChartDataHelper.initChartData(
                "src/test/resources/documents/pie/pie-column-labels.xlsx",
                ChartType.PIE,
                DataSeriesType.COLUMNS
        ));
        chartDataMap.put("pieRowNoLabels", ChartDataHelper.initChartData(
                "src/test/resources/documents/pie/pie-row-no-labels.xlsx",
                ChartType.PIE,
                DataSeriesType.ROWS
        ));
        chartDataMap.put("pieRowLabels", ChartDataHelper.initChartData(
                "src/test/resources/documents/pie/pie-row-labels.xlsx",
                ChartType.PIE,
                DataSeriesType.ROWS
        ));
    }

    @Test
    @DisplayName("should generate columns and rows for file without labels")
    void shouldGetRowsAndColumnsFileWithoutLabels() {
        ChartData pieRowNoLabels = chartDataMap.get("pieRowNoLabels");
        ChartData pieRowLabels = chartDataMap.get("pieRowLabels");
        ChartData pieColumnNoLabels = chartDataMap.get("pieColumnNoLabels");
        ChartData pieColumnLabels = chartDataMap.get("pieColumnLabels");
        // columns
        assertEquals(1, pieRowNoLabels.getChartDataSet().size());
        assertEquals(1, pieRowLabels.getChartDataSet().size());
        assertEquals(4, pieColumnNoLabels.getChartDataSet().get(0).getData().size());
        assertEquals(4, pieColumnLabels.getChartDataSet().get(0).getData().size());
        //rows
        assertEquals(6, pieRowNoLabels.getChartDataSet().get(0).getData().size());
        assertEquals(4, pieRowLabels.getChartDataSet().get(0).getData().size());
        assertEquals(1, pieColumnNoLabels.getChartDataSet().size());
        assertEquals(1, pieColumnLabels.getChartDataSet().size());
    }

    @Test
    @DisplayName("should get ChartDataSet labels")
    void shouldGetChartDataSetsLabel() {
        ChartData pieRowNoLabels = chartDataMap.get("pieRowNoLabels");
        ChartData pieRowLabels = chartDataMap.get("pieRowLabels");
        ChartData pieColumnNoLabels = chartDataMap.get("pieColumnNoLabels");
        ChartData pieColumnLabels = chartDataMap.get("pieColumnLabels");
        assertEquals(
                Arrays.asList("Data 1", "Data 2", "Data 3", "Data 4", "Data 5", "Data 6"),
                pieRowNoLabels.getLineChartLabels()
        );
        assertEquals(
                Arrays.asList("A", "B", "C", "D"),
                pieRowLabels.getLineChartLabels()
        );
        assertEquals(
                Arrays.asList("Data 1", "Data 2", "Data 3", "Data 4"),
                pieColumnNoLabels.getLineChartLabels()
        );
        assertEquals(
                Arrays.asList("Java", "Ruby", "C#", "C++"),
                pieColumnLabels.getLineChartLabels()
        );
    }

}