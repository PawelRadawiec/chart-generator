package com.info.chartgenerator.service.charts;

import com.info.chartgenerator.helper.ChartDataHelper;
import com.info.chartgenerator.model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LineCharDataServiceTest {
    private ChartData columnChartWithLabels;
    private ChartData columnChartWithoutLabels;
    private ChartData rowChartWithLabels;
    private ChartData rowChartWithoutLabels;

    @BeforeAll
    void beforeTest() {
        columnChartWithLabels = ChartDataHelper.initChartData(
                "src/test/resources/documents/line/unit-line-labels-data.xlsx",
                DataSeriesType.COLUMNS
        );
        columnChartWithoutLabels = ChartDataHelper.initChartData(
                "src/test/resources/documents/line/unit-line-data.xlsx",
                DataSeriesType.COLUMNS
        );
        rowChartWithLabels = ChartDataHelper.initChartData(
                "src/test/resources/documents/line/unit-line-labels-data.xlsx",
                DataSeriesType.ROWS
        );
        rowChartWithoutLabels = ChartDataHelper.initChartData(
                "src/test/resources/documents/line/unit-line-data.xlsx",
                DataSeriesType.ROWS
        );
    }

    @Test
    @DisplayName("should generate columns and rows for file without labels, expected 3 columns, 2 row")
    void shouldGetRowsAndColumnsFileWithoutLabels() {
        Assertions.assertEquals(3, columnChartWithoutLabels.getChartDataSet().size());
        Assertions.assertEquals(2, columnChartWithoutLabels.getChartDataSet().get(0).getData().size());
        Assertions.assertEquals(2, columnChartWithoutLabels.getChartDataSet().get(1).getData().size());
        Assertions.assertEquals(2, columnChartWithoutLabels.getChartDataSet().get(2).getData().size());

        Assertions.assertEquals(2, rowChartWithoutLabels.getChartDataSet().size());
        Assertions.assertEquals(3, rowChartWithoutLabels.getChartDataSet().get(0).getData().size());
        Assertions.assertEquals(3, rowChartWithoutLabels.getChartDataSet().get(1).getData().size());
    }

    @Test
    @DisplayName("should generate columns and rows for file with labels, expected 3 columns, 2 row")
    void shouldGetRowsAndColumnsFileWithLabels() {
        Assertions.assertEquals(3, columnChartWithLabels.getChartDataSet().size());
        Assertions.assertEquals(2, columnChartWithLabels.getChartDataSet().get(0).getData().size());
        Assertions.assertEquals(2, columnChartWithLabels.getChartDataSet().get(1).getData().size());
        Assertions.assertEquals(2, columnChartWithLabels.getChartDataSet().get(2).getData().size());

        Assertions.assertEquals(2, rowChartWithLabels.getChartDataSet().size());
        Assertions.assertEquals(3, rowChartWithLabels.getChartDataSet().get(0).getData().size());
        Assertions.assertEquals(3, rowChartWithLabels.getChartDataSet().get(1).getData().size());
    }

    @Test
    @DisplayName("should get two labels, expected 0, 1")
    void shouldGetTwoLabels() {
        Assertions.assertEquals(Arrays.asList("0", "1"), columnChartWithoutLabels.getLineChartLabels());
        Assertions.assertEquals(Arrays.asList("0", "1", "2"), rowChartWithoutLabels.getLineChartLabels());
    }

    @Test
    @DisplayName("should get ChartDataSet labels, expected: Column series 1, Column series 2, Column series 3 ")
    void shouldGetChartDataSetsLabel() {
        Assertions.assertEquals(
                Arrays.asList("Column series 1", "Column series 2", "Column series 3"),
                ChartDataHelper.getLabels(columnChartWithLabels)
        );
        Assertions.assertEquals(
                Arrays.asList("Series 1", "Series 2"),
                ChartDataHelper.getLabels(rowChartWithLabels)
        );
    }

}