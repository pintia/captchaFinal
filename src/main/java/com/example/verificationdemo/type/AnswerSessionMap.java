package com.example.verificationdemo.type;

import java.io.Serializable;

public class AnswerSessionMap implements Serializable {
    private double answer1;
    private double answer2;
    private double scale;
    public AnswerSessionMap(double answer1, double answer2, double scale){
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.scale = scale;
    }

    public double getAnswer1() {
        return answer1;
    }

    public double getAnswer2() {
        return answer2;
    }

    public double getScale() {
        return scale;
    }
}
