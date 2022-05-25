package cn.patest.captchafinal;

import cn.patest.imageUtils.AnswerCreateUtil;
import cn.patest.imageUtils.QuestionCreateUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"cn.patest.verificationdemo"})
public class CaptchaFinalApplication {

    public static void main(String[] args) {
        QuestionCreateUtil.main(args);
        AnswerCreateUtil.main(args);
        SpringApplication.run(CaptchaFinalApplication.class, args);
    }

}
