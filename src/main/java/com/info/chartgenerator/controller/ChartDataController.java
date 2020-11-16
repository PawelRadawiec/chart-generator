package com.info.chartgenerator.controller;

import com.info.chartgenerator.model.ChartData;
import com.info.chartgenerator.service.ChartDataSetService;
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
    public ResponseEntity<List<ChartData>> search() {
        return new ResponseEntity<>(chartDataSetService.search(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<List<ChartData>> delete(@PathVariable("id") String id) {
        return new ResponseEntity<>(chartDataSetService.delete(id), HttpStatus.OK);
    }

}
