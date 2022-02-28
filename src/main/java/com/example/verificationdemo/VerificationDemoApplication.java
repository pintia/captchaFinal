package com.example.verificationdemo;

import com.example.imageUtils.AnswerCreateUtil;
import com.example.imageUtils.QuestionCreateUtil;
import com.example.verificationdemo.FileTemplates.FileTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication(scanBasePackages = {"com.example.verificationdemo"})
public class VerificationDemoApplication {

    public static void main(String[] args) {
        QuestionCreateUtil.main(args);
        AnswerCreateUtil.main(args);
        SpringApplication.run(VerificationDemoApplication.class, args);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    FileTemplate.changeJsScale();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(task, 0, 500);
    }

}
