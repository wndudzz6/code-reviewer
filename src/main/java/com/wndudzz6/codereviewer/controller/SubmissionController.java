package com.wndudzz6.codereviewer.controller;

import com.wndudzz6.codereviewer.domain.Submission;
import com.wndudzz6.codereviewer.dto.SubmissionRequest;
import com.wndudzz6.codereviewer.dto.SubmissionResponse;
import com.wndudzz6.codereviewer.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping
    public ResponseEntity<Submission> submit(
            @RequestBody SubmissionRequest request,
            @RequestParam Long userId) {
        Submission saved = submissionService.submit(userId, request);
        return ResponseEntity.ok().body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubmissionResponse> getSubmission(@PathVariable Long id){
        Submission submission = submissionService.findById(id);
        return  ResponseEntity.ok(SubmissionResponse.from(submission));
    }
}
