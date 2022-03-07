package com.example.verificationdemo.type;

import java.io.Serializable;

public class AnswerSessionMap implements Serializable {
    private double answer;
    private double scale;
    public AnswerSessionMap(double answer, double scale){
        this.answer = answer;
        this.scale = scale;
    }

    public double getAnswer() {
        return answer;
    }

    public double getScale() {
        return scale;
    }
}
