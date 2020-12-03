package com.info.chartgenerator.controller;

import com.info.chartgenerator.model.ChartData;
import com.info.chartgenerator.service.charts.ChartDataSetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<ChartData>> search(Pageable pageable) {
        return new ResponseEntity<>(chartDataSetService.search(pageable), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity delete(@PathVariable("id") String id) {
        chartDataSetService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
