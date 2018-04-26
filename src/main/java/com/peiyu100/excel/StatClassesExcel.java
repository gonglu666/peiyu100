package com.peiyu100.excel;

import com.peiyu100.model.StatClasses;
import com.peiyu100.model.StatSchool;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class StatClassesExcel {
    public static void main(String[] args) throws Exception{
        StatClasses statClasses = new StatClasses();
        statClasses.setSchoolName("陪育小学");
        statClasses.setClasses("333");
        statClasses.setNum(2);

        StatClasses statClasses2 = new StatClasses();
        statClasses2.setSchoolName("陪育小学3");
        statClasses2.setClasses("33333");
        statClasses2.setNum(3);

        List<StatClasses> list = new ArrayList<>();
        list.add(statClasses);
        list.add(statClasses2);

        report(list);
    }


    public static void report(List<StatClasses> list) throws Exception{
        String fileName = "按班级统计";
        File f = new File("e:/data/"+fileName+".xlsx");
        FileOutputStream out = new FileOutputStream(f);
        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            XSSFSheet sheet = wb.createSheet(fileName);
            int rowIndex = 0;
            List<String> titles = new ArrayList<>();
            titles.add("学校");
            titles.add("班级");
            titles.add("学生数");
            rowIndex = ExcelUtil.writeTitlesToExcel(wb, sheet, titles);
            writeRowsToExcel(wb, sheet, list, rowIndex);
            ExcelUtil. autoSizeColumns(sheet, titles.size() + 1);

            wb.write(out);
        } finally {
            wb.close();
        }
        out.close();
    }



    private static int writeRowsToExcel(XSSFWorkbook wb, Sheet sheet, List<StatClasses>  rows, int rowIndex) {
        Font dataFont = wb.createFont();
        dataFont.setFontName("simsun");
        // dataFont.setFontHeightInPoints((short) 14);
        dataFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle dataStyle = wb.createCellStyle();
        dataStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        dataStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        dataStyle.setFont(dataFont);
        ExcelUtil.setBorder(dataStyle, BorderStyle.THIN, new XSSFColor(new java.awt.Color(0, 0, 0)));

        for (StatClasses statClasses : rows) {
            Row dataRow = sheet.createRow(rowIndex);
            //学校
            Cell cell0 = dataRow.createCell(0);
            cell0.setCellValue(statClasses.getSchoolName());
            cell0.setCellStyle(dataStyle);
            //班级
            Cell cell1 = dataRow.createCell(1);
            cell1.setCellValue(statClasses.getClasses());
            cell1.setCellStyle(dataStyle);

            //数量
            Cell cell2 = dataRow.createCell(2);
            cell2.setCellValue(statClasses.getNum());
            cell2.setCellStyle(dataStyle);
            rowIndex++;
        }
        return rowIndex;
    }
}
