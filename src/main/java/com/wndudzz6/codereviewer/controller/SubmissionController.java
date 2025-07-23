package com.wndudzz6.codereviewer.controller;

import com.wndudzz6.codereviewer.domain.Submission;
import com.wndudzz6.codereviewer.dto.SubmissionRequest;
import com.wndudzz6.codereviewer.dto.SubmissionResponse;
import com.wndudzz6.codereviewer.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //유저 전체 제출 목록
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SubmissionResponse>> getSubmissionsByUser(@PathVariable Long userId) {
        List<SubmissionResponse> submissions = submissionService.findSubmissionListByUserId(userId)
                .stream()
                .map(SubmissionResponse::from)
                .toList();
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/user/{userId}/submissions")
    public ResponseEntity<List<SubmissionResponse>> getSubmissionsByUserId(@PathVariable Long userId) {
        List<Submission> submissions = submissionService.findSubmissionListByUserId(userId);
        return ResponseEntity.ok(submissions.stream().map(SubmissionResponse::from).toList());
    }

}
