package com.wndudzz6.codereviewer.service;

import com.wndudzz6.codereviewer.domain.Submission;
import com.wndudzz6.codereviewer.dto.SubmissionRequest;

import java.util.List;

public interface SubmissionService {
    Submission submit(Long userId, SubmissionRequest request);
    Submission findById(Long id);
    List<Submission> getSubmissionByUserId(Long userId);
}
