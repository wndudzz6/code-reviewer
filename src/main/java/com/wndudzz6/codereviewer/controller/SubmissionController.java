package com.wndudzz6.codereviewer.controller;

import com.wndudzz6.codereviewer.domain.Submission;
import com.wndudzz6.codereviewer.dto.SubmissionRequest;
import com.wndudzz6.codereviewer.dto.SubmissionResponse;
import com.wndudzz6.codereviewer.service.AICodeReviewerService;
import com.wndudzz6.codereviewer.service.GptCodeReviewerService;
import com.wndudzz6.codereviewer.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final AICodeReviewerService aiCodeReviewerService;
    private final SubmissionService submissionService;

    @PostMapping
    public ResponseEntity<SubmissionResponse> submit(@RequestParam Long userId, @RequestBody Map<String, String> body) {
        String code = body.get("code");
        Submission saved = aiCodeReviewerService.createSubmissionWithReview(code, userId);
        return ResponseEntity.ok(SubmissionResponse.from(saved));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SubmissionResponse>> getSubmissionsByUser(@PathVariable Long userId) {
        List<SubmissionResponse> submissions = submissionService.findSubmissionListByUserId(userId)
                .stream()
                .map(SubmissionResponse::from)
                .toList();
        return ResponseEntity.ok(submissions);
    }
}

