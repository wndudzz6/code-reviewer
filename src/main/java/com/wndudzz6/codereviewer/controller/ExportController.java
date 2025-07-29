package com.wndudzz6.codereviewer.controller;

import com.wndudzz6.codereviewer.dto.ReviewSubmissionDTO;
import com.wndudzz6.codereviewer.service.ExelExportService;
import com.wndudzz6.codereviewer.service.ReviewSubmissionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/export")
public class ExportController {

    private final ReviewSubmissionQueryService reviewSubmissionQueryService;
    private final ExelExportService exelExportService;

    @GetMapping("/excel")
    public ResponseEntity<InputStreamResource> exportToExcel() {
        try {
            List<ReviewSubmissionDTO> data = reviewSubmissionQueryService.getAll();
            ByteArrayOutputStream excelStream = exelExportService.exportDataToStream(data);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(excelStream.toByteArray());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=submission_reviews.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(inputStream));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    //제출Id로 Exel파일 Export
    @GetMapping("/excel/{submissionId:[0-9]+}")
    public ResponseEntity<InputStreamResource> exportSingleSubmissionToExcel(@PathVariable Long submissionId) {
        try {
            ReviewSubmissionDTO dto = reviewSubmissionQueryService.getBySubmissionId(submissionId);
            ByteArrayOutputStream excelStream = exelExportService.exportDataToStream(List.of(dto)); // 1개만 리스트로 감싸서 넘김
            ByteArrayInputStream inputStream = new ByteArrayInputStream(excelStream.toByteArray());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=submission_" + submissionId + ".xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(inputStream));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 특정 유저의 전체 제출을 엑셀 export
    @GetMapping("/excel/user/{userId}")
    public ResponseEntity<InputStreamResource> exportUserSubmissionsToExcel(@PathVariable Long userId) {
        try {
            List<ReviewSubmissionDTO> dtoList = reviewSubmissionQueryService.getExelListByUserId(userId);
            if (dtoList.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            ByteArrayOutputStream excelStream = exelExportService.exportDataToStream(dtoList);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(excelStream.toByteArray());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=user_" + userId + "_submissions.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(inputStream));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    @PostMapping("/excel/batch-export")
    public ResponseEntity<InputStreamResource> exportSelectedSubmissions(@RequestBody List<Long> submissionIds) {
        try {
            List<ReviewSubmissionDTO> dtoList = submissionIds.stream()
                    .map(reviewSubmissionQueryService::getBySubmissionId)
                    .toList();

            ByteArrayOutputStream excelStream = exelExportService.exportDataToStream(dtoList);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(excelStream.toByteArray());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=selected_submissions.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(inputStream));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


}