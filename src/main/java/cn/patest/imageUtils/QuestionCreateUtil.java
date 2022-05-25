package cn.patest.imageUtils;

import org.springframework.util.ClassUtils;

import java.awt.*;

public class QuestionCreateUtil {
    static Color foreColor = new Color(255,255,255);
    static Color lineColor = null;
    static Color backColor = new Color(30, 60, 110);
    static int QuestionWidth = 280;
    static int QuestionHeight = 160;

    public static String getRandomCode() {
        int one = (int)(Math.random() * 90) + 1;
        int two = (int)(Math.random() * (98 - one)) + 1;
        int answer = one + two;
        return one + " + " + two + " = " + "  " + answer%10;
    }
    public static void main(String[] args) {
        boolean createFlag = true;
        int count = 0;
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        int fsize = 52;
        while (createFlag && count < 10){
            createFlag = ImageUtils.create(QuestionWidth, QuestionHeight, 10, getRandomCode(), true, path + "static/image/Question/", fsize, 10, false, backColor, lineColor, foreColor);
            count++;
        }
        //System.out.println(count);
    }
}