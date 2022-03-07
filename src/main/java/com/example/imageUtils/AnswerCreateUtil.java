package com.example.imageUtils;

import org.springframework.util.ClassUtils;

import java.awt.*;
import java.util.Random;

public class AnswerCreateUtil {
    private static Random rand = new Random();
    public static int answerWidth = 400;
    static Color foreColor = new Color(255,255,255);
    static Color lineColor = null;
    static Color backColor = new Color(30, 60, 110);

    public static void swap(int[] a, int i, int j){
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void shuffle(int[] arr) {
        int length = arr.length;
        for ( int i = length; i > 0; i-- ){
            int randInd = rand.nextInt(i);
            swap(arr, randInd, i - 1);
        }
    }

    public static String getRandomCode() {
        int[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        shuffle(arr);
        StringBuffer result = new StringBuffer();
        int begin = (int)(Math.random() * 3);
        for(int i = begin;i < 10; i++){
            result.append(arr[i]);
        }
        return result.toString();
    }

    public static String getRandomCodeStaticLength() {
        int[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        shuffle(arr);
        StringBuffer result = new StringBuffer();
        for(int i = 0;i < 10; i++){
            result.append(arr[i]);
        }
        return result.toString();
    }

    public static void main(String[] args) {
        boolean createFlag = true;
        int count = 0;
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        int fsize = 52;
        while (createFlag && count < 10){
            createFlag = ImageUtils.create(answerWidth, 40, 5, getRandomCodeStaticLength(), false, path + "static/image/Answer/", fsize, 0, false, backColor, lineColor, foreColor);
            count++;
        }
        System.out.println(count);
    }
}
