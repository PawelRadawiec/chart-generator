package com.info.chartgenerator.service;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class WorkBookService {

    public Sheet getSheet(Workbook workbook) {
        return workbook.getSheetAt(0);
    }

    public Workbook generateWorkBook(String fileLocation) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(fileLocation));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new XSSFWorkbook(fileInputStream);
    }

}
