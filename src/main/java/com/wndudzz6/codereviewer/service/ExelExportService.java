package com.wndudzz6.codereviewer.service;

import com.wndudzz6.codereviewer.dto.ReviewSubmissionDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExelExportService implements ExportService {
    public ByteArrayOutputStream exportDataToStream(List<ReviewSubmissionDTO> data) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Review Submission");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Submission ID");
        header.createCell(1).setCellValue("Title");
        header.createCell(2).setCellValue("Problem URL");
        header.createCell(3).setCellValue("Code");
        header.createCell(4).setCellValue("Language");
        header.createCell(5).setCellValue("Platform");
        header.createCell(6).setCellValue("Summary");
        header.createCell(7).setCellValue("Strategy");
        header.createCell(8).setCellValue("Code Quality");
        header.createCell(9).setCellValue("Improvement");
        header.createCell(10).setCellValue("Time Complexity");
        header.createCell(11).setCellValue("Difficulty");
        header.createCell(12).setCellValue("Tags");
        header.createCell(13).setCellValue("Reviewed At");

        int rowIndex = 1;
        for (ReviewSubmissionDTO dto : data) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(dto.getSubmissionId());
            row.createCell(1).setCellValue(dto.getTitle());
            row.createCell(2).setCellValue(dto.getProblemUrl());
            row.createCell(3).setCellValue(dto.getCode());
            row.createCell(4).setCellValue(dto.getLanguage());
            row.createCell(5).setCellValue(dto.getPlatform());
            row.createCell(6).setCellValue(dto.getSummary());
            row.createCell(7).setCellValue(dto.getStrategy());
            row.createCell(8).setCellValue(dto.getCodeQuality());
            row.createCell(9).setCellValue(dto.getImprovement());
            row.createCell(10).setCellValue(dto.getTimeComplexity());
            row.createCell(11).setCellValue(dto.getDifficulty());
            row.createCell(12).setCellValue(String.join(", ", dto.getTags()));
            row.createCell(13).setCellValue(dto.getReviewedAt().toString());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return out;
    }

}
