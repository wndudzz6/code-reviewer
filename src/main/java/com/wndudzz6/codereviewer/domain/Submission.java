package com.wndudzz6.codereviewer.domain;

//제출 목록

import com.wndudzz6.codereviewer.domain.platform.Platform;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Submission {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String problemUrl;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Lob
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "submission", cascade = CascadeType.ALL)
    private Review review;

    private LocalDateTime submittedAt;

    public void setReview(Review review) {
        this.review = review;
    }

}
