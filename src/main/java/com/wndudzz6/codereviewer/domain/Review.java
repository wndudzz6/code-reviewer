package com.wndudzz6.codereviewer.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String summary;
    private String strategy;
    private String codeQuality;
    private String improvement;
    private String timeComplexity;
    private String difficulty;


    @ElementCollection
    private List<String> tags;

    @OneToOne
    @JoinColumn(name = "submission_id")
    private Submission submission;

    private LocalDateTime reviewedAt;
}
