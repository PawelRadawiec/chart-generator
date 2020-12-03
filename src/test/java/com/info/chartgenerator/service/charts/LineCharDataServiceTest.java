package com.info.chartgenerator.service.charts;

import com.info.chartgenerator.model.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class LineCharDataServiceTest {
    private static final String COLUMN_FILE = "src/test/resources/unit-line-columnt-data.xlsx";
    private static final String COLUMN_LABELS_FILE = "src/test/resources/unit-line-column-labels-data.xlsx";

    @BeforeEach
    void beforeEach() {
    }

    @Test
    @DisplayName("should generate columns and rows for file without labels, expected 3 columns, 2 row")
    void shouldGetRowsAndColumnsFileWithoutLabels() throws Exception {
        FileInputStream fileInputStream = new FileInputStream(COLUMN_FILE);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        ChartDataSeriesOption option = new ChartDataSeriesOption(ChartType.LINE, DataSeriesType.COLUMNS);
        LineCharDataService lineCharDataService = new LineCharDataService();
        ChartData chartData = lineCharDataService.generateLineChartData(sheet, option);

        Assertions.assertEquals(3, chartData.getChartDataSet().size());
        Assertions.assertEquals(2, chartData.getChartDataSet().get(0).getData().size());
        Assertions.assertEquals(2, chartData.getChartDataSet().get(1).getData().size());
        Assertions.assertEquals(2, chartData.getChartDataSet().get(2).getData().size());
    }

    @Test
    @DisplayName("should generate columns and rows for file with labels, expected 3 columns, 2 row")
    void shouldGetRowsAndColumnsFileWithLabels() throws Exception {
        FileInputStream fileInputStream = new FileInputStream(COLUMN_LABELS_FILE);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        ChartDataSeriesOption option = new ChartDataSeriesOption(ChartType.LINE, DataSeriesType.COLUMNS);
        LineCharDataService lineCharDataService = new LineCharDataService();
        ChartData chartData = lineCharDataService.generateLineChartData(sheet, option);

        Assertions.assertEquals(3, chartData.getChartDataSet().size());
        Assertions.assertEquals(2, chartData.getChartDataSet().get(0).getData().size());
        Assertions.assertEquals(2, chartData.getChartDataSet().get(1).getData().size());
        Assertions.assertEquals(2, chartData.getChartDataSet().get(2).getData().size());
    }


    @Test
    @DisplayName("should get two labels, expected 0, 1")
    void shouldGetTwoLabels() throws Exception {
        FileInputStream fileInputStream = new FileInputStream(COLUMN_FILE);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        ChartDataSeriesOption option = new ChartDataSeriesOption(ChartType.LINE, DataSeriesType.COLUMNS);
        LineCharDataService lineCharDataService = new LineCharDataService();
        ChartData chartData = lineCharDataService.generateLineChartData(sheet, option);

        Assertions.assertEquals(Arrays.asList("0", "1"), chartData.getLineChartLabels());
    }

    @Test
    @DisplayName("should get ChartDataSet labels, expected: Column series 1, Column series 2, Column series 3 ")
    void shouldGetChartDataSetsLabel() throws Exception {
        FileInputStream fileInputStream = new FileInputStream(COLUMN_FILE);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        ChartDataSeriesOption option = new ChartDataSeriesOption(ChartType.LINE, DataSeriesType.COLUMNS);
        LineCharDataService lineCharDataService = new LineCharDataService();
        ChartData chartData = lineCharDataService.generateLineChartData(sheet, option);

        List<String> labels = chartData.getChartDataSet()
                .stream()
                .map(ChartDataSet::getLabel)
                .collect(Collectors.toList());
        Assertions.assertEquals(Arrays.asList("Column series 1", "Column series 2", "Column series 3"), labels);
    }

}