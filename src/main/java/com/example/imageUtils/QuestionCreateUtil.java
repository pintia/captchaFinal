package com.example.imageUtils;

import org.springframework.util.ClassUtils;

public class QuestionCreateUtil {
    public static String getRandomCode() {
        int one = (int)(Math.random() * 90) + 1;
        int two = (int)(Math.random() * (98 - one)) + 1;
        return one + " + " + two + " = ";
    }
    public static void main(String[] args) {
        boolean createFlag = true;
        int count = 0;
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        while (createFlag && count < 10){
            createFlag = ImageUtils.create(280, 160, 25, getRandomCode(), true, path + "static/image/Question/", 0.4, 80, true);
            count++;
        }
        System.out.println(count);
    }
}