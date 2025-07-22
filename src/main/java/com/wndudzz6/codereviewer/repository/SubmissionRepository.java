package com.wndudzz6.codereviewer.repository;

import com.wndudzz6.codereviewer.domain.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission,Long> {
    List<Submission> findAllByUserId(Long userId);
}
