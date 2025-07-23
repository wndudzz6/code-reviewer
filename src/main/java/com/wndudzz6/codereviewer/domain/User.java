package com.wndudzz6.codereviewer.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private String nickname;

    //사용자 -> 제출목록 1:N
    //mappedBy = "user" -> Submission 클래스의 필드명
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Submission> submissions;

    public void updateNickname(String newNickname) {
        this.nickname = newNickname;
    }

}
