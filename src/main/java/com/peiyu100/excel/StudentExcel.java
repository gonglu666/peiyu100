package com.peiyu100.excel;

import com.peiyu100.model.DStudent;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class StudentExcel {
    public static void main(String[] args) throws Exception{
        DStudent dStudent = new DStudent();
        dStudent.setSchoolName("陪育小学");
        dStudent.setClasses("一年级2班");
        dStudent.setName("龙大琳");
        dStudent.setFatherPhone("1");
        dStudent.setMotherPhone("2");
        dStudent.setParentPhone("3");

        DStudent dStudent1 = new DStudent();
        dStudent1.setSchoolName("陪育小学");
        dStudent1.setClasses("一年级2班");
        dStudent1.setName("龙大琳2");
        dStudent1.setFatherPhone("11");
        dStudent1.setMotherPhone("21");
        dStudent1.setParentPhone("31");

        List<DStudent> list = new ArrayList<>();
        list.add(dStudent);
        list.add(dStudent1);


        String fileName = "陪育小学";

        report(fileName,list);
    }


    public static void report(String fileName,List<DStudent> list) throws Exception{
        File f = new File("e:/data/"+fileName+".xlsx");
        FileOutputStream out = new FileOutputStream(f);
        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            XSSFSheet sheet = wb.createSheet(fileName);
            int rowIndex = 0;
            List<String> titles = new ArrayList<>();
            titles.add("学校");
            titles.add("班级");
            titles.add("学生");
            titles.add("学生父亲联系电话");
            titles.add("学生母亲联系电话");
            titles.add("学生家长联系电话");
            rowIndex = ExcelUtil.writeTitlesToExcel(wb, sheet, titles);
            writeRowsToExcel(wb, sheet, list, rowIndex);
            ExcelUtil.autoSizeColumns(sheet, titles.size() + 1);

            wb.write(out);
        } finally {
            wb.close();
        }
        out.close();
    }


    private static int writeRowsToExcel(XSSFWorkbook wb, Sheet sheet, List<DStudent>  rows, int rowIndex) {
        Font dataFont = wb.createFont();
        dataFont.setFontName("simsun");
        // dataFont.setFontHeightInPoints((short) 14);
        dataFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle dataStyle = wb.createCellStyle();
        dataStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        dataStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        dataStyle.setFont(dataFont);
        ExcelUtil.setBorder(dataStyle, BorderStyle.THIN, new XSSFColor(new java.awt.Color(0, 0, 0)));

        for (DStudent dStudent : rows) {
            Row dataRow = sheet.createRow(rowIndex);
            //学校
            Cell cell0 = dataRow.createCell(0);
            cell0.setCellValue(dStudent.getSchoolName());
            cell0.setCellStyle(dataStyle);
            //班级
            Cell cell1 = dataRow.createCell(1);
            cell1.setCellValue(dStudent.getClasses());
            cell1.setCellStyle(dataStyle);
            //姓名
            Cell cell2 = dataRow.createCell(2);
            cell2.setCellValue(dStudent.getName());
            cell2.setCellStyle(dataStyle);
            //父亲电话
            if(!StringUtils.isEmpty(dStudent.getFatherPhone())){
                Cell cell3 = dataRow.createCell(3);
                cell3.setCellValue(dStudent.getFatherPhone());
                cell3.setCellStyle(dataStyle);
            }

            //母亲电话
            if(!StringUtils.isEmpty(dStudent.getMotherPhone())) {
                Cell cell4 = dataRow.createCell(4);
                cell4.setCellValue(dStudent.getMotherPhone());
                cell4.setCellStyle(dataStyle);
            }
            //家长电话
            if(!StringUtils.isEmpty(dStudent.getParentPhone())) {
                Cell cell5 = dataRow.createCell(5);
                cell5.setCellValue(dStudent.getParentPhone());
                cell5.setCellStyle(dataStyle);
            }
            rowIndex++;
        }
        return rowIndex;
    }
}
