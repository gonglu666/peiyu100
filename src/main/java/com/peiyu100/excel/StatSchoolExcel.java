package com.peiyu100.excel;

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

public class StatSchoolExcel {
    public static void main(String[] args) throws Exception{
        StatSchool statSchool = new StatSchool();
        statSchool.setSchoolName("陪育小学");
        statSchool.setNum(2);

        StatSchool statSchool2 = new StatSchool();
        statSchool2.setSchoolName("陪育小学3");
        statSchool2.setNum(3);

        List<StatSchool> list = new ArrayList<>();
        list.add(statSchool);
        list.add(statSchool2);




        report(list);
    }


    public static void report(List<StatSchool> list) throws Exception{
        String fileName = "按学校统计";

        File f = new File("e:/data/"+fileName+".xlsx");
        File f1 = new File("e:/data");
        if(!f1.exists()){
            f1.mkdirs();
        }
//        if(!f.exists()){
//            f.createNewFile();
//        }
        FileOutputStream out = new FileOutputStream(f);
        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            XSSFSheet sheet = wb.createSheet(fileName);
            int rowIndex = 0;
            List<String> titles = new ArrayList<>();
            titles.add("学校");
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



    private static int writeRowsToExcel(XSSFWorkbook wb, Sheet sheet, List<StatSchool>  rows, int rowIndex) {
        Font dataFont = wb.createFont();
        dataFont.setFontName("simsun");
        // dataFont.setFontHeightInPoints((short) 14);
        dataFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle dataStyle = wb.createCellStyle();
        dataStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        dataStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        dataStyle.setFont(dataFont);
        ExcelUtil.setBorder(dataStyle, BorderStyle.THIN, new XSSFColor(new java.awt.Color(0, 0, 0)));

        for (StatSchool statSchool : rows) {
            Row dataRow = sheet.createRow(rowIndex);
            //学校
            Cell cell0 = dataRow.createCell(0);
            cell0.setCellValue(statSchool.getSchoolName());
            cell0.setCellStyle(dataStyle);
            //数量
            Cell cell1 = dataRow.createCell(1);
            cell1.setCellValue(statSchool.getNum());
            cell1.setCellStyle(dataStyle);
            rowIndex++;
        }
        return rowIndex;
    }
}
