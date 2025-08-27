package org.example.util;

import org.apache.poi.ss.usermodel.*;
import org.example.model.StudentEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.service.StudentService;

import java.io.FileOutputStream;


/*
- creează un .xlsx (XSSFWorkbook)
- adaugă un sheet
- scrie un header
- scrie un rand
- scrie toate randurile
- formatează data //todo
- salvează fișierul pe disc
- export
*/

public class ExcelExporter {

    private static Workbook createWorkbook(){
        return new XSSFWorkbook();
    }

    private static Sheet createSheet(Workbook wb, String name) {
        return wb.createSheet(name);
    }
    private static void writeHeader(Sheet sheet){
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Email");
        header.createCell(3).setCellValue("Age");
        header.createCell(4).setCellValue("Enrollment Date");
        header.createCell(5).setCellValue("Grade");
    }

    private static void writeRow(Sheet sheet, int rowIndex, StudentEntity student){
        Row row = sheet.createRow(rowIndex);

        row.createCell(0).setCellValue(student.getId());
        row.createCell(1).setCellValue(student.getName());
        row.createCell(2).setCellValue(student.getEmail());
        row.createCell(3).setCellValue(student.getAge());
//        row.createCell(4).setCellValue(student.getEnrollment_date());
        row.createCell(4).setCellValue(
                student.getEnrollment_date() != null ? student.getEnrollment_date().toString() : ""
        );

        row.createCell(5).setCellValue(student.getGrade());
    }

    private static void writeRows(Sheet sheet, List<StudentEntity> studentEntityList){
        int row = 1;
        for (StudentEntity student : studentEntityList){
            writeRow(sheet, row, student);
            row++;
        }
    }

    private static void saveWorkbook(Workbook wb, Path outputPath) throws IOException {
        if (outputPath.getParent() != null) {
            Files.createDirectories(outputPath.getParent());
        }
        try (wb; FileOutputStream fos = new FileOutputStream(outputPath.toFile())) {
            wb.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createFinalWorkbook(String name, List<StudentEntity> studentEntityList, Path outputPath){
        Workbook wb = createWorkbook();
        Sheet sheet = createSheet(wb, name);
        writeHeader(sheet);
        writeRows(sheet,studentEntityList);
        try {
            saveWorkbook(wb, outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportAllStudents(List<StudentEntity> studentEntityList, Path outputPath){
        createFinalWorkbook("allStudents", studentEntityList,outputPath);
    }

    public static void exportStudentsWithMinGrade(List<StudentEntity> studentEntityList, double grade, Path outputPath, StudentService studentService){
        List<StudentEntity> sortedByMinGrade = studentService.filterStudentsByMinGrade(studentEntityList, grade);
        createFinalWorkbook("studentsWithMinGradeX", sortedByMinGrade,outputPath);
    }



}
