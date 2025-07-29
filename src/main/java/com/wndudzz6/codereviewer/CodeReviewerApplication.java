package com.wndudzz6.codereviewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.wndudzz6.codereviewer.repository")
public class CodeReviewerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeReviewerApplication.class, args);
    }

}
