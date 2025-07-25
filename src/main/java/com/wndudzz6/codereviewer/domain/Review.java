package com.wndudzz6.codereviewer.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wndudzz6.codereviewer.domain.platform.Difficulty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Builder @Setter
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

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    //BoJ or Programmers


    @ElementCollection
    private List<String> tags;

    @OneToOne
    @JoinColumn(name = "submission_id")
    @JsonIgnore //순환참조 방지
    private Submission submission;

    private LocalDateTime reviewedAt;

//    // Review.java
//    @PrePersist
//    protected void onCreate() {
//        this.reviewedAt = LocalDateTime.now();
//    }

}
