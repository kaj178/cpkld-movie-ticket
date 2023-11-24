package com.cpkld.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpkld.dto.UserDTO;
import com.cpkld.service.UserExcelExpoter;
import com.cpkld.service.auth.AuthService;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserExcelExporterImpl implements UserExcelExpoter {
    @Autowired
    private AuthService service;

    @Override
    public void writeExcel(HttpServletResponse res) throws IOException {
        List<UserDTO> userList = service.getAllAccounts();
        Workbook workBook = new XSSFWorkbook();
        Sheet sheet = workBook.createSheet("User data");
        Row headerRow = sheet.createRow(0);

        // Set font and color for header's columns
        XSSFFont headerFont = (XSSFFont) workBook.createFont();
        CellStyle headerCellStyle = workBook.createCellStyle();

        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        headerCellStyle.setFont(headerFont);

        String[] columns = {"ID", "Họ và tên", "Email", "SDT", "Địa chỉ"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowIndex = 1;
        for (UserDTO user: userList) {
            Row row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getFullname());
            row.createCell(2).setCellValue(user.getEmail());
            row.createCell(3).setCellValue(user.getPhone());
            row.createCell(4).setCellValue(user.getAddress());
            rowIndex++;
        }

        res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        res.setHeader("Content-Disposition", "attachment; filename=customers.xlsx");
        workBook.write(res.getOutputStream());
        workBook.close();
    }
    
}
