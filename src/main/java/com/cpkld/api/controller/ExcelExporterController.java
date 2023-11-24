package com.cpkld.api.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpkld.service.UserExcelExpoter;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/export")
public class ExcelExporterController {
    @Autowired
    private UserExcelExpoter exporter;
    
    @GetMapping
    public void excelExporter(HttpServletResponse res) throws IOException {
        exporter.writeExcel(res);
    }
}
