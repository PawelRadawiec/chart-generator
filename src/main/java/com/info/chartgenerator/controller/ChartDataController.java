package com.info.chartgenerator.controller;

import com.info.chartgenerator.model.ChartData;
import com.info.chartgenerator.service.ChartDataSetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/chart-data")
public class ChartDataController {

    private ChartDataSetService chartDataSetService;

    public ChartDataController(ChartDataSetService chartDataSetService) {
        this.chartDataSetService = chartDataSetService;
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<ChartData>> search() {
        return new ResponseEntity<>(chartDataSetService.search(), HttpStatus.OK);
    }

}
