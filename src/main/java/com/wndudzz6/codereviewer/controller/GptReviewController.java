package com.wndudzz6.codereviewer.controller;

import com.wndudzz6.codereviewer.dto.ReviewRequest;
import com.wndudzz6.codereviewer.dto.SubmissionRequest;
import com.wndudzz6.codereviewer.service.AICodeReviewerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gpt")
@RequiredArgsConstructor
public class GptReviewController {

    private final AICodeReviewerService aiCodeReviewerService;

    @PostMapping("/review")
    public ResponseEntity<ReviewRequest> review(@RequestBody SubmissionRequest request) {
        ReviewRequest result = aiCodeReviewerService.review(request);
        return ResponseEntity.ok(result);
    }
}
