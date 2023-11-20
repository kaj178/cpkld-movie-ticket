package com.cpkld.service;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;

public interface ExcelExpoter {
    public void writeExcel(HttpServletResponse res) throws IOException;
}
