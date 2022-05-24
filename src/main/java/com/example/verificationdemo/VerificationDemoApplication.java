package com.example.verificationdemo;

import com.example.imageUtils.AnswerCreateUtil;
import com.example.imageUtils.QuestionCreateUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.verificationdemo"})
public class VerificationDemoApplication {

    public static void main(String[] args) {
        QuestionCreateUtil.main(args);
        AnswerCreateUtil.main(args);
        SpringApplication.run(VerificationDemoApplication.class, args);
    }

}
